SET DEFINE OFF;
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1, CALL_CENTER)
 Values
   (3290, 'LION AIR', 'JT', 62, 'www.lionair.co.id', '0804-1-77-88-99');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1)
 Values
   (4454, 'SRIWIJAYA AIR', 'SJ', 62, 'www.sriwijayaair.co.id');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1)
 Values
   (19305, 'CITILINK', 'QG', 62, 'www.citilink.co.id');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, HQ, COUNTRYID, WEBSITE1)
 Values
   (2520, 'GARUDA INDONESIA', 'GA', 'ffff', 62, 'www.garuda-indonesia.com');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, WEBSITE1)
 Values
   (2857, 'AIRASIA', 'QZ', 62, 'www.airasia.com/id/');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID, INACTIVE, INACTIVE_DATE)
 Values
   (4936, 'TIGER AIRWAYS', 'TR', 65, 'Y', TO_DATE('07/01/2014 00:00:00', 'MM/DD/YYYY HH24:MI:SS'));
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID)
 Values
   (5451, 'WINGS AIR', 'IW', 62);
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID)
 Values
   (18732, 'MALINDO AIR', 'OD', 60);
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, HQ, COUNTRYID, WEBSITE1)
 Values
   (1111, 'BATIK AIR', 'ID', 'gggtghjghjghjghj', 62, 'www.batikair.com');
Insert into MST_AIRLINES
   (AIRLINE_ID, NAMA, CODE, COUNTRYID)
 Values
   (19225, 'THAI LION AIR', 'SL', 66);
COMMIT;
