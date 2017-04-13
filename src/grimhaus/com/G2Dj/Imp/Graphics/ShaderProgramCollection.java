/*
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Resource.Graphics.PinkShaderOfDeath;
import grimhaus.com.G2Dj.Resource.Graphics.AlphaCutOff;
import grimhaus.com.G2Dj.Resource.Graphics.SimpleColor;
import grimhaus.com.G2Dj.Resources;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class ShaderProgramCollection extends GraphicsResourceCollection<ShaderProgram>
{
    //*************
    // Data members
    //*************
    
    
    //
    //
    //
    //public /*<T extends ShaderProgram>*/ void loadShader(ShaderProgram aShaderProgram)
    /*@Override
    protected void add(final ShaderProgram aItem)
    {
        //hmm
        
        super.add(aItem);
        
    }*/
    
    @Override
    public WeakReference<ShaderProgram> loadFromResource(String aAbsoluteResourcePath) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public WeakReference<ShaderProgram> loadFromFile(String aRelativeFilePath) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }
    
    //*************
    // Constructors
    //*************
    public ShaderProgramCollection()
    {
        addClass(PinkShaderOfDeath.class);
        addClass(AlphaCutOff.class);
        addClass(SimpleColor.class);

        Debug.log("ShaderProgramCollection default: "+get().get().getName(),get().get().getProgramHandle());

    }
    
}
