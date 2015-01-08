/**
 * 
 */
package pivotal.io.springxd.transformer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.integration.transformer.AbstractPayloadTransformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * @author palads1
 *
 */
public class CsvToJsonTransformer extends AbstractPayloadTransformer<String, String>{
    @Override
	public String transformPayload(String payload) throws Exception{
		String jsonStr = null;
		CsvSchema schema = CsvSchema.emptySchema().withHeader(); 
		CsvMapper mapper = new CsvMapper();
			List<Map<?, ?>> data = readCSV(payload, schema, mapper);
			jsonStr = writeJSON(data);
		return jsonStr;
	}

	private String writeJSON(List<Map<?, ?>> data)
			throws JsonProcessingException {
		String jsonStr;
		ObjectMapper wmapper = new ObjectMapper();
		wmapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		jsonStr = wmapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
		return jsonStr;
	}

	private List<Map<?, ?>> readCSV(String payload, CsvSchema schema,
			CsvMapper mapper) throws IOException, JsonProcessingException {
		MappingIterator<Map<?,?>> it = mapper.reader(Map.class)
				   .with(schema)
				   .readValues(payload);
		List<Map<?, ?>> data = it.readAll();
		return data;
	}
	
}
