package org.appraisalparrot.application;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

public class AppTest {
	  private static final String RELATIVE_PROPS = "../Notified/propfile";
		private final AppraisalParrotApplication application = new AppraisalParrotApplication();
	  
	  @BeforeClass
	  public static void ClassSetup(){
	  	
	  	
	  }
    
	    @Test
    public void loadDefaultProps() {
    assertTrue("loadProperty is not returning property", this.application.loadProps() instanceof Properties  );
    }
	    
    @Test
    public void defaultWithHostAndPort() {
    Properties props = this.application.loadProps();
    assertTrue("loadProperty should load a property",  
    		props.getProperty("mail.smtp.host") != null && props.getProperty("mail.smtp.port") != null
    		&& props.getProperty("mongo.port") != null && props.getProperty("mongo.server") != null);
    }
    
//    @Test
//    public void userProps(){
//    	this.application.loadProps("../");
//    }
    
    @Test
    public void findFile(){
    	assertTrue("the path is not being return as expected", 
    			this.application.findFileName(RELATIVE_PROPS).equalsIgnoreCase("/home/lando/workspace/Notified/propfile"));
    }
    
    @Test
    public void loadPropByFile(){
    	String filename = this.application.findFileName(RELATIVE_PROPS);
    	Properties props = this.application.loadProps(filename);
    	assertTrue("The filename didn't work and the and the config.txt didn't work",props.getProperty("mail.smtp.host") != null && props.getProperty("mail.smtp.port") != null
      		&& props.getProperty("mongo.port") != null && props.getProperty("mongo.server") != null);
    }
    
    @Test
    public void loadPropWithNullFile(){
    	Properties props = this.application.loadProps(null);
    	assertTrue("The filename didn't work and the and the config.txt didn't work",props.getProperty("mail.smtp.host") != null && props.getProperty("mail.smtp.port") != null
      		&& props.getProperty("mongo.port") != null && props.getProperty("mongo.server") != null);
    }
}
