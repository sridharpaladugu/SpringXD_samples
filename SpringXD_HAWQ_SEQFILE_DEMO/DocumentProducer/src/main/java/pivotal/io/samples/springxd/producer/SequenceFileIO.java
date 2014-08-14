package pivotal.io.samples.springxd.producer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pivotal.io.samples.springxd.model.DocumentRecord;
import pivotal.io.samples.springxd.util.*;

import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("sequenceFileIO")
@Scope("prototype")
public class SequenceFileIO {
	private Configuration conf;
	private static final Logger LOG = LoggerFactory.getLogger(SequenceFileIO.class);

	public SequenceFileIO() {
		conf = new Configuration();
		conf.set("fs.defaultFS", "file://./");

	}
	public String saveToSequenceFile(String dropboxLocation,
			List<DocumentRecord> records) throws URISyntaxException,
			IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("SPXDocuments_").append(Util.getDateWithTime1())
				.append(".bin");
		String fileName = sb.toString();
		URI outputURI = new URI(dropboxLocation);
		String fullPath = outputURI.getPath() + Path.SEPARATOR + fileName;
		FileSystem fs = FileSystem.get(URI.create(fullPath), conf);
		Path path = new Path(dropboxLocation + Path.SEPARATOR + fileName);
		SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf, path,
				Text.class, DocumentRecord.class);
		for (DocumentRecord record : records) {
			Text key = new Text(UUID.randomUUID().toString());
			writer.append(key, record);
		}
		IOUtils.closeStream(writer);
		return fullPath;

	}

	public List<DocumentRecord> readSequenceFile(String filefullPath)
			throws IOException {
		List<DocumentRecord> returnData = new ArrayList<DocumentRecord>();
		FileSystem fs = FileSystem.get(URI.create(filefullPath), conf);
		Path p = new Path(filefullPath);
		SequenceFile.Reader reader = new SequenceFile.Reader(fs, p, conf);
		Text key = (Text) ReflectionUtils.newInstance(reader.getKeyClass(),
				conf);
		DocumentRecord documentRecord = (DocumentRecord) ReflectionUtils
				.newInstance(reader.getValueClass(), conf);
		try {
			while (reader.next(key, documentRecord)) {
				returnData.add(documentRecord);
			}
		} catch (EOFException e) {
			LOG.warn("Got EOF");
		}
		IOUtils.closeStream(reader);
		return returnData;
	}

}
