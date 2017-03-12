/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Graphics;
import com.jogamp.opengl.GL;

/**
 *
 * @author Joe
 */
public class AlphaCutOff extends ShaderProgram
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
"//***************\n" +
"// Vertex Formats\n" +
"//***************\n" +
"//VertIn\n" +
"attribute vec3 a_Position;\n" +
"attribute vec2 a_UV;\n" +
"\n" +
"//FragIn\n" +
"varying vec2 v_UV;\n" +
"//varying vec3 v_Normal;\n" +
"\n" +
"//*********\n" +
"// Uniforms\n" +
"//*********\n" +
"//uniform  float _Time;\n" +
"//uniform mat4 _ModelScaleMatrix;\n" +
"//uniform mat4 _ModelRotationMatrix;\n" +
"//uniform mat4 _Object2WorldMatrix;\n" +
"//uniform mat4 _ViewMatrix;\n" +
"//uniform mat4 _ProjectionMatrix;\n" +
"\n" +
"//**************\n" +
"// Vertex shader\n" +
"//**************\n" +
"void main()\n" +
"{\n" +
"    //Calculate position\n" +
"     vec4 position = vec4(a_Position,1.0);\n" +
"    {       \n" +
"        //position = /*_MVP **/ position;\n" +
"        \n" +
"    }\n" +
"    \n" +
"    gl_Position = position;\n" +
"    \n" +
"    //Passthrough UV\n" +
"    v_UV = a_UV;\n" +
"   \n" +
"}\n" +
"";
    
    }

    @Override
    protected String fragmentShaderGLSL() 
    {
        return 
"//*************************\n" +
"// FragmentIn vertex format\n" +
"//*************************\n" +
"varying  vec2 v_UV;\n" +
"//varying  vec3 v_Normal ;\n" +
"\n" +
"//*********\n" +
"// Uniforms\n" +
"//*********\n" +
"//uniform  vec4 _Color;\n" +
"uniform sampler2D _Texture;\n" +
"\n" +
"//**********\n" +
"// Constants\n" +
"//**********\n" +
"const float c_AlphaCutOff = 1.0;\n" +
"\n" +
"//*********************************\n" +
"// Fragment operations forward decs\n" +
"//*********************************\n" +
"vec4 calculateTexelColor(const vec4 aFrag);\n" +
"vec4 alphaCutOff(const vec4 aFrag);\n" +
"\n" +
"//*****************\n" +
"// Fragment shader\n" +
"//****************\n" +
"void main()\n" +
"{\n" +
"     vec4 rvalue = vec4(0);\n" +
"    {\n" +
"        rvalue = calculateTexelColor(rvalue);\n" +
"        //rvalue = alphaCutOff(rvalue);\n" +
"        \n" +
"    }\n" +
"\n" +
"    gl_FragColor = rvalue;\n" +
"\n" +
"}\n" +
"//*******************************\n" +
"//Fragment operations definitions\n" +
"//*******************************\n" +
"vec4 calculateTexelColor(const vec4 aFrag)\n" +
"{\n" +
"    vec4 rvalue = aFrag;\n" +
"    {\n" +
"        rvalue = texture2D(_Texture, v_UV);\n" +
"        \n" +
"    }\n" +
"    \n" +
"    return rvalue;      \n" +
"    \n" +
"}\n" +
"\n" +
"vec4 alphaCutOff(const vec4 aFrag)\n" +
"{\n" +
"    vec4 rvalue = aFrag;\n" +
"    {\n" +
"        if (rvalue[3] < c_AlphaCutOff)\n" +
"            discard;  \n" +
"                    \n" +
"    }\n" +
"    \n" +
"    return rvalue;\n" +
"    \n" +
"}";
        
    }

    
}
