package com.big.web.b2b_big2.booking.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Table berisi transaksi sebelum booking dan sesudah booking
 * @author Administrator
 *
 */
@Entity
@Table(name="booking_flight_trans")
public class BookingFlightTransVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8291373695966101959L;	

	private String id;
	
//	aslinya oneToone with BookingFlightVO.id
	private String booking_flight_id;
	private String groupCode;
	
	private BigDecimal basefare;
	private BigDecimal commission;
	private BigDecimal tax;
	private BigDecimal issued;
	private BigDecimal iwjr;
	private BigDecimal psc;
	private BigDecimal surchargefee;
	private BigDecimal ins;
	private BigDecimal ins_comm;
	private BigDecimal svc;
	private BigDecimal svc_comm;
	private BigDecimal book_balance;	//dipakai oleh kalstar
	private BigDecimal nta;
	
	private BigDecimal comm_rate;	//percent. yg berlaku saat transaksi dengan airline
	private BigDecimal insentive;
	private BigDecimal pph23;
	private BigDecimal comm_berleha;	//percent. komisi yg diperoleh berleha
	
	private BigDecimal amount;	//biasanya basefate + psc + iwjr + tax
	private BigDecimal other;
	
	//harga setelah booking
	private BigDecimal after_amount;
	private BigDecimal after_basefare;
	private BigDecimal after_tax;
	private BigDecimal after_iwjr;
	private BigDecimal after_psc;
	private BigDecimal after_nta;
	private BigDecimal after_comm;
	
	public BookingFlightTransVO() {
		setId(java.util.UUID.randomUUID().toString());
	}
	
	public BookingFlightTransVO(String booking_flight_id) {
		this();
		setBooking_flight_id(booking_flight_id);
	}

	
	@Id
	@XmlTransient
	@JsonIgnore
	@Column(length = 40, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 40, nullable = false)
	public String getBooking_flight_id() {
		return booking_flight_id;
	}

	public void setBooking_flight_id(String booking_flight_id) {
		this.booking_flight_id = booking_flight_id;
	}
	
	public BigDecimal getAfter_amount() {
		return after_amount;
	}
	public void setAfter_amount(BigDecimal after_amount) {
		this.after_amount = after_amount;
	}
	public BigDecimal getBasefare() {
		return basefare;
	}
	public void setBasefare(BigDecimal basefare) {
		this.basefare = basefare;
	}

	@Column(name = "comm")
	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	
	public BigDecimal getNta() {
		return nta;
	}

	public void setNta(BigDecimal nta) {
		this.nta = nta;
	}

	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getIssued() {
		return issued;
	}
	public void setIssued(BigDecimal issued) {
		this.issued = issued;
	}
	public BigDecimal getIwjr() {
		return iwjr;
	}
	public void setIwjr(BigDecimal iwjr) {
		this.iwjr = iwjr;
	}
	public BigDecimal getPsc() {
		return psc;
	}
	public void setPsc(BigDecimal psc) {
		this.psc = psc;
	}
	public BigDecimal getSurchargefee() {
		return surchargefee;
	}
	public void setSurchargefee(BigDecimal surchargefee) {
		this.surchargefee = surchargefee;
	}
	public BigDecimal getIns() {
		return ins;
	}
	public void setIns(BigDecimal ins) {
		this.ins = ins;
	}
	public BigDecimal getIns_comm() {
		return ins_comm;
	}
	public void setIns_comm(BigDecimal ins_comm) {
		this.ins_comm = ins_comm;
	}
	public BigDecimal getSvc() {
		return svc;
	}
	public void setSvc(BigDecimal svc) {
		this.svc = svc;
	}
	public BigDecimal getSvc_comm() {
		return svc_comm;
	}
	public void setSvc_comm(BigDecimal svc_comm) {
		this.svc_comm = svc_comm;
	}
	public BigDecimal getBook_balance() {
		return book_balance;
	}
	public void setBook_balance(BigDecimal book_balance) {
		this.book_balance = book_balance;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Deprecated
	public BigDecimal getComm_rate() {
		return comm_rate;
	}

	@Deprecated
	public void setComm_rate(BigDecimal comm_rate) {
		this.comm_rate = comm_rate;
	}

	public BigDecimal getInsentive() {
		return insentive;
	}

	public void setInsentive(BigDecimal insentive) {
		this.insentive = insentive;
	}

	@Deprecated
	public BigDecimal getPph23() {
		return pph23;
	}

	@Deprecated
	public void setPph23(BigDecimal pph23) {
		this.pph23 = pph23;
	}

	@Deprecated
	public BigDecimal getComm_berleha() {
		return comm_berleha;
	}

	@Deprecated
	public void setComm_berleha(BigDecimal comm_berleha) {
		this.comm_berleha = comm_berleha;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAfter_basefare() {
		return after_basefare;
	}

	public void setAfter_basefare(BigDecimal after_basefare) {
		this.after_basefare = after_basefare;
	}

	public BigDecimal getAfter_tax() {
		return after_tax;
	}

	public void setAfter_tax(BigDecimal after_tax) {
		this.after_tax = after_tax;
	}

	public BigDecimal getAfter_iwjr() {
		return after_iwjr;
	}

	public void setAfter_iwjr(BigDecimal after_iwjr) {
		this.after_iwjr = after_iwjr;
	}

	public BigDecimal getAfter_psc() {
		return after_psc;
	}

	public void setAfter_psc(BigDecimal after_psc) {
		this.after_psc = after_psc;
	}

	public BigDecimal getAfter_nta() {
		return after_nta;
	}

	public void setAfter_nta(BigDecimal after_nta) {
		this.after_nta = after_nta;
	}

	public BigDecimal getAfter_comm() {
		return after_comm;
	}

	public void setAfter_comm(BigDecimal after_comm) {
		this.after_comm = after_comm;
	}
	
	public BigDecimal getOther() {
		return other;
	}

	public void setOther(BigDecimal other) {
		this.other = other;
	}

	@Override
	public String toString() {
		return "BookingFlightTransVO [id=" + id + ", booking_flight_id="
				+ booking_flight_id + ", groupCode=" + groupCode
				+ ", basefare=" + basefare + ", commission=" + commission
				+ ", tax=" + tax + ", issued=" + issued + ", iwjr=" + iwjr
				+ ", psc=" + psc + ", surchargefee=" + surchargefee + ", ins="
				+ ins + ", ins_comm=" + ins_comm + ", svc=" + svc
				+ ", svc_comm=" + svc_comm + ", book_balance=" + book_balance
				+ ", nta=" + nta + ", comm_rate=" + comm_rate + ", insentive="
				+ insentive + ", pph23=" + pph23 + ", comm_berleha="
				+ comm_berleha + ", amount=" + amount + ", other=" + other
				+ ", after_amount=" + after_amount + ", after_basefare="
				+ after_basefare + ", after_tax=" + after_tax + ", after_iwjr="
				+ after_iwjr + ", after_psc=" + after_psc + ", after_nta="
				+ after_nta + ", after_comm=" + after_comm + "]";
	}
	
	
}
