package org.mql.java.models;

import java.util.List;
import java.util.Vector;

public class User extends Thread {
	
	protected String email;
	protected String firstName;
	protected String lastName;
	protected String password;
	protected Vector<Resource> resources;
	
	public User(String email, String firstName, String lastName, String password) {
		super(email);
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.resources = new Vector<Resource>();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(Vector<Resource> resources) {
		this.resources = resources;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", resources=" + resources + "]";
	}
	
}
