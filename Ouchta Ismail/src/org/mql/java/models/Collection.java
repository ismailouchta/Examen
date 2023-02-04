package org.mql.java.models;

import java.util.Vector;

public class Collection {
	
	private String directoryName;
	private AnnotatedCollectionType type;
	private User owner;
	private Vector<Collection> collections;
	private Vector<Resource> resources;
	private Vector<User> users;
	
	public Collection(String directoryName, AnnotatedCollectionType type, User owner) {
		super();
		this.directoryName = directoryName;
		this.type = type;
		this.owner = owner;
		this.collections = new Vector<Collection>();
		this.resources = new Vector<Resource>();
		this.users = new Vector<User>();
		
		users.add(owner);
	}
	
	public void addUser(User user) {
		for (Resource res : resources) {
				res.allowAccess(user);
		}
		users.add(user);
	}
	
	public void shareResource(User user, Resource resourceID) {
		for (Resource res : resources) {
			if (res.getSimpleDirectoryName().equals(resourceID))
				res.allowAccess(user);
		}
	}
	
	public void addCollection(Collection c) {
		c.type = getType();
		c.owner = getOwner();
		if (c.users.get(0) != owner) c.users.add(owner); // add this owner as user in there
		collections.add(c);
	}
	
	public void addResource(Resource r) {
		r.allowAccess(owner);
		resources.add(r);
	}

	public AnnotatedCollectionType getType() {
		return type;
	}

	public User getOwner() {
		return owner;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	public Vector<Collection> getCollections() {
		return collections;
	}

	public void setCollections(Vector<Collection> collections) {
		this.collections = collections;
	}

	public Vector<Resource> getResources() {
		return resources;
	}

	public void setResources(Vector<Resource> resources) {
		this.resources = resources;
	}

	public Vector<User> getUsers() {
		return users;
	}

	public void setUsers(Vector<User> users) {
		this.users = users;
	}

	public void setType(AnnotatedCollectionType type) {
		this.type = type;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Collection [directoryName=" + directoryName + ", type=" + type + ", owner=" + owner + ", collections="
				+ collections + ", resources=" + resources + ", users=" + users + "]";
	}
	
}
