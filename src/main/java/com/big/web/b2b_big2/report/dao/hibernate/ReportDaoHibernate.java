package com.big.web.b2b_big2.report.dao.hibernate;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.report.dao.IReportDao;
import com.big.web.b2b_big2.report.pojo.REPORT_TYPE;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.hibernate.BasicHibernate;

@Repository("reportDao")
public class ReportDaoHibernate extends BasicHibernate implements IReportDao {

	private void sendToJasper(Map<String,Object> params){
		
	}
	
	@Override
	public JasperPrint printTicketFlight(BookingFlightVO bookingFlightVO, String reportPath, REPORT_TYPE reportType) throws Exception {

		reportPath = Utils.includeTrailingPathDelimiter(reportPath);
		Airline _airline = bookingFlightVO.getAirlines()[0]; 
		
		switch (reportType){
		case CUSTOMER:
				reportPath += _airline.name().toLowerCase() + "/cust_" + _airline.name().toLowerCase() + "/"; 
			break;
			default:
				reportPath += _airline.name().toLowerCase() + "/agent_" + _airline.name().toLowerCase() + "/"; 
				break;
		}
		
		String reportFile = "";
		String subReportFile1 = "";
		String subReportFile2 = "";
		
		reportFile = _airline.name().toLowerCase() + ".jasper";
		subReportFile1 = _airline.name().toLowerCase() + "_subreport1.jasper";
		subReportFile2 = _airline.name().toLowerCase() + "_subreport2.jasper";
		/*
		switch (_airline){
		case CITILINK:
		case SRIWIJAYA:

				break;
			default:
				break;
		}
		*/
		
		/*
		Map<String, Object> parameters = new HashMap<String, Object>();
		newpdftemplate newpdftemplate = new newpdftemplate();
		ArrayList<usingbean> dataBeanList = newpdftemplate.getDataBeanList();
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList);
		parameters.put("subReportDataSource", beanColDataSource);
		*/
		/*
		Map<String, Object> parameters = new HashMap<String, Object>();
		newpdftemplate newpdftemplate = new newpdftemplate();
		ArrayList<usingbean> dataBeanList = newpdftemplate.getDataBeanList();
		
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList);
		parameters.put("SUBREPORT_DS1", beanColDataSource);
		 */
		
//	        JasperReport jasperReport = JasperCompileManager.compileReport(url.getFile());
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(reportPath + reportFile));
		
//		List<BookingTicket> _data = new ArrayList<BookingTicket>();
//		_data.add(new BookingTicket(bookingFlightVO));

		Map<String,Object> params = new HashMap<>();
		params.put("CODE", bookingFlightVO.getCode());
		
		if (!Utils.isEmpty(subReportFile1)){
//	        	URL urlSubReport = classLoader.getResource("reports/" + subReportFile);	//utk mengambil file di src/main/resource gunakan ini 
			JasperReport jasperReportSubReport = (JasperReport) JRLoader.loadObject(new File(reportPath + subReportFile1));
			params.put("SUBREPORT_DIR1", jasperReportSubReport);

			//schedule list
//			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(_data, false);
//			params.put("SUBREPORT_DS1", beanColDataSource);

		}

		if (!Utils.isEmpty(subReportFile2)){
//	        	URL urlSubReport = classLoader.getResource("reports/" + subReportFile);	//utk mengambil file di src/main/resource gunakan ini 
			JasperReport jasperReportSubReport = (JasperReport) JRLoader.loadObject(new File(reportPath + subReportFile2));
			params.put("SUBREPORT_DIR2", jasperReportSubReport);
			
			//pnr list
//			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(_data, false);
//			params.put("SUBREPORT_DS2", beanColDataSource);
			
		}
		
		
		/* jika pake bean use this
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(_data, false);
		return JasperFillManager.fillReport(jasperReport, params, JRdataSource);
		*/
		
		//jika pake database use this
		SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory();
		ConnectionProvider cp = sfi.getConnectionProvider();
		Connection conn = cp.getConnection();
		
		return JasperFillManager.fillReport(jasperReport, params, conn);

	}

	@Override
	public void printVoucherHotel(String bookCode, String reportPath) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
