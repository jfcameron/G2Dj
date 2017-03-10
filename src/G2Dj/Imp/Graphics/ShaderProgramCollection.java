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
    public <T extends ShaderProgram> void loadShader(T aShaderProgram)
    {
        Debug.log(aShaderProgram.getName());
        
        //m_Vector.add(new T());
        
        //ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        //Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];
                
        /*try
        {
            return type.newInstance();
        }
        catch (Exception e)
        {
            // Oops, no default constructor
            throw new RuntimeException(e);
        }*/
        
    }
    
    //*************
    // Constructors
    //*************
    public ShaderProgramCollection()
    {
        Debug.log(this.getClass());
        
    }
        
}
