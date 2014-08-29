package com.memtrip.xcodebuild;

import java.util.ArrayList;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.memtrip.xcodebuild.bash.ExecProcess;
import com.memtrip.xcodebuild.bash.XcodeProcessBuilder;
import com.memtrip.xcodebuild.utils.FileUtils;
import com.memtrip.xcodebuild.utils.StringUtils;

/**
 * @goal generate
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
	 * The maven build directory
	 * @parameter default-value="${project.build.directory}"
	 */
	private String mavenBuildDirectoryParam;

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
	
	public void setMavenBuildDirectory(String newVal) {
		mavenBuildDirectoryParam = newVal;
	}
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (xcodebuildExecParam == null) 
			xcodebuildExecParam = DEFAULT_XCODEBUILD_EXEC;
		
		// execute the xcode process
		ArrayList<String> output = executeXcodeProcess();
		System.out.println(StringUtils.arrayListOut(output));
		
		// get the symlinkPath from the xcodebuild output
		String symlinkPath = null;
		for (String line : output) {
			if (line.contains("-resolve-src-symlinks")) {
				symlinkPath = StringUtils.resolveSymlinkPath(line);
				break;
			}
		}
		
		// ensure the xcodebuild output can be resolved
		if (symlinkPath == null)
			throw new MojoExecutionException("Could not resolve xcodebuild output");
		
		// copy the xcodebuild output to a new directory
		executeCopyProcess(symlinkPath);
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
		ProcessBuilder processBuilder = xcodeProcessBuilder.getProcessBuilder();
		
		// execute the xcodebuild process
		ExecProcess execProcess = new ExecProcess(processBuilder);
		int result = execProcess.start();
		System.out.println(execProcess.getOutput());
		
		if (result != ExecProcess.XCODE_SUCCESS)
			throw new MojoExecutionException("xcodebuild FAILED");
		
		return execProcess.getOutput();
	}
	
	/**
	 * Copy the xcodebuild output to the maven directory
	 * @param	symlinkPath	The path of the xcodebuild output
	 * @throws MojoExecutionException
	 */
	private void executeCopyProcess(String symlinkPath) throws MojoExecutionException {
		ProcessBuilder processBuilder = FileUtils.copyArtefact(
			symlinkPath, 
			mavenBuildDirectoryParam + "/ios",
			projectDirParam
		);
		
		ExecProcess execProcess = new ExecProcess(processBuilder);
		int result = execProcess.start(); 
		
		if (result != ExecProcess.UNIX_SUCCESS) {
			throw new MojoExecutionException("Copying xcode artifacts failed"); 
		} else {
			System.out.println("**Files copied**");
		}
	}
}	