package org.appraisalparrot.repository;

import java.util.Date;
import java.util.List;

public class Employee {
	public String firstName ;
	public String lastName;
	public Date hireDate ;
	public String email;
	public List<Responder> contacts;
  public Integer stage;	// TODO change back to ENUM if using Jsonparser
  public List<String> superiors;
  
  public List<String> questions;
	public List<String> answers;
	public String link;
  
  Employee(String firstName,String lastName,Date hireDate,String email, List<Responder> contacts,Integer stage,List<String> superiors){
  	this.firstName = firstName;
  	this.lastName= lastName;
  	this.hireDate = hireDate;
  	this.email= email;
  	this.contacts= contacts;
  	this.stage=	stage;
  	this.superiors = superiors;
    
  	
  }
  public Employee(){}
}
