/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Resource.Graphics;

import grimhaus.com.G2Dj.Imp.Graphics.ResourceShader;
import grimhaus.com.G2Dj.Imp.Graphics.GL;

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
        GL.glDisable  (GL.GL_CULL_FACE);
    
    }
    
}
