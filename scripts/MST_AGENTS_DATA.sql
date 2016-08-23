SET DEFINE OFF;
Insert into MST_AGENTS
   (AGENT_ID, AGENT_NAME, ADDRESS1, CITY, ZIPCODE, COUNTRY_ID, WEBSITE1, PHONE1, EMAIL1, EMAIL2, LATITUDE, LONGITUDE, LASTUPDATE)
 Values
   (16, 'BERJAYA INOVASI GLOBAL', 'Ruko Tematik Pasar Modern
Paramount Serpong Blok ', 'Gading Serpong', '15810', 62, 'www.leha-leha.com', '021-29324380', 'cs@leha-leha.com', 'admin@leha-leha.com', '-6.249935', '106.622979', TO_DATE('09/30/2014 14:56:42', 'MM/DD/YYYY HH24:MI:SS'));
Insert into MST_AGENTS
   (AGENT_ID, AGENT_NAME, ADDRESS1, CITY, ZIPCODE, COUNTRY_ID, WEBSITE1, PHONE1, FAX1, EMAIL1, EMAIL2)
 Values
   (112, 'VOLTRAS AGENT NETWORK', 'Golden Boulevard S-50
Bumi Serpong Damai', 'Tangerang Selatan', '15323', 62, 'www.travelagent.co.id', '021-53163350', '021-53163352', 'sales@travelagent.co.id', 'helpdesk@travelagent.co.id');
Insert into MST_AGENTS
   (AGENT_ID, AGENT_NAME, ADDRESS1, CITY, COUNTRY_ID, WEBSITE1, PHONE1, FAX1, EMAIL1)
 Values
   (113, 'MANDIRA ABADI HOTEL', 'Jl. Taman Ubud Asri III / 25
Lippo Village', 'Tangerang', 62, 'www.mandiratravel.com', '081510334088', '021-59493189', 'inquiry@mandiratravel.com');
Insert into MST_AGENTS
   (AGENT_ID, AGENT_NAME, WEBSITE1)
 Values
   (212, 'SRIWIJAYA AIR AGENT', 'https://agent.sriwijayaair.co.id');
Insert into MST_AGENTS
   (AGENT_ID, AGENT_NAME, WEBSITE1)
 Values
   (213, 'LION AIR AGENT', 'https://agent.lionair.co.id/LionAirAgentsPortal/');
Insert into MST_AGENTS
   (AGENT_ID, AGENT_NAME, WEBSITE1)
 Values
   (214, 'AGODA', 'www.Agoda.com');
Insert into MST_AGENTS
   (AGENT_ID, AGENT_NAME, WEBSITE1, WEBSITE2)
 Values
   (3385, 'EXPEDIA AFFILIATE NETWORK', 'www.expediaaffiliate.com', 'www.ean.com');
COMMIT;
