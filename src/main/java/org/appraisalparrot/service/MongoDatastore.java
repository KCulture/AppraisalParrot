package org.appraisalparrot.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.appraisalparrot.repository.EmployeeAndUnresponsives;
import org.appraisalparrot.repository.Responder;
import org.appraisalparrot.repository.Stage;

import com.mongodb.DB;
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
		this.initDatabase();
	}
	
	private MongoClient initDatabase(){
		try{
			if(this.props != null){
				mongoClient = new MongoClient( this.props.getProperty("mongo.server"), 
						Integer.valueOf(this.props.getProperty("mongo.port")));
				mongoDB = mongoClient.getDB(this.props.getProperty("mongo.database"));
				cursor = mongoDB.getCollection(this.props.getProperty("mongo.appraised.collection")).find();
			}
		}catch(UnknownHostException noHost){
			noHost.addSuppressed(noHost);
			System.out.println(noHost.getMessage());
		}
		return mongoClient;
	}
	public DB getDB(){
		return mongoDB;
	}

	@Override
	public List<EmployeeAndUnresponsives> findUnresponsiveInStore() {
		DBCursor localCursor = cursor.copy();
		List<EmployeeAndUnresponsives> employees = this.getUnresponsive(localCursor);
		return employees;
	}
	
	
	private List<EmployeeAndUnresponsives> getUnresponsive(DBCursor localCursor) {
		List<EmployeeAndUnresponsives> nonResponders = new ArrayList<>(); 
	  for(DBObject mongoObject:localCursor){
	  	if(this.doesEmployeeUnresponsiveContracts(mongoObject)){
	  		nonResponders.add(createEmployeeAndUnresponsive(mongoObject));
	  	} 
	  }
	  return nonResponders;
  }
	
	private boolean doesEmployeeUnresponsiveContracts(DBObject mongoObject){
		return mongoObject.get("contacts") != null && 
				Stage.CONTACTS.ordinal() <= ((Integer) mongoObject.get("stage"))
						&& Stage.REPONSE.ordinal() > ((Integer) mongoObject.get("stage"))  ; 
	}

	private EmployeeAndUnresponsives createEmployeeAndUnresponsive(
      DBObject mongoObject) {
	  EmployeeAndUnresponsives eau = new EmployeeAndUnresponsives();
	  eau.name = ((String) mongoObject.get("firstname") + " " + (String) mongoObject.get("lastname"));
	  //eau.emails = createResponders(mongoObject); TODO remove if possible
	  eau.emails = (List<Responder>) mongoObject.get("contacts");
	  return eau;
  }

	// change emails is assigned to return of function 
	public List<String> employeesEmailsWithoutContacts(){
		List<String> emails = new ArrayList<>();
		if(cursor != null){
			DBCursor localCursor = cursor.copy();
			for(DBObject mongoObject: localCursor){
				if(this.emailedWithEmptyContacts(mongoObject)) {
					emails.add((String)mongoObject.get("email"));
				}
			}
		}
		return emails;
	}

	private boolean emailedWithEmptyContacts(DBObject mongoObject) {
	 return  Stage.EMPLOYEE_EMAILED.ordinal() <= ((Integer)mongoObject.get("stage")) &&
			 ((Integer)mongoObject.get("stage")) < Stage.CONTACTS.ordinal() && 
			 (((List<Responder>)mongoObject.get("contacts")) == null 
					 || ((List<Responder>)mongoObject.get("contacts")).isEmpty());
	}
	
	

}
