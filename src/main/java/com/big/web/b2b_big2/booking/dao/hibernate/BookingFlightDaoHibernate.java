package com.big.web.b2b_big2.booking.dao.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.booking.BOOK_STATUS;
import com.big.web.b2b_big2.booking.dao.IBookingFlightDao;
import com.big.web.b2b_big2.booking.model.BookingFlightDtlVO;
import com.big.web.b2b_big2.booking.model.BookingFlightSchedVO;
import com.big.web.b2b_big2.booking.model.BookingFlightTransVO;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.flight.RATE_TYPE;
import com.big.web.b2b_big2.flight.dao.IAirportDao;
import com.big.web.b2b_big2.flight.model.AirportVO;
import com.big.web.b2b_big2.flight.model.FlightAvailSeatVO;
import com.big.web.b2b_big2.flight.pojo.Airport;
import com.big.web.b2b_big2.flight.pojo.BasicContact;
import com.big.web.b2b_big2.flight.pojo.BookingFormB2B;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;
import com.big.web.b2b_big2.flight.pojo.ContactInfant;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.flight.pojo.PERSON_TYPE;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.flight.pojo.Route;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.hibernate.BasicHibernate;
import com.big.web.model.LabelValue;

@Repository("bookingFlightDao")
public class BookingFlightDaoHibernate extends BasicHibernate implements IBookingFlightDao{

	@Autowired
	IAirportDao airportDao;
	
	@Override
	public List<BookingFlightVO> findBookingFlight(String username) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public void cancelBookingFlight(String id) {
		Criteria c = getSession().createCriteria(BookingFlightVO.class);
		
		c.add(Restrictions.eq("uid", id));

		BookingFlightVO b = null;// = get(id);
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			b = (BookingFlightVO) iterator.next();
			break;
		}

		if (b == null) return;
		
		b.setStatus(BOOK_STATUS.XX.name());
		b.setLastUpdate(new Date());

		getSession().saveOrUpdate(b);
//		save(b);
		
	}
	
	@Override
	public void cancelBookingByCode(String bookCode) {
		Criteria c = getSession().createCriteria(BookingFlightVO.class);
		
		c.add(Restrictions.eq("code", bookCode));

		BookingFlightVO b = null;// = get(id);
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			b = (BookingFlightVO) iterator.next();
			break;
		}

		if (b == null) return;
		
		b.setStatus(BOOK_STATUS.XX.name());	//jadi meski sebelumnya HK, HK akan menjadi single XX
		b.setLastUpdate(new Date());

		getSession().saveOrUpdate(b);
	}

	/*
	@SuppressWarnings("unchecked")
	@Override
	public List<BookingFlightVO> getPNRs(String bookingCode) {
		Criteria c = getSession().createCriteria(BookingFlightVO.class);
		
		c.add(Restrictions.eq("code", bookingCode));

		BookingFlightVO found = null;
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			found = (BookingFlightVO) iterator.next();
			break;
		}
		
		if (found != null){
			//ambil teman2nya
			c = getSession().createCriteria(BookingFlightVO.class);
			c.add(Restrictions.eq("groupCode", found.getGroupCode()));
			
			return c.list();
		}
		
		return null;
	}*/

	
	@Override
	public BookingFlightVO getBookingFlightByBookingCode(String bookingCode) {
		Criteria c = getSession().createCriteria(BookingFlightVO.class);
		
		c.add(Restrictions.eq("code", bookingCode));

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (BookingFlightVO) iterator.next();
		}

		return null;
	}

	@Override
	public void saveBookingFlight(BookingFlightVO obj) {
		getSession().saveOrUpdate(obj);
	}

	@Override
	public void saveBookingFlightDtl(BookingFlightDtlVO obj) {
		getSession().saveOrUpdate(obj);
	}

	@Override
	public void saveBookingFlightTrans(BookingFlightTransVO obj) {
		getSession().saveOrUpdate(obj);
	}

	@Override
	public BookingFlightVO getBookingFlight(String uid) {
		Criteria c = getSession().createCriteria(BookingFlightVO.class);
		
		c.add(Restrictions.eq("id", uid));

		BookingFlightVO b = null;
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			b = (BookingFlightVO) iterator.next();
			break;
		}
		
		if (b == null) return null;
		
		if (b.getItineraries() == null){
			b.setItineraries(getFareInfosFromBookingFlightSchedules(uid));
		}
		
		if (b.getPnr() == null){
			b.setPnr(getPNRs(uid));
		}

		if (b.getDetailTransaction() == null){
			b.setDetailTransaction(getBookingFlightTrans(uid));
		}

		return b;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingFlightVO> getBookingFlight(Date from, Date to, String[] airlineCode, String userName, String[] status) {
		Criteria c = getSession().createCriteria(BookingFlightVO.class);

		c.createAlias("user", "usr");
		c.add(Restrictions.eq("usr.username", userName));
		
		if(from!=null){
			c.add(Restrictions.ge("createdDate", from)); 
		}
		
		c.add(Restrictions.isNotNull("code"));
		
		if (!Utils.isEmpty(status)){
			//[HOLD, HK, HK,HK, HK,HK,HK, EXPIRED]
			if (Utils.search(status, "EXPIRED")){
				c.add(Restrictions.lt("timeToPay", new Date()));
			}
			else
				c.add(Restrictions.in("status", status));
		}
		
		if (to != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(to);
			cal.add(Calendar.DATE, 1); 
			
			c.add(Restrictions.lt("createdDate", cal.getTime()));
			
		}

		/*
		 * due to airlines_iata using csv, cant use restrictions
		if (!Utils.isEmpty(airlineCode)){
			c.add(Restrictions.in("status", status));
		}*/
		
//		c.add(Restrictions.between("createdDate", from, to));	JANGAN PAKE BETWEEN
		
		airlineCode = Utils.isEmpty(airlineCode) ? null : airlineCode;
		
		List<BookingFlightVO> list = new ArrayList<BookingFlightVO>();
		
		List<BookingFlightVO> buffer = c.list();
		for (int i = 0; i < buffer.size(); i++) {

			BookingFlightVO item = buffer.get(i);
			
			if (airlineCode != null && item.getAirlines_iata() != null){
				
				String[] _iatas = Utils.csvToArray(item.getAirlines_iata());
				
				boolean _match = false;
				for (int j = 0; j < _iatas.length; j++){
					for (int k = 0; k < airlineCode.length; k++) {
						if (_iatas[j].equalsIgnoreCase(airlineCode[k])){
							_match = true;
							break;
						}
					}
					
					if (_match) break;
				}

				if (!_match) continue;
				
			}

			if (item.getItineraries() == null){
				item.setItineraries(getFareInfosFromBookingFlightSchedules(item.getId()));
			}
			
			if (item.getPnr() == null){
				item.setPnr(getPNRs(item.getId()));
			}
			
			if (item.getDetailTransaction() == null){
				item.setDetailTransaction(getBookingFlightTrans(item.getId()));
			}
			
			list.add(item);
			
//			buffer.set(i, item);	//update item
			
		}

		return list;
	}

	@Override
	public void saveBookingFlightSched(BookingFlightSchedVO obj) {
		getSession().saveOrUpdate(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PNR> getPNRs(String booking_flight_id) {
		Criteria c = getSession().createCriteria(BookingFlightDtlVO.class);
		c.add(Restrictions.eq("booking_flight_id", booking_flight_id));

		List<PNR> pnrList = new ArrayList<PNR>();
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			BookingFlightDtlVO _b = (BookingFlightDtlVO) iterator.next();

			PNR _p = new PNR(_b);
			
			pnrList.add(_p);
		}

		return pnrList;
	}

	@Override
	public int getPaxCountWithoutInfant(String booking_flight_id) {
		
		int count = 0;
		for (PNR pnr : getPNRs(booking_flight_id)){
			if (pnr.getPersonType() != PERSON_TYPE.INFANT)
				count = count +1;
		}
		
		return count;
	}

	/**
	 * utk lov di client
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<PNR> getUniquePNRs(PERSON_TYPE personType, Long userId) {

		SQLQuery q = getSession().createSQLQuery("select a.* from booking_flight_detail a inner join booking_flight b"
										+ " on a.booking_flight_id = b.id where b.userid=" + userId
										+ " and a.passenger_type= :passenger_type order by a.passenger_first_name asc"
											);
		q.setParameter("passenger_type", personType.getFlag().toUpperCase());
		q.addEntity(BookingFlightDtlVO.class);
		
		/*
		Criteria c = getSession().createCriteria(BookingFlightDtlVO.class);
		c.add(Restrictions.eq("passenger_type", personType.getFlag().toUpperCase()));
		c.addOrder(Order.asc("passenger_first_name"));
		 */
		List<PNR> pnrList = new ArrayList<PNR>();
		
		for (Iterator iterator = q.list().iterator(); iterator.hasNext();) {
			BookingFlightDtlVO _bf = (BookingFlightDtlVO) iterator.next();
			
			PNR _p = new PNR(_bf);
			
			boolean _exists = false;
			for (int j = 0; j < pnrList.size(); j++){
				if (pnrList.get(j).getFullName().equalsIgnoreCase(_p.getFullName())){
					_exists = true;
					break;
				}
			}
			
			if (!_exists)
				pnrList.add(_p);
		}

		//mungkin ditambah smart filter lagi bisa disini
		
		return pnrList; 

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<BookingFlightSchedVO> getBookingFlightSchedules(String booking_flight_id) {
		Criteria c = getSession().createCriteria(BookingFlightSchedVO.class);
		c.add(Restrictions.eq("booking_flight_id", booking_flight_id));

		return (List<BookingFlightSchedVO>) c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingFlightDtlVO> getBookingFlightDetails(String booking_flight_id) {
		Criteria c = getSession().createCriteria(BookingFlightDtlVO.class);
		c.add(Restrictions.eq("booking_flight_id", booking_flight_id));

		return (List<BookingFlightDtlVO>) c.list();
	}


	@Override
	public List<FareInfo> getFareInfosFromBookingFlightSchedules(String booking_flight_id){
		Criteria c = getSession().createCriteria(BookingFlightSchedVO.class);
		c.add(Restrictions.eq("booking_flight_id", booking_flight_id));

		List<FareInfo> fareInfos = new ArrayList<FareInfo>();
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			BookingFlightSchedVO _b = (BookingFlightSchedVO) iterator.next();
			FareInfo _fi = new FareInfo();
			
			_fi.setFlightNo(_b.getFlightNum());
			_fi.setClassName(_b.getClass_name());
			
			AirportVO _airport = airportDao.getAirportByIata(_b.getDeparture());				
			Airport _fromAirport = new Airport(_airport.getAirport_name(), _airport.getCity(), _b.getDeparture(), _b.getDep_time());
			
			_airport = airportDao.getAirportByIata(_b.getArrival());				
			Airport _toAirport = new Airport(_airport.getAirport_name(), _airport.getCity(), _b.getArrival(), _b.getArrival_time());
			
			Route _route = new Route(_fromAirport, _toAirport);				
			_fi.setRoute(_route);
			
			fareInfos.add(_fi);
		}

		return fareInfos;
		
	}

	@Override
	public void issuedBookingByCode(String bookCode) {
		Criteria c = getSession().createCriteria(BookingFlightVO.class);
		
		c.add(Restrictions.eq("code", bookCode));

		BookingFlightVO b = null;// = get(id);
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			b = (BookingFlightVO) iterator.next();
			break;
		}

		if (b == null) return;
		
		b.setStatus(BOOK_STATUS.OK.name());	//jadi meski sebelumnya HK, HK akan menjadi single XX
		b.setBook_balance(BigDecimal.ZERO);//reset jadi 0
		b.setLastUpdate(new Date());

		getSession().saveOrUpdate(b);
	}

	@Override
	public List<ContactCustomer> getContactCustomer(Long userId) {
		
		SQLQuery q = getSession().createSQLQuery("select distinct(cust_name), cust_phone1,cust_phone2 from booking_flight where userid=:userid and cust_name is not null order by cust_name");
		q.setParameter("userid", userId);
		
		List<ContactCustomer> ccList = new ArrayList<ContactCustomer>();
		
		for (Iterator iterator = q.list().iterator(); iterator.hasNext();) {
				Object[] obj = (Object[])iterator.next();

				ContactCustomer _cc = new ContactCustomer();
				_cc.setFullName((String)obj[0]);
				_cc.setPhone1((String)obj[1]);
				_cc.setPhone2((String)obj[2]);
				
				ccList.add(_cc);
		}

		return ccList; 

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingFlightVO> getBookingFlights(String uid) {
		Criteria c1 = getSession().createCriteria(BookingFlightVO.class);
		
		c1.add(Restrictions.eq("id", uid));
		
		BookingFlightVO b1 = null;
		for (Iterator iterator = c1.list().iterator(); iterator.hasNext();) {
			b1 = (BookingFlightVO) iterator.next();
			break;
		}
		
		if (b1 == null) return null;
		
		List<BookingFlightVO> list = new ArrayList<BookingFlightVO>();
		if (!Utils.isEmpty(b1.getGroupCode())){
			//cari pasangannya
			String groupCode = b1.getGroupCode();
						
			Criteria c2 = getSession().createCriteria(BookingFlightVO.class);
			c2.add(Restrictions.eq("groupCode", groupCode));
			// TODO harusnya ditambah filter apakah booking di hari yang sama, utk kasus clash booking code

			// pengurutan berdasarkan groupCode, misal Z2NIC4,EHOVWJ berarti Z2NIC4 adalah penerbangan pertama
			// pengurutan terpaksa manual karena di table BOOKINGFLIGHT tidak ada counter/penunjuknya
			String[] codes = Utils.csvToArray(groupCode);
			
			for (int i = 0; i < codes.length; i++){
				
				String _currentCode = codes[i].trim();
				
				for (int j = 0; j < c2.list().size(); j++){
					BookingFlightVO b2 = (BookingFlightVO) c2.list().get(j);

					if (!b2.getCode().equals(_currentCode)) continue;
					
					if (b2.getItineraries() == null){
						b2.setItineraries(getFareInfosFromBookingFlightSchedules(b2.getId()));
					}
					
					if (b2.getPnr() == null){
						b2.setPnr(getPNRs(b2.getId()));
					}		
					
					if (b2.getDetailTransaction() == null){
						b2.setDetailTransaction(getBookingFlightTrans(b2.getId()));
					}

					list.add(b2);	// sampai sini harusnya sudah urut
					
					break;

				}
			}
			
			//double checking utk antisipasi gagal urut karena ada kode booking yg kosong
			if (list.size() != c2.list().size()){
				//dibalikin seperti semula sambil kasih warning
				log.warn("Unmatched size after sort booking flights AFTER : BEFORE = " + list.size() + " : " + c2.list().size() + ". Restored to previous state");
				list.clear();
				for (int j = 0; j < c2.list().size(); j++){
					BookingFlightVO b2 = (BookingFlightVO) c2.list().get(j);
					
					if (b2.getItineraries() == null){
						b2.setItineraries(getFareInfosFromBookingFlightSchedules(b2.getId()));
					}
					
					if (b2.getPnr() == null){
						b2.setPnr(getPNRs(b2.getId()));
					}		
					
					if (b2.getDetailTransaction() == null){
						b2.setDetailTransaction(getBookingFlightTrans(b2.getId()));
					}

					list.add(b2);	// sampai sini harusnya sudah urut

				}
			}
			
		}else{
			
			if (b1.getItineraries() == null){
				b1.setItineraries(getFareInfosFromBookingFlightSchedules(uid));
			}
			
			if (b1.getPnr() == null){
				b1.setPnr(getPNRs(uid));
			}		
			
			if (b1.getDetailTransaction() == null){
				b1.setDetailTransaction(getBookingFlightTrans(uid));
			}
			list.add(b1);
		}
		
		return list;
	}

	@Override
	public BookingFlightTransVO getBookingFlightTrans(String booking_flight_id) {
		Criteria c = getSession().createCriteria(BookingFlightTransVO.class);
		
		c.add(Restrictions.eq("booking_flight_id", booking_flight_id));

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (BookingFlightTransVO) iterator.next();
		}

		return null;

	}

	@Override
	public Set<LabelValue> getBookingSummaryByStatus(String[] status) {
		SQLQuery q = getSession().createSQLQuery("select airlines_iata, count(airlines_iata) as x from booking_flight where status in (:status)"
				+ " group by airlines_iata order by x desc");

		q.setParameterList("status", status);
		
		Set<LabelValue> list = new LinkedHashSet<LabelValue>();
		
		for (Iterator iterator = q.list().iterator(); iterator.hasNext();) {
			Object[] row =  (Object[]) iterator.next();
			String str = (String)row[0];
			
			//skip multi airlines
			if (str.length() > 2) continue;
			
			if (row[1] == null) continue;
			
			LabelValue lblValue = new LabelValue(str, ((BigDecimal)row[1]).toString());
			
			list.add(lblValue);
		}
		
		return list;
	}

	@Override
	public Set<LabelValue> getBookingFavouriteRoute() {
		StringBuffer sb = new StringBuffer("select x, count(x) as cnt from (");
		
		sb.append("select (origin ||'-'|| destination) as x from booking_flight bf where (");
//		sb.append("select (origin ||'-'|| destination) as x from booking_flight bf where (STATUS='BOOKED' or STATUS='ISSUED')");
		String[] ticketStatus = {"OK", "TKT", "CONFIRMED", "BOOKED", "ISSUED"};
		for (int i = 0; i < ticketStatus.length; i++) {
			if (i > 0)
				sb.append(" or ");
			
			sb.append("STATUS='").append(ticketStatus[i]).append("'");
		}
		sb.append(")");
		
		sb.append(") group by x order by cnt desc");
		
		
		SQLQuery q = getSession().createSQLQuery(sb.toString());

		Set<LabelValue> list = new LinkedHashSet<LabelValue>();
		
		for (Iterator iterator = q.list().iterator(); iterator.hasNext();) {
			Object[] row =  (Object[]) iterator.next();
			
			// dont accept empty
			if (row[0] == null ||  row[1] == null) continue;
			
			LabelValue lblValue = new LabelValue((String)row[0], ((BigDecimal)row[1]).toString());
			
			list.add(lblValue);
		}
		
		return list;
	}

	@Override
	public RATE_TYPE[] anyPriceChanged(String booking_flight_id) {
		
		BookingFlightTransVO trans = getBookingFlightTrans(booking_flight_id);
		
		Set<RATE_TYPE> differentRateTypes = new LinkedHashSet<RATE_TYPE>();
		
		if (Utils.compareBigDecimal(trans.getAfter_basefare(), trans.getBasefare()) != 0)
				differentRateTypes.add(RATE_TYPE.BASE_FARE);
		
		if (Utils.compareBigDecimal(trans.getAfter_iwjr(), trans.getIwjr()) != 0)
				differentRateTypes.add(RATE_TYPE.IWJR);

		if (Utils.compareBigDecimal(trans.getAfter_nta(), trans.getNta()) != 0)
			differentRateTypes.add(RATE_TYPE.NTA);
		
		if (Utils.compareBigDecimal(trans.getAfter_psc(), trans.getPsc()) != 0)
			differentRateTypes.add(RATE_TYPE.PSC);
		
		if (Utils.compareBigDecimal(trans.getAfter_tax(), trans.getTax()) != 0)
			differentRateTypes.add(RATE_TYPE.TAX);
		
		return (differentRateTypes.size() < 1) ? null :	differentRateTypes.toArray(new RATE_TYPE[differentRateTypes.size()]);

		/*
			bf.getAfter_amount();	v	tp ga perlu dibandingkan krn jika yg lain ga ada yg berubah harusnya sama
			bf.getAfter_basefare(); v
			bf.getAfter_iwjr(); v
			bf.getAfter_nta();	tdk diisi oleh scraping / api
			bf.getAfter_psc(); v
			bf.getAfter_tax(); v
		*/
		
		/*
			bf.getAmount()
			bf.getBasefare()
			bf.getIwjr()
			bf.getNta
			bf.getPsc()
			bf.getTax()
		*/
		
	}

	@Override
	public List<BookingFlightVO> getExpiredBookingFlights() {
		Criteria c = getSession().createCriteria(BookingFlightVO.class);
		
		c.add(Restrictions.isNotNull("timeToPay"));
		
		//FIXME status belum lengkap mengcover BOOKED status
		String[] status = {BOOK_STATUS.HK.name()
						 , BOOK_STATUS.HOLD.name()
						 };
		
		c.add(Restrictions.in("status", status));
		
		c.add(Restrictions.ge("timeToPay", new Date()));

		return c.list();
	}

	@Override
	public BasicContact getPNR(String fullName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> isDoubleBooking(BookingFormB2B bookingForm, FlightAvailSeatVO fas) {
		
		if (bookingForm.isEmptyPnr() || fas == null) return null;
		
//		StringBuffer sql = new StringBuffer("select a.id from booking_flight_detail a inner join booking_flight b on a.booking_flight_id = b.id where ");
		StringBuffer sql = new StringBuffer("select a.id from booking_flight_detail a")
		.append(" inner join booking_flight b on a.booking_flight_id = b.id")
		.append(" inner join booking_flight_schedule s on s.booking_flight_id = b.id")
		.append(" where ");
		
		List<BasicContact> adults = bookingForm.getAdult();
		if (adults.size() > 0){
			
			sql.append(" ( a.passenger_type=").append(Utils.quoteStr(PERSON_TYPE.ADULT.getFlag()));
			
			for (BasicContact basicContact : adults) {
				String[] personNames = Utils.splitPersonNameInto3Words(basicContact.getFullName());
				
				sql.append(" AND a.passenger_first_name=").append(Utils.quoteStr(personNames[0].toUpperCase()));
				
				if (Utils.isEmpty(personNames[1]))
					sql.append(" AND a.passenger_middle_name is null");
				else
					sql.append(" AND a.passenger_middle_name=").append(Utils.quoteStr(personNames[1].toUpperCase()));
				
				sql.append(" AND a.passenger_last_name=").append(Utils.quoteStr(personNames[2].toUpperCase()));
				
				if (basicContact.getTitle() != null)
					sql.append(" AND a.passenger_title=").append(Utils.quoteStr(basicContact.getTitle().name()));
			}
			sql.append(")");
		}
		
		List<BasicContact> children = bookingForm.getChild();
		if (children.size() >  0){
			
			if (adults.size() > 0)
				sql.append(" OR ");
			
			sql.append(" ( a.passenger_type=").append(Utils.quoteStr(PERSON_TYPE.CHILD.getFlag()));
			
			for (BasicContact basicContact : children) {
				String[] personNames = Utils.splitPersonNameInto3Words(basicContact.getFullName());
				
				sql.append(" AND a.passenger_first_name=").append(Utils.quoteStr(personNames[0].toUpperCase()));
				
				if (Utils.isEmpty(personNames[1]))
					sql.append(" AND a.passenger_middle_name is null");
				else
					sql.append(" AND a.passenger_middle_name=").append(Utils.quoteStr(personNames[1].toUpperCase()));
				
				sql.append(" AND a.passenger_last_name=").append(Utils.quoteStr(personNames[2].toUpperCase()));
				
				if (basicContact.getTitle() != null)
					sql.append(" AND a.passenger_title=").append(Utils.quoteStr(basicContact.getTitle().name()));
			}
			sql.append(")");

		}
		
		List<ContactInfant> infants = bookingForm.getInfant();
		if (infants.size() > 0){
			
			if (adults.size() > 0 || children.size() > 0)
				sql.append(" OR ");
			
			sql.append(" ( a.passenger_type=").append(Utils.quoteStr(PERSON_TYPE.INFANT.getFlag()));

			for (ContactInfant contactInfant : infants) {
				
				String[] personNames = Utils.splitPersonNameInto3Words(contactInfant.getFullName());
				
				sql.append(" AND a.passenger_first_name=").append(Utils.quoteStr(personNames[0].toUpperCase()));
				
				if (Utils.isEmpty(personNames[1]))
					sql.append(" AND a.passenger_middle_name is null");
				else
					sql.append(" AND a.passenger_middle_name=").append(Utils.quoteStr(personNames[1].toUpperCase()));
				
				sql.append(" AND a.passenger_last_name=").append(Utils.quoteStr(personNames[2].toUpperCase()));
				
				if (contactInfant.getTitle() != null)
					sql.append(" AND a.passenger_title=").append(Utils.quoteStr(contactInfant.getTitle().name()));
			}
			sql.append(" )");
		}
		
		// check schedule
		sql.append(" AND s.flightnum=").append(Utils.quoteStr(fas.getFlightnum()));
		sql.append(" AND b.origin=").append(Utils.quoteStr(fas.getOrigin()));
		sql.append(" AND b.destination=").append(Utils.quoteStr(fas.getDestination()));
		sql.append(" AND TO_CHAR(b.departure_date, 'DDMMRRRR')=").append(Utils.quoteStr(Utils.Date2String(fas.getDep_time(), "ddMMyyyy")));
		
		SQLQuery sqlQuery = getSession().createSQLQuery(sql.toString());
		
		return sqlQuery.list();
		
		
//		Criteria c_bf = getSession().createCriteria(BookingFlightVO.class);
		
//		Criteria c_bf_detail = getSession().createCriteria(BookingFlightDtlVO.class);


		/*
		, fasdepart.getDep_time()
		, fasdepart.getFlightnum()
		, fasdepart.getOrigin()
		, fasdepart.getDestination()
		*/

	}

}
