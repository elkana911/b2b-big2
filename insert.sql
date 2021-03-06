Insert into APP_USER
   (ID, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, CITY, COUNTRY, POSTAL_CODE, PROVINCE, CREDENTIALS_EXPIRED, EMAIL, ACCOUNT_ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, PASSWORD_HINT, USERNAME, VERSION, WEBSITE)
 Values
   (-2, 0, 0, 'Serpong', 'ID', '15210', 'CO', 0, 'eric.elkana@ppu.co.id', 1, 'Eric', 'Tarigan', '$2a$10$4LKPChYbspTiXyQ0O6ufXuCm/IpLOI5pV5eSjgvqUtz/pikD0UJHG', 'Not a female kitty.', 'admin', 1, 'http://www.ppu.co.id');

Insert into ROLE
   (ID, DESCRIPTION, NAME)
 Values
   (-1, 'Administrator role (can edit Users)', 'ROLE_ADMIN');
Insert into ROLE
   (ID, DESCRIPTION, NAME)
 Values
   (-2, 'Default role for all Users', 'ROLE_USER');
Insert into ROLE
   (ID, DESCRIPTION, NAME)
 Values
   (-3, 'Wholesaler role', 'ROLE_WHOLESALER');
Insert into ROLE
   (ID, DESCRIPTION, NAME)
 Values
   (-5, 'Administrator role for Mandira', 'ROLE_MANDIRA_ADMIN');
Insert into ROLE
   (ID, DESCRIPTION, NAME)
 Values
   (-6, 'Supervisor role for Mandira', 'ROLE_MANDIRA_SPV');
Insert into ROLE
   (ID, DESCRIPTION, NAME)
 Values
   (-4, 'Supervisor role', 'ROLE_SPV');

Insert into USER_ROLE
   (USER_ID, ROLE_ID)
 Values
   (-2, -1);

Insert into MST_COUNTRIES
   (COUNTRY_ID, COUNTRY_NAME, COUNTRY_CODE, CAPITAL, CURRENCY_CODE, CURRENCY_NAME)
 Values
   (62, 'Indonesia', 'ID', 'Jakarta', 'IDR', 'Rupiah');
Insert into MST_COUNTRIES
   (COUNTRY_ID, COUNTRY_NAME, COUNTRY_CODE, CAPITAL, CURRENCY_CODE, CURRENCY_NAME)
 Values
   (60, 'Malaysia', 'MY', 'Kuala Lumpur', 'MYR', 'Ringgit');
Insert into MST_COUNTRIES
   (COUNTRY_ID, COUNTRY_NAME, COUNTRY_CODE, CAPITAL, CURRENCY_CODE, CURRENCY_NAME)
 Values
   (65, 'Singapore', 'SG', 'Singapore', 'SGD', 'Dollar');
Insert into MST_COUNTRIES
   (COUNTRY_ID, COUNTRY_NAME, COUNTRY_CODE, CAPITAL, CURRENCY_CODE, CURRENCY_NAME)
 Values
   (66, 'Thailand', 'TH', 'Bangkok', 'THB', 'Baht');
Insert into MST_COUNTRIES
   (COUNTRY_ID, COUNTRY_NAME, COUNTRY_CODE, CAPITAL, CURRENCY_CODE, CURRENCY_NAME)
 Values
   (81, 'Japan', 'JP', 'Tokyo', 'JPY', 'Yen');

Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1, CALL_CENTER, AGENTID)
 Values
   (3290, 'LION AIR', 'JT', 62, 'www.lionair.co.id', '0804-1-77-88-99', '07A5AAE6E3B24926E0535A020A0A8066');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1, AGENTID)
 Values
   (4454, 'SRIWIJAYA AIR', 'SJ', 62, 'www.sriwijayaair.co.id', '07A5AAE6E3B14926E0535A020A0A8066');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1, AGENTID)
 Values
   (19305, 'CITILINK', 'QG', 62, 'www.citilink.co.id', '49C14970C4234A8EB4B41781ADF1982B');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1, AGENTID)
 Values
   (2520, 'GARUDA INDONESIA', 'GA', 62, 'www.garuda-indonesia.com', '0C1EC3A3D58D6BF3E0535A020A0AEB9E');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1, AGENTID)
 Values
   (2857, 'AIR ASIA', 'QZ', 62, 'www.airasia.com/id/', 'EF1EEC8AA73B429299C51217D27445E7');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, INACTIVE)
 Values
   (4936, 'TIGER AIRWAYS', 'TR', 65, 'Y');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, AGENTID)
 Values
   (5451, 'WINGS AIR', 'IW', 62, '07A5AAE6E3B24926E0535A020A0A8066');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, AGENTID)
 Values
   (18732, 'MALINDO AIR', 'OD', 60, '07A5AAE6E3B24926E0535A020A0A8066');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1, AGENTID)
 Values
   (1111, 'BATIK AIR', 'ID', 62, 'www.batikair.com', '07A5AAE6E3B24926E0535A020A0A8066');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID)
 Values
   (19225, 'THAI LION AIR', 'SL', 66);
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1)
 Values
   (4933, 'TRIGANA AIR', 'IL', 62, 'www.trigana-air.com');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, AGENTID)
 Values
   (14858, 'KALSTAR', 'KD', 62, '7E7D09072CD3432094A79EA487EB76A6');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1)
 Values
   (45000, 'EXPRESS AIR', 'XN', 62, 'www.xpressair.co.id');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1)
 Values
   (45001, 'SUSI AIR', 'SI', 62, 'fly.susiair.com');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1, AGENTID)
 Values
   (45002, 'NAM AIR', 'IN', 62, 'www.sriwijayaair.co.id', '07A5AAE6E3B14926E0535A020A0A8066');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1)
 Values
   (45003, 'AVIASTAR', 'MV', 62, 'http://www.aviastar.biz/');

Insert into MST_BANK
   (ID, CODE, NAME, ATMSUPPORT, AKA)
 Values
   ('D8514B23F2A349D892A12E3032721281', '014', 'BANK CENTRAL ASIA', 'Y', 'BCA');
Insert into MST_BANK
   (ID, CODE, NAME, VAN, AKA, VASUPPORT)
 Values
   ('C9B5B1338B914743B59E2F1DD2D1195A', '022', 'CIMB NIAGA', '1539', 'CIMB', 'Y');
Insert into MST_BANK
   (ID, CODE, NAME, AKA)
 Values
   ('6AC37AD5955D4FB9A32770D0674930FC', '013', 'PERMATA BANK', 'PERMATA');

Insert into MST_TERMINALS
   (TERMINAL_ID, TERMINAL_NAME, AIRPORT_ID)
 Values
   (1, '1A', '04F5A33A34200A70E0535A020A0A1BFD');
Insert into MST_TERMINALS
   (TERMINAL_ID, TERMINAL_NAME, AIRPORT_ID)
 Values
   (2, '1B', '04F5A33A34200A70E0535A020A0A1BFD');
Insert into MST_TERMINALS
   (TERMINAL_ID, TERMINAL_NAME, AIRPORT_ID)
 Values
   (3, '1C', '04F5A33A34200A70E0535A020A0A1BFD');
Insert into MST_TERMINALS
   (TERMINAL_ID, TERMINAL_NAME, AIRPORT_ID)
 Values
   (4, '2D', '04F5A33A34200A70E0535A020A0A1BFD');
Insert into MST_TERMINALS
   (TERMINAL_ID, TERMINAL_NAME, AIRPORT_ID)
 Values
   (5, '2E', '04F5A33A34200A70E0535A020A0A1BFD');
Insert into MST_TERMINALS
   (TERMINAL_ID, TERMINAL_NAME, AIRPORT_ID)
 Values
   (6, '2F', '04F5A33A34200A70E0535A020A0A1BFD');


   
   
   