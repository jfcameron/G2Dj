/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Resources;

/**
 *
 * @author Joe
 */
public abstract class ResourceShader extends ShaderProgram
{
    @Override
    protected String vertexShaderGLSL() 
    {
        return Resources.loadTextFile(this.getClass().getName().replaceAll("\\.","/")+"_Vert.glsl");
    
    }

    @Override
    protected String fragmentShaderGLSL() 
    {
        return Resources.loadTextFile(this.getClass().getName().replaceAll("\\.","/")+"_Frag.glsl"); 
        
    }
    
}
