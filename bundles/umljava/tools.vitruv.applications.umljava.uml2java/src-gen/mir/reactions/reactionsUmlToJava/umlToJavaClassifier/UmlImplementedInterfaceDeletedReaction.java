package mir.reactions.reactionsUmlToJava.umlToJavaClassifier;

import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class UmlImplementedInterfaceDeletedReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.Class, InterfaceRealization> typedChange = (RemoveEReference<org.eclipse.uml2.uml.Class, InterfaceRealization>)change;
    org.eclipse.uml2.uml.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    InterfaceRealization oldValue = typedChange.getOldValue();
    mir.routines.umlToJavaClassifier.RoutinesFacade routinesFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceDeletedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.UmlImplementedInterfaceDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.Class, InterfaceRealization> relevantChange = (RemoveEReference<org.eclipse.uml2.uml.Class, InterfaceRealization>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Class)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("interfaceRealization")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof InterfaceRealization)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class affectedEObject, final EReference affectedFeature, final InterfaceRealization oldValue, @Extension final RoutinesFacade _routinesFacade) {
      EList<NamedElement> _suppliers = oldValue.getSuppliers();
      NamedElement _head = IterableExtensions.<NamedElement>head(_suppliers);
      _routinesFacade.deleteJavaImplementedInterface(((Interface) _head), affectedEObject);
    }
  }
}