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
	private String xcodebuildExecParam;
	
	/**
	 * The location of the project file
	 * @parameter
	 */
	private String projectDirParam;
	
	/**
	 * An optional scheme that the xcodebuild should target
	 * @parameter
	 */
	private String schemeParam;

	/**
	 * projectDirParam
	 */
	public void setProjectDir(String newVal) {
		projectDirParam = newVal;
	}
	
	/**
	 * scheme
	 */
	public void setScheme(String newVal) {
		schemeParam = newVal;
	}
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (xcodebuildExecParam == null) 
			xcodebuildExecParam = DEFAULT_XCODEBUILD_EXEC;
		
		// execute the xcode process
		ArrayList<String> output = executeXcodeProcess();
		System.out.println(StringUtils.arrayListOut(output));
	}
	
	/**
	 * Execute an xcode process
	 * @return	The output of the process
	 * @throws MojoExecutionException
	 */
	private ArrayList<String> executeXcodeProcess() throws MojoExecutionException {
		// create the xcodebuild command
		XcodeProcessBuilder xcodeProcessBuilder = new XcodeProcessBuilder(xcodebuildExecParam);
		xcodeProcessBuilder.setDirectory(projectDirParam);
		xcodeProcessBuilder.setScheme(schemeParam);
		xcodeProcessBuilder.setConfiguration(true, false);
		ProcessBuilder processBuilder = xcodeProcessBuilder.getProcessBuilder();
		
		// execute the xcodebuild process
		ExecProcess execProcess = new ExecProcess(processBuilder);
		int result = execProcess.start();
		System.out.println(execProcess.getOutput());
		
		if (result != ExecProcess.XCODE_SUCCESS)
			throw new MojoExecutionException("xcodebuild FAILED");
		
		return execProcess.getOutput();
	}
}	