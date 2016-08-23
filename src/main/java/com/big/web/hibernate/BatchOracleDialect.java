package com.big.web.hibernate;

import org.hibernate.dialect.Oracle10gDialect;

/*
 * to fix Encountered request for locking however dialect reports that database prefers locking be done in a separate select (follow-on locking); results will be locked after initial query executes
 * 
 * http://reach2ramesh.blogspot.com/2013/11/hibernate-usefollowonlocking-warning.html
 */
public class BatchOracleDialect extends Oracle10gDialect{
    @Override
    public boolean useFollowOnLocking() {
       return false;
    }
}
