package org.mql.java.models;

import java.util.Vector;

public class Document {
	
	private String fileName;
	private String creationDate;
	private Vector<String> keywords;
	private DocType type;
	
	public Document(String fileName, String creationDate, Vector<String> keywords2, DocType type) {
		super();
		this.fileName = fileName;
		this.creationDate = creationDate;
		this.keywords = keywords2;
		this.type = type;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Vector<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(Vector<String> keywords) {
		this.keywords = keywords;
	}

	public DocType getType() {
		return type;
	}

	public void setType(DocType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Document [fileName=" + fileName + ", creationDate=" + creationDate + ", keywords=" + keywords
				+ ", type=" + type + "]";
	}
	
}
