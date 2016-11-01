package org.appraisalparrot.service;

import java.util.List;

import org.appraisalparrot.repository.Employee;
import org.appraisalparrot.repository.EmployeeAndUnresponsives;

public interface Datastore {
	public List<EmployeeAndUnresponsives> findUnresponsiveInStore();
	public List<Employee> employeesEmailsWithoutContacts();
}
