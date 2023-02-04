package org.mql.java.tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.mql.java.annotations.TypeAnnotation;
import org.mql.java.models.AnnotatedCollectionType;
import org.mql.java.models.DocType;
import org.mql.java.models.Film;
import org.mql.java.models.Work;
import org.mql.java.utils.TypeDefinition;

class Tests {
	
	@Test
	void testExtractType() {
		DocType type = TypeDefinition.extractType("mp4");
		assertEquals(DocType.VIDEO, type);
	}
	
	@Test
	void testAccept() {
		AnnotatedCollectionType a = new AnnotatedCollectionType();
		assertEquals(true, a.accept("png"));
		assertEquals(false, a.accept("xml"));
	}
	
	@Test
	void testAcceptAnnotation() {
		Work work = new Work();
		Film film = new Film();
		boolean result = work.accept(DocType.PDF);
		boolean result2 = film.accept(DocType.VIDEO);
		assertEquals(true, result);
		assertEquals(true, result2);
	}
	
	@Test
	void testName() {
		TypeAnnotation an = Work.class.getDeclaredAnnotation(TypeAnnotation.class);
		String name = an.name();
		String t = an.types()[1];
		assertEquals("PDF", t);
		assertEquals("Work", name);
	}
	
	@Test
	void testEnum() {
		assertEquals(DocType.valueOf("AUDIO"), DocType.AUDIO);
	}
}
