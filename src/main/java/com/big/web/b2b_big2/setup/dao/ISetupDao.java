package com.big.web.b2b_big2.setup.dao;

import java.math.BigDecimal;


public interface ISetupDao {
	
	static final String KEY_LOGO = "local.logo";
	static final String KEY_NEWS_SRC = "local.news.src";
	static final String KEY_NEWS_THUMB = "local.news.thumb";
	
	static final String KEY_SEARCH_PAST_ENABLED = "search.past.enabled";

	/**
	 * deposit is differ from top up. 
	 */
	static final String KEY_DEPOSIT = "DEPOSIT";

	static final String KEY_REPORT_PATH = "report.path";

	static final String KEY_MANDIRA_TOPUP_FIRST = "mandira.topup.first";

	static final String KEY_EMAIL_CACHE = "email.cache";
	
	static final String KEY_TOPUP_ATM_COUNTER_OFFSET = "topup.atm.counter.offset";
	static final String KEY_TOPUP_TIMEOUT_MINUTES = "topup.timeout.minutes";

	static final String KEY_TRANS_CODE_LENGTH = "trans.code.length";

	static final String KEY_CAPTCHA_SKIP = "captcha.skip";
	
	static final String KEY_TOPUP_MULTIPLE = "topup.multiple";

	static final String KEY_PRICE_IDR_CHEAP_ALERT = "price.idr.cheap.alert";

	static final String KEY_PRICE_USD_CHEAP_ALERT = "price.usd.cheap.alert";

	static final String KEY_AIRLINE_BOOKING_TIMEOUT_MINUTES = "airline.booking.timeout.minutes";
//	static final String KEY_AIRLINE_PRICE_MIN_IDR = "airline.price.idr.minimum";

	static final String KEY_SYNC_SEAT_DISABLED = "sync.seat.disabled";	//block sync seat after ticket searching
	static final String KEY_SYNC_SEAT_SLEEP = "sync.seat.sleep";	//default is 5000 ms. minimum is 1000
	
	static final String KEY_CIMB_EXPORT_PATH = "cimb.export.path";

	static final String KEY_ODR_JAVA_URL = "odr.java.url";
	static final String KEY_ODR_DATA_AGE_MINUTES = "odr.data.age.minutes";
	static final String KEY_ODR_SEARCH_ENABLED = "odr.search.enabled";
	static final String KEY_ODR_SEARCH_DETAIL_ENABLED = "odr.search.detail.enabled";

	static final String KEY_SYS_CPU_WAIT_SEC = "sys.cpu.wait.seconds";
	
	static final String KEY_TOKEN_TIMEOUT = "token.timeout.minutes";
	
	String getValue(String key);
	long getValueAsLong(String key);
	int getValueAsInt(String key);
	/**
	 * Recognize 1, true, TrUe, Yes, yes as true. otherwise false.
	 * @param key
	 * @return
	 */
	boolean getValueAsBoolean(String key);
	BigDecimal getValueAsBigDecimal(String key);
	
	boolean isForbidUser(String userName);
	
	long getCountFromTable(String fromTable, String where);
	
	/**
	 * Berbeda dengan existing token. token ini dibuat untuk 3rd party
	 * @param token
	 * @return true if token valid
	 */
	boolean checkToken3rdParty(String token, String ip);
	String generateToken3rdParty(String ip);
}
