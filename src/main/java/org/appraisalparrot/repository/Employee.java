package org.appraisalparrot.repository;

import java.util.Date;
import java.util.List;

public class Employee {
	public String firstName ;
	public String lastName;
	public Date hireDate ;
	public String email;
	public List<Responder> contacts;
  public Stage stage;	
}
