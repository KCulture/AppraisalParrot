package org.appraisalparrot.repository;

import com.mongodb.BasicDBObject;

public class Responder extends BasicDBObject{
  private static final long serialVersionUID = 1L;
	
	public Responder(){}
	
	public Responder(String fname,String email){
		super.put("fname",fname);
		super.put("email", email);
		
	}

	public String getFname() {
		return super.getString("fname");
	}

	public void setFname(String fname) {
		super.put("fname",fname);
	}

	public String getEmail() {
		return super.getString("email");
	}

	public void setEmail(String email) {
		super.put("email", email);
	}
	

}
