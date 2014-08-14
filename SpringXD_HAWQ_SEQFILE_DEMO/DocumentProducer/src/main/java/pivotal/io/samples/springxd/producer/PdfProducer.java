package pivotal.io.samples.springxd.producer;

/**
 * @author palads1
 *
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import pivotal.io.samples.springxd.model.DocumentRecord;
import pivotal.io.samples.springxd.model.DocumentType;
import pivotal.io.samples.springxd.util.Util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Component("pdfProducer")
public class PdfProducer {

	@Value("#{systemProperties['dropbox.location']}")
	private String dropboxLocation;
	@Autowired
	DocumentIO documentIO;

	/**
	 * create a Document record with a byte representation of pdf document.
	 * @return Document
	 */
	public DocumentRecord document() {
		String generatedFile = create();
		byte[] documentBytes = documentIO.readFile(generatedFile);
		FileUtils.deleteQuietly(new File(generatedFile));
		DocumentRecord record = new DocumentRecord(
				documentIO.extractFileName(generatedFile),
				DocumentType.PDF.getTypeDescription(), documentBytes);
		return record;
	}
	
	private String create() {
		String fileName = dropboxLocation + "/" + DocumentType.PDF.getFilename();
		byte[] doc = createDocumentContent();
		try {
			FileUtils.writeByteArrayToFile(new File(fileName), doc);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return fileName;
	}

	private byte[] createDocumentContent() {
		byte[] returnData = new byte[1];
		Document pdfDocument = new Document();
		StringBuffer msg = new StringBuffer("Hello and Welcome To Pivotal!");
		msg.append("\n\t").append(Util.getDateWithTime());
		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(pdfDocument, baos);
			pdfDocument.open();
			pdfDocument.add(new Paragraph(msg.toString()));
			pdfDocument.close();
			returnData = baos.toByteArray();
		} catch (DocumentException e) {
			throw new RuntimeException("Error writing to document.", e);
		}
		return returnData;
	}
}
