package mir.reactions.class2comp;

import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
public class RemovedInterfaceRealizationReaction extends AbstractReactionRealization {
  private RemoveEReference<org.eclipse.uml2.uml.Class, InterfaceRealization> removeChange;
  
  private DeleteEObject<InterfaceRealization> deleteChange;
  
  private int currentlyMatchedChange;
  
  public RemovedInterfaceRealizationReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Class affectedEObject = removeChange.getAffectedEObject();
    EReference affectedFeature = removeChange.getAffectedFeature();
    org.eclipse.uml2.uml.InterfaceRealization oldValue = removeChange.getOldValue();
    int index = removeChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.class2comp.RemovedInterfaceRealizationReaction.ActionUserExecution userExecution = new mir.reactions.class2comp.RemovedInterfaceRealizationReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(removeChange, affectedEObject, affectedFeature, oldValue, index, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private boolean matchDeleteChange(final EChange change) {
    if (change instanceof DeleteEObject<?>) {
    	DeleteEObject<org.eclipse.uml2.uml.InterfaceRealization> _localTypedChange = (DeleteEObject<org.eclipse.uml2.uml.InterfaceRealization>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.InterfaceRealization)) {
    		return false;
    	}
    	this.deleteChange = (DeleteEObject<org.eclipse.uml2.uml.InterfaceRealization>) change;
    	return true;
    }
    
    return false;
  }
  
  private void resetChanges() {
    removeChange = null;
    deleteChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchRemoveChange(final EChange change) {
    if (change instanceof RemoveEReference<?, ?>) {
    	RemoveEReference<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.InterfaceRealization> _localTypedChange = (RemoveEReference<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.InterfaceRealization>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Class)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("interfaceRealization")) {
    		return false;
    	}
    	if (!(_localTypedChange.getOldValue() instanceof org.eclipse.uml2.uml.InterfaceRealization)) {
    		return false;
    	}
    	this.removeChange = (RemoveEReference<org.eclipse.uml2.uml.Class, org.eclipse.uml2.uml.InterfaceRealization>) change;
    	return true;
    }
    
    return false;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchRemoveChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    	return false; // Only proceed on the last of the expected changes
    }
    if (currentlyMatchedChange == 1) {
    	if (!matchDeleteChange(change)) {
    		resetChanges();
    		checkPrecondition(change); // Reexecute to potentially register this as first change
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final RemoveEReference removeChange, final org.eclipse.uml2.uml.Class affectedEObject, final EReference affectedFeature, final InterfaceRealization oldValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.removeInterfaceRealization(oldValue);
      _routinesFacade.removeUsage(oldValue);
    }
  }
}
