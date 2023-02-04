package org.mql.java.examples;

import java.util.Random;
import java.util.Vector;

import org.mql.java.annotations.TypeAnnotation;
import org.mql.java.controllers.DocumentManagmentSystem;
import org.mql.java.models.AnnotatedCollectionType;
import org.mql.java.models.Collection;
import org.mql.java.models.DocType;
import org.mql.java.models.Resource;
import org.mql.java.models.User;

public class Worker extends User implements Runnable {
	private Thread runner;
	private DocumentManagmentSystem m;
	private Collection collection;
	private AnnotatedCollectionType type;

	public Worker(String email, String firstName, String lastName, String password, DocumentManagmentSystem m) {
		super(email, firstName, lastName, password);
		this.m = m;
		runner = new Thread(this, "Worker");
	}
	
	public void start() {
		runner.start();
	}

	@Override
	public void run() {
		do {			
			// Description
			String description = "";
			Random rand = new Random();
			int num = rand.nextInt((15 - 1) + 1) + 1;
			for (int i = 0; i < num; i++) {
				description += genName() + " ";
			}
			
			Resource r =  m.createResource(
					collection, 
					genName(),
					description,
					this);
			
			int ln = rand.nextInt((10 - 1) + 1) + 1;
			for (int i = 0; i < ln; i++) {
				
				//Keywords
				Vector<String> keywords = new Vector<String>();
				int x = rand.nextInt((5 - 1) + 1) + 1;
				for (int j = 0; j < x; j++) {
					keywords.add(genName());
				}
				
				// Types
				String types[]  = type.getClass()
						.getDeclaredAnnotation(TypeAnnotation.class)
						.types();
				DocType docType;
				if (types.length != 0) {					
					int rn = rand.nextInt((types.length - 1) + 1) + 1;
					docType = DocType.valueOf(types[rn-1].toUpperCase());
				} else {
					docType = DocType.OTHER;
				}
				
				m.addDocument(collection, r, genName(), generateDate(), keywords, docType);
			}
			
			pause(5000);
		}
		while (true);
	}
	
	public void setCollection(Collection c) {
		type = c.getType();
		collection = c;
	}
	
	public String genName() {
		return generateString("abcdefghijklmnopqrstuvwxyz", 1) + generateString("1234567890", 3);
	}
	
	public String gen(int length) {
		return generateString("abcdefghijklmnopqrstuvwxyz", length);
	}
	
	public String generateDate() {
		Random rand = new Random();
		int day = rand.nextInt((30 - 1) + 1) + 1;
		int month = rand.nextInt((12 - 1) + 1) + 1;
		int year = rand.nextInt((2022 - 2000) + 1) + 2000;
		
		return day+"/"+month+"/"+year;
	}
	
	public String generateString(String characters, int length) {
		Random rng = new Random();
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
	
	public static void pause(long time) {
		try {
			Thread.sleep(time);
		}
		catch (Exception e) {}
	}
}
