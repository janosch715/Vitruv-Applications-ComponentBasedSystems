package tools.vitruv.applications.umljava.util.java

import java.util.List
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.generics.GenericsFactory
import org.emftext.language.java.generics.QualifiedTypeArgument
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.Type
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypedElement
import org.emftext.language.java.types.TypesFactory
import org.apache.log4j.Logger
import org.emftext.language.java.types.ClassifierReference
import static tools.vitruv.domains.java.util.JavaModificationUtil.*

/**
 * Class for type and typereference util function
 * @author Fei
 */
class JavaTypeUtil {
    
    private static val logger = Logger.getLogger(JavaTypeUtil.simpleName)
    private new() {}
    
    /**
     * Converts a list with ConcreteClassifiers to a list with corresponding NamespaceClassifierRefrences.
     */
    def static EList<NamespaceClassifierReference> createNamespaceReferenceFromList(List<? extends ConcreteClassifier> list) {
        val typeReferences = new BasicEList<NamespaceClassifierReference>
        for (ConcreteClassifier i : list) {
            typeReferences += createNamespaceReferenceFromClassifier(i)
        }
        return typeReferences
    }
    
    def static TypeReference createCollectiontypeReference(String collectionQualifiedName, Class innerTypeClass) {
        val innerTypeReference = createNamespaceReferenceFromClassifier(innerTypeClass)
        val qualifiedTypeArgument = GenericsFactory.eINSTANCE.createQualifiedTypeArgument();
        qualifiedTypeArgument.typeReference = innerTypeReference;
        val collectionClassNamespaceReference = createNamespaceReferenceFromClassifier(createJavaClassImport(collectionQualifiedName).classifier)
        collectionClassNamespaceReference.classifierReferences.get(0).typeArguments += qualifiedTypeArgument;
        return collectionClassNamespaceReference
    }
    
    /**
     * Wraps a copy of the given concreteClassifier into a ClassfierReference which is then wrapped into a namespaceClassifierReference
     * @throws IllegalArgumentException if concreteCLassifier is null
     */
    def static NamespaceClassifierReference createNamespaceReferenceFromClassifier(ConcreteClassifier concreteClassifier) {
        if (concreteClassifier === null) {
            throw new IllegalArgumentException("Cannot create a NamespaceClassifierReference for null")
        }
        val namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
        var classifierRef = TypesFactory.eINSTANCE.createClassifierReference
        classifierRef.target = concreteClassifier
        namespaceClassifierReference.classifierReferences.add(classifierRef)
        return namespaceClassifierReference
    }
    
    /**
     * 
     * @return the classifier that is wrapped in the typeref. Returns null if the type reference does not contain
     *          any classifier
     */
    def static Classifier getClassifierFromTypeReference(TypeReference typeRef) {
        val type = getJavaTypeFromTypeReference(typeRef)
        if (type instanceof Classifier) {
            return type
        } else {
            logger.warn("The TypeReference " + typeRef + " does not contain a Classifier. Returning null.")
            return null
        }
    }
    
    /**
     * Unwraps the type reference and returns the contained type.
     * 
     */
    def static dispatch Type getJavaTypeFromTypeReference(TypeReference typeRef) {
        logger.warn(typeRef + " is neither a NamespaceClassifierReference nor a PrimitiveType. Returning null.")
        return null
    }
    
    def static dispatch Type getJavaTypeFromTypeReference(PrimitiveType primType) {
        return primType
    }
    
    def static dispatch Type getJavaTypeFromTypeReference(NamespaceClassifierReference namespaceRef) {
        if (namespaceRef.classifierReferences.nullOrEmpty) {
            throw new IllegalArgumentException(namespaceRef + " has no classifierReferences")
        } else {
            return getJavaTypeFromTypeReference(namespaceRef.classifierReferences.head)
        }
    }
    
    def static dispatch Type getJavaTypeFromTypeReference(ClassifierReference classifRef) {
        if (classifRef.target === null) {
            throw new IllegalArgumentException(classifRef + " contains no classifier")
        } else {
            return classifRef.target
        }
    }
    
    def static dispatch Type getJavaTypeFromTypeReference(Void nullReference) {
        logger.warn("Cannot get Type of a null-TypeReference. Returning null.")
        return null
    }
    
    def static void setTypeReference(TypedElement typedElement, TypeReference typeRef) {
        if (typeRef !== null) {
            typedElement.typeReference = typeRef
        } else {
            typedElement.typeReference = TypesFactory.eINSTANCE.createVoid
        }
    }
    
    /**
     * Compares two type references. Both type references must be primitive types or
     * namespaceclassifierreferences or null.
     * If they are primitive types, it will check if they are same primitive type
     * If they are NamespaceClassifierReferences, it will check the name of their wrapped classifiers for equality
     */
    def static dispatch typeReferenceEquals(TypeReference typeRef1, TypeReference typeRef2) {
        logger.warn("No dispatch Method found for the typeReferences " + typeRef1 + " and " + typeRef2 + ". Returning false.")
        return false
    }
    
    def static dispatch typeReferenceEquals(NamespaceClassifierReference typeRef1, NamespaceClassifierReference typeRef2) {
        return getClassifierFromTypeReference(typeRef1).name.equals(getClassifierFromTypeReference(typeRef2).name)
    }
    
    def static dispatch typeReferenceEquals(PrimitiveType primType1, PrimitiveType primtype2) {
        return primType1.class.equals(primtype2.class)
    }
    
    def static dispatch typeReferenceEquals(Void type1, Void typ22) {
        logger.warn("Both TypeReferences to compare are null. Returning true.")
        return true
    }
    
    def static getInnerTypeReferenceOfCollectionTypeReference(TypeReference typeRef) {
       if (typeRef instanceof NamespaceClassifierReference) {
           return (typeRef.classifierReferences.head.typeArguments.head as QualifiedTypeArgument).typeReference
       }
       logger.warn("Cannot get inner TypeReference of a non-NamespaceClassifierReference. Returning null.")
       return null
   }
}