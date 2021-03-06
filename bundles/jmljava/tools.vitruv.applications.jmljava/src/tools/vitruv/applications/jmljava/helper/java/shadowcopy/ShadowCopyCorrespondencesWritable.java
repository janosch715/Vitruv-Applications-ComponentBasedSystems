package tools.vitruv.applications.jmljava.helper.java.shadowcopy;

import java.io.IOException;

import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.statements.Statement;

import tools.vitruv.domains.jml.language.jML.JMLExpressionHaving;
import tools.vitruv.domains.jml.language.jML.JMLSpecifiedElement;

public interface ShadowCopyCorrespondencesWritable extends ShadowCopyCorrespondences {

    public void addCorrespondence(Member shadowMember, ClassMethod specContainingMember);
    
    public void addCorrespondence(JMLSpecifiedElement jml, Member java);

    public CachedModelInstanceLoader getShadowModelInstanceLoader();

    public CachedModelInstanceLoader getShadowModelInstanceLoaderJML();

    public void addCorrespondence(JMLExpressionHaving jml, Statement java);
    
    
    public void saveAll() throws IOException;

}
