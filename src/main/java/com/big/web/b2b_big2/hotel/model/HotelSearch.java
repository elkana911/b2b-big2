package com.big.web.b2b_big2.hotel.model;

import java.util.Date;

/*dipakai utk pencarian saja utk speed.jadi tdk dipakai utk entity suatu tabel*/
public class HotelSearch {
	private String room_id;
	private String hotel_id;
	private String hotelName;
	private String address;
	private String longitude;
	private String lattitude;
	private String city;
	private String star;
	private Date checkInDate;
	private String availRoom;
	private String roomType;
	private String price;
	private String urlThumb;
	private String brevity;
	private String review;
	private String roomImage;
	
	public String getHotel_id() {
		return hotel_id;
	}
	public void setHotel_id(String hotel_id) {
		this.hotel_id = hotel_id;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getAvailRoom() {
		return availRoom;
	}
	public void setAvailRoom(String availRoom) {
		this.availRoom = availRoom;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLattitude() {
		return lattitude;
	}
	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}
	public String getUrlThumb() {
		return urlThumb;
	}
	public void setUrlThumb(String urlThumb) {
		this.urlThumb = urlThumb;
	}
	public String getBrevity() {
		return brevity;
	}
	public void setBrevity(String brevity) {
		this.brevity = brevity;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}

	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	public String getRoomImage() {
		return roomImage;
	}
	public void setRoomImage(String roomImage) {
		this.roomImage = roomImage;
	}
	@Override
	public String toString() {
		return "HotelBasic [room_id=" + room_id + ", hotel_id=" + hotel_id
				+ ", hotelName=" + hotelName + ", address=" + address
				+ ", longitude=" + longitude + ", lattitude=" + lattitude
				+ ", city=" + city + ", star=" + star + ", price=" + price
				+ ", urlThumb=" + urlThumb + ", brevity=" + brevity
				+ ", review=" + review + "]";
	}
	
	
}
