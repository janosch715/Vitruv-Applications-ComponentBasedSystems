package mir.reactions.class2comp;

import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

/**
 * ******
 * *Class:*
 * *******
 */
@SuppressWarnings("all")
public class CreatedUmlClassReaction extends AbstractReactionRealization {
  private CreateEObject<org.eclipse.uml2.uml.Class> createChange;
  
  private InsertEReference<org.eclipse.uml2.uml.Package, org.eclipse.uml2.uml.Class> insertChange;
  
  private int currentlyMatchedChange;
  
  public CreatedUmlClassReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Package affectedEObject = insertChange.getAffectedEObject();
    EReference affectedFeature = insertChange.getAffectedFeature();
    org.eclipse.uml2.uml.Class newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.class2comp.CreatedUmlClassReaction.ActionUserExecution userExecution = new mir.reactions.class2comp.CreatedUmlClassReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(insertChange, affectedEObject, affectedFeature, newValue, index, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private void resetChanges() {
    createChange = null;
    insertChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchCreateChange(final EChange change) {
    if (change instanceof CreateEObject<?>) {
    	CreateEObject<org.eclipse.uml2.uml.Class> _localTypedChange = (CreateEObject<org.eclipse.uml2.uml.Class>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Class)) {
    		return false;
    	}
    	this.createChange = (CreateEObject<org.eclipse.uml2.uml.Class>) change;
    	return true;
    }
    
    return false;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchCreateChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    	return false; // Only proceed on the last of the expected changes
    }
    if (currentlyMatchedChange == 1) {
    	if (!matchInsertChange(change)) {
    		resetChanges();
    		checkPrecondition(change); // Reexecute to potentially register this as first change
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchInsertChange(final EChange change) {
    if (change instanceof InsertEReference<?, ?>) {
    	InsertEReference<org.eclipse.uml2.uml.Package, org.eclipse.uml2.uml.Class> _localTypedChange = (InsertEReference<org.eclipse.uml2.uml.Package, org.eclipse.uml2.uml.Class>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Package)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("packagedElement")) {
    		return false;
    	}
    	if (!(_localTypedChange.getNewValue() instanceof org.eclipse.uml2.uml.Class)) {
    		return false;
    	}
    	this.insertChange = (InsertEReference<org.eclipse.uml2.uml.Package, org.eclipse.uml2.uml.Class>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertEReference insertChange, final org.eclipse.uml2.uml.Package affectedEObject, final EReference affectedFeature, final org.eclipse.uml2.uml.Class newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.routineCreatedUmlClass(newValue, affectedEObject);
    }
  }
}
