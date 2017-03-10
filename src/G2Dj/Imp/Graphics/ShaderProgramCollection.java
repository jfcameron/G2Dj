/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Debug;
import java.util.ArrayList;

import java.lang.reflect.ParameterizedType;


/**
 *
 * @author Joe
 */
public class ShaderProgramCollection extends GraphicsObjectCollection<ShaderProgram>
{
    //*************
    // Data members
    //*************
    
    
    //
    //
    //
    //public /*<T extends ShaderProgram>*/ void loadShader(ShaderProgram aShaderProgram)
    @Override
    public void add(final ShaderProgram aItem)
    {
        //hmm
        
        super.add(aItem);
        
    }
    
    //*************
    // Constructors
    //*************
    public ShaderProgramCollection()
    {
        //Debug.log(this.getClass());
        
    }
        
}
