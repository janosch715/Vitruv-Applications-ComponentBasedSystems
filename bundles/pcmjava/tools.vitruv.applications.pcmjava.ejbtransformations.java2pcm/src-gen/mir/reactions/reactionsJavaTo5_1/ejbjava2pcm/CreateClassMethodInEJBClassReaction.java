package mir.reactions.reactionsJavaTo5_1.ejbjava2pcm;

import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBAnnotationHelper;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBJava2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreateClassMethodInEJBClassReaction extends AbstractReactionRealization {
  public CreateClassMethodInEJBClassReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertEReference<org.emftext.language.java.classifiers.Class, ClassMethod> typedChange = ((CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, ClassMethod>)change).getInsertChange();
    org.emftext.language.java.classifiers.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    ClassMethod newValue = typedChange.getNewValue();
    mir.routines.ejbjava2pcm.RoutinesFacade routinesFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateClassMethodInEJBClassReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateClassMethodInEJBClassReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.emftext.language.java.classifiers.Class, ClassMethod> relevantChange = ((CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, ClassMethod>)change).getInsertChange();
    // Check affected object
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.classifiers.Class)) {
    	return false;
    }
    // Check feature
    if (!relevantChange.getAffectedFeature().getName().equals("members")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof ClassMethod)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    if (!checkChangeProperties(change)) {
    	return false;
    }
    InsertEReference<org.emftext.language.java.classifiers.Class, ClassMethod> typedChange = ((CreateAndInsertNonRoot<org.emftext.language.java.classifiers.Class, ClassMethod>)change).getInsertChange();
    org.emftext.language.java.classifiers.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    ClassMethod newValue = typedChange.getNewValue();
    if (!checkUserDefinedPrecondition(affectedEObject, affectedFeature, newValue)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final org.emftext.language.java.classifiers.Class affectedEObject, final EReference affectedFeature, final ClassMethod newValue) {
    return (EJBAnnotationHelper.isEJBClass(affectedEObject) && 
      EJBJava2PcmHelper.overridesInterfaceMethod(newValue, affectedEObject));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class affectedEObject, final EReference affectedFeature, final ClassMethod newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createdClassMethodInEJBClass(affectedEObject, newValue);
    }
  }
}