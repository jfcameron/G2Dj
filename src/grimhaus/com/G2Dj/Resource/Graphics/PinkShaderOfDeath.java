/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Resource.Graphics;

import grimhaus.com.G2Dj.Imp.Graphics.GL;
import grimhaus.com.G2Dj.Imp.Graphics.ResourceShader;

/**
 *
 * @author Joe
 */
public class PinkShaderOfDeath extends ResourceShader
{
    @Override
    protected void glDrawCalls() 
    {        
        //put gl calls here
        GL.glEnable(GL.GL_PROGRAM_POINT_SIZE);
    
    }
    
}
