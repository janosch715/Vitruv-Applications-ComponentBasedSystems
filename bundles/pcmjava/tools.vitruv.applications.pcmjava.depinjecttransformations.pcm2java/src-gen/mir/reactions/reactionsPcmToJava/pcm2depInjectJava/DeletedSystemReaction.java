package mir.reactions.reactionsPcmToJava.pcm2depInjectJava;

import mir.routines.pcm2depInjectJava.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;
import tools.vitruv.framework.change.echange.root.RemoveRootEObject;

@SuppressWarnings("all")
class DeletedSystemReaction extends AbstractReactionRealization {
  private RemoveRootEObject<org.palladiosimulator.pcm.system.System> removeChange;
  
  private DeleteEObject<org.palladiosimulator.pcm.system.System> deleteChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.system.System oldValue = removeChange.getOldValue();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.pcm2depInjectJava.RoutinesFacade routinesFacade = new mir.routines.pcm2depInjectJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedSystemReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.DeletedSystemReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(oldValue, routinesFacade);
    
    resetChanges();
  }
  
  private boolean matchDeleteChange(final EChange change) {
    if (change instanceof DeleteEObject<?>) {
    	DeleteEObject<org.palladiosimulator.pcm.system.System> _localTypedChange = (DeleteEObject<org.palladiosimulator.pcm.system.System>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.system.System)) {
    		return false;
    	}
    	this.deleteChange = (DeleteEObject<org.palladiosimulator.pcm.system.System>) change;
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
    if (change instanceof RemoveRootEObject<?>) {
    	RemoveRootEObject<org.palladiosimulator.pcm.system.System> _localTypedChange = (RemoveRootEObject<org.palladiosimulator.pcm.system.System>) change;
    	if (!(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.system.System)) {
    		return false;
    	}
    	this.removeChange = (RemoveRootEObject<org.palladiosimulator.pcm.system.System>) change;
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
    
    public void callRoutine1(final org.palladiosimulator.pcm.system.System oldValue, @Extension final RoutinesFacade _routinesFacade) {
      final org.palladiosimulator.pcm.system.System system = oldValue;
      _routinesFacade.deleteJavaPackage(system, system.getEntityName(), "root_system");
      _routinesFacade.deleteJavaClassifier(system);
    }
  }
}
