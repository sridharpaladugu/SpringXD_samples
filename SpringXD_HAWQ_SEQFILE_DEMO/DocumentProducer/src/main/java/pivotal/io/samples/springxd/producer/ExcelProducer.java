/**
 * 
 */
package pivotal.io.samples.springxd.producer;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

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
@Component("excelProducer")
public class ExcelProducer {

	@Value("#{systemProperties['dropbox.location']}")
	private String dropboxLocation;
	@Autowired
	DocumentIO documentIO;
	/**
	 * create a Document record with a byte representation of excel document
	 * @return Document
	 */
	public DocumentRecord document() {
		String generatedFile = create();
		byte[] documentBytes = documentIO.readFile(generatedFile);
		FileUtils.deleteQuietly(new File(generatedFile));
		DocumentRecord record = new DocumentRecord(
				documentIO.extractFileName(generatedFile),
				DocumentType.EXCEL.getTypeDescription(), documentBytes);
		return record;
	}

	private String create() {
		String fileName = dropboxLocation+"/"+DocumentType.EXCEL.getFilename();
		File file = new File(fileName);
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(file, wbSettings);
			workbook.createSheet("Report", 0);
			WritableSheet excelSheet = workbook.getSheet(0);
			createLabel(excelSheet);
			workbook.write();
			workbook.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	private void createLabel(WritableSheet sheet) throws WriteException {
		WritableFont font = new WritableFont(WritableFont.TIMES, 40);
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		cellFormat.setWrap(false);
		WritableFont boldFont = new WritableFont(WritableFont.TIMES, 40,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat timesBold = new WritableCellFormat(boldFont);
		timesBold.setWrap(false);
		CellView cv = new CellView();
		cv.setFormat(cellFormat);
		cv.setFormat(timesBold);
		cv.setSize(200);
		Label label1 = new Label(0, 0, "Hello and Welcome To Pivotal!",
				timesBold);
		sheet.addCell(label1);
		Label label2 = new Label(0, 1, Util.getDateWithTime(), timesBold);
		sheet.addCell(label2);

	}
}
