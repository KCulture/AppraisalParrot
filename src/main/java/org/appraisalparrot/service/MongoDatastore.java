package org.appraisalparrot.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.appraisalparrot.repository.EmployeeAndUnresponsives;
import org.appraisalparrot.repository.Responder;

import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDatastore implements Datastore {
	Properties props;
	
	private static  MongoClient mongoClient ;
	private static DB mongoDB = null;
	private static DBCursor cursor = null;
	
	public MongoDatastore(Properties props){
		this.props = props;
		
	}
	
	private MongoClient initDatabase(){
		try{
			if(this.props != null){
				mongoClient = new MongoClient( this.props.getProperty("mongo.server"), 
						Integer.valueOf(this.props.getProperty("mongo.port")));
				mongoDB = mongoClient.getDB(this.props.getProperty("mongo.database"));
				cursor = mongoDB.getCollection(this.props.getProperty("mongo.collection")).find();
			}
		}catch(UnknownHostException noHost){
			noHost.addSuppressed(noHost);
			System.out.println(noHost.getMessage());
		}
		return mongoClient;
	}

	@Override
	public List<EmployeeAndUnresponsives> findUnresponsiveInStore() {
		
		return employeeWithUnresponsive();
	}
	
	private List<EmployeeAndUnresponsives> employeeWithUnresponsive(){
		
		List<EmployeeAndUnresponsives> employees = new ArrayList<>();
		DBCursor localCursor = cursor.copy();
		employees.addAll(getUnresponsive(localCursor));
		return employees;
	}
  // TODO make sure to add contacts to employee
	private List<EmployeeAndUnresponsives> getUnresponsive(
      DBCursor localCursor) {
		List<EmployeeAndUnresponsives> nonResponders = new ArrayList<>(); 
	  for(DBObject mongoObject:localCursor){
	  	if(mongoObject.get("contacts") != null){
	  		nonResponders.add(createEmployeeAndUnresponsive(mongoObject));
	  	} 
	  }
	  return null;
  }

	private EmployeeAndUnresponsives createEmployeeAndUnresponsive(
      DBObject mongoObject) {
	  EmployeeAndUnresponsives eau = new EmployeeAndUnresponsives();
	  eau.name = ((String) mongoObject.get("firstname") + " " + (String) mongoObject.get("lastname"));
	  eau.emails = createResponders(mongoObject); 
	  return eau;
  }

	private List<Responder> createResponders(DBObject mongoObject) {
	   List<String> nameEmailsCode = ((List<String>) mongoObject.get("contacts"));
	   List<Responder> responders = processContacts(nameEmailsCode);
	  return responders;
  }

	private List<Responder> processContacts(List<String> nameEmailsCode) {
		List<Responder> responders = new ArrayList<>();
	  for(String nameEmailCode:nameEmailsCode){
	  	 String[]  nameEmail = nameEmailCode.split(":");
	  	 Responder responderEntry = new Responder();
	  	 responderEntry.fname = nameEmail[0];
	  	 responderEntry.email = nameEmail[1];
	  	 responders.add(responderEntry);
	   }
	  return responders ;
  }

}
