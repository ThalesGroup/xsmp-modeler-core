package org.eclipse.xsmp.tool.python.generator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.tool.python.generator.type.PythonArrayGenerator;
import org.eclipse.xsmp.tool.python.generator.type.PythonComponentGenerator;
import org.eclipse.xsmp.tool.python.generator.type.PythonEnumerationGenerator;
import org.eclipse.xsmp.tool.python.generator.type.PythonEventTypeGenerator;
import org.eclipse.xsmp.tool.python.generator.type.PythonFloatGenerator;
import org.eclipse.xsmp.tool.python.generator.type.PythonIntegerGenerator;
import org.eclipse.xsmp.tool.python.generator.type.PythonStringGenerator;
import org.eclipse.xsmp.tool.python.generator.type.PythonStructureGenerator;
import org.eclipse.xsmp.xcatalogue.Catalogue;
import org.eclipse.xsmp.xcatalogue.Namespace;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

import com.google.inject.Inject;

public class PythonGenerator extends AbstractGenerator {

	@Inject
	protected GeneratorExtension ext;

	@Inject
	protected IGenerationStrategy generationStrategy;

	@Override
	public void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		final var cat = (Catalogue) input.getContents().get(0);

		generateCatalogue(cat, fsa);
	}

	protected void generateCatalogue(Catalogue cat, IFileSystemAccess2 fsa) {
		cat.getMember().stream().filter(Namespace.class::isInstance)
				.forEach(ns -> generateNamespace((Namespace) ns, fsa, cat));

		fsa.generateFile(cat.getName() + "/_Package.py", PythonOutputConfigurationProvider.PYTHON, packageGenerator.generate(cat));
	}

	protected void generateNamespace(Namespace ns, IFileSystemAccess2 fsa, Catalogue cat) {
		// Generate types
		ns.getMember().stream().filter(Type.class::isInstance).forEach(type -> generateType((Type) type, fsa, cat));

		// Generate nested Namespaces
		ns.getMember().stream().filter(Namespace.class::isInstance)
				.forEach(nns -> generateNamespace((Namespace) nns, fsa, cat));
	}

	@SuppressWarnings("unchecked")
	private void generateType(Type type, IFileSystemAccess2 fsa, Catalogue cat) {
		final var typeGenerator = getGenerator(type);
		
		if (typeGenerator == null)
			return;

		boolean useGenStrategy = generationStrategy.useGenerationGapPattern(type);
		var path = ext.fqn(type).toString("/") + ".py";

		if (useGenStrategy)
			generateFile(fsa, path, PythonOutputConfigurationProvider.PYTHON, typeGenerator.generateUserCodeFile(type, cat));

		// If the file already exists in the usercode part, use the gen pattern
		useGenStrategy |= fsa.isFile(path, PythonOutputConfigurationProvider.PYTHON);
		if (useGenStrategy)
			path = ext.fqn(type, true).toString("/") + ".py";

		generateFile(fsa, path, PythonOutputConfigurationProvider.PYTHON_GEN, typeGenerator.generateFile(type, cat));
	}

	@SuppressWarnings("rawtypes")
	protected AbstractFileGenerator getGenerator(Type t) {
		switch (t.eClass().getClassifierID()) {
		case XcataloguePackage.MODEL:
		case XcataloguePackage.SERVICE:
			return componentGenerator;
		case XcataloguePackage.ENUMERATION:
			return enumGenerator;
		case XcataloguePackage.INTEGER:
			return integerGenerator;
		case XcataloguePackage.FLOAT:
			return floatGenerator;
		case XcataloguePackage.ARRAY:
			return arrayGenerator;
		case XcataloguePackage.STRUCTURE:
			return structureGenerator;
		case XcataloguePackage.EVENT_TYPE:
			return eventTypeGenerator;
		case XcataloguePackage.STRING:
			return stringGenerator;
		default:
			return null;
		}
	}

	protected void generateFile(IFileSystemAccess2 fsa, String fileName, String outputConfigurationName,
			CharSequence contents) {
		if (contents != null) {
			fsa.generateFile(fileName, outputConfigurationName, contents);
		}
	}

	@Inject
	protected PythonComponentGenerator componentGenerator;

	@Inject
	protected PythonEnumerationGenerator enumGenerator;

	@Inject
	protected PythonIntegerGenerator integerGenerator;

	@Inject
	protected PythonFloatGenerator floatGenerator;

	@Inject
	protected PythonArrayGenerator arrayGenerator;

	@Inject
	protected PythonStructureGenerator structureGenerator;

	@Inject
	protected PythonEventTypeGenerator eventTypeGenerator;
	
	@Inject
	protected PythonStringGenerator stringGenerator;

	@Inject
	protected PackageGenerator packageGenerator;
}
