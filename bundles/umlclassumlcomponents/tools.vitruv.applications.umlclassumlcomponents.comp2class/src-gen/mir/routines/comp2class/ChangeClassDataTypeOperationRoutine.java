package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeClassDataTypeOperationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeClassDataTypeOperationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation compOperation, final Operation classOperation) {
      return classOperation;
    }
    
    public void update0Element(final Operation compOperation, final Operation classOperation) {
      String _name = compOperation.getName();
      classOperation.setName(_name);
    }
    
    public String getRetrieveTag1(final Operation compOperation) {
      return "DataTypeOperation";
    }
    
    public EObject getCorrepondenceSourceClassOperation(final Operation compOperation) {
      return compOperation;
    }
  }
  
  public ChangeClassDataTypeOperationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation compOperation) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.ChangeClassDataTypeOperationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compOperation = compOperation;
  }
  
  private Operation compOperation;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeClassDataTypeOperationRoutine with input:");
    getLogger().debug("   Operation: " + this.compOperation);
    
    Operation classOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassOperation(compOperation), // correspondence source supplier
    	Operation.class,
    	(Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(compOperation));
    if (classOperation == null) {
    	return;
    }
    registerObjectUnderModification(classOperation);
    // val updatedElement userExecution.getElement1(compOperation, classOperation);
    userExecution.update0Element(compOperation, classOperation);
    
    postprocessElements();
  }
}
