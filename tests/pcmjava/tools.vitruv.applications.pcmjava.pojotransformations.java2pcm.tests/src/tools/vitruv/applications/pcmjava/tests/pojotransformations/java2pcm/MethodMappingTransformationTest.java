package tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Callable;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.members.Method;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;

import tools.vitruv.applications.pcmjava.tests.util.CompilationUnitManipulatorHelper;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import static edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*;

public class MethodMappingTransformationTest extends Java2PcmPackageMappingTransformationTest {

    @Test
    public void testAddMethod() throws Throwable {
        super.addRepoContractsAndDatatypesPackage();
        final OperationInterface opInterface = super.addInterfaceInContractsPackage();

        final OperationSignature opSig = super.addMethodToInterfaceWithCorrespondence(opInterface.getEntityName());

        this.assertOperationSignature(opSig, opInterface, Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME);
    }

    @Test
    public void testRenameMethod() throws Throwable {
        this.addRepoContractsAndDatatypesPackage();
        final OperationInterface opInterface = super.addInterfaceInContractsPackage();
        final OperationSignature opSig = super.addMethodToInterfaceWithCorrespondence(opInterface.getEntityName());

        final OperationSignature newOpSig = super.renameMethodInClassWithName(opInterface.getEntityName(),
                opSig.getEntityName());

        this.assertOperationSignature(newOpSig, opInterface,
                Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME + Pcm2JavaTestUtils.RENAME);
    }

    @Test
    public void testAddReturnType() throws Throwable {
        this.addRepoContractsAndDatatypesPackage();

        final OperationInterface opInterface = super.addInterfaceInContractsPackage();
        final OperationSignature opSig = super.addMethodToInterfaceWithCorrespondence(opInterface.getEntityName());

        final OperationSignature newOpSig = this.changeReturnType(opSig);

        this.assertOperationSignature(newOpSig, opInterface, Pcm2JavaTestUtils.OPERATION_SIGNATURE_1_NAME);
    }

    private OperationSignature changeReturnType(final OperationSignature opSig) throws Throwable {
        final String className = opSig.getInterface__OperationSignature().getEntityName();
        final String methodName = opSig.getEntityName();
        final ICompilationUnit cu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
                this.getCurrentTestProject());
        final IMethod iMethod = cu.getType(className).getMethod(methodName, null);
        final int returnTypeOffset = iMethod.getSourceRange().getOffset();
        final String retTypeName = iMethod.getSource().split(" ")[0];
        final int returnTypeLength = retTypeName.length();
        final String newReturnTypeName = "String ";
        final DeleteEdit deleteEdit = new DeleteEdit(returnTypeOffset, returnTypeLength);
        final InsertEdit insertEdit = new InsertEdit(returnTypeOffset, newReturnTypeName);
        editCompilationUnit(cu, deleteEdit, insertEdit);
        return super.findOperationSignatureForJaMoPPMethodInCompilationUnit(methodName, className, cu);
    }

    private void assertOperationSignature(final OperationSignature opSig, final OperationInterface opInterface,
            final String expectedName) throws Throwable {
        assertEquals("OperationSignature " + opSig + " is not in OperationInterface " + opInterface,
                opSig.getInterface__OperationSignature().getId(), opInterface.getId());
        this.assertPCMNamedElement(opSig, expectedName);

        this.getVirtualModel().executeCommand(new Callable<Void>() {

            @Override
            public Void call() {
                Method jaMoPPMethod;
                try {
                    jaMoPPMethod = claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(
                            MethodMappingTransformationTest.this.getCorrespondenceModel(), opSig, Method.class));
                } catch (final Throwable e) {
                    throw new RuntimeException(e);
                }
                MethodMappingTransformationTest.this.assertDataTypeName(jaMoPPMethod.getTypeReference(),
                        opSig.getReturnType__OperationSignature());
                return null;
            }
        });
    }
}
