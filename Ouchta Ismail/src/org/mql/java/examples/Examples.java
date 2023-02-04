package org.mql.java.examples;

import org.mql.java.controllers.DocumentManagmentSystem;
import org.mql.java.models.Collection;
import org.mql.java.models.Film;
import org.mql.java.models.Work;

public class Examples {

	public Examples() {
		exp01();
	}
	
	void exp01() {
		DocumentManagmentSystem m = new DocumentManagmentSystem();

		Worker worker1 = new Worker("worker1@email.com", "Worker1", "worker1", "pass123", m);
		Worker worker2 = new Worker("worker2@email.com", "Worker2", "worker2", "pass123", m);

		m.addUser(worker1);
		m.addUser(worker2);
		
		Collection c1 = m.createCollection("worker1@email.com", new Work(), "WorkCol");
		Collection c2 = m.createCollection("worker2@email.com", new Film(), "FilmCol");
		
		worker1.setCollection(c1);
		worker2.setCollection(c2);
		
		Picker p1 = new Picker("Picker1@email.com", "Picker1", "Picker1", "pass123",
				m, "p765");
		
		m.addUser(p1);
		c1.addUser(p1); c1.addUser(p1);
		
		m.runUser(worker1);
		m.runUser(worker2);
		m.runUser(p1);
		
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m.save();
	}

	public static void main(String[] args) {
		new Examples();
	}
}
