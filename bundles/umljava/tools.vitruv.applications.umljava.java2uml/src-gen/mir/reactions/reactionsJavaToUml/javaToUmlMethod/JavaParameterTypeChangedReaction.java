package mir.reactions.reactionsJavaToUml.javaToUmlMethod;

import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
class JavaParameterTypeChangedReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEReference<OrdinaryParameter, TypeReference> replaceChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.emftext.language.java.parameters.OrdinaryParameter affectedEObject = replaceChange.getAffectedEObject();
    EReference affectedFeature = replaceChange.getAffectedFeature();
    org.emftext.language.java.types.TypeReference oldValue = replaceChange.getOldValue();
    org.emftext.language.java.types.TypeReference newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.javaToUmlMethod.RoutinesFacade routinesFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterTypeChangedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterTypeChangedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
    
    resetChanges();
  }
  
  private void resetChanges() {
    replaceChange = null;
    currentlyMatchedChange = 0;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchReplaceChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchReplaceChange(final EChange change) {
    if (change instanceof ReplaceSingleValuedEReference<?, ?>) {
    	ReplaceSingleValuedEReference<org.emftext.language.java.parameters.OrdinaryParameter, org.emftext.language.java.types.TypeReference> _localTypedChange = (ReplaceSingleValuedEReference<org.emftext.language.java.parameters.OrdinaryParameter, org.emftext.language.java.types.TypeReference>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.emftext.language.java.parameters.OrdinaryParameter)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("typeReference")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof org.emftext.language.java.types.TypeReference)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof org.emftext.language.java.types.TypeReference)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEReference<org.emftext.language.java.parameters.OrdinaryParameter, org.emftext.language.java.types.TypeReference>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final OrdinaryParameter affectedEObject, final EReference affectedFeature, final TypeReference oldValue, final TypeReference newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeUmlParameterType(affectedEObject, newValue);
    }
  }
}
