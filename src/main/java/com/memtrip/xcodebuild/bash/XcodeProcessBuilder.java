package com.memtrip.xcodebuild.bash;

import java.io.File;
import java.util.ArrayList;

/**
 * @author memtrip
 */
public class XcodeProcessBuilder {
	private ArrayList<String> commandList;
	private String directory;
	
	public void setScheme(String scheme) {
		commandList.add("-scheme");
		commandList.add(scheme);
	}
	
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
	public XcodeProcessBuilder(String xcodebuildExec) {
		commandList = new ArrayList<String>();
		commandList.add(xcodebuildExec);
	}
	
	public ProcessBuilder getProcessBuilder() {
		String[] commandArray = new String[commandList.size()];
		commandList.toArray(commandArray);
		ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
		
		if (directory != null)
			processBuilder.directory(new File(directory));
		
		processBuilder.redirectErrorStream(true);
		
		return processBuilder;
	}
}
