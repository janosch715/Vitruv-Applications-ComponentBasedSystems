package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.types.Int;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;
import tools.vitruv.applications.pcmjava.util.pcm2java.DataTypeCorrespondenceHelper;

public class DataTypeCorrespondenceHelperTest extends Pcm2JavaTransformationTest {

	@Test
	public void testCorrespondenceForCompositeDataType() throws Throwable {
		final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);

		// Create and sync CompositeDataType
		final CompositeDataType cdt = this.createAndSyncCompositeDataType(repo);

		final TypeReference type = DataTypeCorrespondenceHelper.claimUniqueCorrespondingJaMoPPDataTypeReference(cdt,
				super.getCorrespondenceModel());
		final NamespaceClassifierReference ncr = (NamespaceClassifierReference) type;
		final Classifier classifier = (Classifier) ncr.getTarget();
		assertEquals("Name of composite data type does not equals name of classifier", cdt.getEntityName(), // +
																											// "Impl",
				classifier.getName());
	}

	@Test
	public void testCorrespondenceForPrimitiveDataType() throws Throwable {
		// final Repository repo =
		// this.createAndSyncRepository(this.resourceSet,
		// PCM2JaMoPPUtils.REPOSITORY_NAME);
		final PrimitiveDataType pdtInt = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
		pdtInt.setType(PrimitiveTypeEnum.INT);
		final TypeReference type = DataTypeCorrespondenceHelper.claimUniqueCorrespondingJaMoPPDataTypeReference(pdtInt,
				super.getCorrespondenceModel());
		if (!(type instanceof Int)) {
			fail("found type is not instance of Int");
		}
	}

	@Test
    public void testCorrespondenceForCollectionDataType() throws Throwable {
    	final Repository repo = this.createAndSyncRepository(Pcm2JavaTestUtils.REPOSITORY_NAME);

    	// Create and sync CollectionDataType
    	this.getUserInteractor().addNextSelections(0);
    	final CollectionDataType collectionDataType = this.addCollectionDatatypeAndSync(repo,
                Pcm2JavaTestUtils.COLLECTION_DATA_TYPE_NAME, null);
    	
    	final TypeReference type = DataTypeCorrespondenceHelper.claimUniqueCorrespondingJaMoPPDataTypeReference(collectionDataType,
    			super.getCorrespondenceModel());
    	final NamespaceClassifierReference ncr = (NamespaceClassifierReference) type;
    	final Classifier classifier = (Classifier) ncr.getTarget();
    	assertEquals("Name of composite data type does not equals name of classifier", collectionDataType.getEntityName(), // + "Impl",
    			classifier.getName());
	}

}
