package org.appraisalparrot.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AppraisalParrotApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public Properties loadProps(){
		Properties properties = new Properties();
		try {
			properties.load(AppraisalParrotApplication.class.getResourceAsStream("config.txt"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("redownload or rebuild because default file is corrupted");
		}
		return properties;
	}
	
	public String findFileName(String filename){
		if(filename.startsWith("../")) return findRoot(filename,Paths.get(System.getProperty("user.dir")));
		return Paths.get(filename).toString();
	}
	
	public String findRoot(String fileName,Path path){
		if(!fileName.startsWith("..") || path.toString().isEmpty()) return path +"/"+ fileName;
		else return findRoot(fileName.substring(3),path.getParent());
	}
	
//Produces property file from fileName or default property file stored in jar 
	public  Properties loadProps(String fileName){
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

}
