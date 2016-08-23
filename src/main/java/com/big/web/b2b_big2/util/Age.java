package com.big.web.b2b_big2.util;

import java.util.Calendar;
import java.util.Date;

public class Age {
	private int years;
	private int months;
	private int days;
	
	public Age() {
		super();
	}
	
	public Age(int years, int months, int days) {
		super();
		this.years = years;
		this.months = months;
		this.days = days;
	}
	public int getYears() {
		return years;
	}
	public void setYears(int years) {
		this.years = years;
	}
	public int getMonths() {
		return months;
	}
	public void setMonths(int months) {
		this.months = months;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	@Override
	public String toString() {
		return years + " years, " + months + " months and " + days + " days";
	}
	
	public static int calculateAgeInYears(Date dateOfBirth, Date countFrom){
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(dateOfBirth);
	    
	     Calendar now = Calendar.getInstance();
	     now.setTime(countFrom);
	     
	     int res = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
	     if((cal.get(Calendar.MONTH) > now.get(Calendar.MONTH))
	       || (cal.get(Calendar.MONTH) == now.get(Calendar.MONTH)
	       && cal.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH)))
	     {
	        res--;
	     }
	     return res;
	}
	
	/**
			<br>1 tahun 11 bulan masih bayi
			<br>boolean infantAge = age.getYears() < 2 && age.getMonths() <= 11 && age.getDays() < 1;
			<p>12 tahun kurang sehari masih anak
			<br>boolean childAge = infantAge == false && (age.getYears() < 12);
			<p>12 tahun pas dianggap adult
			<br>boolean adultAge = age.getYears() >= 12;
			<p>
1 years, 11 months and 0 days (infantAge=true, childAge=false,adultAge=false)
<br>1 years, 0 months and 0 days (infantAge=true, childAge=false,adultAge=false)
<br>37 years, 7 months and 24 days (infantAge=false, childAge=false,adultAge=true)
<br>12 years, 0 months and 0 days (infantAge=false, childAge=false,adultAge=true)
<br>11 years, 11 months and 29 days (infantAge=false, childAge=true,adultAge=false)

	 * @param dateOfBirth
	 * @param countfrom
	 * @return
	 */
	  public static Age calculateAge(Date dateOfBirth, Date countfrom){
		  int years = 0;
		  int months = 0;
		  int days = 0;
		  //create calendar object for birth day
		  Calendar birthDay = Calendar.getInstance();
		  birthDay.setTime(dateOfBirth);

		  //create calendar object for current day
//		  long currentTime = System.currentTimeMillis();
		  Calendar currentDay = Calendar.getInstance();
		  currentDay.setTime(countfrom);
		   
		  //Get difference between years
		  years = currentDay.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
		   
		   
		  int currMonth = currentDay.get(Calendar.MONTH)+1;
		  int birthMonth = birthDay.get(Calendar.MONTH)+1;
		   
		  //Get difference between months
		  months = currMonth - birthMonth;
		   
		  //if month difference is in negative then reduce years by one and calculate the number of months.
		  if(months < 0)
		  {
		   years--;
		   months = 12 - birthMonth + currMonth;
		    
		   if(currentDay.get(Calendar.DATE)<birthDay.get(Calendar.DATE))
		    months--;
		    
		  }else if(months == 0 && currentDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE)){
		   years--;
		   months = 11;
		  }
		   
		   
		  //Calculate the days
		  if(currentDay.get(Calendar.DATE)>birthDay.get(Calendar.DATE))
		   days = currentDay.get(Calendar.DATE) -  birthDay.get(Calendar.DATE);
		  else if(currentDay.get(Calendar.DATE)<birthDay.get(Calendar.DATE)){
		   int today = currentDay.get(Calendar.DAY_OF_MONTH);
		   currentDay.add(Calendar.MONTH, -1);
		   days = currentDay.getActualMaximum(Calendar.DAY_OF_MONTH)-birthDay.get(Calendar.DAY_OF_MONTH)+today;
		  }else{
		   days=0;
		    
		   if(months == 12){
		    years++;
		    months = 0;
		   }
		  }
		  
		  Age age = new Age(years, months, days);
		  return age;
	  }
	  public static final int getMonthsDifference(Date date1, Date date2) {
		    int m1 = date1.getYear() * 12 + date1.getMonth();
		    int m2 = date2.getYear() * 12 + date2.getMonth();
		    return m2 - m1;
		}  
	 
	  public static boolean isInfantAge(Age age){
			boolean infantAge = age.getYears() < 1 || (age.getYears() < 2 && age.getMonths() <= 11);
			if (infantAge && (age.getYears() == 1 && age.getMonths() == 11)){
				infantAge = age.getDays() < 1;
			}
		  
			return infantAge;
	  }
	  
	  public static boolean isChildAge(Age age){
			boolean infantAge = isInfantAge(age);
			return infantAge == false && (age.getYears() < 12);
	  }
	  
	  public static boolean isAdultAge(Age age){
			return age.getYears() >= 12;
	  }
	  
	 public static void tesAge(Date dob, Date countFrom){
			Age age = calculateAge(dob, countFrom);

			//1 tahun 11 bulan masih bayi
			boolean infantAge = isInfantAge(age);

	//12 tahun kurang sehari masih anak
			boolean childAge = isChildAge(age);
	//12 tahun pas dianggap adult
			boolean adultAge = isAdultAge(age);
			
			System.err.println(age.toString() + " (infantAge=" + infantAge + ", childAge=" + childAge + ",adultAge=" + adultAge + ")");
		 
	 }
	 
	  public static void main(String[] args) {
		  System.out.println(getMonthsDifference(Utils.String2Date("05/06/2013", "dd/MM/yyyy"), Utils.String2Date("05/06/2013", "dd/MM/yyyy")));
		  
			tesAge(Utils.String2Date("05/06/2013", "dd/MM/yyyy"), new Date());
			tesAge(Utils.String2Date("05/05/2014", "dd/MM/yyyy"), new Date());
			tesAge(Utils.String2Date("11/09/1977", "dd/MM/yyyy"), new Date());
			tesAge(Utils.String2Date("05/05/2003", "dd/MM/yyyy"), new Date());
			tesAge(Utils.String2Date("06/05/2003", "dd/MM/yyyy"), new Date());
			tesAge(Utils.String2Date("21/12/2014", "dd/MM/yyyy"), Utils.String2Date("20/05/2015", "dd/MM/yyyy"));

	  }

}
