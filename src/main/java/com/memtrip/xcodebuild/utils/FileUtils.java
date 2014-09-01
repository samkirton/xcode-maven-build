package com.memtrip.xcodebuild.utils;

import java.io.File;

/**
 * @author memtrip
 */
public class FileUtils {

	/**
	 * Create a ProcessBuilder that copies the contents of the buildProductDir into the 
	 * provided projectBuildDir. The ProcessBuilder directory is set to the provided 
	 * processDirectory argument
	 * @param	buildProductDir	The contents to copy
	 * @param	projectBuildDir	The directory to copy the contents into
	 * @param	processDirectory	Where the process will run
	 * @return
	 */
	public static final ProcessBuilder copyArtefact(String buildProductDir, String projectBuildDir, String processDirectory, String sysPassword) {		
		ProcessBuilder processBuilder = new ProcessBuilder(
			"/bin/bash",
			"-c",
			"echo " + sysPassword + "| sudo -S cp -R " + buildProductDir + " " + projectBuildDir
		);
		
		processBuilder.directory(new File(processDirectory));
		processBuilder.redirectErrorStream(true);
		return processBuilder;
	}
}
