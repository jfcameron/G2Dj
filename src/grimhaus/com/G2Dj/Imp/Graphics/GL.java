/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

//.if ANDROID
//|import android.opengl.GLES20;
//.endif

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
        //|return GLES20.glGetUniformLocation(aShaderHandle, aUniformName);
        //.endif
        
    }
    
    public static void glActiveTexture(final int aTextureUnit)
    {
        //.if DESKTOP
        gl.glActiveTexture(aTextureUnit);
        //.elseif ANDROID
        //|GLES20.glActiveTexture(aTextureUnit);
        //.endif
        
    }
    
    public static void glBindTexture(final int aTextureType, final int aTextureHandle)
    {
        //.if DESKTOP
        gl.glBindTexture(aTextureType, aTextureHandle);
        //.elseif ANDROID
        //|GLES20.glBindTexture(aTextureType, aTextureHandle);
        //.endif
    }
    
    public static void glUniform1i(final int aUniformHandle, final int aValue)
    {
        //.if DESKTOP
        gl.glUniform1i(aUniformHandle, aValue);
        //.elseif ANDROID
        //|GLES20.glUniform1i(aUniformHandle, aValue);
        //.endif
        
    }
    
    public static void glUniform1f(final int aUniformHandle, final float aValue)
    {
        //.if DESKTOP
        gl.glUniform1f(aUniformHandle, aValue);
        //.elseif ANDROID
        //|GLES20.glUniform1f(aUniformHandle, aValue);
        //.endif
        
    }
    
    public static void glUniformMatrix4fv(final int aUniformHandle, final int aCount, final Boolean aTranspose, FloatBuffer aData  )
    {
        //.if DESKTOP
        gl.glUniformMatrix4fv(aUniformHandle, aCount, aTranspose, aData);
        //.elseif ANDROID
        //|GLES20.glUniformMatrix4fv(aUniformHandle, aCount, aTranspose, aData);
        //.endif
        
    }

    public static void glViewport(final int aX, final int aY, final int aWidth, final int aHeight)
    {
        //.if DESKTOP
        gl.glViewport(aX,aY,aWidth,aHeight);
        //.elseif ANDROID
        //|GLES20.glViewport(aX,aY,aWidth,aHeight);
        //.endif
        
    }
    
    public static void glScissor(final int aX, final int aY, final int aWidth, final int aHeight)
    {
        //.if DESKTOP
        gl.glScissor (aX,aY,aWidth,aHeight); 
        //.elseif ANDROID
        //|GLES20.glScissor (aX,aY,aWidth,aHeight);
        //.endif
        
    }
    
    public static void glClearColor(final float aRed, final float aGreen, final float aBlue, final float aAlpha)
    {
        //.if DESKTOP
        gl.glClearColor(aRed,aGreen,aBlue,aAlpha);
        //.elseif ANDROID
        //|GLES20.glClearColor(aRed,aGreen,aBlue,aAlpha);
        //.endif
        
    }
    
    public static void glClear(final int aClearMask)
    {
        //.if DESKTOP
        gl.glClear(aClearMask);
        //.elseif ANDROID
        //|GLES20.glClear(aClearMask);
        //.endif
        
    }
    
    public static void glEnable(final int aCapability)
    {
        //.if DESKTOP
        gl.glEnable(aCapability);
        //.elseif ANDROID
        //|GLES20.glEnable(aCapability);
        //.endif
        
    }
    
    public static void glDisable(final int aCapability)
    {
        //.if DESKTOP
        gl.glDisable(aCapability);
        //.elseif ANDROID
        //|GLES20.glDisable(aCapability);
        //.endif
        
    }
    
    public static void glUseProgram(final int aProgramHandle)
    {
        //.if DESKTOP
        gl.glUseProgram(aProgramHandle);
        //.elseif ANDROID
        //|GLES20.glUseProgram(aProgramHandle);
        //.endif
        
    }
    
    public static int glCreateShader(final int aShaderType)
    {
        //.if DESKTOP
        return gl.glCreateShader(aShaderType);
        //.elseif ANDROID
        //|return GLES20.glCreateShader(aShaderType);
        //.endif
        
    }
    
    public static void glShaderSource(final int aShaderHandle, final int aCount, final String[] aStrings, final IntBuffer aLengths)
    {
        //.if DESKTOP
        gl.glShaderSource(aShaderHandle, aCount, aStrings, aLengths);
        //.elseif ANDROID
        //|throw new java.lang.UnsupportedOperationException("Not supported yet.");
        //.endif
        
    }
    
    public static void glCompileShader(final int aShaderHandle)
    {
        //.if DESKTOP
        gl.glCompileShader(aShaderHandle);
        //.elseif ANDROID
        //|GLES20.glCompileShader(aShaderHandle);
        //.endif
        
    }
    
    public static int glCreateProgram()
    {
        //.if DESKTOP
        return gl.glCreateProgram();
        //.elseif ANDROID
        //|return GLES20.glCreateProgram();
        //.endif
        
    }
    
    public static void glAttachShader(final int aProgramHandle, final int aShaderHandle)
    {
        //.if DESKTOP
        gl.glAttachShader(aProgramHandle,aShaderHandle);
        //.elseif ANDROID
        //|GLES20.glAttachShader(aProgramHandle,aShaderHandle);
        //.endif
        
    }
    
    public static void glLinkProgram(final int aProgramHandle)
    {
        //.if DESKTOP
        gl.glLinkProgram(aProgramHandle);
        //.elseif ANDROID
        //|GLES20.glLinkProgram(aProgramHandle);
        //.endif

    }
    
    public static void glGetProgramiv(final int aProgramHandle, final int aPName,int[] params, final int aOffset)
    {
        //.if DESKTOP
        gl.glGetProgramiv(aProgramHandle, aPName, params, aOffset);
        //.elseif ANDROID
        //|GLES20.glGetProgramiv(aProgramHandle, aPName, params, aOffset);
        //.endif
        
    }

    //.if DESKTOP
    public static void glGetProgramInfoLog(final int aProgramHandle, final int aMaxLength, final int[] aLogLength, final int aInt, final byte[] aInfoBuffer, final int aInt2 )
    {
        gl.glGetProgramInfoLog(aProgramHandle, aMaxLength, aLogLength, aInt, aInfoBuffer, aInt2);
        
    }
    //.elseif ANDROID
    //|public static String glGetProgramInfoLog(final int aProgramHandle)
    //|{
    //|    return GLES20.glGetProgramInfoLog(aProgramHandle);
    //|
    //|}
    //.endif
    
    public static void glDrawArrays(final int aPrimitiveType, final int aFirst, final int aCount)
    {
        //.if DESKTOP
        gl.glDrawArrays(aPrimitiveType, aFirst, aCount);
        //.elseif ANDROID
        //|GLES20.glDrawArrays(aPrimitiveType, aFirst, aCount);
        //.endif
        
    }
    
    public static int glGetAttribLocation(final int aProgramHandle, final String aAttributeName)
    {
        //.if DESKTOP
        return gl.glGetAttribLocation(aProgramHandle, aAttributeName);
        //.elseif ANDROID
        //|return GLES20.glGetAttribLocation(aProgramHandle, aAttributeName);
        //.endif
        
    }
    
    public static void glEnableVertexAttribArray(final int aAttributeIndex)
    {
        //.if DESKTOP
        gl.glEnableVertexAttribArray(aAttributeIndex);
        //.elseif ANDROID
        //|GLES20.glEnableVertexAttribArray(aAttributeIndex);
        //.endif
        
    }
    
    public static void glVertexAttribPointer(final int aAttributeIndex, final int aAttributeSize, final int aType, final Boolean aNormalized, final int aStride, final int aOffset )
    {
        //.if DESKTOP
        gl.glVertexAttribPointer(aAttributeIndex,aAttributeSize,aType,aNormalized,aStride,aOffset);
        //.elseif ANDROID
        //|GLES20.glVertexAttribPointer(aAttributeIndex,aAttributeSize,aType,aNormalized,aStride,aOffset);
        //.endif
        
    }
    
    public static void glBindBuffer(final int aTarget, final int aBufferHandle)
    {
        //.if DESKTOP
        gl.glBindBuffer(aTarget,aBufferHandle);
        //.elseif ANDROID
        //|GLES20.glBindBuffer(aTarget,aBufferHandle);
        //.endif
        
    }
    
    public static void glGenBuffers(final int aNumber, final IntBuffer aBufferHandle)
    {
        //.if DESKTOP
        gl.glGenBuffers(aNumber,aBufferHandle);
        //.elseif ANDROID
        //|GLES20.glGenBuffers(aNumber,aBufferHandle);
        //.endif
        
    }
    
    public static void glBufferData(final int aTarget, final int aSize, final FloatBuffer aVertexData, final int aUsageHint)
    {
        //.if DESKTOP
        gl.glBufferData (aTarget, aSize, aVertexData, aUsageHint);
        //.elseif ANDROID
        //|GLES20.glBufferData (aTarget, aSize, aVertexData, aUsageHint);
        //.endif
        
        
    }
    
    public static void glGenTextures(final int aNumberOfTextures, final IntBuffer aTextureHandles)
    {
        //.if DESKTOP
        gl.glGenTextures( aNumberOfTextures, aTextureHandles );
        //.elseif ANDROID
        //|GLES20.glGenTextures( aNumberOfTextures, aTextureHandles );
        //.endif
        
    }
    
    public static void glTexImage2D(final int aTarget, final int aLevelOfDetail, final int aInternalFormat, final int aWidth, final int aHeight, final int aBorder, final int aFormat, final int aType, final IntBuffer aData)
    {
        //.if DESKTOP
        gl.glTexImage2D( aTarget, aLevelOfDetail, aInternalFormat, aWidth, aHeight, aBorder, aFormat, aType, aData );
        //.elseif ANDROID
        //|GLES20.glTexImage2D( aTarget, aLevelOfDetail, aInternalFormat, aWidth, aHeight, aBorder, aFormat, aType, aData );
        //.endif
        
    }
    
    public static void glTexParameteri(final int aTarget, final int aPName, final int aParam)
    {
        //.if DESKTOP
        gl.glTexParameteri(aTarget, aPName, aParam );
        //.elseif ANDROID
        //|GLES20.glTexParameteri(aTarget, aPName, aParam );
        //.endif
        
    }
    
//.if DESKTOP
        
//.elseif ANDROID
//|
//.endif
    
/*
gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST );
    
    */
    
    //
    // Constants
    //
    public static final int GL_TEXTURE_2D;
    public static final int GL_TEXTURE0;
    public static final int GL_TEXTURE1;
    public static final int GL_TEXTURE2;
    public static final int GL_TEXTURE3;
    public static final int GL_TEXTURE4;
    public static final int GL_TEXTURE5;
    public static final int GL_TEXTURE6;
    public static final int GL_TEXTURE7;
    public static final int GL_COLOR_BUFFER_BIT;
    public static final int GL_DEPTH_BUFFER_BIT;
    public static final int GL_DEPTH_TEST;
    public static final int GL_SCISSOR_TEST;      
    public static final int GL_CULL_FACE;
    public static final int GL_FALSE;
    public static final int GL_VERTEX_SHADER;
    public static final int GL_FRAGMENT_SHADER;
    public static final int GL_LINK_STATUS;
    public static final int GL_INFO_LOG_LENGTH;
    public static final int GL_ACTIVE_ATTRIBUTES;
    public static final int GL_ACTIVE_UNIFORMS;
    public static final int GL_TRIANGLES;
    public static final int GL_FLOAT;
    public static final int GL_ARRAY_BUFFER;
    public static final int GL_STATIC_DRAW;
    public static final int GL_RGBA;
    public static final int GL_UNSIGNED_BYTE;
    public static final int GL_TEXTURE_MIN_FILTER;
    public static final int GL_NEAREST;
    public static final int GL_TEXTURE_MAG_FILTER;
    
    static
    {
        //.if DESKTOP
        GL_TEXTURE_2D = com.jogamp.opengl.GL.GL_TEXTURE_2D;
        GL_TEXTURE0 = com.jogamp.opengl.GL.GL_TEXTURE0;
        GL_TEXTURE1 = com.jogamp.opengl.GL.GL_TEXTURE1;
        GL_TEXTURE2 = com.jogamp.opengl.GL.GL_TEXTURE2;
        GL_TEXTURE3 = com.jogamp.opengl.GL.GL_TEXTURE3;
        GL_TEXTURE4 = com.jogamp.opengl.GL.GL_TEXTURE4;
        GL_TEXTURE5 = com.jogamp.opengl.GL.GL_TEXTURE5;
        GL_TEXTURE6 = com.jogamp.opengl.GL.GL_TEXTURE6;
        GL_TEXTURE7 = com.jogamp.opengl.GL.GL_TEXTURE7;
        GL_COLOR_BUFFER_BIT = com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
        GL_DEPTH_BUFFER_BIT = com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
        GL_DEPTH_TEST = com.jogamp.opengl.GL.GL_DEPTH_TEST;
        GL_SCISSOR_TEST = com.jogamp.opengl.GL.GL_SCISSOR_TEST;
        GL_CULL_FACE = com.jogamp.opengl.GL.GL_CULL_FACE;
        GL_FALSE = com.jogamp.opengl.GL.GL_FALSE;
        GL_VERTEX_SHADER = com.jogamp.opengl.GL2.GL_VERTEX_SHADER; 
        GL_FRAGMENT_SHADER = com.jogamp.opengl.GL2.GL_FRAGMENT_SHADER;
        GL_LINK_STATUS = com.jogamp.opengl.GL2.GL_LINK_STATUS;
        GL_INFO_LOG_LENGTH = com.jogamp.opengl.GL2.GL_INFO_LOG_LENGTH;
        GL_ACTIVE_ATTRIBUTES = com.jogamp.opengl.GL2.GL_ACTIVE_ATTRIBUTES;
        GL_ACTIVE_UNIFORMS = com.jogamp.opengl.GL2.GL_ACTIVE_UNIFORMS;
        GL_TRIANGLES = com.jogamp.opengl.GL2.GL_TRIANGLES;
        GL_FLOAT = com.jogamp.opengl.GL2.GL_FLOAT;
        GL_ARRAY_BUFFER = com.jogamp.opengl.GL2.GL_ARRAY_BUFFER;
        GL_STATIC_DRAW = com.jogamp.opengl.GL2.GL_STATIC_DRAW;
        GL_RGBA = com.jogamp.opengl.GL2.GL_RGBA;
        GL_UNSIGNED_BYTE = com.jogamp.opengl.GL2.GL_UNSIGNED_BYTE;
        GL_TEXTURE_MIN_FILTER = com.jogamp.opengl.GL2.GL_TEXTURE_MIN_FILTER;
        GL_NEAREST = com.jogamp.opengl.GL2.GL_NEAREST;
        GL_TEXTURE_MAG_FILTER = com.jogamp.opengl.GL2.GL_TEXTURE_MAG_FILTER;
        //.elseif ANDROID
        //|GL_TEXTURE_2D = GLES20.GL_TEXTURE_2D;
        //|GL_TEXTURE0 = GLES20.GL_TEXTURE0;
        //|GL_TEXTURE1 = GLES20.GL_TEXTURE1;
        //|GL_TEXTURE2 = GLES20.GL_TEXTURE2;
        //|GL_TEXTURE3 = GLES20.GL_TEXTURE3;
        //|GL_TEXTURE4 = GLES20.GL_TEXTURE4;
        //|GL_TEXTURE5 = GLES20.GL_TEXTURE5;
        //|GL_TEXTURE6 = GLES20.GL_TEXTURE6;
        //|GL_TEXTURE7 = GLES20.GL_TEXTURE7;
        //|GL_COLOR_BUFFER_BIT = GLES20.GL_COLOR_BUFFER_BIT;
        //|GL_DEPTH_BUFFER_BIT = GLES20.GL_DEPTH_BUFFER_BIT;
        //|GL_DEPTH_TEST = GLES20.GL_DEPTH_TEST;
        //|GL_SCISSOR_TEST = GLES20.GL_SCISSOR_TEST;
        //|GL_CULL_FACE = GLES20.GL_CULL_FACE;
        //|GL_FALSE = GLES20.GL_FALSE;
        //|GL_VERTEX_SHADER = GLES20.GL_VERTEX_SHADER;
        //|GL_FRAGMENT_SHADER = GLES20.GL_FRAGMENT_SHADER;
        //|GL_LINK_STATUS = GLES20.GL_LINK_STATUS;
        //|GL_INFO_LOG_LENGTH = GLES20.GL_INFO_LOG_LENGTH;
        //|GL_ACTIVE_ATTRIBUTES = GLES20.GL_ACTIVE_ATTRIBUTES;
        //|GL_ACTIVE_UNIFORMS = GLES20.GL_ACTIVE_UNIFORMS;
        //|GL_TRIANGLES = GLES20.GL_TRIANGLES;
        //|GL_FLOAT = GLES20.GL_FLOAT;
        //|GL_ARRAY_BUFFER = GLES20.GL_ARRAY_BUFFER;
        //|GL_STATIC_DRAW = GLES20.GL_STATIC_DRAW;
        //|GL_RGBA = GLES20.GL_RGBA;
        //|GL_UNSIGNED_BYTE = GLES20.GL_UNSIGNED_BYTE;
        //|GL_TEXTURE_MIN_FILTER = GLES20.GL_TEXTURE_MIN_FILTER;
        //|GL_NEAREST = GLES20.GL_NEAREST;
        //|GL_TEXTURE_MAG_FILTER = GLES20.GL_TEXTURE_MAG_FILTER;
        //.endif
        
    }
    
    
}
