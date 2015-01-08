/**
 * 
 */
package pivotal.io.springxd.transformer;

import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

/**
 * @author palads1
 *
 */
public class CsvToJsonTest {
	private static final Logger logger = Logger.getLogger(CsvToJsonTest.class.getName());
	public CsvToJsonTransformer csvToJson;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		csvToJson = new CsvToJsonTransformer();
	}

	/**
	 * Test method for
	 * {@link pivotal.io.springxd.transformer.CsvToJsonTransformer#transform(java.lang.String)}
	 * .
	 */
	@Test
	public void testTransform() throws Exception {
		URL url = CsvToJsonTest.class.getClassLoader()
				.getResource("Sample.csv");
		Path path = Paths.get(url.toURI());
		String csv = new String(Files.readAllBytes(path));
		logger.info("------- converting CSV to JSON-----------+\n" + csv);
		String json = csvToJson.transformPayload(csv);
		logger.info("------- JSON --------------\n" + json);
		assertNotNull(json);
	}

}
