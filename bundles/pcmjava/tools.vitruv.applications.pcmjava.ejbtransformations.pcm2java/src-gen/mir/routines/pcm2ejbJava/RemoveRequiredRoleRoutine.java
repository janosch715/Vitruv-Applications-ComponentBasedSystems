package mir.routines.pcm2ejbJava;

import java.io.IOException;
import mir.routines.pcm2ejbJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.members.Field;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.RequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceRequiredInterfaceField(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport) {
      return requiredRole;
    }
    
    public EObject getCorrepondenceSourceRequiredInterfaceImport(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
      return requiredRole;
    }
    
    public EObject getElement1(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField, final org.emftext.language.java.classifiers.Class javaClass) {
      return requiredInterfaceImport;
    }
    
    public EObject getCorrepondenceSourceJavaClass(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField) {
      return requiringEntity;
    }
    
    public EObject getElement2(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField, final org.emftext.language.java.classifiers.Class javaClass) {
      return requiredInterfaceField;
    }
  }
  
  public RemoveRequiredRoleRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2ejbJava.RemoveRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2ejbJava.RoutinesFacade(getExecutionState(), this);
    this.requiredRole = requiredRole;this.requiringEntity = requiringEntity;
  }
  
  private RequiredRole requiredRole;
  
  private InterfaceRequiringEntity requiringEntity;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveRequiredRoleRoutine with input:");
    getLogger().debug("   RequiredRole: " + this.requiredRole);
    getLogger().debug("   InterfaceRequiringEntity: " + this.requiringEntity);
    
    ClassifierImport requiredInterfaceImport = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRequiredInterfaceImport(requiredRole, requiringEntity), // correspondence source supplier
    	ClassifierImport.class,
    	(ClassifierImport _element) -> true, // correspondence precondition checker
    	null);
    if (requiredInterfaceImport == null) {
    	return;
    }
    registerObjectUnderModification(requiredInterfaceImport);
    Field requiredInterfaceField = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRequiredInterfaceField(requiredRole, requiringEntity, requiredInterfaceImport), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    if (requiredInterfaceField == null) {
    	return;
    }
    registerObjectUnderModification(requiredInterfaceField);
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClass(requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (javaClass == null) {
    	return;
    }
    registerObjectUnderModification(javaClass);
    deleteObject(userExecution.getElement1(requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField, javaClass));
    
    deleteObject(userExecution.getElement2(requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField, javaClass));
    
    postprocessElements();
  }
}
