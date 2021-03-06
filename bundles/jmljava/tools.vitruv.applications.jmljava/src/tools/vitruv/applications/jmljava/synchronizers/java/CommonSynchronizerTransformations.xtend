package tools.vitruv.applications.jmljava.synchronizers.java

import tools.vitruv.domains.jml.language.jML.Modifiable
import tools.vitruv.domains.jml.language.jML.RegularModifier
import tools.vitruv.domains.jml.language.jML.Typed
import tools.vitruv.applications.jmljava.correspondences.Java2JMLCorrespondenceAdder
import tools.vitruv.applications.jmljava.synchronizers.helpers.CorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import java.util.ArrayList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.commons.Commentable
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.variables.Variable

import static extension tools.vitruv.framework.util.bridges.CollectionBridge.*

class CommonSynchronizerTransformations {
	
	private static val LOGGER = Logger.getLogger(CommonSynchronizerTransformations)
	
	static def replaceNonRootEObjectInList(EObject affectedJavaObject, Modifier javaOldValue, Modifier javaNewValue, CorrespondenceModel ci) {
		
		val jmlModifiable = CorrespondenceHelper.getSingleCorrespondingEObjectOfType(ci, affectedJavaObject, Modifiable)
		val changedObjects = new ArrayList
		if (jmlModifiable != null) {
			LOGGER.trace("Replacing " + javaOldValue + " with " + javaNewValue)
			
			val jmlModifiableTUIDOld = ci.calculateTUIDFromEObject(jmlModifiable)
			CorrespondenceHelper.getSingleCorrespondence(ci, affectedJavaObject, jmlModifiable)
			
			val oldJmlModifier = CorrespondenceHelper.getSingleCorrespondingEObjectOfType(ci, javaOldValue, RegularModifier)
			val oldIndex = jmlModifiable.modifiers.indexOf(oldJmlModifier)
			
			ci.removeCorrespondencesThatInvolveAtLeastAndDependend(oldJmlModifier.toSet)
			ci.removeCorrespondencesThatInvolveAtLeastAndDependend(javaOldValue.toSet)
			
			jmlModifiable.modifiers.remove(oldJmlModifier)
			
			val newJmlModifier = CommonSynchronizerTasks.createJMLModifier(javaNewValue)
			jmlModifiable.modifiers.add(oldIndex, newJmlModifier)
			
			Java2JMLCorrespondenceAdder.addCorrespondences(javaNewValue, newJmlModifier, ci)
			jmlModifiableTUIDOld.updateTuid(jmlModifiable)
			
			changedObjects.add(jmlModifiable)
		}
	}
	
	static def createNonRootEObjectInList(EObject affectedJavaObject, Modifier javaModifier, CorrespondenceModel ci) {
		val changedObjects = new ArrayList
		
		val jmlModifiable = CorrespondenceHelper.getSingleCorrespondingEObjectOfType(ci, affectedJavaObject, Modifiable)
		if (jmlModifiable != null) {
			LOGGER.trace("Creating " + javaModifier)
			
			CorrespondenceHelper.getSingleCorrespondence(ci, affectedJavaObject, jmlModifiable)
			val jmlModifier = CommonSynchronizerTasks.createJMLModifier(javaModifier)
			jmlModifiable.modifiers.add(0, jmlModifier)
			
			Java2JMLCorrespondenceAdder.addCorrespondences(javaModifier, jmlModifier, ci)
			
			changedObjects.add(jmlModifiable)			
		}
	}
	
	static def replaceNonRootEObjectSingleType(EObject affectedJavaObject, TypeReference javaOldValue, TypeReference javaNewValue, CorrespondenceModel ci) {
		val changedObjects = new ArrayList<EObject>()
		
		// this does not treat varargs for parameters...
		
		val jmlTyped = CorrespondenceHelper.getSingleCorrespondingEObjectOfType(ci, affectedJavaObject, Typed)
		if (jmlTyped != null) {
			LOGGER.trace("Updating " + jmlTyped)
			val jmlTypedTUIDOld = ci.calculateTUIDFromEObject(jmlTyped)
			val typeArrayDimensions = getArrayDimensions(affectedJavaObject as Commentable)
			jmlTyped.type = CommonSynchronizerTasks.createJMLType(javaNewValue, typeArrayDimensions)
			jmlTypedTUIDOld.updateTuid(jmlTyped)
			changedObjects.add(jmlTyped)
		}
	}
	
	private static def dispatch getArrayDimensions(Method javaMethod) {
		return javaMethod.arrayDimension
	}
	
	private static def dispatch getArrayDimensions(Variable javaVariable) {
		return javaVariable.arrayDimension
	}
	
}