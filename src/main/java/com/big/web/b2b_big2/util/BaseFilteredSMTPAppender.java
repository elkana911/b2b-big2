package com.big.web.b2b_big2.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.log4j.net.SMTPAppender;

public class BaseFilteredSMTPAppender extends SMTPAppender {

	  private int timeFrame;

	  private int maxEMails;

	  protected long timeFrameMillis;

	  protected Boolean isTLS;

	  protected List<Date> exceptionDates = new ArrayList<Date>();

	  public int getTimeFrame() {

	    return timeFrame;

	  }

	  public void setTimeFrame(int timeFrame) {

	    this.timeFrame = timeFrame;

	  }

	  public int getMaxEMails() {

	    return maxEMails;

	  }

	  public void setMaxEMails(int maxEMails) {

	    this.maxEMails = maxEMails;

	  }

	  public void setTLS(boolean isTLS) {

	    this.isTLS = isTLS;

	  }

	  @Override

	  public void activateOptions() {

	    super.activateOptions();

	    timeFrameMillis = timeFrame * 60 * 1000;

	  }

	  @Override

	  protected Session createSession() {

	    Properties props = null;

	    try {

	        props = new Properties (System.getProperties());

	    } catch(SecurityException ex) {

	        props = new Properties();

	    }

	    if (getSMTPHost() != null) {

	      props.put("mail.smtp.host", getSMTPHost());

	    }

	    Authenticator auth = null;

	    if(getSMTPUsername() != null && getSMTPPassword() != null) {

	      props.put("mail.smtp.auth", "true");

	      auth = new Authenticator() {

	        protected PasswordAuthentication getPasswordAuthentication() {

	          return new PasswordAuthentication(getSMTPUsername(), getSMTPPassword());

	        }

	      };

	    }

	    if (isTLS != null && isTLS)

	    {

	      props.put("mail.smtp.starttls.enable","true");

	    }

	    Session session = Session.getInstance(props, auth);

	    if (getSMTPDebug()) {

	        session.setDebug(getSMTPDebug());

	    }

	    return session;

	  }

	  protected void cleanTimedoutExceptions() {

	    Date current = new Date();

	    // Remove timedout exceptions

	    Iterator<Date> itr = exceptionDates.iterator();

	    while (itr.hasNext()) {

	      Date exceptionDate = itr.next();

	      if (current.getTime() - exceptionDate.getTime() > timeFrameMillis) {

	        itr.remove();

	      } else {

	        break;

	      }

	    }

	  }

	  protected void addException() {

	    exceptionDates.add(new Date());

	  }

	  protected boolean isSendMailAllowed() {

	    return exceptionDates.size() < maxEMails;

	  }

	}