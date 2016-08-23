CREATE TABLE HOTEL_BOOKING
(
  ID                 VARCHAR2(40 BYTE),
  HOTELID            VARCHAR2(40 BYTE)          NOT NULL,
  GUEST_ID           VARCHAR2(40 BYTE),
  CHECKIN            DATE,
  CHECKOUT           DATE,
  BEDTYPE            VARCHAR2(50 BYTE),
  ROOMTYPE           VARCHAR2(50 BYTE),
  TOTAL_OUTSTANDING  VARCHAR2(20 BYTE),
  REMARKS            VARCHAR2(255 BYTE),
  STATUS             VARCHAR2(40 BYTE)
);

CREATE UNIQUE INDEX HOTEL_BOOKING_PK ON HOTEL_BOOKING
(ID);

ALTER TABLE HOTEL_BOOKING ADD (
  CONSTRAINT HOTEL_BOOKING_PK
 PRIMARY KEY
 (ID)
    USING INDEX 
);
