package com.big.web.b2b_big2.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "commission_range", schema = "big_trans")
public class CommissionRangeVO implements Serializable{

	public static final String tblName = "big_trans.commission_range";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1435201792691024378L;
	
	private String id;
	private String airline_code;
	private String origin;
	private String destination;
	private Integer returnTrip;
	private String flightNum;
	
	private BigDecimal classA;
	private BigDecimal classB;
	private BigDecimal classC;
	private BigDecimal classD;
	private BigDecimal classE;
	private BigDecimal classF;
	private BigDecimal classG;
	private BigDecimal classH;
	private BigDecimal classI;
	private BigDecimal classJ;
	private BigDecimal classK;
	private BigDecimal classL;
	private BigDecimal classM;
	private BigDecimal classN;
	private BigDecimal classO;
	private BigDecimal classP;
	private BigDecimal classQ;
	private BigDecimal classR;
	private BigDecimal classS;
	private BigDecimal classT;
	private BigDecimal classU;
	private BigDecimal classV;
	private BigDecimal classW;
	private BigDecimal classX;
	private BigDecimal classY;
	private BigDecimal classZ;
	
	private Date last_Update;
	private Long updated_By;
	
	@Id
	@XmlTransient
	@JsonIgnore
	@Column(length = 40)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(length = 3)
	public String getAirline_code() {
		return airline_code;
	}
	public void setAirline_code(String airline_code) {
		this.airline_code = airline_code;
	}
	@Column(length = 5)
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	@Column(length = 5)
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	@Column(name = "return")
	public Integer getReturnTrip() {
		return returnTrip;
	}
	public void setReturnTrip(Integer returnTrip) {
		this.returnTrip = returnTrip;
	}
	
	@Column(length = 30)
	public String getFlightNum() {
		return flightNum;
	}
	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	@Column(name = "A")
	public BigDecimal getClassA() {
		return classA;
	}
	public void setClassA(BigDecimal classA) {
		this.classA = classA;
	}
	@Column(name = "B")
	public BigDecimal getClassB() {
		return classB;
	}
	public void setClassB(BigDecimal classB) {
		this.classB = classB;
	}
	@Column(name = "C")
	public BigDecimal getClassC() {
		return classC;
	}
	public void setClassC(BigDecimal classC) {
		this.classC = classC;
	}
	@Column(name = "D")	
	public BigDecimal getClassD() {
		return classD;
	}
	public void setClassD(BigDecimal classD) {
		this.classD = classD;
	}
	@Column(name = "E")
	public BigDecimal getClassE() {
		return classE;
	}
	public void setClassE(BigDecimal classE) {
		this.classE = classE;
	}
	@Column(name = "F")
	public BigDecimal getClassF() {
		return classF;
	}
	public void setClassF(BigDecimal classF) {
		this.classF = classF;
	}
	@Column(name = "G")
	public BigDecimal getClassG() {
		return classG;
	}
	public void setClassG(BigDecimal classG) {
		this.classG = classG;
	}
	@Column(name = "H")
	public BigDecimal getClassH() {
		return classH;
	}
	public void setClassH(BigDecimal classH) {
		this.classH = classH;
	}
	@Column(name = "I")
	public BigDecimal getClassI() {
		return classI;
	}
	public void setClassI(BigDecimal classI) {
		this.classI = classI;
	}
	@Column(name = "J")
	public BigDecimal getClassJ() {
		return classJ;
	}
	public void setClassJ(BigDecimal classJ) {
		this.classJ = classJ;
	}
	@Column(name = "K")
	public BigDecimal getClassK() {
		return classK;
	}
	public void setClassK(BigDecimal classK) {
		this.classK = classK;
	}
	@Column(name = "L")
	public BigDecimal getClassL() {
		return classL;
	}
	public void setClassL(BigDecimal classL) {
		this.classL = classL;
	}
	@Column(name = "M")
	public BigDecimal getClassM() {
		return classM;
	}
	public void setClassM(BigDecimal classM) {
		this.classM = classM;
	}
	@Column(name = "N")
	public BigDecimal getClassN() {
		return classN;
	}
	public void setClassN(BigDecimal classN) {
		this.classN = classN;
	}
	@Column(name = "O")
	public BigDecimal getClassO() {
		return classO;
	}
	public void setClassO(BigDecimal classO) {
		this.classO = classO;
	}
	@Column(name = "P")
	public BigDecimal getClassP() {
		return classP;
	}
	public void setClassP(BigDecimal classP) {
		this.classP = classP;
	}
	@Column(name = "Q")
	public BigDecimal getClassQ() {
		return classQ;
	}
	public void setClassQ(BigDecimal classQ) {
		this.classQ = classQ;
	}
	@Column(name = "R")
	public BigDecimal getClassR() {
		return classR;
	}
	public void setClassR(BigDecimal classR) {
		this.classR = classR;
	}
	@Column(name = "S")
	public BigDecimal getClassS() {
		return classS;
	}
	public void setClassS(BigDecimal classS) {
		this.classS = classS;
	}
	@Column(name = "T")
	public BigDecimal getClassT() {
		return classT;
	}
	public void setClassT(BigDecimal classT) {
		this.classT = classT;
	}
	@Column(name = "U")
	public BigDecimal getClassU() {
		return classU;
	}
	public void setClassU(BigDecimal classU) {
		this.classU = classU;
	}
	@Column(name = "V")
	public BigDecimal getClassV() {
		return classV;
	}
	public void setClassV(BigDecimal classV) {
		this.classV = classV;
	}
	@Column(name = "W")
	public BigDecimal getClassW() {
		return classW;
	}
	public void setClassW(BigDecimal classW) {
		this.classW = classW;
	}
	@Column(name = "X")
	public BigDecimal getClassX() {
		return classX;
	}
	public void setClassX(BigDecimal classX) {
		this.classX = classX;
	}
	@Column(name = "Y")
	public BigDecimal getClassY() {
		return classY;
	}
	public void setClassY(BigDecimal classY) {
		this.classY = classY;
	}
	@Column(name = "Z")
	public BigDecimal getClassZ() {
		return classZ;
	}
	public void setClassZ(BigDecimal classZ) {
		this.classZ = classZ;
	}
	public Date getLast_Update() {
		return last_Update;
	}
	public void setLast_Update(Date last_Update) {
		this.last_Update = last_Update;
	}
	public Long getUpdated_By() {
		return updated_By;
	}
	public void setUpdated_By(Long updated_By) {
		this.updated_By = updated_By;
	}
	@Override
	public String toString() {
		return "CommissionRangeVO [id=" + id + ", airline_code=" + airline_code
				+ ", origin=" + origin + ", destination=" + destination
				+ ", returnTrip=" + returnTrip + ", flightNum=" + flightNum
				+ ", classA=" + classA + ", classB=" + classB + ", classC="
				+ classC + ", classD=" + classD + ", classE=" + classE
				+ ", classF=" + classF + ", classG=" + classG + ", classH="
				+ classH + ", classI=" + classI + ", classJ=" + classJ
				+ ", classK=" + classK + ", classL=" + classL + ", classM="
				+ classM + ", classN=" + classN + ", classO=" + classO
				+ ", classP=" + classP + ", classQ=" + classQ + ", classR="
				+ classR + ", classS=" + classS + ", classT=" + classT
				+ ", classU=" + classU + ", classV=" + classV + ", classW="
				+ classW + ", classX=" + classX + ", classY=" + classY
				+ ", classZ=" + classZ + ", last_Update=" + last_Update
				+ ", updated_By=" + updated_By + "]";
	}

}
