package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import tools.vitruv.applications.umljava.util.java.JavaContainerAndClassifierUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaSuperInterfaceRoutine extends AbstractRepairRoutineRealization {
  private DeleteJavaSuperInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface superUMLInterface, final Interface uI, final org.emftext.language.java.classifiers.Interface jI, final org.emftext.language.java.classifiers.Interface javaSuperInterface) {
      return jI;
    }
    
    public void update0Element(final Interface superUMLInterface, final Interface uI, final org.emftext.language.java.classifiers.Interface jI, final org.emftext.language.java.classifiers.Interface javaSuperInterface) {
      JavaContainerAndClassifierUtil.removeClassifierFromIterator(jI.getExtends().iterator(), javaSuperInterface);
    }
    
    public EObject getCorrepondenceSourceJI(final Interface superUMLInterface, final Interface uI) {
      return uI;
    }
    
    public EObject getCorrepondenceSourceJavaSuperInterface(final Interface superUMLInterface, final Interface uI, final org.emftext.language.java.classifiers.Interface jI) {
      return superUMLInterface;
    }
  }
  
  public DeleteJavaSuperInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface superUMLInterface, final Interface uI) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.DeleteJavaSuperInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.superUMLInterface = superUMLInterface;this.uI = uI;
  }
  
  private Interface superUMLInterface;
  
  private Interface uI;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaSuperInterfaceRoutine with input:");
    getLogger().debug("   superUMLInterface: " + this.superUMLInterface);
    getLogger().debug("   uI: " + this.uI);
    
    org.emftext.language.java.classifiers.Interface jI = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJI(superUMLInterface, uI), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jI == null) {
    	return false;
    }
    registerObjectUnderModification(jI);
    org.emftext.language.java.classifiers.Interface javaSuperInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaSuperInterface(superUMLInterface, uI, jI), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaSuperInterface == null) {
    	return false;
    }
    registerObjectUnderModification(javaSuperInterface);
    // val updatedElement userExecution.getElement1(superUMLInterface, uI, jI, javaSuperInterface);
    userExecution.update0Element(superUMLInterface, uI, jI, javaSuperInterface);
    
    postprocessElements();
    
    return true;
  }
}
