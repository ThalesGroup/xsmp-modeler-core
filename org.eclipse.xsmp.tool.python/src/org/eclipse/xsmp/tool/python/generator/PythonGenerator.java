package org.eclipse.xsmp.tool.python.generator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.xcatalogue.Catalogue;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

import com.google.inject.Inject;

public class PythonGenerator extends AbstractGenerator {

	@Override
	public void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		final var cat = (Catalogue) input.getContents().get(0);

		generateCatalogue(cat, fsa);
	}

	protected void generateCatalogue(Catalogue cat, IFileSystemAccess2 fsa) {

		fsa.generateFile(cat.getName() + ".py", PythonOutputConfigurationProvider.PYTHON,
				packageGenerator.generate(cat));
	}

	@Inject
	protected PackageGenerator packageGenerator;
}
