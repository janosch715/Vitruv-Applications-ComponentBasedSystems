package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import tools.vitruv.applications.umljava.util.UmlUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveUmlPackageOfClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveUmlPackageOfClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier, final Classifier uClassifier, final org.eclipse.uml2.uml.Package uPackage) {
      return uPackage;
    }
    
    public void update0Element(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier, final Classifier uClassifier, final org.eclipse.uml2.uml.Package uPackage) {
      UmlUtil.removePackagedElementFromPackage(uPackage, uClassifier);
    }
    
    public EObject getCorrepondenceSourceUClassifier(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
      return jClassifier;
    }
    
    public EObject getCorrepondenceSourceUPackage(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier, final Classifier uClassifier) {
      return jPackage;
    }
  }
  
  public RemoveUmlPackageOfClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.RemoveUmlPackageOfClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jPackage = jPackage;this.jClassifier = jClassifier;
  }
  
  private org.emftext.language.java.containers.Package jPackage;
  
  private ConcreteClassifier jClassifier;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveUmlPackageOfClassRoutine with input:");
    getLogger().debug("   Package: " + this.jPackage);
    getLogger().debug("   ConcreteClassifier: " + this.jClassifier);
    
    Classifier uClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClassifier(jPackage, jClassifier), // correspondence source supplier
    	Classifier.class,
    	(Classifier _element) -> true, // correspondence precondition checker
    	null);
    if (uClassifier == null) {
    	return;
    }
    registerObjectUnderModification(uClassifier);
    org.eclipse.uml2.uml.Package uPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUPackage(jPackage, jClassifier, uClassifier), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	null);
    if (uPackage == null) {
    	return;
    }
    registerObjectUnderModification(uPackage);
    // val updatedElement userExecution.getElement1(jPackage, jClassifier, uClassifier, uPackage);
    userExecution.update0Element(jPackage, jClassifier, uClassifier, uPackage);
    
    postprocessElements();
  }
}
