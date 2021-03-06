package mir.reactions.comp2class;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.comp2class.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.comp2class.CreatedCompModelReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.RenamedComponentModelReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.RenamedElementReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.ElementVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.CreatedUmlComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.RenameComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.DeletedCompReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.CreatedDatatypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.AddedDataTypePropertyReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.ChangedDataTypePropertyReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.AddedDataTypeOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.ChangedDataTypeOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.AddedProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.AddedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.RemovedInterfaceRealizationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.comp2class.RemovedUsageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("comp2class"))));
  }
}
