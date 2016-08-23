package com.big.web.b2b_big2.flight.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.big.web.b2b_big2.flight.pojo.FareInfo;

@MappedSuperclass	//sebagai penanda kalau dipakai oleh banyak table seperti kalstar,trigana dan avia
public abstract class AbstractFlightClassFare{

	private Long class_fare_id;
	private String flightavailid;
	private String class_name;
	private BigDecimal class_avail_seat;
	private BigDecimal class_rate;		//biasa utk ditampilkan di web utk di compare dgn travel lain
	private BigDecimal basic_rate;		//diisi jika ada detilnya
	private BigDecimal tax;
	private BigDecimal iwjr;
	private BigDecimal agent_discount;	//misal harga tiket 250rb, nta 200rb, agent_discount=50rb
	private BigDecimal extra_cover;
	private BigDecimal nta;
	private BigDecimal surcharge_fee;
	private BigDecimal insentif;
	private BigDecimal total;
	private String radio_id;
	private BigDecimal class_sequence;
	private Date lastUpdate;
	
	private BigDecimal psc;	//passenger service charge. kalstar use this as airporttax, citilink use psc
	private BigDecimal chd_fare;
	private BigDecimal inf_fare;

	/*
    @SequenceGenerator(name = "ID_GENERATOR", sequenceName = "admin_id_seq")
    @GeneratedValue(generator = "ID_GENERATOR")
    */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="STUDENTS_SEQ")
//	@SequenceGenerator(name="STUDENTS_SEQ", sequenceName="STUDENTS_SEQ")
	public Long getClass_fare_id() {
		return class_fare_id;
	}
	public void setClass_fare_id(Long class_fare_id) {
		this.class_fare_id = class_fare_id;
	}
	
	@Column(length=40)
	public String getFlightavailid() {
		return flightavailid;
	}
	public void setFlightavailid(String flightavailid) {
		this.flightavailid = flightavailid;
	}
	
	@Column(length=1)
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public BigDecimal getClass_avail_seat() {
		return class_avail_seat;
	}
	public void setClass_avail_seat(BigDecimal class_avail_seat) {
		this.class_avail_seat = class_avail_seat;
	}
	public BigDecimal getClass_rate() {
		return class_rate;
	}
	public void setClass_rate(BigDecimal class_rate) {
		this.class_rate = class_rate;
	}
	public BigDecimal getBasic_rate() {
		return basic_rate;
	}
	public void setBasic_rate(BigDecimal basic_rate) {
		this.basic_rate = basic_rate;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getIwjr() {
		return iwjr;
	}
	public void setIwjr(BigDecimal iwjr) {
		this.iwjr = iwjr;
	}
	public BigDecimal getAgent_discount() {
		return agent_discount;
	}
	public void setAgent_discount(BigDecimal agent_discount) {
		this.agent_discount = agent_discount;
	}
	public BigDecimal getExtra_cover() {
		return extra_cover;
	}
	public void setExtra_cover(BigDecimal extra_cover) {
		this.extra_cover = extra_cover;
	}
	public BigDecimal getNta() {
		return nta;
	}
	public void setNta(BigDecimal nta) {
		this.nta = nta;
	}
	public BigDecimal getSurcharge_fee() {
		return surcharge_fee;
	}
	public void setSurcharge_fee(BigDecimal surcharge_fee) {
		this.surcharge_fee = surcharge_fee;
	}
	public BigDecimal getInsentif() {
		return insentif;
	}
	public void setInsentif(BigDecimal insentif) {
		this.insentif = insentif;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	@Column(length=255)
	public String getRadio_id() {
		return radio_id;
	}
	public void setRadio_id(String radio_id) {
		this.radio_id = radio_id;
	}
	public BigDecimal getClass_sequence() {
		return class_sequence;
	}
	public void setClass_sequence(BigDecimal class_sequence) {
		this.class_sequence = class_sequence;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public BigDecimal getPsc() {
		return psc;
	}
	public void setPsc(BigDecimal psc) {
		this.psc = psc;
	}
	public BigDecimal getChd_fare() {
		return chd_fare;
	}
	public void setChd_fare(BigDecimal chd_fare) {
		this.chd_fare = chd_fare;
	}
	public BigDecimal getInf_fare() {
		return inf_fare;
	}
	public void setInf_fare(BigDecimal inf_fare) {
		this.inf_fare = inf_fare;
	}

	/**
	 * collect all values from into {@link FareInfo} object.
	 * @return
	 */
	@Transient
	public FareInfo getFareInfo(FareInfo data){
//		FareInfo data = new FareInfo();
		
		data.setId(getFlightavailid());
		data.setRef_ClassFareId(getClass_fare_id());
		
		data.setAgentDiscount(getAgent_discount());
		data.setRadioId(getRadio_id());
		data.setClassName(getClass_name());
		data.setTax_adult(getTax());
		data.setTax_child(getTax());	//defaultnya disamain dengan dewasa
		data.setTax_infant(BigDecimal.ZERO);
		data.setAmount(getTotal());
		data.setFuelSurcharge(getSurcharge_fee());
		data.setPsc(getPsc());
		data.setChildFare(getChd_fare());
		data.setInfantFare(getInf_fare());
		data.setIwjr(getIwjr());

		if (getBasic_rate() != null){
				data.setRate(getBasic_rate());
		}else
			data.setRate(getClass_rate());
		
		int multiplier = 1000;	//multiply
		//fix non thousand rate
		if (data.getRate().toPlainString().length() < 5){
			
			data.setRate(new BigDecimal(multiplier).multiply(data.getRate()));
		}
		
/*
 cant provide further
		data.setAirline(fas.getAirline());		
		AirlineVO vo = airlineDao.findByName(fas.getAirline());
		data.setAirlineIata(vo.getCode());
		data.setAgentId(vo.getAgentId());
		data.setFlightNo(fas.getFlightnum());
		
		AirportVO a = airportDao.getAirportByIata(fas.getDeparture());
		Airport _from = new Airport();
		_from.setCity(a.getCity());
		_from.setName(a.getAirport_name());
		_from.setCountryId(a.getCountry_id());
		_from.setIataCode(a.getIata());
		_from.setSchedule(fas.getDep_time());
		
		AirportVO b = airportDao.getAirportByIata(fas.getArrival());
		Airport _to = new Airport();
		_to.setCity(b.getCity());
		_to.setName(b.getAirport_name());
		_to.setCountryId(b.getCountry_id());
		_to.setIataCode(b.getIata());
		_to.setSchedule(fas.getArr_time());
		
		Route route = new Route(_from, _to);			
		data.setRoute(route);
		
		data.setCurrency(fas.getCurrency());
		*/
		
		return data;
	}
	
}
