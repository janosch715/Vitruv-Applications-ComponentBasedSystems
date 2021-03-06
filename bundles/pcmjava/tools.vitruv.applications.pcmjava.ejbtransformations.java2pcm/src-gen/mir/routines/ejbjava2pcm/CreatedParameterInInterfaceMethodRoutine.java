package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.Parameter;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatedParameterInInterfaceMethodRoutine extends AbstractRepairRoutineRealization {
  private CreatedParameterInInterfaceMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceOpSignature(final InterfaceMethod method, final Parameter parameter) {
      return method;
    }
    
    public void callRoutine1(final InterfaceMethod method, final Parameter parameter, final OperationSignature opSignature, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createPCMParameter(parameter, opSignature);
    }
  }
  
  public CreatedParameterInInterfaceMethodRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceMethod method, final Parameter parameter) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatedParameterInInterfaceMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.method = method;this.parameter = parameter;
  }
  
  private InterfaceMethod method;
  
  private Parameter parameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedParameterInInterfaceMethodRoutine with input:");
    getLogger().debug("   method: " + this.method);
    getLogger().debug("   parameter: " + this.parameter);
    
    org.palladiosimulator.pcm.repository.OperationSignature opSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpSignature(method, parameter), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (opSignature == null) {
    	return false;
    }
    registerObjectUnderModification(opSignature);
    userExecution.callRoutine1(method, parameter, opSignature, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
