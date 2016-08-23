CREATE TABLE HOTEL_AREA
(
  ID          VARCHAR2(40 BYTE),
  NAME        VARCHAR2(100 BYTE)                NOT NULL,
  COUNTRY_ID  NUMBER,
  AGENT_ID    NUMBER,
  LASTUPDATE  DATE
);

CREATE UNIQUE INDEX HOTEL_AREA_PK ON HOTEL_AREA
(ID);

ALTER TABLE HOTEL_AREA ADD (
  CONSTRAINT HOTEL_AREA_PK
 PRIMARY KEY
 (ID)
    USING INDEX 
);