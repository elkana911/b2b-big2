package com.big.web.b2b_big2.report.dao;

import net.sf.jasperreports.engine.JasperPrint;

import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.report.pojo.REPORT_TYPE;

public interface IReportDao {
	
	JasperPrint printTicketFlight(BookingFlightVO bookingFlightVO, String reportPath, REPORT_TYPE reportType) throws Exception; 
	void printVoucherHotel(String bookCode, String reportPath) throws Exception;
}
