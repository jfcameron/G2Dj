/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import java.nio.FloatBuffer;

/**
 * OpenGLES2.0 call wrapper. Wraps Android GLES2.0 and JOGLES2.0
 * @author Joseph Cameron
 */
public class GL 
{
    //.if DESKTOP
    protected static com.jogamp.opengl.GL2ES2 gl;
    //.endif
    //
    // OpenGL Interface
    //
    public static int glGetUniformLocation(final int aShaderHandle, final String aUniformName)
    {
        //.if DESKTOP
        return gl.glGetUniformLocation(aShaderHandle, aUniformName);
        //.elseif ANDROID
        
        //.endif
    }
    
    public static void glActiveTexture(final int aTextureUnit)
    {
        //.if DESKTOP
        gl.glActiveTexture(aTextureUnit);
        //.elseif ANDROID
        
        //.endif
    }
    
    public static void glBindTexture(final int aTextureType, final int aTextureHandle)
    {
        //.if DESKTOP
        gl.glBindTexture(aTextureType, aTextureHandle);
        //.elseif ANDROID
        
        //.endif
    }
    
    public static void glUniform1i(final int aUniformHandle, final int aValue)
    {
        //.if DESKTOP
        gl.glUniform1i(aUniformHandle, aValue);
        //.elseif ANDROID
        
        //.endif
    }
    
    public static void glUniform1f(final int aUniformHandle, final float aValue)
    {
        //.if DESKTOP
        gl.glUniform1f(aUniformHandle, aValue);
        //.elseif ANDROID
        
        //.endif
    }
    
    public static void glUniformMatrix4fv(final int aUniformHandle, final int aCount, final Boolean aTranspose, FloatBuffer aData  )
    {
        //.if DESKTOP
        gl.glUniformMatrix4fv(aUniformHandle, aCount, aTranspose, aData);
        //.elseif ANDROID
        
        //.endif
    }
    
    
    //
    // Constants
    //
    public static final int GL_TEXTURE_2D;// = com.jogamp.opengl.GL.GL_TEXTURE_2D;
    //Texture units
    public static final int GL_TEXTURE0;// = com.jogamp.opengl.GL.GL_TEXTURE0;
    public static final int GL_TEXTURE1;// = com.jogamp.opengl.GL.GL_TEXTURE1;
    public static final int GL_TEXTURE2;// = com.jogamp.opengl.GL.GL_TEXTURE2;
    public static final int GL_TEXTURE3;// = com.jogamp.opengl.GL.GL_TEXTURE3;
    public static final int GL_TEXTURE4;// = com.jogamp.opengl.GL.GL_TEXTURE4;
    public static final int GL_TEXTURE5;// = com.jogamp.opengl.GL.GL_TEXTURE5;
    public static final int GL_TEXTURE6;// = com.jogamp.opengl.GL.GL_TEXTURE6;
    public static final int GL_TEXTURE7;// = com.jogamp.opengl.GL.GL_TEXTURE7;
    
    static
    {
        //.if DESKTOP
        GL_TEXTURE_2D = com.jogamp.opengl.GL.GL_TEXTURE_2D;
        GL_TEXTURE0   = com.jogamp.opengl.GL.GL_TEXTURE0;
        GL_TEXTURE1   = com.jogamp.opengl.GL.GL_TEXTURE1;
        GL_TEXTURE2   = com.jogamp.opengl.GL.GL_TEXTURE2;
        GL_TEXTURE3   = com.jogamp.opengl.GL.GL_TEXTURE3;
        GL_TEXTURE4   = com.jogamp.opengl.GL.GL_TEXTURE4;
        GL_TEXTURE5   = com.jogamp.opengl.GL.GL_TEXTURE5;
        GL_TEXTURE6   = com.jogamp.opengl.GL.GL_TEXTURE6;
        GL_TEXTURE7   = com.jogamp.opengl.GL.GL_TEXTURE7;
        //.elseif ANDROID
        
        //.endif
        
    }
    
    
}