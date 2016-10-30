package org.appraisalparrot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.appraisalparrot.repository.Responder;
import org.appraisalparrot.repository.Stage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MongoStoreTest {
	private static Properties props ;
	private static MongoDatastore mongo;
	private static DBCollection collection;
	
	
	@BeforeClass
	public static void classSetup(){
		props = new Properties();
		try{
			props.load(MongoStoreTest.class.getResourceAsStream("../application/config.txt"));
		}catch(IOException IOE){
			Assert.fail("What did you do?");
		}
		mongo = new MongoDatastore(props);
		collection = mongo.getDB().getCollection("appraised");
		collection.remove(new BasicDBObject());
	}
	
	@Test
	public void emailsOfEmployeeWithoutContacts(){
		Assert.assertTrue("not returning list",mongo.employeesEmailsWithoutContacts() instanceof List);
	}
	
	//TODO next test will require inserting Employees with contacts and stages
	
		@Test 
		public void insertSomeEmployees(){
			List<Responder> responder1 = Arrays.asList(new Responder("greg","Ayougo@oh.org"),
					new Responder("steve","Byouknow@oh.org"), new Responder("crest","Cyouso@oh.org"));
			
			DBObject employee1 = new BasicDBObject("firstName","s") 
			.append("lastName", "a").append("hireDate", Calendar.getInstance().getTime())
			.append("email", "a@b.c").append("contacts", responder1).append("stage",Stage.START.ordinal());
			
			DBObject employee2 = new BasicDBObject("firstName","w") 
			.append("lastName", "c").append("hireDate", Calendar.getInstance().getTime())
			.append("email", "wqq@be.c").append("contacts", null).append("stage",Stage.EMPLOYEE_EMAILED.ordinal());
			
			DBObject employee3 = new BasicDBObject("firstName","quill") 
			.append("lastName", "bean").append("hireDate", Calendar.getInstance().getTime())
			.append("email", "a@b.c").append("contacts", responder1).append("stage",Stage.PARTIAL_CONTACTS.ordinal());
			
			DBObject employee4 = new BasicDBObject("firstName","coda") 
			.append("lastName", "turkey").append("hireDate", Calendar.getInstance().getTime())
			.append("email", "a@b.c").append("contacts", responder1).append("stage",Stage.PARTIAL_EMAILED.ordinal());
			
			DBObject employee7 = new BasicDBObject("firstName","cord") 
			.append("lastName", "salad").append("hireDate", Calendar.getInstance().getTime())
			.append("email", "a@b.c").append("contacts", responder1).append("stage",Stage.PARTIAL_RESPONSE.ordinal());
			
			DBObject employee8 = new BasicDBObject("firstName","cord") 
			.append("lastName", "salad").append("hireDate", Calendar.getInstance().getTime())
			.append("email", "a@b.c").append("contacts", responder1).append("stage",Stage.EMAILED.ordinal());
			
			DBObject employee5 = new BasicDBObject("firstName","wan") 
			.append("lastName", "kiwi").append("hireDate", Calendar.getInstance().getTime())
			.append("email", "w@be.c").append("contacts", responder1).append("stage",Stage.CONTACTS.ordinal());
			
			DBObject employee6 = new BasicDBObject("firstName","green") 
			.append("lastName", "stower").append("hireDate", Calendar.getInstance().getTime())
			.append("email", "w@be.c").append("contacts", null).append("stage",Stage.REPONSE.ordinal());
			
			collection.insert(Arrays.asList(employee1,employee2,employee3,employee4,
					employee5,employee6,employee7,employee8));
			
			Assert.assertTrue("count is "+collection.count(), collection.count() == 8L);
		}
		
		@Test
		public void count(){
			Assert.assertTrue("the size is not 4 the size is "+ this.mongo.findUnresponsiveInStore().size(),
					this.mongo.findUnresponsiveInStore().size()== 4);
		}
		
		@Test
		public void countEmployeeWithNoResponders(){
			Assert.assertTrue("the size is not 4 the size is "+ this.mongo.findUnresponsiveInStore().size(),
					this.mongo.employeesEmailsWithoutContacts().size()== 1);
		}
		
		@Test
		public void employeeEmailWithNoResponders(){
			Assert.assertTrue("the size is not 4 the size is "+ this.mongo.findUnresponsiveInStore().size(),
					this.mongo.employeesEmailsWithoutContacts().get(0).equalsIgnoreCase("wqq@be.c"));
		}
		
}
