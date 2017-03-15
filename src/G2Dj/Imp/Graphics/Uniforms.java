/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Debug;
import G2Dj.Graphics;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;
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
        GL2ES2 gl = Graphics.getGL().getGL2ES2();
        
        int uniformHandle  = gl.glGetUniformLocation(aShaderHandle, aUniformName);
        int theTextureType = GL.GL_TEXTURE_2D;
        
        if (uniformHandle == -1)
		return;
        
        switch (aTextureUnit)
	{
		case  1:gl.glActiveTexture(GL. GL_TEXTURE1);break;
                case  2:gl.glActiveTexture(GL. GL_TEXTURE2);break;
		case  3:gl.glActiveTexture(GL. GL_TEXTURE3);break;
		case  4:gl.glActiveTexture(GL. GL_TEXTURE4);break;
		case  5:gl.glActiveTexture(GL. GL_TEXTURE5);break;
		case  6:gl.glActiveTexture(GL. GL_TEXTURE6);break;
		case  7:gl.glActiveTexture(GL. GL_TEXTURE7);break;
                
                default:gl.glActiveTexture(GL. GL_TEXTURE0);break;
		
	}
        
        gl.glBindTexture(theTextureType, aTextureHandle);
        gl.glUniform1i(uniformHandle, aTextureUnit);
        
        
    }
    
    void load1Foat(final int aShaderHandle, final String aUniformName, final float aValue)
    {
        GL2ES2 gl = Graphics.getGL().getGL2ES2();
        
	int uniformHandle = gl.glGetUniformLocation(aShaderHandle, aUniformName);

	if (uniformHandle != -1)
		gl.glUniform1f(uniformHandle, aValue);

    }
    
    void loadMatrix4x4(final int aShaderHandle, final String aUniformName, final FloatBuffer aMatrix4x4)
    {
        GL2ES2 gl = Graphics.getGL().getGL2ES2();
        
        int uniformHandle = gl.glGetUniformLocation(aShaderHandle, aUniformName);

	if (uniformHandle != -1)
		gl.glUniformMatrix4fv(uniformHandle, 1, false, aMatrix4x4);
        
    }
    
}
