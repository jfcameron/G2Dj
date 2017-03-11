/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Debug;
import G2Dj.Graphics;
import com.jogamp.opengl.GL;

/**
 *
 * @author Joe
 */
public class PinkShaderOfDeath extends ShaderProgram
{
    @Override
    protected void glDrawCalls() 
    {        
        //put gl calls here
        GL gl = Graphics.getGL();
        gl.glEnable   (gl.GL_DEPTH_TEST);
        gl.glEnable   (gl.GL_CULL_FACE);
        gl.glDisable  (gl.GL_CULL_FACE);
        gl.glCullFace (gl.GL_BACK);
    
    }
    
    @Override
    protected String vertexShaderGLSL() 
    {    
        return 
"//VertIn\n" +
"attribute vec3 a_Position;\n" +
"attribute vec2 a_UV;\n" +
"    \n" +
"//Uniforms\n" +
"uniform mat4 _MVP;\n" +
"    \n" +
"void main ()                        \n" +
"{\n" +
"    gl_Position = /*_MVP **/ vec4(a_Position,1.0);  \n" +
"    \n" +
"}";
    
    }

    @Override
    protected String fragmentShaderGLSL() 
    {
        return 
"    void main()                            \n" +
"    {                                      \n" +
"        gl_FragColor = vec4(1,0.2,0.8,1);\n" +
"    \n" +
"    }";
        
    }

    
}
