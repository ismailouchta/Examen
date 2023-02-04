package org.mql.java.controllers;

import java.util.Set;
import java.util.Vector;

import org.mql.java.models.AnnotatedCollectionType;
import org.mql.java.models.Collection;
import org.mql.java.models.DocType;
import org.mql.java.models.Document;
import org.mql.java.models.Resource;
import org.mql.java.models.User;
import org.mql.java.xml.SaveXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentManagmentSystem {
	
	private Logger logger = LoggerFactory.getLogger(DocumentManagmentSystem.class);
	private Vector<User> users;
	private Vector<Collection> collections;
	
	public DocumentManagmentSystem() {
		users = new Vector<User>();
		collections = new Vector<Collection>();
	}
	
	public User createUser(String email, String firstName, String lastName, String password) {
		logger.trace("createUser()");
		boolean exists = checkUser(email);
		if (exists == false) {
			User user = new User(email, firstName, lastName, password);
			return user;
		}
		logger.error("Email is unique for each user.");
		return null;
	}
	
	public void addUser(User user) {
		boolean exists = false;
		for (User u : users)
			if (u.getEmail().equals(user.getEmail())) exists = true;
		
		if (exists == false) users.add(user);
		else logger.error("Cannot add User, already added before.");
	}
	
	public void runUser(User user) {
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		boolean running = false;
		for (Thread t : threadSet)
			if (user.getEmail().equals(t.getName())) running = true;
		
		if (running == false) user.start();
		else { logger.error("User already running.");}
	}
	
	synchronized public Collection createCollection(String email, AnnotatedCollectionType type, String directoryName) {
		boolean exists = checkCollection(directoryName);
		
		if (exists == false) {
			for (User u : users) {
				if (u.getEmail().equals(email)) {
					Collection collection = new Collection(directoryName, type, u);
					collections.add(collection);
					logger.info("collection created" + collection.toString());
					return collection;
				}
			}
			logger.error("Cannot create collection: "
					+ "User cound not be found with the email provided.");
		} 
		return null;
	}
	
	synchronized public boolean checkCollection(String directoryName) {
		
		for (Collection c : collections) {
			
			if (c.getDirectoryName().equals(directoryName)) {
				logger.error("Collection with the same name already exists");
				return true;
			}
		}
		
		return false;
	}

	synchronized public Resource createResource(Collection collection, String simpleDirectoryName, String description, User user) {
		boolean exists = false;
		for (Resource r : collection.getResources()) {
			if (r.getSimpleDirectoryName().equals(simpleDirectoryName)) {
				exists = true;
				logger.error("Resource already exists: choose another resource name.");
			}
		}
		
		if (exists == false) {			
			Resource r = new Resource(simpleDirectoryName, description, user);
			logger.info("Resource added" + r.toString());
			collection.addResource(r);
			return r;
		} else {
			return null;
		}
	}
	
	synchronized public void addDocument(Collection collection, Resource resource, String fileName, String date, Vector<String> keywords, DocType docType) {		
		if (docType != null) {
			if (collection.getType().accept(docType)) {
				
				Vector<Document> documents = resource.getDocuments();
				
				boolean exists = false;
				
				for (Document d : resource.getDocuments()) {
					if (d.getFileName().equals(fileName)) {
						exists = true;
						logger.error("Choose another document name");
					}
				}

				if (exists == false ) {
					Document doc = new Document(fileName, date, keywords, docType);
					documents.add(doc);
					resource.setDocuments(documents);
					logger.info("Document added" + doc.toString());
				}
				
			} else {
				logger.error("Type not supported on this collection.");
			}
		} else { logger.error("Type not supported at all."); }
		
	}
	
	synchronized public void shareResource(Collection c, Resource r, User u) {
		boolean exists = false;
		for (User user : c.getUsers())
			if (user.equals(u)) {
				logger.info("User aleady exists in Collection.");
				exists = true;
			}
		if (exists == false) {
			logger.info("User " + u.getEmail() + " Added to collection " + c.getDirectoryName());
			c.addUser(u);
		}
		
		exists = false;
		for (User user : r.getUsers())
			if (user.equals(u)) {
				logger.info("User aleady exists in Resource.");
				exists = true;
			}
		if (exists == false) {
			logger.info("User " + u.getEmail() + " Added to ressource " + r.getSimpleDirectoryName());
			r.allowAccess(u);
		}
	}
	
	synchronized public Document searchDocument(String keyword) {
		for (Collection c : collections) {
			for (Resource r : c.getResources()) {
				if (r.getDescription().contains(keyword)) {
					
					logger.info("Found Keyword in Resource : " + r.toString());
					
					for (Document d : r.getDocuments()) {

						if (d.getFileName().equals(keyword)) {
							logger.info("! Found Document : " + d.toString());
							return d;
						}

						for (String kw : d.getKeywords()) {
							if (kw.contains(keyword)) {
								logger.info("! Found Document : " + d.toString());
								return d;
							}					
						}
					}
					return null;
				}
			}
		}
		logger.info("Could not find anything, retrying next 20sec.");
		return null;
	}
	
	synchronized public boolean checkUser(String email) {
		for (User u : users)
			if (u.getEmail().equals(email)) 
				return true;
		return false;
	}
	
	public void save() {
		SaveXML xml = new SaveXML(collections, users);
		xml.save();
	}
	
//	public void load(String file) {
//		LoadXML xml = new LoadXML("project.xml");
//		users = xml.getUsers();
//		collections = xml.getCollections();
//	}
}
