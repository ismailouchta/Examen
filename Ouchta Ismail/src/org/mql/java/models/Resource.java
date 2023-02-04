package org.mql.java.models;

import java.util.Vector;

public class Resource {
	
	private String simpleDirectoryName;
	private String description;
	private Vector<Document> documents;
	private Vector<User> users;
	
	public Resource(String simpleDirectoryName, String description, User user) {
		super();
		this.simpleDirectoryName = simpleDirectoryName;
		this.description = description;
		this.documents = new Vector<Document>();
		this.users = new Vector<User>();
		
		users.add(user);
	}

	public void renameDocument(String name) {
		for (Document doc : documents) {
			if (doc.getFileName().equals(name))
				doc.setFileName(name);
		}
	}
	
	public void deleteDocument(String name) {
		for (Document doc : documents) {
			if (doc.getFileName().equals(name))
				documents.remove(doc);
		}
	}
	
	public void allowAccess(User user) {
		users.add(user);
	}
	
	public void restrictAccess(User user) {
		for (User u : users) {
			if (u.equals(user))
				users.remove(u);
		}
	}

	public String getSimpleDirectoryName() {
		return simpleDirectoryName;
	}

	public void setSimpleDirectoryName(String simpleDirectoryName) {
		this.simpleDirectoryName = simpleDirectoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Vector<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Vector<Document> documents) {
		this.documents = documents;
	}

	public Vector<User> getUsers() {
		return users;
	}

	public void setUsers(Vector<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Resource [simpleDirectoryName=" + simpleDirectoryName + ", description=" + description + ", documents="
				+ documents + ", users=" + users + "]";
	}
	
}
