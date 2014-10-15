package com.memtrip.xcodebuild.bash;

import java.io.File;
import java.util.ArrayList;

import com.memtrip.xcodebuild.utils.StringUtils;

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
	
	public void setProject(String project) {
		if (project != null) {
			commandList.add("-project");
			commandList.add(project);
		}
	}
	
	public void setSdk(String sdk) {
		if (sdk != null) {
			commandList.add("-sdk");
			commandList.add(sdk);
		}
	}
	
	public void setProvisioningProfileConstant(String provisioningProfile) {
		if (provisioningProfile != null) {
			commandList.add("PROVISIONING_PROFILE=" + provisioningProfile + "");
		}
	}
	
	public void setCodeSigningIdentityConstant(String codeSigningIdentity) {
		if (codeSigningIdentity != null) {
			commandList.add("CODE_SIGN_IDENTITY=\"" + codeSigningIdentity + "\"");
		}
	}
	
	public void setConfiguration(String[] values) {
		if (values != null && values.length > 0) {
			commandList.add("-configuration");
			
			for (String value : values) {
				if (value != null)
					commandList.add(value);
			}
		}
	}
	
	public void setExportArchive(boolean shouldExportArchive) {
		if (shouldExportArchive) {
			commandList.add("-exportArchive");
		}
	}
	
	public void setExportFormat(String format) {
		if (format != null) {
			commandList.add("-exportFormat");
			commandList.add(format);
		}
	}
	
	public void setExportProvisioningProfile(String exportProvisioningProfile) {
		if (exportProvisioningProfile != null) {
			commandList.add("-exportProvisioningProfile");
			commandList.add(exportProvisioningProfile);
		}
	}
	
	public void setExportPath(String exportPath) {
		if (exportPath != null) {
			commandList.add("-exportPath");
			commandList.add(exportPath);			
		}
	}
	
	public void setArchivePath(String archivePath) {
		if (archivePath != null) {
			commandList.add("-archivePath");
			commandList.add(archivePath);
		}
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
		System.out.println(" > " + StringUtils.arrayListOut(commandList,true));
		String[] commandArray = new String[commandList.size()];
		commandList.toArray(commandArray);
		ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
		
		if (directory != null)
			processBuilder.directory(new File(directory));
		
		processBuilder.redirectErrorStream(true);
		
		return processBuilder;
	}
}
