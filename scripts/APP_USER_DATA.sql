SET DEFINE OFF;
Insert into APP_USER
   (ID, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, CITY, COUNTRY, POSTAL_CODE, PROVINCE, CREDENTIALS_EXPIRED, EMAIL, ACCOUNT_ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, PASSWORD_HINT, USERNAME, VERSION, WEBSITE)
 Values
   (-1, 0, 0, 'Denver', 'US', '80210', 'CO', 0, 'matt_raible@yahoo.com', 1, 'Tomcat', 'User', '$2a$10$CnQVJ9bsWBjMpeSKrrdDEeuIptZxXrwtI6CZ/OgtNxhIgpKxXeT9y', 'A male kitty.', 'user', 1, 'http://tomcat.apache.org');
Insert into APP_USER
   (ID, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, CITY, COUNTRY, POSTAL_CODE, PROVINCE, CREDENTIALS_EXPIRED, EMAIL, ACCOUNT_ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, PASSWORD_HINT, USERNAME, VERSION, WEBSITE)
 Values
   (-2, 0, 0, 'Denver', 'US', '80210', 'CO', 0, 'matt@raibledesigns.com', 1, 'Matt', 'Raible', '$2a$10$bH/ssqW8OhkTlIso9/yakubYODUOmh.6m5HEJvcBq3t3VdBh7ebqO', 'Not a female kitty.', 'admin', 1, 'http://raibledesigns.com');
Insert into APP_USER
   (ID, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, CITY, COUNTRY, POSTAL_CODE, PROVINCE, CREDENTIALS_EXPIRED, EMAIL, ACCOUNT_ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, PASSWORD_HINT, USERNAME, VERSION, WEBSITE)
 Values
   (-3, 0, 0, 'Denver', 'US', '80210', 'CO', 0, 'two_roles_user@appfuse.org', 1, 'Two Roles', 'User', '$2a$10$bH/ssqW8OhkTlIso9/yakubYODUOmh.6m5HEJvcBq3t3VdBh7ebqO', 'Not a female kitty.', 'two_roles_user', 1, 'http://raibledesigns.com');
Insert into APP_USER
   (ID, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, CREDENTIALS_EXPIRED, EMAIL, ACCOUNT_ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, PASSWORD_HINT, USERNAME, VERSION)
 Values
   (2, 0, 0, 0, 'sdf@yahoo.com', 0, 'fwefwe', 'fwefwef', '$2a$10$TKH3hynAX2KMJ3jix4PD1.zQ7d.wkJ.mGQFsiHsgNkNfIqm0Oxdfu', 'sdfsd', 'fsdf', 0);
COMMIT;
