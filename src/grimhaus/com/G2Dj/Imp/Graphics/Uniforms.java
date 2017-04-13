/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;
import java.nio.FloatBuffer;

/**
 *
 * @author Joe
 */
public class Uniforms 
{
    protected static final String s_StandardDiffuseTextureName = "_Texture";
    
    public static final void loadTexture(final int aShaderHandle, final String aUniformName, final int aTextureHandle, final int aTextureUnit/*, final GLenum &aTextureType*/)
    {
        int uniformHandle  = GL.glGetUniformLocation(aShaderHandle, aUniformName);
        int theTextureType = GL.GL_TEXTURE_2D;
        
        if (uniformHandle == -1)
		return;
        
        switch (aTextureUnit)
	{
		case  1:GL.glActiveTexture(GL. GL_TEXTURE1);break;
                case  2:GL.glActiveTexture(GL. GL_TEXTURE2);break;
		case  3:GL.glActiveTexture(GL. GL_TEXTURE3);break;
		case  4:GL.glActiveTexture(GL. GL_TEXTURE4);break;
		case  5:GL.glActiveTexture(GL. GL_TEXTURE5);break;
		case  6:GL.glActiveTexture(GL. GL_TEXTURE6);break;
		case  7:GL.glActiveTexture(GL. GL_TEXTURE7);break;
                
                default:GL.glActiveTexture(GL. GL_TEXTURE0);break;
		
	}
        
        GL.glBindTexture(theTextureType, aTextureHandle);
        GL.glUniform1i(uniformHandle, aTextureUnit);
        
        
    }
    
    public static final void load1Float(final int aShaderHandle, final String aUniformName, final float aValue)
    {
	int uniformHandle = GL.glGetUniformLocation(aShaderHandle, aUniformName);

	if (uniformHandle != -1)
		GL.glUniform1f(uniformHandle, aValue);

    }
    
    public static final void load2Float(final int aShaderHandle, final String aUniformName, final float aX, final float aY)
    {
	int uniformHandle = GL.glGetUniformLocation(aShaderHandle, aUniformName);

	if (uniformHandle != -1)
            GL.glUniform2f(uniformHandle, aX, aY);

    }
    
    public static final void load3Float(final int aShaderHandle, final String aUniformName, final float aX, final float aY, final float aZ)
    {
	int uniformHandle = GL.glGetUniformLocation(aShaderHandle, aUniformName);

	if (uniformHandle != -1)
            GL.glUniform3f(uniformHandle, aX, aY, aZ);

    }
    
    public static final void load4Float(final int aShaderHandle, final String aUniformName, final float aX, final float aY, final float aZ, final float aW)
    {
	int uniformHandle = GL.glGetUniformLocation(aShaderHandle, aUniformName);

	if (uniformHandle != -1)
            GL.glUniform4f(uniformHandle, aX, aY, aZ, aW);

    }
    
    public static final void loadMatrix4x4(final int aShaderHandle, final String aUniformName, final FloatBuffer aMatrix4x4)
    {
        int uniformHandle = GL.glGetUniformLocation(aShaderHandle, aUniformName);

	if (uniformHandle != -1)
		GL.glUniformMatrix4fv(uniformHandle, 1, false, aMatrix4x4);
        
    }
    
}
