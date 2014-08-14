package pivotal.io.samples.springxd.model;

import pivotal.io.samples.springxd.util.Util;

/**
 * 
 * @author palads1
 *
 */
public enum DocumentType {
	PDF{
		@Override
		public String getFilename() {
			return getFileName(".pdf");
		}

		@Override
		public String getTypeDescription() {
			return "Portable Document Format (Adobe Acrobat)";
		}
		
	},EXCEL{
		@Override
		public String getFilename() {
			return getFileName(".xls");
		}

		@Override
		public String getTypeDescription() {
			return "Microsoft Excel";
		}
	},IMAGE {
		@Override
		public String getFilename() {
			return getFileName(".png");
		}

		@Override
		public String getTypeDescription() {
			return "Portable Network Graphics (graphic file standard/extension)";
		}
	};
	
	public abstract String getFilename(); 
	
	public abstract String getTypeDescription();
	
	public static String getFileName(String extention) {
		StringBuffer sb =new StringBuffer();
		sb.append("SPXD_").append(Util.getDateWithTime1()).append(extention);
		return sb.toString() ;
	}
	public static String getTypeName(String extension) {
		if(extension.equalsIgnoreCase("pdf")){
			return "Portable Document Format (Adobe Acrobat)";
		}else if(extension.equalsIgnoreCase("xls")){
			return "Microsoft Excel";
		}if(extension.equalsIgnoreCase("png")){
			return "Portable Network Graphics (graphic file standard/extension)";
		}{
			return "UNIDENTIFIED";
		}
	}
}
