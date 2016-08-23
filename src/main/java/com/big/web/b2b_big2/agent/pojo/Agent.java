package com.big.web.b2b_big2.agent.pojo;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.finance.cart.pojo.Cart;
import com.big.web.b2b_big2.finance.cart.pojo.FlightCart;
import com.big.web.b2b_big2.finance.cart.pojo.HotelCart;
import com.big.web.b2b_big2.finance.cart.pojo.RentCarCart;
import com.big.web.b2b_big2.util.Rupiah;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.model.UserAccountVO;

@Component
@Scope(value="session")
public class Agent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7397690052461537074L;
	private String id;
	private String name;
	private String email;
	private String balanceIdr;
	private String balanceUsd;
	private PackageType packageType;
	private String logoFileName;
	private String travelName;
	private String userName;
	private Long userId;
	
	private Cart cart;
	
	public void reset() {
		setId(null);
		setName(null);
		setEmail(null);
		setBalanceIdr(null);
		setBalanceUsd(null);
		setPackageType(null);
		setLogoFileName(null);
		setTravelName(null);
		setUserName(null);
		setUserId(null);
		setCart(null);

	}
	public void loadFromModel(AgentVO agentVO, UserAccountVO accountIDR, UserAccountVO accountUSD){
		setId(agentVO.getId());
		setName(agentVO.getAgent_Name());
		setEmail(agentVO.getEmail1());
		setLogoFileName(agentVO.getLogoId());
		setUserName(agentVO.getUserLogin().getUsername());
		setUserId(agentVO.getUserLogin().getId());
		setPackageType(PackageType.get(agentVO.getPackage_code()));
		setTravelName(Utils.capitalizeWords(agentVO.getAgent_Name()));
		
		setBalanceIdr(accountIDR == null ? "0" : Rupiah.format(accountIDR.getBalance() == null ? 0 : accountIDR.getBalance().doubleValue(), false));
		setBalanceUsd(accountUSD == null ? "0" : Rupiah.format(accountUSD.getBalance() == null ? 0 : accountUSD.getBalance().doubleValue(), false));
		
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getBalanceIdr() {
		return balanceIdr;
	}
	public void setBalanceIdr(String balanceIdr) {
		this.balanceIdr = balanceIdr;
	}
	public String getBalanceUsd() {
		return balanceUsd;
	}
	public void setBalanceUsd(String balanceUsd) {
		this.balanceUsd = balanceUsd;
	}
	public PackageType getPackageType() {
		return packageType;
	}
	public void setPackageType(PackageType packageType) {
		this.packageType = packageType;
	}
	public String getLogoFileName() {
		return logoFileName;
	}
	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	public String getTravelName() {
		return travelName;
	}
	public void setTravelName(String travelName) {
		this.travelName = travelName;
	}
	public Cart getCart() {
		if (cart == null) cart = new Cart();		//!bahaya dangerous
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public FlightCart getFlightCart(){
		return getCart().getFlightCart();
	}
	public HotelCart getHotelCart(){
		return getCart().getHotelCart();
	}
	public RentCarCart getRentCarCart(){
		return getCart().getRentCarCart();
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Agent [id=" + id + ", name=" + name + ", email=" + email + ", balanceIdr=" + balanceIdr
				+ ", balanceUsd=" + balanceUsd + ", packageType=" + packageType + ", logoFileName=" + logoFileName
				+ ", travelName=" + travelName + ", userName=" + userName + ", userId=" + userId + ", cart=" + cart
				+ "]";
	}
}
