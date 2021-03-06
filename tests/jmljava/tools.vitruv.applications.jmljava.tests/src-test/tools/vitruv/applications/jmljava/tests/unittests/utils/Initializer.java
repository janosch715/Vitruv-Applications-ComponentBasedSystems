package tools.vitruv.applications.jmljava.tests.unittests.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EPackage;
import org.emftext.language.java.resource.JaMoPPUtil;

import tools.vitruv.domains.jml.language.JMLStandaloneSetup;
import tools.vitruv.domains.jml.language.jML.JMLPackage;

public class Initializer {

    private static boolean INITIALIZED_JML = false;
    private static boolean INITIALIZED_JAMOPP = false;
    
    public static void initLogging() {
        Logger.getRootLogger().setLevel(Level.TRACE);
    }
    
    public static void initJML() {
        if (!INITIALIZED_JML) {
        	if (!EPackage.Registry.INSTANCE.containsKey(JMLPackage.eINSTANCE.getNsURI())) {
        		new JMLStandaloneSetup().createInjectorAndDoEMFRegistration();        		
        	}
            INITIALIZED_JML = true;
        }
    }
    
    public static void initJaMoPP() {
        if (!INITIALIZED_JAMOPP) {
            JaMoPPUtil.initialize();
            INITIALIZED_JAMOPP = true;
        }
    }
    
}
