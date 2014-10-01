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
		if (scheme != null) {
			commandList.add("-scheme");
			commandList.add(scheme);
		}
	}
	
	public void setProvisioningProfile(String provisioningProfile) {
		if (provisioningProfile != null) {
			commandList.add("PROVISIONING_PROFILE=" + provisioningProfile + "");
		}
	}
	
	public void setCodeSigningIdentity(String codeSigningIdentity) {
		if (codeSigningIdentity != null) {
			commandList.add("CODE_SIGN_IDENTITY=\"" + codeSigningIdentity + "\"");
		}
	}
	
	public void setConfiguration(boolean isRelease, boolean isClean) {
		commandList.add("-configuration");
		
		if (isRelease) {
			commandList.add("Release");
		} else {
			commandList.add("Debug");
		}
		
		if (isClean)
			commandList.add("clean");
	}
	
	public void setBuildDir(String buildDir) {
		if (buildDir != null)
			commandList.add("BUILD_DIR="+buildDir);
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
