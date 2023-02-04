package org.mql.java.examples;

import org.mql.java.controllers.DocumentManagmentSystem;
import org.mql.java.models.User;

public class Picker extends User implements Runnable {
	private Thread runner;
	private DocumentManagmentSystem m;
	private String keyword;

	public Picker(String email, String firstName, String lastName, String password, DocumentManagmentSystem m, String keyword) {
		super(email, firstName, lastName, password);
		this.m = m;
		runner = new Thread(this, "Picker");
		this.keyword = keyword;
	}
	
	public void start() {
		runner.start();
	}

	@Override
	public void run() {
		do {	
			m.searchDocument(keyword);
			pause(20000);
		}
		while (true);
	}
	
	public static void pause(long time) {
		try {
			Thread.sleep(time);
		}
		catch (Exception e) {}
	}
}
