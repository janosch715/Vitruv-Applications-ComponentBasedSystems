package tools.vitruv.applications.pcmumlcomponents.uml2pcm

import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.UMLFactory
import org.junit.Test
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.PrimitiveDataType

import static org.junit.Assert.*

class DataTypesTest extends AbstractUmlPcmTest {
	
	protected val DATATYPE_NAME = "fooType"
	
	@Test
	public def void primitiveTypeCreate() {
		val primitiveType = UMLFactory.eINSTANCE.createPrimitiveType()
		primitiveType.name = UML_TYPE_BOOL
		rootElement.packagedElements += primitiveType
		saveAndSynchronizeChanges(rootElement)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[primitiveType]).flatten
		assertEquals(1, correspondingElements.length)
		val pcmType = correspondingElements.get(0)
		assertTrue(pcmType instanceof PrimitiveDataType)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(primitiveType.name), (pcmType as PrimitiveDataType).type)
	}
		
	@Test
	public def void compositeTypeCreate() {
		val typeName = DATATYPE_NAME
		val dataType = UMLFactory.eINSTANCE.createDataType()
		dataType.name = typeName
		val nrOwnedTypesBefore = rootElement.ownedTypes.length
		rootElement.packagedElements += dataType
		userInteractor.addNextSelections(1)
		saveAndSynchronizeChanges(rootElement)
		assertEquals(nrOwnedTypesBefore + 1, rootElement.ownedTypes.length)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[dataType]).flatten
		assertEquals(1, correspondingElements.length)
		val pcmType = correspondingElements.get(0)
		assertTrue(pcmType instanceof CompositeDataType)
	}
	
	@Test
	public def void compositeTypeAddProperty() {
		val dataType = UMLFactory.eINSTANCE.createDataType()
		dataType.name = DATATYPE_NAME
		rootElement.packagedElements += dataType
		userInteractor.addNextSelections(1)
		val propertyName = PARAMETER_NAME
		val propertyType = UMLFactory.eINSTANCE.createPrimitiveType()
		propertyType.name = UML_TYPE_INT
		rootElement.packagedElements += propertyType
		val property = UMLFactory.eINSTANCE.createProperty()
		property.name = propertyName
		property.type = propertyType
		dataType.ownedAttributes += property
		saveAndSynchronizeChanges(rootElement)
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[dataType]).flatten
		val pcmType = (correspondingElements.get(0) as CompositeDataType)
		assertEquals(1, pcmType.innerDeclaration_CompositeDataType.length)
		assertEquals(propertyName, pcmType.innerDeclaration_CompositeDataType.get(0).entityName)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(propertyType.name),
			(pcmType.innerDeclaration_CompositeDataType.get(0).datatype_InnerDeclaration as PrimitiveDataType).type)
	}
	
	protected def DataType createCompositeDataType(String name) {
		val dataType = UMLFactory.eINSTANCE.createDataType()
		dataType.name = DATATYPE_NAME
		rootElement.packagedElements += dataType
		userInteractor.addNextSelections(1)
		saveAndSynchronizeChanges(rootElement)
		return dataType
	}
	
	protected def Property createProperty(DataType umlType, String name, String datatype) {
		val propertyType = UMLFactory.eINSTANCE.createPrimitiveType()
		propertyType.name = datatype
		rootElement.packagedElements += propertyType
		val property = UMLFactory.eINSTANCE.createProperty()
		property.name = name
		property.type = propertyType
		umlType.ownedAttributes += property
		saveAndSynchronizeChanges(rootElement)
		return property
	}
	
	protected def org.palladiosimulator.pcm.repository.DataType getCorrespondingDataType(DataType umlType) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlType]).flatten
		return correspondingElements.get(0) as org.palladiosimulator.pcm.repository.DataType
	}
	
	@Test
	public def void compositeTypeChangeProperty() {
		val umlType = createCompositeDataType(DATATYPE_NAME)
		val umlProperty = createProperty(umlType, PARAMETER_NAME, UML_TYPE_INT)
		
		val newType = UMLFactory.eINSTANCE.createPrimitiveType()
		newType.name = UML_TYPE_BOOL
		rootElement.packagedElements += newType
		umlProperty.type = newType
		
		val newPropertyName = PARAMETER_NAME_2
		umlProperty.name = newPropertyName
		
		saveAndSynchronizeChanges(rootElement)
		
		var pcmType = (getCorrespondingDataType(umlType) as CompositeDataType)
		assertEquals(UmlToPcmUtil.getPcmPrimitiveType(newType.name),
			(pcmType.innerDeclaration_CompositeDataType.get(0).datatype_InnerDeclaration as PrimitiveDataType).type)
		assertEquals(newPropertyName, pcmType.innerDeclaration_CompositeDataType.get(0).entityName)
	}
	
	@Test
	public def void compositeTypeDeleteProperty() {
		val umlType = createCompositeDataType(DATATYPE_NAME)
		val property1 = createProperty(umlType, PARAMETER_NAME, UML_TYPE_INT)
		val remainingPropertyName = PARAMETER_NAME_2
		createProperty(umlType, remainingPropertyName, UML_TYPE_REAL)
		
		umlType.ownedAttributes -= property1
		
		saveAndSynchronizeChanges(rootElement)
		
		var pcmType = (getCorrespondingDataType(umlType) as CompositeDataType)
		assertEquals(1, pcmType.innerDeclaration_CompositeDataType.length())
		assertEquals(remainingPropertyName, pcmType.innerDeclaration_CompositeDataType.get(0).entityName)
	}
		
}