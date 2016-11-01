package org.appraisalparrot.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.activation.DataSource;

import org.appraisalparrot.service.Communicator;
import org.appraisalparrot.service.Datastore;
import org.appraisalparrot.service.EmailCommunicator;
import org.appraisalparrot.service.MongoDatastore;

public class AppraisalParrotApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Datastore dataStore = selectDatastore(args);
		Communicator comm = selectComm(args);
		comm.broadcastToResponders(dataStore.findUnresponsiveInStore());
		comm.broadcastToEmployees(dataStore.employeesEmailsWithoutContacts());
	}
	
	private static EmailCommunicator selectComm(String[] args){
		return (args != null && args.length > 0) 
			?	new EmailCommunicator(loadProps(args[0]))
		  :	new EmailCommunicator(loadProps());
	}
	private static MongoDatastore selectDatastore(String[] args){
		return (args != null && args.length > 0) 
		? new MongoDatastore(loadProps(args[0]))
		:	new MongoDatastore(loadProps());
	}
	
	private static Properties loadProps(){
		Properties properties = new Properties();
		try {
			properties.load(AppraisalParrotApplication.class.getResourceAsStream("config.txt"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("redownload or rebuild because default file is corrupted");
		}
		return properties;
	}
	
	
//Produces property file from fileName or default property file stored in jar 
	private static  Properties loadProps(String fileName){
		Properties properties = new Properties();
		if(fileName != null ){
			try (InputStream in = new FileInputStream(fileName)){
				properties.load(in);
				return properties;
			} catch (IOException e) {
				System.out.println("file provide caused an error attempting to use default values");
				return loadProps();
			}
		}
		properties = loadProps();
		return properties;
	}

	private static String findFileName(String filename){
		if(filename.startsWith("../")) return findRoot(filename,Paths.get(System.getProperty("user.dir")));
		return Paths.get(filename).toString();
	}
	
	private static String findRoot(String fileName,Path path){
		if(!fileName.startsWith("..") || path.toString().isEmpty()) return path +"/"+ fileName;
		else return findRoot(fileName.substring(3),path.getParent());
	}
}
