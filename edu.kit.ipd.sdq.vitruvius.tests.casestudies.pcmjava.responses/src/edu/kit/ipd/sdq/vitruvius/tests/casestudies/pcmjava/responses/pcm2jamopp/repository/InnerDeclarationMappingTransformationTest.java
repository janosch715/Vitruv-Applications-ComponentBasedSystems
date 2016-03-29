package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.responses.pcm2jamopp.repository;

import org.junit.Test;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.responses.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.responses.utils.PCM2JaMoPPTestUtils;

public class InnerDeclarationMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddInnerDeclaration() throws Throwable {
        final InnerDeclaration innerDec = this.createAndSyncRepositoryCompositeDataTypeAndInnerDeclaration();

        this.assertInnerDeclaration(innerDec);
    }

    @Test
    public void testRenameInnerDeclaration() throws Throwable {
        final InnerDeclaration innerDec = this.createAndSyncRepositoryCompositeDataTypeAndInnerDeclaration();

        innerDec.setEntityName(PCM2JaMoPPTestUtils.INNER_DEC_NAME + PCM2JaMoPPTestUtils.RENAME);
        super.triggerSynchronization(VURI.getInstance(innerDec.eResource()));

        this.assertInnerDeclaration(innerDec);
    }

    @Test
    public void testChangeInnerDeclarationType() throws Throwable {
        final InnerDeclaration innerDec = this.createAndSyncRepositoryCompositeDataTypeAndInnerDeclaration();

        final PrimitiveDataType newPDT = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
        newPDT.setType(PrimitiveTypeEnum.STRING);
        innerDec.setDatatype_InnerDeclaration(newPDT);
        super.triggerSynchronization(VURI.getInstance(innerDec.eResource()));

        this.assertInnerDeclaration(innerDec);
    }

}