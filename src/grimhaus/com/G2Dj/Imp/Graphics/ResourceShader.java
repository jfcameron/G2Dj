/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import grimhaus.com.G2Dj.Resources;

/**
 *
 * @author Joe
 */
public abstract class ResourceShader extends ShaderProgram
{
    @Override
    protected String vertexShaderGLSL() 
    {
        return Resources.loadTextFile(this.getClass().getName().replaceAll("\\.","/")+"_Vert.glsl").getData();
    
    }

    @Override
    protected String fragmentShaderGLSL() 
    {
        return Resources.loadTextFile(this.getClass().getName().replaceAll("\\.","/")+"_Frag.glsl").getData(); 
        
    }
    
}
