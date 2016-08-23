package com.big.web.b2b_big2.webapp.controller.flight.b2b.booking;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class BookingDataSource extends JRAbstractBeanDataSourceProvider{
	
	private List<Booking> list;
	
	public BookingDataSource() {
		super(Booking.class);
	}

	@Override
	public JRDataSource create(JasperReport arg0) throws JRException {
		list = new ArrayList<Booking>();
		list.add(new Booking("1", "XYZ123"));
		list.add(new Booking("2", "XYZ423"));
		
		return new JRBeanCollectionDataSource(list);
	}

	@Override
	public void dispose(JRDataSource arg0) throws JRException {
		list.clear(); list = null;
	}

}
