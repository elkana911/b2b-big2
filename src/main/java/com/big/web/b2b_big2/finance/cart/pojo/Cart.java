package com.big.web.b2b_big2.finance.cart.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable, ICart{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//permitted to per item only
	private FlightCart flightCart;
	private HotelCart hotelCart;
	private RentCarCart rentCarCart;
	
	//disediakan tapi sementara ga kugunakan utk ticketing
	private List<Item> items;
	
	private Payment payment;
	
	private static Cart _self;
	
	public static final Cart getInstance(){
		if (_self == null)
			_self = new Cart();
		
		return _self;
	}
	
	public Cart(){
		items = new ArrayList<Item>();
		
		flightCart = new FlightCart();
		hotelCart = new HotelCart();
		rentCarCart = new RentCarCart();
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public BigDecimal getAmount(){
		
		BigDecimal sum = BigDecimal.ZERO;
		
		if (items == null) return sum;
		
		for (Item i : items){
			if (i.getPrice() == null){
				sum = sum.add(BigDecimal.ZERO);
				continue;
			}
			
			BigDecimal d = i.getPrice().multiply(new BigDecimal(i.getQuantity()));
			
			sum = sum.add(d);
		}
		
		return
		sum.add(flightCart.getAmount()).add(hotelCart.getAmount()).add(rentCarCart.getAmount());
	}
	
	public void add(Item item){
		
		if (item.getQuantity() < 1)
			item.setQuantity(1);
		
		items.add(item);
	}
	
	public void remove(String uid){
		for (Item i : items){
			if (i.getId().equals(uid)){
				items.remove(i);
				break;
			}
		}
	}

	public void clear(){
		items.clear();
	}
	
	public Item get(String uid){
		for (Item i : items){
			if (i.getId().equals(uid)){
				return i;
			}
		}
		
		return null;
	}
	
	public boolean isEmpty(){
		return items.size() < 1;
	}
	
	public FlightCart getFlightCart() {
		return flightCart;
	}

	public void setFlightCart(FlightCart flightCart) {
		this.flightCart = flightCart;
	}

	public HotelCart getHotelCart() {
		return hotelCart;
	}

	public void setHotelCart(HotelCart hotelCart) {
		this.hotelCart = hotelCart;
	}

	public RentCarCart getRentCarCart() {
		return rentCarCart;
	}

	public void setRentCarCart(RentCarCart rentCarCart) {
		this.rentCarCart = rentCarCart;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public static void main(String[] args) {
		
		Cart cart = Cart.getInstance();
		
		Item item1 = new Item();
		item1.setName("Eric");
		item1.setQuantity(2);
		item1.setPrice(new BigDecimal(444));
		
		Item item2 = new Item();
		item2.setName("Elkana");
		item2.setPrice(new BigDecimal(333));
		
		cart.add(item1);
		cart.add(item2);
		
		BigDecimal total = cart.getAmount();
		System.out.println("total=" + total.doubleValue());
	}
}
