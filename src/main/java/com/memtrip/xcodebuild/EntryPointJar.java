package com.memtrip.xcodebuild;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * A test entry point for debugging the plugin
 * @author	memtrip
 */
public class EntryPointJar {
	public static void main(String[] args) {
		try {
			// Provide these values to test the plugin from the java entry point
			EntryPoint entryPoint = new EntryPoint();
			entryPoint.setProjectDir("");
			entryPoint.setBuildDir("");
			entryPoint.setScheme("");
			entryPoint.setProvisioningProfile("");
			entryPoint.execute();
		} catch (MojoExecutionException e) {
		} catch (MojoFailureException e) {}
	}
}