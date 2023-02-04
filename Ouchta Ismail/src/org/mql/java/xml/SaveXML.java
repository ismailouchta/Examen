package org.mql.java.xml;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.mql.java.models.Collection;
import org.mql.java.models.Resource;
import org.mql.java.models.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SaveXML {
	private Document document;
	private Element gestionDoc, collections;
	
	public SaveXML(Vector<Collection> cols, Vector<User> usrs) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();

			document = dbuilder.newDocument();

			gestionDoc = document.createElement("GestionDocuments");
			document.appendChild(gestionDoc);

			collections = document.createElement("collections");
			gestionDoc.appendChild(collections);
			
			for (Collection c : cols) {
				Element collection = document.createElement("collection");
				collections.appendChild(collection);
				
				collection.setAttribute("directoryName", c.getDirectoryName());
				collection.setAttribute("type", c.getType().getClass().getSimpleName());
				collection.setAttribute("owner", c.getOwner().getEmail());
				
				writeResources(collection, c);
			}
			
			writeUsers(usrs);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	void writeResources(Element collection, Collection c) {
		for (Resource r : c.getResources()) {
			
			Element resource = document.createElement("resource");
			collection.appendChild(resource);
			
			resource.setAttribute("simpleDirectoryName", r.getSimpleDirectoryName());
			resource.setAttribute("Description", r.getDescription());
			
			writeDocuments(resource, r);
		}
	}
	
	void writeDocuments(Element resource, Resource r) {
		for (org.mql.java.models.Document d : r.getDocuments()) {
			Element doc = document.createElement("document");
			resource.appendChild(doc);
			
			doc.setAttribute("name", d.getFileName());
			doc.setAttribute("creationDate", d.getCreationDate());
			doc.setAttribute("type", d.getType().toString());
			
			Element keywords = document.createElement("keywords");
			doc.appendChild(keywords);
			
			for (String k : d.getKeywords()) {
				Element keyword = document.createElement("keywords");
				doc.appendChild(keyword);
				keyword.appendChild(document.createTextNode(k));
			}
			
		}
	}
	
	void writeUsers(Vector<User> usrs) {
		Element users = document.createElement("users");
		gestionDoc.appendChild(users);
		
		for (User u : usrs) {
			Element user = document.createElement("user");
			users.appendChild(user);
			
			user.setAttribute("email", u.getEmail());
			
			Element firstName = document.createElement("firstName");
			user.appendChild(firstName);
			firstName.appendChild(document.createTextNode(u.getFirstName()));
			
			Element lastName = document.createElement("lastName");
			user.appendChild(lastName);
			lastName.appendChild(document.createTextNode(u.getLastName()));
			
			Element password = document.createElement("password");
			user.appendChild(password);
			password.appendChild(document.createTextNode(u.getPassword()));
		}
	}
	
	public void save() {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File("project.xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
