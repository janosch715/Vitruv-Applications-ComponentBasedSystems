package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddClassDataTypePropertyRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddClassDataTypePropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property compProperty, final DataType compDataType, final Model umlModel, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      return dataTypeClass;
    }
    
    public void update0Element(final Property compProperty, final DataType compDataType, final Model umlModel, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      EList<Property> _ownedAttributes = dataTypeClass.getOwnedAttributes();
      _ownedAttributes.add(classProperty);
    }
    
    public String getRetrieveTag1(final Property compProperty, final DataType compDataType, final Model umlModel) {
      return "DataTypeRepresentation";
    }
    
    public void updateClassPropertyElement(final Property compProperty, final DataType compDataType, final Model umlModel, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      String _name = compProperty.getName();
      classProperty.setName(_name);
      Type _type = compProperty.getType();
      classProperty.setType(_type);
    }
    
    public EObject getElement2(final Property compProperty, final DataType compDataType, final Model umlModel, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      return classProperty;
    }
    
    public EObject getCorrepondenceSourceDataTypeClass(final Property compProperty, final DataType compDataType, final Model umlModel) {
      return compDataType;
    }
    
    public EObject getElement3(final Property compProperty, final DataType compDataType, final Model umlModel, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      return compProperty;
    }
    
    public String getTag1(final Property compProperty, final DataType compDataType, final Model umlModel, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      return "DataTypeProperty";
    }
    
    public EObject getCorrepondenceSourceUmlModel(final Property compProperty, final DataType compDataType) {
      Model _model = compProperty.getModel();
      return _model;
    }
  }
  
  public AddClassDataTypePropertyRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property compProperty, final DataType compDataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.AddClassDataTypePropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compProperty = compProperty;this.compDataType = compDataType;
  }
  
  private Property compProperty;
  
  private DataType compDataType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddClassDataTypePropertyRoutine with input:");
    getLogger().debug("   Property: " + this.compProperty);
    getLogger().debug("   DataType: " + this.compDataType);
    
    Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(compProperty, compDataType), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (umlModel == null) {
    	return;
    }
    registerObjectUnderModification(umlModel);
    org.eclipse.uml2.uml.Class dataTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDataTypeClass(compProperty, compDataType, umlModel), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(compProperty, compDataType, umlModel));
    if (dataTypeClass == null) {
    	return;
    }
    registerObjectUnderModification(dataTypeClass);
    Property classProperty = UMLFactoryImpl.eINSTANCE.createProperty();
    userExecution.updateClassPropertyElement(compProperty, compDataType, umlModel, dataTypeClass, classProperty);
    
    // val updatedElement userExecution.getElement1(compProperty, compDataType, umlModel, dataTypeClass, classProperty);
    userExecution.update0Element(compProperty, compDataType, umlModel, dataTypeClass, classProperty);
    
    addCorrespondenceBetween(userExecution.getElement2(compProperty, compDataType, umlModel, dataTypeClass, classProperty), userExecution.getElement3(compProperty, compDataType, umlModel, dataTypeClass, classProperty), userExecution.getTag1(compProperty, compDataType, umlModel, dataTypeClass, classProperty));
    
    postprocessElements();
  }
}
