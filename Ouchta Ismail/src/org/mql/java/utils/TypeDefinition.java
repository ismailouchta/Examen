package org.mql.java.utils;

import org.mql.java.models.DocType;

public class TypeDefinition {
	
	public static DocType extractType(String fileName) {
		
		if (fileName.equals("doc") || fileName.equals("docx")) {
			return DocType.WORD;
		} else if (fileName.equals("pdf")) {
			return DocType.PDF;
		} else if (fileName.equals("jar")) {
			return DocType.API;
		} else if (fileName.equals("gif") || fileName.equals("jpg") || fileName.equals("png")) {
			return DocType.IMAGE;
		} else if (fileName.equals("jar")) {
			return DocType.API;
		} else if (fileName.equals("png") || fileName.equals("mp4")) {
			return DocType.VIDEO;
		} else if (fileName.equals("wav") || fileName.equals("mp3")) {
			return DocType.AUDIO;
		} else {
			return null;
		}
	}
}
