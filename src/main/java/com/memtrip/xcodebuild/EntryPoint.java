package com.memtrip.xcodebuild;

import java.util.ArrayList;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.memtrip.xcodebuild.bash.ExecProcess;
import com.memtrip.xcodebuild.bash.XcodeProcessBuilder;
import com.memtrip.xcodebuild.utils.StringUtils;

/**
 * @goal build
 * @requiresProject false
 */
public class EntryPoint extends AbstractMojo {
	/**
	 * The default location of xcodebuild on mac
	 */
	private static final String DEFAULT_XCODEBUILD_EXEC = "xcodebuild";
	
	/**
	 * The location of the xcodebuild executable
	 * @parameter
	 */
	private String xcodebuildExec;
	
	/**
	 * The location of the project file
	 * @parameter
	 */
	private String directory;
	
	/**
	 * The build directory
	 * @parameter
	 */
	private String buildDir;
	
	/**
	 * An optional -scheme that the xcodebuild should target
	 * @parameter
	 */
	private String scheme;
	
	/**
	 * An optional -project that the xcodebuild should target
	 * @parameter
	 */
	private String project;
	
	/**
	 * An optional -sdk that the xcodebuild should target
	 * @parameter
	 */
	private String sdk;
	
	/**
	 * An optional -archivePath that the xcodebuild should target
	 */
	private String archivePath;
	
	/**
	 * An optional -exportArchive that the xcodebuild should target
	 */
	private boolean exportArchive;
	
	/**
	 * An optional -exportFormat that the xcodebuild should target
	 */
	private String exportFormat;
	
	private String exportProvisioningProfile;
	
	private String exportPath;
	
	/**
	 * An optional comma seperated list of -configuration values (Release, Debug, Clean)
	 * @parameter
	 */
	private String configuration;
	
	/**
	 * An optional PROVISIONING_PROFILE constant 
	 * @parameter
	 */
	private String provisioningProfileConstant;
	
	/**
	 * An optional CODE_SIGNING_IDENTITY constant
	 * @parameter
	 */
	private String codeSigningIdentityConstant;

	/**
	 * projectDirParam
	 */
	public void setDirectory(String newVal) {
		directory = newVal;
	}
	
	/**
	 * scheme
	 */
	public void setScheme(String newVal) {
		scheme = newVal;
	}
	
	/**
	 * project
	 */
	public void setProject(String newVal) {
		project = newVal;
	}
	
	/**
	 * sdk
	 */
	public void setSDK(String newVal) {
		sdk = newVal;
	}
	
	/**
	 * archivePath
	 */
	public void setArchivePath(String newVal) {
		archivePath = newVal;
	}
	
	/**
	 * exportArchive
	 */
	public void setExportArchive(boolean newVal) {
		exportArchive = newVal;
	}
	
	/**
	 * exportFormat
	 */
	public void setExportFormat(String newVal) {
		exportFormat = newVal;
	}
	
	/**
	 * exportPath
	 */
	public void setExportPath(String newVal) {
		exportPath = newVal;
	}
	
	/**
	 * exportProvisioningProfile
	 */
	public void setExportProvisioningProfile(String newVal) {
		exportProvisioningProfile = newVal;
	}
	
	/**
	 * configuration
	 */
	public void setConfiguration(String newVal) {
		configuration = newVal;
	}
	
	/**
	 * buildDirParam
	 */
	public void setBuildDir(String newVal) {
		buildDir = newVal;
	}
	
	/**
	 * provisioningProfile
	 */
	public void setProvisioningProfileConstant(String newVal) {
		provisioningProfileConstant = newVal;
	}
	
	/**
	 * codeSigningIdentity
	 */
	public void setCodeSigningIdentityConstant(String newVal) {
		codeSigningIdentityConstant = newVal;
	}
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (xcodebuildExec == null) 
			xcodebuildExec = DEFAULT_XCODEBUILD_EXEC;
		
		// execute the xcode process
		ArrayList<String> output = executeXcodeProcess();
		System.out.println(StringUtils.arrayListOut(output,false));
	}
	
	/**
	 * Execute an xcode process
	 * @return	The output of the process
	 * @throws MojoExecutionException
	 */
	private ArrayList<String> executeXcodeProcess() throws MojoExecutionException {
		// create the xcodebuild command
		XcodeProcessBuilder xcodeProcessBuilder = new XcodeProcessBuilder(xcodebuildExec);
		xcodeProcessBuilder.setDirectory(directory);
		xcodeProcessBuilder.setBuildDir(buildDir);
		xcodeProcessBuilder.setScheme(scheme);
		xcodeProcessBuilder.setSdk(sdk);
		xcodeProcessBuilder.setProject(project);
		xcodeProcessBuilder.setArchivePath(archivePath);
		xcodeProcessBuilder.setExportArchive(exportArchive);
		xcodeProcessBuilder.setExportFormat(exportFormat);
		xcodeProcessBuilder.setExportPath(exportPath);
		xcodeProcessBuilder.setExportProvisioningProfile(exportProvisioningProfile);
		xcodeProcessBuilder.setConfiguration(StringUtils.buildArrayFromCommaSeperatedList(configuration));
		xcodeProcessBuilder.setProvisioningProfileConstant(provisioningProfileConstant);
		xcodeProcessBuilder.setCodeSigningIdentityConstant(codeSigningIdentityConstant);
		ProcessBuilder processBuilder = xcodeProcessBuilder.getProcessBuilder();
		
		// execute the xcodebuild process
		ExecProcess execProcess = new ExecProcess(processBuilder);
		int result = execProcess.start();
		System.out.println(StringUtils.arrayListOut(execProcess.getOutput(),false));
		
		if (result != ExecProcess.XCODE_SUCCESS)
			throw new MojoExecutionException("xcodebuild FAILED");
		
		return execProcess.getOutput();
	}
}	