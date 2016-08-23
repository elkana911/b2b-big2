package com.big.web.b2b_big2.agent.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.util.Version;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.agent.dao.IAgentDao;
import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.model.Whole_SalerVO;
import com.big.web.b2b_big2.agent.pojo.ACCOUNT_STATUS;
import com.big.web.b2b_big2.finance.bank.model.CIMBVANCounterVO;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.dao.SearchException;
import com.big.web.hibernate.BasicHibernate;
import com.big.web.model.User;

@Repository("agentDao")
public class AgentDaoHibernate extends BasicHibernate implements IAgentDao {

	@Override
	public void update(AgentVO agent) {
		getSession().merge(agent);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentVO> getAgents() {
		Criteria criteria = getSession().createCriteria(AgentVO.class);
		criteria.add(Restrictions.isNull("aff_id"));
		
		return criteria.list();
	}

	@Override
	public List<AgentVO> getSubAgents() {
		Criteria criteria = getSession().createCriteria(AgentVO.class);
		criteria.add(Restrictions.isNotNull("aff_id"));
		
		return criteria.list();
	}

	@Override
	public boolean login(String username, String password) {
		Criteria criteria = getSession().createCriteria(AgentVO.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("password", password));
		
		return criteria.list().size() > 0;
	}


	@Override
	public AgentVO getAgentByAgentId(Long agentId) {
		if (agentId == null) return null;
		
		Criteria c = getSession().createCriteria(AgentVO.class);
		
		c.add(Restrictions.eq("agent_id", agentId));
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			AgentVO obj = (AgentVO) iterator.next();

			return obj;
		}
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public AgentVO getAgentByUserId(Long userId) {
		if (userId == null) return null;
		
		Criteria c = getSession().createCriteria(AgentVO.class);
		
		c.add(Restrictions.eq("userLogin.id", userId));
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			AgentVO obj = (AgentVO) iterator.next();

			return obj;
		}
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public AgentVO getAgentByUser(String userName) {
		if (userName == null) return null;

		SQLQuery q = getSession().createSQLQuery("select a.* from mst_agents a where a.user_id=(select id from app_user where lower(username)=lower(:username))");
		q.addEntity("a", AgentVO.class);
		q.setParameter("username", userName);
		
		for (Iterator iterator = q.list().iterator(); iterator.hasNext();) {
			AgentVO obj = (AgentVO) iterator.next();
			
			obj.setAgent_Name(Utils.capitalizeWords(obj.getAgent_Name()));
			return obj;
		}
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User getUser(String userName) {
		Criteria c = getSession().createCriteria(User.class);
		
		c.add(Restrictions.eq("username", userName));

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (User) iterator.next();
		}
		return null;
	}
/*
	@Override
	public void saveVAN(VANGroupVO van) {
		getSession().saveOrUpdate(van);
	}
*/
	@Override
	public void saveVAN(CIMBVANCounterVO van) {
		getSession().saveOrUpdate(van);
	}

	@Override
	public void saveAgent(AgentVO obj) {
		getSession().saveOrUpdate(obj);
	}

	@Override
	public void removeAgent(Long userId) {
		Query q = getSession().createQuery("delete AgentVO where id = " + userId);
		q.executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long getWholeSalerAgentId(String uid) {
		Criteria criteria = getSession().createCriteria(Whole_SalerVO.class);
		criteria.add(Restrictions.eq("id", uid));
		
		for (Iterator iterator = criteria.list().iterator(); iterator.hasNext();) {
			Whole_SalerVO obj = (Whole_SalerVO) iterator.next();
			return obj.getAgent_id();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Whole_SalerVO> getWholeSalers() {
		Criteria criteria = getSession().createCriteria(Whole_SalerVO.class);
		criteria.addOrder(Order.asc("agent_id"));
		
		List<Whole_SalerVO> list = criteria.list();
		
		//update label name
		for (int i = 0; i < list.size(); i++) {
			Whole_SalerVO obj = list.get(i);
			
			AgentVO _agent = getAgentByAgentId(obj.getAgent_id());
			obj.setAgentName(_agent.getAgent_Name());
			
			list.set(i, obj);
		}
		
		return list;
		
	}
	
	@Override
	public Whole_SalerVO getWholeSalerByAgency(Long agent_id) {
		Criteria c = getSession().createCriteria(Whole_SalerVO.class);
		c.add(Restrictions.eq("agent_id", agent_id));
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (Whole_SalerVO) iterator.next();
		}
		
		return null;
	}


	@Override
	public Whole_SalerVO getWholeSaler(Long userId) {
		/*
		-- ambil data user yg role_id = ROLE_MANDIRA_ADMIN & bukan sub-agent
select * from app_user a,user_role u, role r, role_wholesaler rw
where a.id = u.user_id
and a.id not in (select user_id from mst_agents where user_id = a.id)  
and u.role_id = r.id
and rw.role_id = r.id
and r.name = 'ROLE_MANDIRA_ADMIN'

select ws.* from app_user a,user_role u, role r, role_wholesaler rw, whole_saler ws
where a.id = u.user_id
and a.id not in (select user_id from mst_agents where user_id = a.id)  
and u.role_id = r.id
and rw.role_id = r.id
and rw.wholesaler_id = ws.id 
and a.id = 6742
				*/
		StringBuffer sb = new StringBuffer("select ws.* from app_user a,user_role u, role r, role_wholesaler rw, whole_saler ws")
							.append(" where a.id = u.user_id and a.id not in (select user_id from mst_agents where user_id = a.id)")
							.append(" and u.role_id = r.id and rw.role_id = r.id and rw.wholesaler_id = ws.id and a.id = :user_id")
							;
		SQLQuery q = getSession().createSQLQuery(sb.toString());
		q.addEntity(Whole_SalerVO.class);
		
		q.setParameter("user_id", userId);
		
		for (Iterator iterator = q.list().iterator(); iterator.hasNext();) {
			return (Whole_SalerVO) iterator.next();
		}
		
		return null;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public AgentVO getAgent(String uid) {
		Criteria c = getSession().createCriteria(AgentVO.class);
		
		c.add(Restrictions.eq("id", uid));

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (AgentVO) iterator.next();
		}
		
		return null;
	}

	@Override
	public List<AgentVO> searchAll(String searchTerm) {
		Session sess = getSession();
		Class<?> persistentClass = AgentVO.class;
        FullTextSession txtSession = Search.getFullTextSession(sess);

        org.apache.lucene.search.Query qry;
        try {
            qry = com.big.web.dao.hibernate.HibernateSearchTools.generateQuery(searchTerm, persistentClass, sess, new StandardAnalyzer(Version.LUCENE_35));
        } catch (ParseException ex) {
            throw new SearchException(ex);
        }
        org.hibernate.search.FullTextQuery hibQuery = txtSession.createFullTextQuery(qry,
                persistentClass);
        
        return hibQuery.list();
	}
	
	@Override
	public List<AgentVO> searchAgents(String searchTerm) {
        if (searchTerm == null || "".equals(searchTerm.trim())) {
            return getAgents();
        }
        
		Session sess = getSession();
		Class<?> persistentClass = AgentVO.class;
		FullTextSession txtSession = Search.getFullTextSession(sess);
		
		org.apache.lucene.search.Query qry;
		try {
			qry = com.big.web.dao.hibernate.HibernateSearchTools.generateQuery(searchTerm, persistentClass, sess, new StandardAnalyzer(Version.LUCENE_35));
		} catch (ParseException ex) {
			throw new SearchException(ex);
		}
		org.hibernate.search.FullTextQuery hibQuery = txtSession.createFullTextQuery(qry,
				persistentClass);
		Criteria query = sess.createCriteria(persistentClass)
	            .add(Restrictions.isNull("aff_id"));

		hibQuery.setCriteriaQuery(query);
		
		return hibQuery.list();
	}
	
	@Override
	public List<AgentVO> searchSubAgents(String searchTerm) {
        if (searchTerm == null || "".equals(searchTerm.trim())) {
            return getSubAgents();
        }
        
		Session sess = getSession();
		Class<?> persistentClass = AgentVO.class;
		FullTextSession txtSession = Search.getFullTextSession(sess);
		
		org.apache.lucene.search.Query qry;
		try {
			qry = com.big.web.dao.hibernate.HibernateSearchTools.generateQuery(searchTerm, persistentClass, sess, new StandardAnalyzer(Version.LUCENE_35));
		} catch (ParseException ex) {
			throw new SearchException(ex);
		}
		org.hibernate.search.FullTextQuery hibQuery = txtSession.createFullTextQuery(qry,
				persistentClass);
		Criteria query = sess.createCriteria(persistentClass)
	            .add(Restrictions.isNotNull("aff_id"));

		hibQuery.setCriteriaQuery(query);
		
		return hibQuery.list();
	}

	@Override
	public boolean isSubAgent(AgentVO agent) {
		AgentVO obj = getAgent(agent.getId());

		return !Utils.isEmpty(obj.getAff_id());
	}

	@Override
	public String getWholeSalerVA_Code(Long agentId) {
		Criteria criteria = getSession().createCriteria(Whole_SalerVO.class);
		criteria.add(Restrictions.eq("agent_id", agentId));
		
		for (Iterator iterator = criteria.list().iterator(); iterator.hasNext();) {
			Whole_SalerVO obj = (Whole_SalerVO) iterator.next();
			return obj.getVa_code();
		}
		return null;	
		
	}

	@Override
	public List<AgentVO> searchSubAgents(String searchTerm, ACCOUNT_STATUS accountStatus, Whole_SalerVO wholeSaler) {

		/*
select a.* from mst_agents a 
inner join app_user b 
on a.user_id = b.id 
inner join user_account c 
on c.user_id = b.id and a.user_id = b.id
where a.aff_id = (select id from whole_saler where id = '07A5AAE6E3B04926E0535A020A0A8066')		 
		 */
		StringBuffer sb = new StringBuffer("select a.* from mst_agents a inner join app_user b on a.user_id = b.id inner join user_account c on c.user_id = b.id and a.user_id = b.id WHERE 1=1");

		if (!Utils.isEmpty(searchTerm)){
			sb.append(" and UPPER(a.agent_name) like '%' || UPPER(:searchTerm) || '%'" );
			sb.append(" or UPPER(a.email1) like '%' || UPPER(:searchTerm) || '%'" );
		}
		
		//jika wholeSaler = null, ambil semua
		if (wholeSaler != null){
			sb.append(" and a.aff_id = (select id from whole_saler where id = '").append(wholeSaler.getId()).append("')") ;
		}
		
		if (accountStatus != null)			
			sb.append(" and c.status = '").append(accountStatus.getFlag()).append("'");
		
        sb.append(" order by a.agent_name");
        
        SQLQuery q = getSession().createSQLQuery(sb.toString());
        q.addEntity("a", AgentVO.class);
        
		if (!Utils.isEmpty(searchTerm)){
        	q.setParameter("searchTerm", searchTerm);
		}
		
        return q.list();
			
	}

}
