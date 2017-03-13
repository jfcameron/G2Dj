/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Debug;
import G2Dj.Graphics;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES2;

/**
 *
 * @author Joe
 */
public abstract class ShaderProgram extends GraphicsObject
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
        Graphics.getGL().getGL2ES2().glUseProgram(m_ProgramHandle);
        glDrawCalls();
        
    }
    
    protected final void compileGraphicsProgram(final String aVertexCode, final String aFragmentCode)
    {
        GL2ES2 gl = Graphics.getGL().getGL2ES2();
        
        final String vertex_shader   = aVertexCode;
        final String fragment_shader = aFragmentCode;
        
	Debug.log("Compiling "+getName()+" GLSL code");
        
        //Debug.log(vertex_shader);
        
	Debug.log("Compiling vertex stage sourcecode");
        //run the glsl sources through the compiler, keep handle to both compiled shaders
        int vs = gl.glCreateShader (GL2.GL_VERTEX_SHADER);
        gl.glShaderSource(vs, 1, new String[]{vertex_shader}, null);//glShaderSource (vs, 1, &vertex_shader, 0);
        gl.glCompileShader (vs);
        //GLHelp::Diagnostics::checkGLSLErrors(vs);
    
	Debug.log("Compiling fragment stage sourcecode");
        int fs = gl.glCreateShader (GL2.GL_FRAGMENT_SHADER);
        gl.glShaderSource (fs, 1, new String[]{fragment_shader}, null);
        gl.glCompileShader (fs);
	//GLHelp::Diagnostics::checkGLSLErrors(fs);
    
	Debug.log("Linking graphics program");
        //create the program with the compiled vert and frag shaders
        m_ProgramHandle = gl.glCreateProgram ();
        gl.glAttachShader (m_ProgramHandle, vs);
        gl.glAttachShader (m_ProgramHandle, fs);
        gl.glLinkProgram (m_ProgramHandle);
    
        int[] status = new int[]{-1};//GLint status;//
        
	gl.glGetProgramiv(m_ProgramHandle, GL2.GL_LINK_STATUS, status, 0);
	if (status[0] == GL.GL_FALSE) 
        {
            int[] len = new int[1];
            gl.glGetProgramiv(m_ProgramHandle, GL2ES2.GL_INFO_LOG_LENGTH, len, 0); 
            
            Debug.log("The shader "+getName()+" has failed to compile!");

            byte[] errormessage = new byte[len[0]]; 
            gl.glGetProgramInfoLog(m_ProgramHandle, len[0], len, 0, errormessage, 0); 
            Debug.log("Error: " + new String(errormessage, 0, len[0]));
            
        }
        else
        {   
            Debug.log("Shader program successfully linked");
            
            int[] attributeCount = new int[]{-1};
            gl.glGetProgramiv(m_ProgramHandle, GL2.GL_ACTIVE_ATTRIBUTES, attributeCount,0);
            
            Debug.log("handle number: "+m_ProgramHandle);
            Debug.log("Active attributes: "+attributeCount[0]);
            
            int[] uniformCount = new int[]{-1};
            gl.glGetProgramiv(m_ProgramHandle, GL2.GL_ACTIVE_UNIFORMS, uniformCount,0);
            
            Debug.log("Active uniforms: "+uniformCount[0]);
            
        }
        
	//GLHelp::Diagnostics::checkGLErrors();
        
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
    
}
