package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.Utilities
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.helpers.CorrespondenceHelper
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

/**
 * Base class for all EObject mapping transformations, which provides default
 * implementations for every change type. The default implementation issues a
 * log message on a call. Additionally some utility methods are provided,
 * which are useful for almost all transformations. 
 */
abstract class EObjectMappingTransformationBase extends EObjectMappingTransformation {

	protected static def <T extends EObject> clone(T obj) {
		return Utilities.clone(obj)
	}

	protected static def getTUID(EObject obj) {
		return TUID.getInstance(Utilities.getTUID(obj))
	}

	protected static def <T extends EObject> getParentOfType(EObject eobject, Class<T> type) {
		return Utilities.getParentOfType(eobject, type)
	}

	protected static def <T extends EObject> getChildrenOfType(EObject eobject, Class<T> type) {
		return Utilities.getChildrenOfType(eobject, type)
	}

	protected def <T extends EObject> T getSingleCorrespondingEObjectOfType(EObject subject, Class<T> type) {
		return CorrespondenceHelper.getSingleCorrespondingEObjectOfType(correspondenceInstance, subject, type)
	}

	protected def getSingleCorrespondence(EObject srcElement, EObject dstElement) {
		return CorrespondenceHelper.getSingleCorrespondence(correspondenceInstance, srcElement, dstElement)
	}

	protected def createTransformationChangeResultForEObjectsToSave(EObject... objectsToSave) {
		return TransformationUtils.createTransformationChangeResultForEObjectsToSave(objectsToSave)
	}
	
	protected def createTransformationChangeResultAborted() {
		return new TransformationChangeResult(#[].toSet, #[].toSet, #[].toSet, true);
	}

	protected def createTransformationChangeResultForNewRootEObjects(EObject... newRootObjects) {
		return TransformationUtils.createTransformationChangeResultForNewRootEObjects(newRootObjects)
	}

	protected def createTransformationChangeResult(EObject[] newRootObjectsToSave, VURI[] urisToDelete,
		EObject[] existingObjectsToSave) {
		return TransformationUtils.createTransformationChangeResult(newRootObjectsToSave, urisToDelete,
			existingObjectsToSave)
	}

	protected def <T extends EObject> T getModelInstanceElement(T obj) {
		return obj.getModelInstanceElement(correspondenceInstance) 
	}

	protected static def <T extends EObject> T getModelInstanceElement(T obj, CorrespondenceInstance ci) {
		val match = Utilities.getModelInstanceElement(obj, ci)
		if (match != null) {
			return match
		}
		return Utilities.getModelInstanceElementByTUID(obj, ci)
	}

	protected abstract def Logger getLogger()

	private def createDefaultImplementationLoggingString(String methodName, String[] parameterNames,
		Object[] parameterValues) {
		val str = new StringBuilder()
		str.append("Default implementation of " + methodName + " is called in " + this.class.simpleName + ":")
		for (var i = 0; i < parameterNames.size; i++) {
			val paramName = parameterNames.get(i)
			val paramValue = parameterValues.get(i)
			str.append("\n\t" + paramName + ": " + paramValue)
		}
		return str.toString
	}

	override createEObject(EObject eObject) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName, #["eObject"], #[eObject])
		logger.info(logMsg)
		return #[]
	}

	override removeEObject(EObject eObject) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName, #["eObject"], #[eObject])
		logger.info(logMsg)
		return #[]
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["newRootEObject", "newCorrespondingEObjects"], #[newRootEObject, newCorrespondingEObjects])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["oldRootEObject", "oldCorrespondingEObjectsToDelete"], #[oldRootEObject, oldCorrespondingEObjectsToDelete])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override replaceRoot(EObject oldRootEObject, EObject newRootEObject, EObject[] oldCorrespondingEObjectsToDelete,
		EObject[] newCorrespondingEObjects) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["oldRootEObject", "newRootEObject", "oldCorrespondingEObjectsToDelete", "newCorrespondingEObjects"],
			#[oldRootEObject, newRootEObject, oldCorrespondingEObjectsToDelete, newCorrespondingEObjects])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject newValue,
		int index, EObject[] newCorrespondingEObjects) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["newAffectedEObject", "oldAffectedEObject", "affectedReference", "newValue", "index", "newCorrespondingEObjects"],
			#[newAffectedEObject, oldAffectedEObject, affectedReference, newValue, index, newCorrespondingEObjects])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override deleteNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] eObjectsToDelete) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "eObjectsToDelete"],
			#[affectedEObject, affectedReference, oldValue, eObjectsToDelete])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "newValue", "newCorrespondingEObjects"],
			#[affectedEObject, affectedReference, newValue, newCorrespondingEObjects])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override replaceNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index, EObject[] oldCorrespondingEObjectsToDelete, EObject[] newCorrespondingEObjects) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "newValue", "index",
				"oldCorrespondingEObjectsToDelete", "newCorrespondingEObjects"],
			#[affectedEObject, affectedReference, oldValue, newValue, index, oldCorrespondingEObjectsToDelete,
				newCorrespondingEObjects])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["newAffectedEObject", "oldAffectedEObject", "affectedReference", "oldValue", "index", "oldCorrespondingEObjectsToDelete"],
			#[newAffectedEObject, oldAffectedEObject, affectedReference, oldValue, index, oldCorrespondingEObjectsToDelete])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override replaceNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "newValue"],
			#[affectedEObject, affectedReference, oldValue, newValue])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override permuteContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference,
		EList<Integer> newIndexForElementAt) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "newIndexForElementAt"],
			#[affectedEObject, affectedReference, newIndexForElementAt])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override insertNonContaimentEReference(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "newValue", "index"],
			#[affectedEObject, affectedReference, newValue, index])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "newValue"],
			#[affectedEObject, affectedReference, oldValue, newValue])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override permuteNonContainmentEReferenceValues(EObject affectedEObject, EReference affectedReference,
		EList<Integer> newIndexForElementAt) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "newIndexForElementAt"],
			#[affectedEObject, affectedReference, newIndexForElementAt])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override replaceNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject newValue, int index) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "newValue", "index"],
			#[affectedEObject, affectedReference, oldValue, newValue, index])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override removeNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "index"],
			#[affectedEObject, affectedReference, oldValue, index])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedAttribute", "oldValue", "newValue"],
			#[affectedEObject, affectedAttribute, oldValue, newValue])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override removeEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, int index) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedAttribute", "oldValue", "index"],
			#[affectedEObject, affectedAttribute, oldValue, index])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override insertEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object newValue, int index) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedAttribute", "newValue", "index"],
			#[affectedEObject, affectedAttribute, newValue, index])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override unsetEAttribute(EObject affectedEObject, EStructuralFeature affectedFeature, Object oldValue) {

		//TODO why structural feature instead of eattribute?
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedFeature", "oldValue"], #[affectedEObject, affectedFeature, oldValue])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override unsetContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		EObject[] oldCorrespondingEObjectsToDelete) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue", "oldCorrespondingEObjectsToDelete"],
			#[affectedEObject, affectedReference, oldValue, oldCorrespondingEObjectsToDelete])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override unsetNonContainmentEReference(EObject affectedEObject, EReference affectedReference, EObject oldValue) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedReference", "oldValue"], #[affectedEObject, affectedReference, oldValue])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override replaceEAttributeValue(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue, int index) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedAttribute", "oldValue", "newValue", "index"],
			#[affectedEObject, affectedAttribute, oldValue, newValue, index])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

	override permuteEAttributeValues(EObject affectedEObject, EAttribute affectedAttribute,
		EList<Integer> newIndexForElementAt) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["affectedEObject", "affectedAttribute", "newIndexForElementAt"],
			#[affectedEObject, affectedAttribute, newIndexForElementAt])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}
	
	override insertNonRootEObjectInContainmentList(EObject oldAffectedEObject, EObject newAffectedEObject, EReference reference, EObject newValue) {
		val methodName = new Object() {
		}.getClass().getEnclosingMethod().name
		val logMsg = createDefaultImplementationLoggingString(methodName,
			#["oldAffectedEObject", "newAffectedEObject", "reference", "newValue"],
			#[oldAffectedEObject, newAffectedEObject, reference, newValue])
		logger.info(logMsg)
		return createTransformationChangeResultForEObjectsToSave()
	}

}
