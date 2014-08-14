/**
 * 
 */
package pivotal.io.samples.springxd.producer;

/**
 * @author palads1
 *
 */
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pivotal.io.samples.springxd.model.DocumentRecord;
import pivotal.io.samples.springxd.model.DocumentType;

@Component("documentIO")
public class DocumentIO {
	private static final Logger LOG = LoggerFactory.getLogger(DocumentIO.class);

	public  DocumentRecord readNCreateRecord(String location) {
		if (null == location)
			throw new RuntimeException("Location of pdf document is empty.");
		LOG.debug("Reading Document " + location);
		byte[] bytes = readFile(location);
		DocumentRecord document = new DocumentRecord(extractFileName(location),
				DocumentType.getTypeName(getFileExtension(location)), bytes);
		return document;

	}

	public  String extractFileName(String location) {
		int beginIndex = location.lastIndexOf("/");
		int endIndex = location.length();
		String fileName = location.substring(beginIndex, endIndex);
		return fileName;
	}
	
	public  String getFileExtension(String fileName) {
		int beginIndex = fileName.lastIndexOf(".");
		int endIndex = fileName.length();
		String extension = fileName.substring(beginIndex, endIndex);
		return extension;
	}

	public  byte[] readFile(String location) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			File file = new File(location);
			byte[] bytes = FileUtils.readFileToByteArray(file);
			return bytes;
		} catch (IOException e) {
			throw new RuntimeException("Error loading pdf document.", e);
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				LOG.warn("Error closing stream.", e);
			}
		}
	}
	
	
}
