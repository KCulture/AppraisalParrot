package org.appraisalparrot.service;

import java.util.List;

import org.appraisalparrot.repository.Employee;
import org.appraisalparrot.repository.EmployeeAndUnresponsives;

public interface Communicator {
  //
	public void broadcastToResponders(List<EmployeeAndUnresponsives> group);
	public void broadcastToEmployees(List<Employee> group);

}	
