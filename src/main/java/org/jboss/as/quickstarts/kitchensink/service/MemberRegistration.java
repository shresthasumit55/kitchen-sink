/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.kitchensink.service;

import org.jboss.as.quickstarts.kitchensink.model.Member;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import java.util.Date;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class MemberRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;
    
    @Inject
    private Event<Member> memberEventSrc;
    
    public void editMember(Long id) throws Exception{
    	 Member newMember = em.find(Member.class, id);
    	 
    	 log.info("set info" + newMember.getId());
    	 newMember.setEmail("abcdef.gmail.com");
    	 em.merge(newMember);
  	   em.flush();
    }

    public void register(Member member) throws Exception {
    	String namePrev , emailPrev,  uniPrev;
    	Date dobPrev, dobAfter;
    	String nameAfter, emailAfter, uniAfter;
    	 
    			
    	if (member.getId()!=null) {
    		Member oldMember;
    		oldMember = findById(Long.valueOf(member.getId()));
        	
        	namePrev = oldMember.getName();
        	
        	emailPrev = oldMember.getEmail();
        	dobPrev = oldMember.getBirthDate();  
        	uniPrev = oldMember.getUniversity();
    		em.merge(member);
    		
    	nameAfter = member.getName();
    	emailAfter = member.getEmail();
    	dobAfter = member.getBirthDate();
    	uniAfter = member.getUniversity();
    	
    	 log.info("Updating a member: "+(member.getId()));
         if (!namePrev.equals(nameAfter)){
         	 log.info("Name:" + namePrev + "-> " +nameAfter);
         }
         if(!emailPrev.equals(emailAfter)) {
         	log.info("Email:" + emailPrev + "-> " +emailAfter);
         }

         if(!uniPrev.equals(uniAfter) ) {
         	log.info("University:" + uniPrev + "-> " +uniAfter);
         }

         if(!dobPrev.equals(dobAfter) ) {
         	log.info("Date of Birth:" + dobPrev + "-> " +dobAfter);
         }


    	}
    	else {
    		em.persist(member); 

    	}
    	        
        memberEventSrc.fire(member);
               
    }
    
    public Member findById(Long id) {
    	return em.find(Member.class, id);
    }
    
    public void delete(Member member ) throws Exception {
    	  log.info("Deleting " + member.getName());
    	  System.out.println("I am here");
    	  em.remove(member);
          
          memberEventSrc.fire(member);
    }
}
