/*please create USER_ROLE and APP_USER first*/

CREATE TABLE USER_ROLE
(
  USER_ID  NUMBER(19)                           NOT NULL,
  ROLE_ID  NUMBER(19)                           NOT NULL
)
;

ALTER TABLE USER_ROLE ADD (
  PRIMARY KEY
 (USER_ID, ROLE_ID)
    USING INDEX 
);

ALTER TABLE USER_ROLE ADD (
  CONSTRAINT FK143BF46A4FD90D75 
 FOREIGN KEY (ROLE_ID) 
 REFERENCES ROLE (ID),
  CONSTRAINT FK143BF46AF503D155 
 FOREIGN KEY (USER_ID) 
 REFERENCES APP_USER (ID));
