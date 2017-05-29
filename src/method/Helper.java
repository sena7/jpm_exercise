package method;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.constant.TimeFormat;

public class Helper {
	
	public double roundDownDouble(double d, int decimalPosition){
		double operand = Math.pow(10, decimalPosition);
		return Math.round(d * operand)/operand;
	}

	public String getCurrentDate(TimeFormat format){
		
		return new SimpleDateFormat(format.getFormatString(format)).format(new Date());
		
	}
	
}
