package tools.vitruv.applications.umljava.uml2java.tests

import tools.vitruv.applications.umljava.uml2java.Uml2JavaTransformationTest
import org.junit.Test
import static org.junit.Assert.*
import org.junit.Before
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.VisibilityKind
import static tools.vitruv.applications.umljava.util.uml.UmlClassifierAndPackageUtil.*
import static tools.vitruv.applications.umljava.util.uml.UmlPropertyAndAssociationUtil.*
import static extension tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.testutil.TestUtil.*
import static tools.vitruv.applications.umljava.testutil.JavaTestUtil.*
import org.emftext.language.java.types.TypesFactory
import tools.vitruv.applications.umljava.util.java.JavaVisibility
import org.junit.After

/**
 * This Test class checks the creating, deleting and modifying of attributes in den uml to java
 * direction.
 * 
 * @author Fei
 */
class UmlToJavaAttributeTest extends Uml2JavaTransformationTest {
    private static val ATTRIBUTE_NAME = "attributName";
    private static val ATTRIBUTE_RENAME = "attributeRenamed";
    private static val STANDARD_ATTRIBUTE_NAME = "standardAttributName";
    private static val PRIMITIVE_TYPE = "int";
    private static val CLASS_NAME = "ClassName";
    private static val TYPE_CLASS = "TypeClass";
    
    private static var Property uAttr;
    private static var org.eclipse.uml2.uml.Class uClass;
    private static var org.eclipse.uml2.uml.Class typeClass;
    
    @Before
    def void before() {
        uClass = createSimpleUmlClass(rootElement, CLASS_NAME);
        typeClass = createSimpleUmlClass(rootElement, TYPE_CLASS);
        uAttr = createUmlAttribute(ATTRIBUTE_NAME, typeClass, VisibilityKind.PUBLIC_LITERAL, false, false);
        uClass.ownedAttributes += uAttr;
        saveAndSynchronizeChanges(rootElement);
    }
    
    @After
    def void after() {
        if (uClass !== null) {
            uClass.destroy;
        }
        if (typeClass !== null) {
            typeClass.destroy;
        }
        if (uAttr !== null) {
            uAttr.destroy;
        }
        saveAndSynchronizeChanges(rootElement);
    }
    @Test
    def testCreatePrimitiveAttribute() {
        val pType = createUmlPrimitiveTypeAndAddToModel(rootElement, PRIMITIVE_TYPE);
        val attr = createUmlAttribute(STANDARD_ATTRIBUTE_NAME, pType, VisibilityKind.PUBLIC_LITERAL, false, false);
        uClass.ownedAttributes += attr;
        saveAndSynchronizeChanges(uClass);       
        
        val jClass = getCorrespondingClass(uClass)
        val jAttr = getCorrespondingAttribute(attr);
        assertJavaAttributeTraits(jAttr, STANDARD_ATTRIBUTE_NAME, JavaVisibility.PUBLIC, TypesFactory.eINSTANCE.createInt, false, false, jClass)
        assertAttributeEquals(attr, jAttr)
    }
    
    @Test
    def testCreateAttribute() {
        val attr = uClass.createOwnedAttribute(STANDARD_ATTRIBUTE_NAME, typeClass);
        saveAndSynchronizeChanges(uClass);
           
        val jClass = getCorrespondingClass(uClass)
        val jtypeClass = getCorrespondingClass(typeClass)
        val jAttr = getCorrespondingAttribute(attr)
        assertJavaAttributeTraits(jAttr, STANDARD_ATTRIBUTE_NAME, JavaVisibility.PUBLIC, 
        	createNamespaceReferenceFromClassifier(jtypeClass), false, false, jClass)
        assertAttributeEquals(attr, jAttr)
        
    }
    
    @Test
    def testRenameAttribute() {
        uAttr.name = ATTRIBUTE_RENAME;
        saveAndSynchronizeChanges(uClass)
        
        val jClass = getCorrespondingClass(uClass)
        val jAttr = getCorrespondingAttribute(uAttr)
        assertEquals(ATTRIBUTE_RENAME, uAttr.name)
        assertAttributeEquals(uAttr, jAttr)
        assertJavaMemberContainerDontHaveMember(jClass, ATTRIBUTE_NAME)
    }
    
    @Test
    def testDeleteAttribute() {
        uAttr.destroy;
        saveAndSynchronizeChanges(uClass);
        
        val jClass = getCorrespondingClass(uClass)
        assertJavaMemberContainerDontHaveMember(jClass, ATTRIBUTE_NAME)
    }

    @Test
    def testStaticAttribute() {
        uAttr.isStatic = true;
        saveAndSynchronizeChanges(uClass);
         
        val jAttr = getCorrespondingAttribute(uAttr)
        assertJavaModifiableStatic(jAttr, true)
        assertAttributeEquals(uAttr, jAttr)
    }
    
    @Test
    def testFinalAttribute() {
        uAttr.isReadOnly = true;
        saveAndSynchronizeChanges(uClass);
        
        val jAttr = getCorrespondingAttribute(uAttr)
        assertJavaModifiableFinal(jAttr, true)
        assertAttributeEquals(uAttr, jAttr)
    }
    
    @Test
    def testAttributeVisibility() {
        uAttr.visibility = VisibilityKind.PRIVATE_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        var jAttr = getCorrespondingAttribute(uAttr)
        assertJavaModifiableHasVisibility(jAttr, JavaVisibility.PRIVATE)
        assertAttributeEquals(uAttr, jAttr)
        
        uAttr.visibility = VisibilityKind.PACKAGE_LITERAL;
        saveAndSynchronizeChanges(uClass);
        
        jAttr = getCorrespondingAttribute(uAttr)
        assertJavaModifiableHasVisibility(jAttr, JavaVisibility.PACKAGE)
        assertAttributeEquals(uAttr, jAttr)
    }
    
    @Test
    def testChangeAttributeType() {
        uAttr.type = createUmlPrimitiveTypeAndAddToModel(rootElement, PRIMITIVE_TYPE)
        saveAndSynchronizeChanges(rootElement)
        
        val jClass = getCorrespondingClass(uClass)
        val jAttr = getCorrespondingAttribute(uAttr);
        assertJavaAttributeTraits(jAttr, ATTRIBUTE_NAME, JavaVisibility.PUBLIC, TypesFactory.eINSTANCE.createInt, false, false, jClass)
        assertAttributeEquals(uAttr, jAttr)
    }
}
		