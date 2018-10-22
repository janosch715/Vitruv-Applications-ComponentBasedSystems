package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({	BasicComponentMappingTransformationTest.class, CollectionDataTypeMappingTransformationTest.class,
	CompositeComponentMappingTransformationTest.class, CompositeDataTypeMappingTransformationTest.class,
	InnerDeclarationMappingTransformationTest.class, OperationInterfaceMappingTransformationTest.class,
	OperationProvidedRoleMappingTransformationTest.class, OperationRequiredRoleMappingTransformationTest.class,
	OperationSignatureMappingTransformationTest.class, PcmParameterMappingTransformationTest.class,
	RepositoryMappingTransformaitonTest.class, ResourceDemandingInternalBehaviorMappingTransformationTest.class,
	SEFFMappingTransformationTest.class} )
public class Pcm2JavaRepositoryTestSuite {

}
