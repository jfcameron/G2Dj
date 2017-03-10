/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Debug;

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
        glDrawCalls();
        
    }
    
    //
    // Implementation
    //
    protected void compileGraphicsProgram(final String aVertexCode, final String aFragmentCode)
    {
        final String vertex_shader   = aVertexCode;
        final String fragment_shader = aFragmentCode;
        
	Debug.log("Compiling shader GLSL code\n");
        //std::cout << aVertexCode;
        
	/*Debug::log("Compiling vertex stage sourcecode\n");
        //run the glsl sources through the compiler, keep handle to both compiled shaders
        GLuint vs = glCreateShader (GL_VERTEX_SHADER);
        glShaderSource (vs, 1, &vertex_shader, 0);
        glCompileShader (vs);
        GLHelp::Diagnostics::checkGLSLErrors(vs);
    
	Debug::log("Compiling fragment stage sourcecode\n");
        GLuint fs = glCreateShader (GL_FRAGMENT_SHADER);
        glShaderSource (fs, 1, &fragment_shader, 0);
        glCompileShader (fs);
	GLHelp::Diagnostics::checkGLSLErrors(fs);
    
	Debug::log("Linking graphics program\n");
        //create the program with the compiled vert and frag shaders
        m_ProgramHandle = glCreateProgram ();
        glAttachShader (m_ProgramHandle, vs);
        glAttachShader (m_ProgramHandle, fs);
        glLinkProgram (m_ProgramHandle);
    
        GLint status;
	glGetProgramiv(m_ProgramHandle, GL_LINK_STATUS, &status);
	if (status == GL_FALSE) 
        {
            //print linker error
            int loglen;
            char logbuffer[1000];
            glGetProgramInfoLog(m_ProgramHandle, sizeof(logbuffer), &loglen, logbuffer);
            Debug::log("OpenGL Program Linker Error at ", logbuffer);
            throw(std::runtime_error("GLSL code could not be compiled!"));
    
        }
        else
        {
            Debug::log("Shader program successfully linked\n");
    
            GLint attributeCount = 0;
            glGetProgramiv(m_ProgramHandle, GL_ACTIVE_ATTRIBUTES, &attributeCount);
        
            Debug::log("handle number: ",(int)m_ProgramHandle, "\n");
            Debug::log("Active attributes: ", attributeCount, "\n");
		
    
        }
    
	GLHelp::Diagnostics::checkGLErrors();*/
        
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
        m_Name = this.getClass().getName();
        
        compileGraphicsProgram(vertexShaderGLSL(),fragmentShaderGLSL());
        
    }
    
}
