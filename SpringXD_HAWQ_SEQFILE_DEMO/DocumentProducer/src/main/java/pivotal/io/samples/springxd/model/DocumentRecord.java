package pivotal.io.samples.springxd.model;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
/**
 * 
 * @author palads1
 *
 */
public class DocumentRecord implements Writable {

	public String name;
	public String type;
	public byte[] content;
	private int size;
	public DocumentRecord(){
		
	}
	
	public DocumentRecord(String name, String type, byte[] content) {
		super();
		this.name = name;
		this.type = type;
		this.content = content;
		this.size = content.length;
	}
	public byte[] getBytes() {
		return content;
	}

	public int getSize(){
		return this.size;
	}	
	public void readFields(DataInput in) throws IOException {
		name = in.readUTF();
		type = in.readUTF();
		size = in.readInt();
		content = new byte[size];
		in.readFully(content,0,size); 
		size = content.length;
	}
	public void write(DataOutput out) throws IOException {   
		out.writeUTF(name);
		out.writeUTF(type);
		out.writeInt(size);
		out.write(content, 0, size);
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte[] getcontent() {
		return content;
	}
	@Override
	public String toString() {
		return "DocumentRecord [name=" + name + ", type=" + type + ", size="
				+ size + "]";
	}

}

