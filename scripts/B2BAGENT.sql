CREATE TABLE B2BAGENT
(
  ID        VARCHAR2(40 BYTE),
  NAME      VARCHAR2(100 BYTE)                  NOT NULL,
  EMAIL     VARCHAR2(100 BYTE),
  PHONE     VARCHAR2(30 BYTE),
  USERNAME  VARCHAR2(100 BYTE),
  PASSWORD  VARCHAR2(255 BYTE),
  AGENTID   VARCHAR2(40 BYTE)
)
;

CREATE UNIQUE INDEX B2BAGENT_PK ON B2BAGENT
(ID)
;
ALTER TABLE B2BAGENT ADD (
  CONSTRAINT B2BAGENT_PK
 PRIMARY KEY
 (ID)
    USING INDEX 
);
