/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Resource.Graphics;

import G2Dj.Imp.Graphics.ResourceShader;
import G2Dj.Graphics;
import com.jogamp.opengl.GL;

/**
 *
 * @author Joe
 */
public class AlphaCutOff extends ResourceShader
{
    @Override
    protected void glDrawCalls() 
    {        
        //put gl calls here
        GL gl = Graphics.getGL();
        gl.glDisable  (gl.GL_CULL_FACE);
    
    }
    
}
