SET DEFINE OFF;
Insert into ROLE
   (ID, DESCRIPTION, NAME)
 Values
   (-1, 'Administrator role (can edit Users)', 'ROLE_ADMIN');
Insert into ROLE
   (ID, DESCRIPTION, NAME)
 Values
   (-2, 'Default role for all Users', 'ROLE_USER');
COMMIT;
