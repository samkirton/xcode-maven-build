package com.memtrip.xcodebuild.bash;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author memtrip
 */
public class ExecProcess {
	private ProcessBuilder processBuilder;
	private Process process;
	private ArrayList<String> output;
	
	public static final int XCODE_SUCCESS = 0;
	public static final int UNIX_SUCCESS = 1;
	
	public ExecProcess(ProcessBuilder processBuilder) {
		this.processBuilder = processBuilder;
		output = new ArrayList<String>();
	}
	
	/**
	 * Start executing the process
	 * @return	The result of the process
	 */
	public int start() {
		int result = -1;
		
		try {
			process = processBuilder.start();
			Scanner scanner = new Scanner(process.getInputStream());
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				output.add(line);
				output.add("\n");
			}
			scanner.close();
			result = process.waitFor();
		} catch (IOException e) {
		} catch (InterruptedException e) {
		}
		
		return result;
	}
	
	/**
	 * @return	The output of the process
	 */
	public ArrayList<String> getOutput() {
		return output;
	}
}
