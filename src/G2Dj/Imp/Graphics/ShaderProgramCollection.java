/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Resource.Graphics.PinkShaderOfDeath;
import G2Dj.Resource.Graphics.AlphaCutOff;
import G2Dj.Resources;
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
    @Override
    protected void add(final ShaderProgram aItem)
    {
        //hmm
        
        super.add(aItem);
        
    }
    
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
        add(new PinkShaderOfDeath());
        add(new AlphaCutOff());
        
    }
    
}
