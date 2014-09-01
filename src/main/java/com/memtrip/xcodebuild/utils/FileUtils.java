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
		
		System.out.println("RUNNING COPY:");
		System.out.println("cp -R " + buildProductDir + " " + projectBuildDir);
		
		processBuilder.directory(new File(processDirectory));
		processBuilder.redirectErrorStream(true);
		return processBuilder;
	}
	
	/**
	 * 
	 * @param projectBuildDir
	 * @param sysPassword
	 * @return
	 */
	public static final ProcessBuilder chmodArtefact(String projectBuildDir, String sysPassword) {
		ProcessBuilder processBuilder = new ProcessBuilder(
			"/bin/bash",
			"-c",
			"echo " + sysPassword + "| sudo -S chmod -R 777 " + projectBuildDir
		);
		
		processBuilder.directory(new File(projectBuildDir));
		processBuilder.redirectErrorStream(true);
		return processBuilder;
	}
}
