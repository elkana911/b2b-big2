package com.big.web.b2b_big2.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class RevListener implements RevisionListener {

	@Override
  public void newRevision(Object revisionEntity) {
      CustomRevInfoEntity revision = (CustomRevInfoEntity) revisionEntity;
      
      //akan throw exception karena Principal akan ngasih String kalau user gak ke authenticated, kalau iya dia akan ngembaliin objek userdetails
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
   
      if (!(auth instanceof AnonymousAuthenticationToken)) {

          // userDetails = auth.getPrincipal()
    	  UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    	  revision.setUsername(userDetails.getUsername());
      }else{
    	  revision.setUsername((String)auth.getPrincipal());
      }
      
  }
}
