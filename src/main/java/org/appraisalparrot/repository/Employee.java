package org.appraisalparrot.repository;

import java.util.Date;
import java.util.List;

public class Employee {
	public String firstName ;
	public String lastName;
	public Date hireDate ;
	public String email;
	public List<Responder> contacts;
  public Integer stage;	
  
  Employee(String firstName,String lastName,Date hireDate,String email, List<Responder> contacts,Integer stage){
  	this.firstName = firstName;
  	this.lastName= lastName;
  	this.hireDate = hireDate;
  	this.email= email;
  	this.contacts= contacts;
  	this.stage=	stage;
    
  	
  }
  public Employee(){}
}
