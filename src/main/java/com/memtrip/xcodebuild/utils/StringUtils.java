package com.memtrip.xcodebuild.utils;

import java.util.ArrayList;

/**
 * @author memtrip
 */
public class StringUtils {
	public static String UNIVERSAL_BUILD_TARGET = "Release-iphoneuniversal";
    
    /**
     * Resolve the xcodebuild symlink path from the provided line in file
     * @param	line	A line from a file
     * @return	The xcodebuild symlink path
     */
    public static String resolveSymlinkPath(String line) {
		String[] pathSplit = line.split("-resolve-src-symlinks");
		String copy = pathSplit[pathSplit.length-1];
		String[] copySplit = copy.split(" ");
		String path = copySplit[copySplit.length-1];
		String[] artefactDir = path.split(UNIVERSAL_BUILD_TARGET);
		return artefactDir[0];
    }
    
    /**
     * Covert the provided array list to a string
     * @param	arrayList	The array list to convert to a string
     * @return	The arrayList as a string
     */
    public static String arrayListOut(ArrayList<String> arrayList) {
    	StringBuilder sb = new StringBuilder();
    	for (String string : arrayList)
    		sb.append(string);	

    	return sb.toString();
    }
}
