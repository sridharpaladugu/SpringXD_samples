/**
 * 
 */
package pivotal.io.samples.springxd.driver;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author palads1
 *
 */
public class Driver {

	private static final Logger LOG = LoggerFactory.getLogger(Driver.class);

	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		readInputs();
		ClassPathXmlApplicationContext ctx = null;
		try{
			ctx = new ClassPathXmlApplicationContext("classpath:application-context.xml");
			DocumentGenerator documentGenerator = ctx.getBean("documentGenerator",DocumentGenerator.class);
			documentGenerator.generate();
		}finally{
			if(null!=ctx)	ctx.close();
		}
	}
	
	private static void readInputs() {
		String dropboxLocation = System.getProperty("dropbox.location");
		if(null == dropboxLocation) {
			dropboxLocation = System.getProperty("java.io.tmpdir");
			System.setProperty("dropbox.location", dropboxLocation);	
		}
		String records = System.getProperty("dropbox.location");
		if(null == records) {
			records = "100";
			System.setProperty("records.size", records);	
		}
		makeGreeting(dropboxLocation, records);
	}
	
	private static void makeGreeting(String dropboxLocation, String records) {
		StringBuffer msg = new StringBuffer();
		msg.append("Running DataProducer. \n")
		.append(records).append(" records will be written to folder")
		.append(dropboxLocation);
		LOG.info(msg.toString());
	}
	

}
