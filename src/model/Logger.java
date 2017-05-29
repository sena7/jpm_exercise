package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import method.Helper;
import model.constant.TimeFormat;

public class Logger {
    private Helper helper = new Helper();
	
	
	private String path;
	private boolean append = false;
	private boolean logToFile = false;
	private boolean logToConsole = false;
    private List<String> consoleLogList;
    private List<String> fileLogList;
    
	/**
	 * The default constructor. The Logger will print texts to console
	 */
    public Logger(){
		this.logToConsole = true;
		this.consoleLogList = new ArrayList<>();
	}

	public List<String> getLogList() {
		return this.consoleLogList;
	}


	/**
	 * 
	 * @param filePath
	 * @param fileName
	 * @param fileExtension
	 * @param append
	 *            <p>
	 * The constructor for logging to a file
	 */
	public Logger(String filePath, String fileName, String fileExtension, boolean append, boolean logToConsole) {

		this.path = filePath + fileName + new SimpleDateFormat("_yyyyMMdd_HHmmss").format(new Date()) + "."
				+ fileExtension;
		this.append = append;
		this.logToFile = true;
		this.logToConsole = logToConsole;
		this.fileLogList = new ArrayList<>();

	}
	

	public boolean isLogToFile() {
		return logToFile;
	}

	public boolean isLogToConsole() {
		return logToConsole;
	}

	public void log(String text) throws IOException {
		
		if (this.isLogToConsole()) {
			logToConsole(text);
		}
		if (this.isLogToFile()) {
			logToFile(text);
		}
	}

	public void logToFile(String text) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path), append));
		writer.write(helper.getCurrentDate(TimeFormat.MILLIS));
		writer.newLine();
		writer.write(text);
		writer.newLine();
		writer.newLine();
		this.fileLogList.add(writer.toString());
		writer.close();
	}

	public void logToConsole(String text) throws IOException {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(helper.getCurrentDate(TimeFormat.MILLIS));
		sBuffer.append("\n");
		sBuffer.append(text + "\n");
		String msg = sBuffer.toString();
		System.out.println(msg);
		this.consoleLogList.add(msg);
	}

}
