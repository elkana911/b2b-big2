
Latest bug:
none



AppFuse Basic Spring MVC Archetype
--------------------------------------------------------------------------------
If you're reading this then you've created your new project using Maven and
b2b.  You have only created the shell of an AppFuse Java EE
application.  The project object model (pom) is defined in the file pom.xml.
The application is ready to run as a web application. The pom.xml file is
pre-defined with Hibernate as a persistence model and Spring MVC as the web
framework.

To get started, please complete the following steps:

1. Download and install a MySQL 5.x database from 
   http://dev.mysql.com/downloads/mysql/5.0.html#downloads.

2. Run "mvn jetty:run" and view the application at http://localhost:8080.

3. More information can be found at:

   http://appfuse.org/display/APF/AppFuse+QuickStart

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date deliveryDate;

=====================================================
INSTALASI:
1. mvn install:install-file -Dfile=F:\mobile-projects\Reusable-Libraries\Java\ojdbc14.jar -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.4.0 -Dpackaging=jar
2. mvn install:install-file -Dfile=F:\mobile-projects\Reusable-Libraries\Java\displaytag-1.3-SNAPSHOT.jar -DgroupId=displaytag -DartifactId=displaytag -Dversion=1.3 -packaging=jar
3. Window>Preferences>Maven>Download repository index updates on startup. Restart Eclipse. Tergantung koneksi, indexing might take 1 hour
4. Eclipse marketplace>Sts Tools:Eclipse Quicksearch, Spring IDE Core, Spring IDE Maven Support
4. Run>Maven>maven install
5. buka pom.xml
6. edit <properties> bagian bawah:
	<dropTable>false</dropTable>
	<skipTests>false</skipTests>
7. buka hibernate.properties
	hibernate.hbm2ddl.auto=create
EMAIL:
1. buka applicationContext-service.xml, bagian <bean id="mailSender...
2. buka mail.properties
	 
DEVELOPMENT:
1. buka pom.xml
2. edit <properties> bagian bawah:
	<dropTable>false</dropTable>
	<skipTests>true</skipTests>
3. buka hibernate.properties
	hibernate.hbm2ddl.auto=update

MIGRASI KE KUNINGAN:
1. buka pom.xml
2. edit <properties> bagian bawah:
	<dropTable>false</dropTable>
	<skipTests>true</skipTests>
3. buka hibernate.properties
	hibernate.hbm2ddl.auto=update

LOGIN ADMIN:
admin/admin

=====================================================
This project created automatically using maven repositories.
Features:
	spring
	hibernate
	web service
	report ?

Progress:

2014-09-12 di agoda, sehabis mencari daftar hotel, saat klik Book Now, menampilkan hotelDtl new tab. di spring tidak bisa, harus dilakukan via html. 
But how ?
Sedangkan hotelDtl kuusahakan tidak perlu controller. hopefully bisa diatur di HotelSearchController saja, alasannya di hotelDtl tidak ada form. 
Biasanya aku berpikiran, kalo ada Form, berarti butuh ModelAttribute

Blank ?
start with web.xml


FAQ:
see evernote
WHAT IS //link 920CBAEF564C496F9A89D6E21E0ACD4C ?
to link related important codes due to plenty of source files.
By using the pattern, enable me to search linked codes if there are any changes.
to generate uuid, i use oracle SELECT SYS_GUID() FROM dual;
I wish eclipse can do that.

i need quick uuid generation for a oracle table ?

insert into <tableName>(ID)values(SYS_GUID()) 

---------------------------------------------------------
HOW searching transits are possible. lupa aq.
istransit = 1,2,3 dst

How to send email:
emailManager.sendTopUpATMConfirm(true, agentVO, topUp);


HOW TO add/create NEW PAGE with Request / Response handler ?
1. define new jsp. but decide either use Binding bean or not.
<easy>
2. add/create controller for request
<quite easy>
use GET,POST concept. GET will be load onload, POST when submit something, so there are 2 methods at beginning.

3. response handler
<confuse>
4. done
For example: see UpdateRouteController.java


---------------------------------------------------------
HOW TO check date expired from today ?
where sysdate >= timetopay


HOW TO use web service ?
b2b use soap with user/pass
https://www.evernote.com/shard/s100/nl/11306326/fe6b99ec-d3a6-4df7-9187-cb05276fa1f6

HOW TO href ?
<a href="${ctx}/home" class="btn btn-primary">
<a href="mailto:<c:out value="${user.email}"/>">
<a href="<c:url value='/admin/airlines?q=${routeId.airline.airlineName}'/>">
onclick="window.location.href='./flightdtl/${flights.flightdetid}'"><fmt:message key="button.book" /></button>

HOW TO change database configuration ?
pom.xml
	search for  <!-- Database settings -->

HOW TO use parameter variables at applicationResources.properties ?
in jsp:
    <fmt:message key="login.signup">
        <fmt:param><c:url value="/signup"/></fmt:param>
    </fmt:message>
in java extends BaseFormController:
	getText("agent.deleted", agent.getAgent_Name(), locale)


WHAT IS the step-by-step to add Master CRUD ?
1. start with model package
2. dao package, to implements hibernate (pastikan akan digunakan utk suatu entity, jangan utk multi entity, contoh BookingFlightDaoHibernate)
3. service package as bridge for jsp to dao (manager, isinya banyak dao supaya bisa rollback jika salah satu dao ada yg error)
4. create controller
5. build jsp

Contoh ManyToOne ada di BookingFlightVO.java
	@ManyToOne
	@JoinColumn(name="paymentid")
	public PaymentVO getPayment() {
		return payment;
	}
	@ManyToOne
	@JoinColumn(name="flightdetid")
	public FlightDetailsVO getFlightDetails() {
		return flightDetails;
	}
	@ManyToOne
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}

Contoh ENUM dalam table:
@Enumerated(EnumType.STRING)	kalau mau insert string
@Column(name = "category_type")
private CategoryType categoryType;
	@Enumerated(EnumType.ORDINAL)	kalau mau insert integer
	public Gender getGender() {
	    return gender;
	}
http://www.codejava.net/frameworks/hibernate/hibernate-enum-type-mapping-example

CONTOH TRANSIT > 1x
Sriwijaya: Balikpapan(BPN) - Padang(PDG) 16/12/2014

CONTOH TRANSIT > 2x


ABOUT BOOKING:
ada 2 table utama, 
BOOKINGFLIGHT berisi kode booking dan harga 
BOOKINGFLIGHTDETAIL yg berisi data penumpangnya


memodifikasi file2 berikut dpt dilihat langsung hasilnya tanpa restart server:
jsp
xml
html

adokumen terkait:
Mindjet:
Engine

Balsamiq:

Office:


Penggunaan FareInfo di commission diperlukan utk menentukan range commission yang tergantung dari suatu rute.
gara2 lion air.

***************************************************************************************************
BOOTSTRAP CSS:
btn btn-default
btn btn-primary
btn btn-sm			tombol kecil. contoh di userList.jsp
checkbox-inline 	contoh di searchform-home.jsp
control-label 		blm tau efeknya. contoh di searchform-home.jsp
form-control 		membuat tampilan kotak2 input dan select menjadi gaya bootstrap. contoh di userList.jsp
form-horizontal		contoh di userList.jsp
form-group 			dapat membuat 2 tombol seolah2 dipasangin. contoh di userList.jsp
text-right			membawa isi konten kearah kanan. contoh di userList.jsp

***************************************************************************************************



--------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------
IF YOU WANT TO:
--------------------------------------------------------------------------------------------------------------------------------------------
Q:Use another DAO within a DAO
A:see /b2b-big2/src/main/java/com/big/web/b2b_big2/booking/dao/hibernate/BookingFlightDaoHibernate.java

Q:Modify email message
A: /b2b/src/main/resources/passwordRecovery.vm

Q:Change email configuration
A:/b2b/src/main/resources/mail.properties

Q:play with Captcha
A: /b2b/src/main/java/com/big/web/b2b/webapp/controller/agent/SignupAgentController.java
must use berleha16@gmail account 

Q:OneToOne sample
A:As Master
/b2b/src/main/java/com/big/web/model/User.java
As Detail
/b2b/src/main/java/com/big/web/b2b/agent/model/AgentVO.java
Reference:
http://howtodoinjava.com/2012/11/15/hibernate-one-to-one-mapping-using-annotations/
http://www.javabeat.net/hibernate-one-to-one-mapping-annotations/

Q:Delete user account:
delete from mst_agents
delete from user_account
delete from user_role
jika bingung, use toad,select app_user, Referential tab


Q: How to map FlightSelect ?
var IDX_PLANECOUNTER = -1;
var IDX_CLASS = -8;

var IDX_LIST_INDEX = -7
var LENGTH_LIST_INDEX = 6;

var IDX_AIRLINE_CODE = -10;
var LENGTH_AIRLINE_CODE = 2;

4ef2c29d-e585-48f0-a935-352d7d39fa53SJQ0000011
51c08b1a-3553-4afd-aba9-8677d939c246SJQ0000012
1234567890123456789012345678901234567890123456
        10        20        30        40        
6543210987654321098765432109876543210987654321
                         20        10


                                                  
