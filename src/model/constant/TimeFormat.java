package model.constant;

public enum TimeFormat {
MILLIS("yyyy-MM-dd HH:mm:ss.SSS");
	
	private String formatString; 
	
	TimeFormat(String formatString){
		this.formatString  = formatString;
	}
	
	public String getFormatString(TimeFormat format){
		return formatString;
	}
}
