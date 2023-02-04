package org.mql.java.models;

import java.lang.reflect.Field;

import org.mql.java.annotations.TypeAnnotation;
import org.mql.java.utils.TypeDefinition;

public class AnnotatedCollectionType implements CollectionType {

	@Override
	public boolean accept(String type) {
		DocType docType = TypeDefinition.extractType(type);		
		boolean x = false;
		if (docType != null) {
			String dType = docType.toString();
			
			Field fields[] = DocType.class.getFields();
			for (Field f : fields)
				if (f.getName().equals(dType)) x = true;
		}
		return x;
	}
	
	public boolean accept(DocType docType) {
		TypeAnnotation an = this.getClass().getDeclaredAnnotation(TypeAnnotation.class);
		String types[] = an.types();

		boolean found = false;
		for (String type : types)
				if (docType.toString().equals(type.toUpperCase())) found = true;
		
		return found;
	}
}
