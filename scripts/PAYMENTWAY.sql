CREATE TABLE PAYMENTWAY
(
  ID        NUMBER,
  NAMA      VARCHAR2(25 BYTE),
  DISABLED  VARCHAR2(1 BYTE),
  ORDERID   INTEGER
);

CREATE UNIQUE INDEX PAYMENTWAY_PK ON PAYMENTWAY
(ID)
LOGGING
;

ALTER TABLE PAYMENTWAY ADD (
  CONSTRAINT PAYMENTWAY_PK
 PRIMARY KEY
 (ID)
    USING INDEX 
);
