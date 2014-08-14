/**
 * 
 */
package pivotal.io.samples.springxd.producer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import pivotal.io.samples.springxd.model.DocumentRecord;
import pivotal.io.samples.springxd.model.DocumentType;
import pivotal.io.samples.springxd.util.Util;

/**
 * @author palads1
 *
 */
@Component("pngProducer")
public class PngProducer {
	@Value("#{systemProperties['dropbox.location']}")
	private String dropboxLocation;
	@Autowired
	DocumentIO documentIO;
	
	/**
	 * create a Document record with a byte representation of image
	 * @return Document
	 */
	public DocumentRecord document() {
		String generatedFile = create() ;
		byte[] documentBytes = documentIO.readFile(generatedFile);
		FileUtils.deleteQuietly(new File(generatedFile));
		DocumentRecord record = new DocumentRecord(
				documentIO.extractFileName(generatedFile),
				DocumentType.IMAGE.getTypeDescription(), 
				documentBytes);
		return record;
	}
	
	private String create() {
		String fileName = dropboxLocation+"/"+DocumentType.IMAGE.getFilename();
		try {
	      int width = 600, height = 100;
	      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	      Graphics2D ig2 = bi.createGraphics();
	      Font font = new Font("TimesRoman", Font.BOLD, 30);
	      ig2.setFont(font);
	      String msg1 = "Hello and Welcome To Pivotal!";
	      FontMetrics fontMetrics = ig2.getFontMetrics();
	      int stringWidth = fontMetrics.stringWidth(msg1);
	      int stringHeight = fontMetrics.getAscent();
	      ig2.setPaint(Color.WHITE);
	      ig2.drawString(msg1, (width - stringWidth) / 2, height / 4 + stringHeight / 8);
	      Graphics2D ig3 = bi.createGraphics();
	      ig3.setFont(font);
	      String msg2 = Util.getDateWithTime();
	      stringWidth = fontMetrics.stringWidth(msg2);
	      stringHeight = fontMetrics.getAscent();
	      ig3.setPaint(Color.WHITE);
	      ig3.drawString(msg2, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
	      ImageIO.write(bi, "PNG", new File(fileName));
		} catch (IOException e) {
		     throw new RuntimeException("Error creating Image. ", e);
		}
		return fileName;
	}

}
