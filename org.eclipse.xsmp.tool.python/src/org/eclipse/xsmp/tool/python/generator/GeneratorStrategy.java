package org.eclipse.xsmp.tool.python.generator;

import org.eclipse.xsmp.xcatalogue.ReferenceType;
import org.eclipse.xsmp.xcatalogue.Type;

@SuppressWarnings("all")
public class GeneratorStrategy implements IGenerationStrategy {

	@Override
	public boolean useGenerationGapPattern(final Type t) {
		return t instanceof ReferenceType;
	}
}