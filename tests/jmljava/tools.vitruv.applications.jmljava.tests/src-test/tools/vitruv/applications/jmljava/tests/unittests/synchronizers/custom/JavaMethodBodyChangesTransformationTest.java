package tools.vitruv.applications.jmljava.tests.unittests.synchronizers.custom;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.isA;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.statements.ExpressionStatement;
import org.emftext.language.java.statements.LocalVariableStatement;
import org.emftext.language.java.statements.Statement;
import org.emftext.language.java.statements.StatementsPackage;
import org.junit.Test;

import tools.vitruv.applications.jmljava.helper.JaMoPPConcreteSyntax;
import tools.vitruv.applications.jmljava.helper.Utilities;
import tools.vitruv.applications.jmljava.synchronizers.custom.JavaMethodBodyChangedTransformation;
import tools.vitruv.framework.change.description.CompositeTransactionalChange;
import tools.vitruv.framework.change.description.VitruviusChangeFactory;
import tools.vitruv.framework.metamodel.ModelInstance;
import tools.vitruv.framework.userinteraction.UserInteractionType;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.util.datatypes.Pair;
import tools.vitruv.applications.jmljava.tests.unittests.synchronizers.TransformationTestsBase;
import tools.vitruv.applications.jmljava.tests.unittests.utils.ModelLoader.IResourceFiles;
import tools.vitruv.domains.java.echange.feature.reference.JavaInsertEReference;
import tools.vitruv.domains.java.echange.feature.reference.JavaRemoveEReference;
import tools.vitruv.domains.java.echange.feature.reference.ReferenceFactory;

public class JavaMethodBodyChangesTransformationTest extends TransformationTestsBase {
    
    private static enum ResourceFiles implements IResourceFiles {
        JAVA("JavaMethodBodyChangesTransformationTest.java.resource"),
        JML("JavaMethodBodyChangesTransformationTest.jml.resource"),
        
        EXPECTED_AddNonPureStatementToNotUsedPureMethod("JavaMethodBodyChangesTransformationTest_EXPECTED_AddNonPureStatementToNotUsedPureMethod.jml.resource"),
        EXPECTED_AddNonPureStatementToPureMethodCalledByOtherPureMethodNotUsedInSpecification("JavaMethodBodyChangesTransformationTest_EXPECTED_AddNonPureStatementToPureMethodCalledByOtherPureMethodNotUsedInSpecification.jml.resource"),
        EXPECTED_RemoveAllNonPureStatementsFromNonPureMethod("JavaMethodBodyChangesTransformationTest_EXPECTED_RemoveAllNonPureStatementsFromNonPureMethod.jml.resource"),
        EXPECTED_RemoveOfAllNonPureStatementsAddsPureToCallingMethod("JavaMethodBodyChangesTransformationTest_EXPECTED_RemoveOfAllNonPureStatementsAddsPureToCallingMethod.jml.resource");

        
        private String modelFileName;

        private ResourceFiles(String modelFileName) {
            this.modelFileName = modelFileName;
        }

        @Override
        public String getModelFileName() {
            return modelFileName;
        }
    }

    private CompilationUnit cuJava;
    private tools.vitruv.domains.jml.language.jML.CompilationUnit cuJML;

    @Override
    protected Pair<ModelInstance, ModelInstance> getModelInstances() throws Exception {
        ModelInstance miJava = loadModelInstance(ResourceFiles.JAVA);
        cuJava = miJava.getUniqueRootEObjectIfCorrectlyTyped(CompilationUnit.class);
        ModelInstance miJML = loadModelInstance(ResourceFiles.JML);
        cuJML = miJML
                .getUniqueRootEObjectIfCorrectlyTyped(tools.vitruv.domains.jml.language.jML.CompilationUnit.class);
        return new Pair<ModelInstance, ModelInstance>(miJava, miJML);
    }
    
    private LocalVariableStatement convertToStatement(String stmt, EObject newParent) {
        return JaMoPPConcreteSyntax.convertFromConcreteSyntax(stmt, StatementsPackage.eINSTANCE.getLocalVariableStatement(), LocalVariableStatement.class, newParent.eResource().getURI());
    }
    
    private ExpressionStatement convertToMethodCallStatement(String stmt, EObject newParent) {
        return JaMoPPConcreteSyntax.convertFromConcreteSyntax(stmt, StatementsPackage.eINSTANCE.getExpressionStatement(), ExpressionStatement.class, newParent.eResource().getURI());
    }
    
    private CompositeTransactionalChange createCompositeChange(CloneContainer<ClassMethod> clones) {
        return createCompositeChange(clones.original(), clones.changed());
    }
    
    private CompositeTransactionalChange createCompositeChange(ClassMethod oldMethod, ClassMethod newMethod) {
        CompositeTransactionalChange compositeChange = VitruviusChangeFactory.getInstance().createCompositeTransactionalChange();
        
        for (Statement stmt : oldMethod.getStatements()) {
            JavaRemoveEReference<EObject, EObject> change = ReferenceFactory.eINSTANCE.createJavaRemoveEReference();
            change.setIsDelete(true);
            change.setAffectedFeature(StatementsPackage.eINSTANCE.getStatementListContainer_Statements());
            change.setIndex(oldMethod.getStatements().indexOf(stmt));
            change.setAffectedEObject(newMethod);
            change.setOldAffectedEObject(oldMethod);
            change.setOldValue(stmt);
            compositeChange.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(change, VURI.getInstance(stmt.eResource())));
        }
        
        for (Statement stmt : newMethod.getStatements()) {
            JavaInsertEReference<EObject, EObject> change = ReferenceFactory.eINSTANCE.createJavaInsertEReference();
            change.setAffectedFeature(StatementsPackage.eINSTANCE.getStatementListContainer_Statements());
            change.setIndex(oldMethod.getStatements().indexOf(stmt));
            change.setAffectedEObject(newMethod);
            change.setOldAffectedEObject(oldMethod);
            change.setNewValue(stmt);
            compositeChange.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(change, VURI.getInstance(stmt.eResource())));
        }
        
        return compositeChange;
    }
    
    @Test
    public void testAddNonPureStatementToNonPureMethod() throws Exception {
        // nonPureMethodNotMarked
        CloneContainer<ClassMethod> method = createClones((ClassMethod)cuJava.getClassifiers().get(0).getMethods().get(2));
        Statement newStatement = Utilities.clone(convertToStatement("Object a = nonPureMethodCalled();", method.changed()));
        method.changed().getStatements().add(0, newStatement);
        
        EObject expectedJMLCu = Utilities.clone(cuJML);
        
        CompositeTransactionalChange change = createCompositeChange(method);

        callSynchronizer(change);

        assertEqualsModel(expectedJMLCu, cuJML);
        assertNumberOfRealUpdateCalls(0);
    }
    
    @Test
    public void testAddNonPureStatementToNotUsedPureMethod() throws Exception {
        // pureMethodMarked
        CloneContainer<ClassMethod> method = createClonesForSerializedObject((ClassMethod)(cuJava.getClassifiers().get(0).getMethods().get(1)));
        Statement newStatement = convertToStatement("Object a = nonPureMethodCalled();", method.changed());
        method.changed().getStatements().add(0, newStatement);
        
        CompositeTransactionalChange change = createCompositeChange(method);

        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_AddNonPureStatementToNotUsedPureMethod, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // pure modifier is removed
    }
    
    @Test
    public void testAddNonPureStatementToPureMethodDirectlyUsedInSpecification() throws Exception {
        // pureMethodUsedInSpec
        CloneContainer<ClassMethod> method = createClonesForSerializedObject((ClassMethod)(cuJava.getClassifiers().get(0).getMethods().get(4)));
        Statement newStatement = convertToMethodCallStatement("nonPureMethodCalled();", method.changed());
        method.changed().getStatements().add(0, newStatement);       
        
        EObject expectedJMLCu = Utilities.clone(cuJML);
        
        CompositeTransactionalChange change = createCompositeChange(method);
        userInteracting.showMessage(eq(UserInteractionType.MODAL), anyString());
        syncAbortedListener.synchronisationAborted(isA(JavaMethodBodyChangedTransformation.class));
        
        callSynchronizer(change);

        assertEqualsModel(expectedJMLCu, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // change is blocked
    }
    
    @Test
    public void testAddNonPureStatementToPureMethodCalledByOtherPureMethodNotUsedInSpecification() throws Exception {
        // pureCalledMethodMarked
        CloneContainer<ClassMethod> method = createClonesForSerializedObject((ClassMethod)(cuJava.getClassifiers().get(0).getMethods().get(0)));
        Statement newStatement = convertToStatement("Object a = nonPureMethodCalled();", method.changed());
        method.changed().getStatements().add(0, newStatement);

        CompositeTransactionalChange change = createCompositeChange(method);

        callSynchronizer(change);
        
        assertEqualsModel(ResourceFiles.EXPECTED_AddNonPureStatementToPureMethodCalledByOtherPureMethodNotUsedInSpecification, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // pure removed from both methods
    }
    
    @Test
    public void testAddNonPureStatementToPureMethodTransitivelyUsedInSpec() throws Exception {
        // pureMethodTransitivelyUsedInSpec
        CloneContainer<ClassMethod> method = createClonesForSerializedObject((ClassMethod)(cuJava.getClassifiers().get(0).getMethods().get(6)));
        Statement newStatement = convertToStatement("Object a = nonPureMethodCalled();", method.changed());
        method.changed().getStatements().add(0, newStatement);
        
        EObject expectedJMLCu = Utilities.clone(cuJML);
        
        CompositeTransactionalChange change = createCompositeChange(method);
        userInteracting.showMessage(eq(UserInteractionType.MODAL), anyString());
        syncAbortedListener.synchronisationAborted(isA(JavaMethodBodyChangedTransformation.class));
        
        callSynchronizer(change);

        assertEqualsModel(expectedJMLCu, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // change is blocked
    }
    
    @Test
    public void testRemovePureStatementFromNonPureMethod() throws Exception {
        // nonPureMethodNotMarked
        CloneContainer<ClassMethod> method = createClones((ClassMethod)cuJava.getClassifiers().get(0).getMethods().get(2));
        method.changed().getStatements().remove(1);
        
        EObject expectedJMLCu = Utilities.clone(cuJML);
        
        CompositeTransactionalChange change = createCompositeChange(method);

        callSynchronizer(change);

        assertEqualsModel(expectedJMLCu, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // models are not changed by the transformation
    }
    
    @Test
    public void testRemoveAllNonPureStatementsFromNonPureMethod() throws Exception {
        // nonPureMethodNotMarked2
        CloneContainer<ClassMethod> method = createClones((ClassMethod)cuJava.getClassifiers().get(0).getMethods().get(3));
        method.changed().getStatements().remove(0);
        
        CompositeTransactionalChange change = createCompositeChange(method);

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_RemoveAllNonPureStatementsFromNonPureMethod, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // pure modifier is added
    }
    
    @Test
    public void testRemoveOfAllNonPureStatementsAddsPureToCallingMethod() throws Exception {
        // nonPureMethodCalled
        CloneContainer<ClassMethod> method = createClones((ClassMethod)cuJava.getClassifiers().get(0).getMethods().get(10));
        method.changed().getStatements().remove(0);
        
        CompositeTransactionalChange change = createCompositeChange(method);

        callSynchronizer(change);

        assertEqualsModel(ResourceFiles.EXPECTED_RemoveOfAllNonPureStatementsAddsPureToCallingMethod, cuJML);
        assertNumberOfRealUpdateCalls(0);
        // pure modifier is added to method and calling method
    }
    
}
