/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import grimhaus.com.G2Dj.Debug;
import java.nio.IntBuffer;

/**
 *
 * @author Joe
 */
public abstract class ShaderProgram extends GraphicsResource
{
    //
    // Data members
    //
    protected int m_ProgramHandle = 0;
    
    //
    // Accessors
    //
    public int getProgramHandle(){return m_ProgramHandle;}
    
    //
    // Public Interface
    //
    public void draw()
    {
        GL.glUseProgram(m_ProgramHandle);
        glDrawCalls();
        
    }
    
    protected final void compileGraphicsProgram(final String aVertexCode, final String aFragmentCode)
    {
        //GL2ES2 gl = Graphics.getGL().getGL2ES2();
        
        final String vertex_shader   = aVertexCode;
        final String fragment_shader = aFragmentCode;
        
	    Debug.log("Compiling "+getName()+" GLSL code");

	    Debug.log("Compiling vertex stage sourcecode");
        //run the glsl sources through the compiler, keep handle to both compiled shaders
        int vs = GL.glCreateShader (GL.GL_VERTEX_SHADER);
        //.if DESKTOP
        GL.glShaderSource(vs, 1, new String[]{vertex_shader}, null);//glShaderSource (vs, 1, &vertex_shader, 0);
        //.elseif ANDROID
        //|GL.glShaderSource(vs,vertex_shader);
        //.endif
        GL.glCompileShader (vs);

	    Debug.log("Compiling fragment stage sourcecode");
        int fs = GL.glCreateShader (GL.GL_FRAGMENT_SHADER);
        //.if DESKTOP
        GL.glShaderSource (fs, 1, new String[]{fragment_shader}, null);
        //.elseif ANDROID
        //|GL.glShaderSource(fs,fragment_shader);
        //.endif
        GL.glCompileShader (fs);

	    Debug.log("Linking graphics program");
        //create the program with the compiled vert and frag shaders
        m_ProgramHandle = GL.glCreateProgram ();
        GL.glAttachShader (m_ProgramHandle, vs);
        GL.glAttachShader (m_ProgramHandle, fs);
        GL.glLinkProgram (m_ProgramHandle);
    
        int[] status = new int[]{-1};//GLint status;//
        
	    GL.glGetProgramiv(m_ProgramHandle, GL.GL_LINK_STATUS, status, 0);
	    if (status[0] == GL.GL_FALSE)
        {
            Debug.log("The shader "+getName()+" has failed to compile!");

            String errormessageString;

            //.if DESKTOP
            int[] len = new int[1];
            GL.glGetProgramiv(m_ProgramHandle, GL.GL_INFO_LOG_LENGTH, len, 0);
            byte[] errormessage = new byte[len[0]];
            GL.glGetProgramInfoLog(m_ProgramHandle, len[0], len, 0, errormessage, 0);
            //Debug.log("Error: " + new String(errormessage, 0, len[0]));
            errormessageString = new String(errormessage, 0, len[0]);
            //.elseif ANDROID
            //|errormessageString = GL.glGetProgramInfoLog(m_ProgramHandle);

            //.endif
            Debug.log("Error: " +errormessageString);
            
        }
        else
        {   
            Debug.log("Shader program successfully linked");
            
            int[] attributeCount = new int[]{-1};
            GL.glGetProgramiv(m_ProgramHandle, GL.GL_ACTIVE_ATTRIBUTES, attributeCount,0);
            
            Debug.log("handle number: "+m_ProgramHandle);
            Debug.log("Active attributes: "+attributeCount[0]);
            
            int[] uniformCount = new int[]{-1};
            GL.glGetProgramiv(m_ProgramHandle, GL.GL_ACTIVE_UNIFORMS, uniformCount,0);
            
            Debug.log("Active uniforms: "+uniformCount[0]);
            
        }

    }
    
    /*protected void processShaderSourceFile(final String aFileName, String ioDrawCode, String ioVertexCode, String ioFragmentCode)
    {
        
        
    }*/

    protected abstract void   glDrawCalls();
    protected abstract String vertexShaderGLSL();
    protected abstract String fragmentShaderGLSL();
    
    //
    // Constructors
    //
    public ShaderProgram()
    {
        //super(this.getClass().getSimpleName());
        m_Name = this.getClass().getSimpleName();
        
        compileGraphicsProgram(vertexShaderGLSL(),fragmentShaderGLSL());
        
    }
    
    @Override
    protected void finalize() throws Throwable
    {
        GL.glDeleteProgram(m_ProgramHandle);
        
        super.finalize();
        
    }
    
}
