/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj;

import G2Dj.Imp.Graphics.Model;
import G2Dj.Imp.Graphics.ModelCollection;
import G2Dj.Imp.Graphics.ShaderProgram;
import G2Dj.Imp.Graphics.ShaderProgramCollection;
import G2Dj.Imp.Graphics.Texture;
import G2Dj.Imp.Graphics.TextureCollection;
import G2Dj.Imp.Graphics.Window;

import com.jogamp.opengl.GL;

import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class Graphics 
{
    //*************
    // Data members
    //*************
    private static final Window s_Window;
    private static final GL     s_GL;
    
    private static final ShaderProgramCollection s_ShaderPrograms;
    private static final ModelCollection         s_Models;
    private static final TextureCollection       s_Textures;
    
    //********
    // Getters
    //********
    public static Window getWindow(){return s_Window;}
    public static GL     getGL    (){return s_GL;    }
    
    //*****************
    // Public interface
    //*****************
    //Shader funcs
    //public static void loadShader(ShaderProgram aShaderProgram){s_ShaderPrograms.add(aShaderProgram);}
    public static WeakReference<ShaderProgram> getShaderProgram(final String aName){return s_ShaderPrograms.get(aName);}
    //Model funcs
    public static WeakReference<Model> getModel(final String aName){return s_Models.get(aName);}
    public static WeakReference<Model> getModel(){return s_Models.get();}
    //Tex func
    public static WeakReference<Texture> getTexture(final String aName){return s_Textures.get(aName);}
    public static WeakReference<Texture> getTexture(){return s_Textures.get();}
    
    //*******************
    // Static Constructor
    //*******************    
    protected static void init(){}
    static
    {
        s_Window = new Window(Input.s_KeyboardInputHandler);
        s_GL     = s_Window.getGL();
        
        s_ShaderPrograms  = new ShaderProgramCollection();
        s_Models          = new ModelCollection();
        s_Textures        = new TextureCollection();
        
    }
    
}
