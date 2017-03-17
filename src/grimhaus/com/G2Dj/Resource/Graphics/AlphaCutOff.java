/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Resource.Graphics;

import grimhaus.com.G2Dj.Imp.Graphics.ResourceShader;
import grimhaus.com.G2Dj.Graphics;
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
