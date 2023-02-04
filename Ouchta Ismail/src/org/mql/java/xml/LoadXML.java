package org.mql.java.xml;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mql.java.models.Collection;
import org.mql.java.models.DocType;
import org.mql.java.models.Resource;
import org.mql.java.models.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Uncomplete Class
public class LoadXML {

	private Document document;
	private Vector<Collection> collections;
	private Vector<User> users;

	@SuppressWarnings("unused")
	public LoadXML(String fileName) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(fileName));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		document.getDocumentElement().normalize();

		// Users
		users = new Vector<User>();
		NodeList usrs = document.getElementsByTagName("user");
		for (int i = 0; i < usrs.getLength(); i++) {
			Node node = usrs.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) node;

				String email = e.getAttribute("email");
				String firstName = e.getElementsByTagName("firstName").item(0).getTextContent();
				String lastName = e.getElementsByTagName("lastName").item(0).getTextContent();
				String password = e.getElementsByTagName("password").item(0).getTextContent();

				User user = new User(email, firstName, lastName, password);
				users.add(user);
			}
		}

		// Collections
		Vector<Collection> collections = new Vector<Collection>();
		NodeList cols = document.getElementsByTagName("collection");
		for (int i = 0; i < cols.getLength(); i++) {
			Node node = cols.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) node;

				String directoryName = e.getAttribute("directoryName");
//				String type = e.getAttribute("type");
				String email = e.getAttribute("owner");

				for (User u : users) {
					if (email.equals(u.getEmail())) {
						Collection collection = new Collection(directoryName, null, u);
						collections.add(collection);

						// Resource
						Vector<Resource> resources = new Vector<Resource>();
						NodeList res = document.getElementsByTagName("resource");
						for (int j = 0; j < res.getLength(); j++) {
							Node node2 = res.item(j);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								Element r = (Element) node2;

								String directoryNameR = r.getAttribute("simpleDirectoryName");
								String description = r.getAttribute("Description");

								for (User u2 : users) {
									if (email.equals(u.getEmail())) {
										Resource resource = new Resource(directoryName, description, u2);

										// Document
										@SuppressWarnings("unused")
										Vector<Document> documents = new Vector<Document>();
										NodeList docs = document.getElementsByTagName("document");

										for (int k = 0; k < docs.getLength(); k++) {
											Node node3 = docs.item(k);
											if (node.getNodeType() == Node.ELEMENT_NODE) {
												Element d = (Element) node3;

												String docName = d.getAttribute("name");
												String creationDate = d.getAttribute("creationDate");
												DocType docType = DocType.valueOf(d.getAttribute("type"));

												Vector<String> keywords = new Vector<String>();
												NodeList keys = d.getElementsByTagName("keyword");

											}
										}

										resources.add(resource);
									}
								}
							}
						}

						collection.setResources(resources);
					}
				}
			}
		}
	}

	public Vector<Collection> getCollections() {
		return collections;
	}

	public Vector<User> getUsers() {
		return users;
	}

	public static void main(String[] args) {
		new LoadXML("project.xml");
	}
}
