package com.big.web.b2b_big2.agent.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.WordUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.big.web.model.User;

@Indexed
@Entity
@Audited
@Table(name="mst_agents")
public class AgentVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5176841555187484724L;

	private String id;
	private String aff_id;
	
	private Long agent_id;
	private String agent_Name;
	private String address1;
	private String address2;
	private String city;
	private String stateCode;
	private String zipCode;
	private Integer country_id;
	private String website1;
	private String website2;
	private String website3;
	private String phone1;
	private String phone2;
	private String phone3;
	private String fax1;
	private String fax2;
	private String email1;
	private String email2;
	private String npwp;
	private String latitude;
	private String longitude;
	private Date lastUpdate;
	
	//related with app_user.id
	private User userLogin; 

	private Date register;	
	private String bbm_id;
	private String ym_id;
	
	private String inactive;
	private Date inactive_date;

    private String logoId;
	
    private Integer package_code;
    
//    private UserAccountVO userAccount;
    
    private String accountNo;
    
    public AgentVO() {
    	setId(java.util.UUID.randomUUID().toString());
	}
    
    public AgentVO(User user){
    	
    	this();
    	
        setUserLogin(user);
        setAgent_Name(WordUtils.capitalizeFully(user.getAgent().getAgent_Name()));
        setAddress1(user.getAddress().getAddress());
        setCity(user.getAddress().getCity());
        //agent.setCountry_id(country_id);
        setZipCode(user.getAddress().getPostalCode());
        setPhone1(user.getPhoneNumber());
        setPhone2(user.getAgent().getPhone2());
        setFax1(user.getAgent().getFax1());
        setEmail1(user.getEmail());
        setWebsite1(user.getWebsite());
        setYm_id(user.getAgent().getYm_id());
        setBbm_id(user.getAgent().getBbm_id());
        setPackage_code(user.getAgent().getPackage_code());
        setLogoId(user.getAgent().getLogoId());
    	
        setLastUpdate(new Date());
    }
    
    
	public Long getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}
	
	@Field
	@Column(nullable = false, length=40)
	public String getAgent_Name() {
		return agent_Name;
	}
	public void setAgent_Name(String agent_Name) {
		this.agent_Name = agent_Name;
	}
	
	@Column(length = 100)
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(length = 50)
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(length=30)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Column(length=3)
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	@Column(length=15)
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getCountry_id() {
		return country_id;
	}
	public void setCountry_id(Integer country_id) {
		this.country_id = country_id;
	}

	@Column(length=255)
	public String getWebsite1() {
		return website1;
	}
	public void setWebsite1(String website1) {
		this.website1 = website1;
	}
	
	@Column(length=255)
	public String getWebsite2() {
		return website2;
	}

	public void setWebsite2(String website2) {
		this.website2 = website2;
	}

	@Column(length=255)
	public String getWebsite3() {
		return website3;
	}

	public void setWebsite3(String website3) {
		this.website3 = website3;
	}

	@Column(length=20)
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	@Column(length=20)	
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	@Column(length=20)	
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	
	@Column(length=20)
	public String getFax1() {
		return fax1;
	}
	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}

	@Column(length=20)
	public String getFax2() {
		return fax2;
	}
	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}

	@Column(length=100)
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	@Column(length=100)
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	@Column(length=20)
	public String getNpwp() {
		return npwp;
	}
	public void setNpwp(String npwp) {
		this.npwp = npwp;
	}
	@Column(length=20)
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	@Column(length=20)
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@Id
	@XmlTransient
	@JsonIgnore
	@Column(length=40, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * If not null, this agent is a sub agent of its master
	 * @return
	 */
	@XmlTransient
	@JsonIgnore
	@Column(length=40)
	public String getAff_id() {
		return aff_id;
	}
	public void setAff_id(String aff_id) {
		this.aff_id = aff_id;
	}

	public Date getRegister() {
		return register;
	}
	public void setRegister(Date register) {
		this.register = register;
	}
	
	@Column(length=10)
	public String getBbm_id() {
		return bbm_id;
	}
	public void setBbm_id(String bbm_id) {
		this.bbm_id = bbm_id;
	}

	@Column(length=100)
	public String getYm_id() {
		return ym_id;
	}
	public void setYm_id(String ym_id) {
		this.ym_id = ym_id;
	}

	@OneToOne
	@JoinColumn(name="user_id")
	public User getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(User userLogin) {
		this.userLogin = userLogin;
	}
	
	@XmlTransient
	@JsonIgnore
	@Column(length=255)
	public String getLogoId() {
		return logoId;
	}
	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}
	public Integer getPackage_code() {
		return package_code;
	}
	public void setPackage_code(Integer package_code) {
		this.package_code = package_code;
	}

	@Column(length=1)
	public String getInactive() {
		return inactive;
	}

	public void setInactive(String inactive) {
		this.inactive = inactive;
	}

	public Date getInactive_date() {
		return inactive_date;
	}

	public void setInactive_date(Date inactive_date) {
		this.inactive_date = inactive_date;
	}

	@Transient
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/*
	@Transient
	public UserAccountVO getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccountVO userAccount) {
		this.userAccount = userAccount;
	}
	*/
	
}
