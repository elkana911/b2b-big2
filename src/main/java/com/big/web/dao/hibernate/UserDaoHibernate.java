package com.big.web.dao.hibernate;

import java.util.Date;
import java.util.List;

import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.pojo.ACCOUNT_STATUS;
import com.big.web.dao.UserDao;
import com.big.web.model.User;
import com.big.web.model.UserAccountVO;

/**
 * This class interacts with Hibernate session to save/delete and
 * retrieve User objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *   Modified by <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 *   Extended to implement Acegi UserDetailsService interface by David Carter david@carter.net
 *   Modified by <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> to work with
 *   the new BaseDaoHibernate implementation that uses generics.
 *   Modified by jgarcia (updated to hibernate 4)
*/
@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements UserDao, UserDetailsService {

    /**
     * Constructor that sets the entity to User.class.
     */
    public UserDaoHibernate() {
        super(User.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        Query qry = getSession().createQuery("from User u order by upper(u.username)");
        return qry.list();
    }

    /**
     * {@inheritDoc}
     */
    public User saveUser(User user) {
        if (log.isDebugEnabled()) {
            log.debug("user's id: " + user.getId());
        }
        getSession().saveOrUpdate(user);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getSession().flush();
        return user;
    }

    /**
     * Overridden simply to call the saveUser method. This is happening
     * because saveUser flushes the session and saveObject of BaseDaoHibernate
     * does not.
     *
     * @param user the user to save
     * @return the modified user (with a primary key set if they're new)
     */
    @Override
    public User save(User user) {
        return this.saveUser(user);
    }

    /**
     * {@inheritDoc}
    */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List users = getSession().createCriteria(User.class).add(Restrictions.eq("username", username)).list();
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (UserDetails) users.get(0);
        }
    }

    /**
     * {@inheritDoc}
    */
    public String getUserPassword(Long userId) {
        JdbcTemplate jdbcTemplate =
                new JdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(User.class, Table.class);
        return jdbcTemplate.queryForObject(
                "select password from " + table.name() + " where id=?", String.class, userId);
    }

	@Override
	public User getUserByEmail(String email) {
        List users = getSession().createCriteria(User.class).add(Restrictions.eq("email", email)).list();
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return (User) users.get(0);
        }
	}

	@Override
	public void deleteUser(Long userId) {
		//dont forget to audit 
		
		/**
		 * Unfortunately, audit wont effect on hql or sql. 
		 */
        //aturannya urut
        //1.delete from mst_agents where user_id=8924;
        //2.delete from user_account where user_id=8924;
        //3.delete from user_role where user_id=8924;
        //4.delete from app_user where username='eric';
		/*
		getSession().createSQLQuery("delete from mst_agents where user_id=:userid").setParameter("userid", userId).executeUpdate();
		
		getSession().createSQLQuery("update user_account set status=:status, lastupdate=sysdate where user_id=:userid")
					.setParameter("status", ACCOUNT_STATUS.DELETED.getFlag())
					.setParameter("userid", userId)	//bingung apa ga tabrakan nanti dengan userid yg baru ? tp kayanya ga mungkin userid bisa berulang, toh pelanggan berleha ga mungkin sampe 9juta (max LONG)
					.executeUpdate();
		
		getSession().createSQLQuery("delete from user_role where user_id=:userid").setParameter("userid", userId).executeUpdate();
		getSession().createSQLQuery("delete from app_user where id=:id").setParameter("id", userId).executeUpdate();
		*/
        List agents = getSession().createCriteria(AgentVO.class).add(Restrictions.eq("userLogin.id", userId)).list();
        if (agents == null || agents.isEmpty()){}
        else
        	getSession().delete(agents.get(0));
        
        List accounts = getSession().createCriteria(UserAccountVO.class).add(Restrictions.eq("user.id", userId)).list();
        if (accounts == null || accounts.isEmpty()){}
        else{
        	UserAccountVO account = (UserAccountVO) accounts.get(0);
        	account.setStatus(ACCOUNT_STATUS.DELETED.getFlag());
        	account.setTrx_enabled("0");
        	account.setLastUpdate(new Date());
        	getSession().saveOrUpdate(account);
        }
		
		getSession().createSQLQuery("delete from user_role where user_id=:userid").setParameter("userid", userId).executeUpdate();

        List users = getSession().createCriteria(User.class).add(Restrictions.eq("id", userId)).list();
        if (users == null || users.isEmpty()) {}
        else
        	getSession().delete(users.get(0));
        	
	}
}
