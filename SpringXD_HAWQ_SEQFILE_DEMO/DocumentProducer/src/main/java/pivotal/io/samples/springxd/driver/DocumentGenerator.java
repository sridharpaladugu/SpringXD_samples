/**
 * 
 */
package pivotal.io.samples.springxd.driver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pivotal.io.samples.springxd.model.DocumentRecord;
import pivotal.io.samples.springxd.producer.ExcelProducer;
import pivotal.io.samples.springxd.producer.PdfProducer;
import pivotal.io.samples.springxd.producer.PngProducer;
import pivotal.io.samples.springxd.producer.SequenceFileIO;
//import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author palads1
 *
 */

public class DocumentGenerator {
	private static final Logger LOG = LoggerFactory.getLogger(DocumentGenerator.class);

	private String dropboxLocation;
	private int recordSize;

	public void generate(){
		List<DocumentRecord> documentRecords = new ArrayList<DocumentRecord> ();
		ClassPathXmlApplicationContext ctx = null;
		try{
			ctx = new ClassPathXmlApplicationContext("classpath:application-context.xml");
			System.setProperty("dropbox.location", dropboxLocation);
			System.setProperty("records.size", ""+recordSize);
			PdfProducer pdfProducer = ctx.getBean("pdfProducer", PdfProducer.class);
			ExcelProducer excelProducer = ctx.getBean("excelProducer", ExcelProducer.class);
			PngProducer pngProducer = ctx.getBean("pngProducer", PngProducer.class);
			SequenceFileIO sequenceFileIO = ctx.getBean("sequenceFileIO", SequenceFileIO.class);
			LOG.info("Start Generating records .......");
			for (int count=0; count<recordSize; count++){			
				DocumentRecord pdfRecord = pdfProducer.document();
				documentRecords.add(pdfRecord);
				DocumentRecord excelRecord = excelProducer.document();
				documentRecords.add(excelRecord);
				DocumentRecord imageRecord = pngProducer.document();
				documentRecords.add(imageRecord);
			}
			LOG.info("finished steps. Saving Records to File ........");
			String path;
			try {
				path = sequenceFileIO.saveToSequenceFile(dropboxLocation, documentRecords);
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);		
			}
			LOG.info("finished Task. File is at : " + path);
		}finally{
			if(null!=ctx)	ctx.close();
		}
	}

	public String getDropboxLocation() {
		return dropboxLocation;
	}

	public void setDropboxLocation(String dropboxLocation) {
		this.dropboxLocation = dropboxLocation;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

}
