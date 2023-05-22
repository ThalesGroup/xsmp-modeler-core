package org.eclipse.xsmp.tool.python.generator;

import org.eclipse.xsmp.xcatalogue.Type;

import com.google.inject.ImplementedBy;

@ImplementedBy(GeneratorStrategy.class)
public interface IGenerationStrategy {

	boolean useGenerationGapPattern(Type t);
}