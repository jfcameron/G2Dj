/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

import grimhaus.com.G2Dj.Imp.Graphics.Model;
import grimhaus.com.G2Dj.Imp.Graphics.ModelCollection;
import grimhaus.com.G2Dj.Imp.Graphics.ShaderProgram;
import grimhaus.com.G2Dj.Imp.Graphics.ShaderProgramCollection;
import grimhaus.com.G2Dj.Imp.Graphics.Texture;
import grimhaus.com.G2Dj.Imp.Graphics.TextureCollection;
import grimhaus.com.G2Dj.Type.Math.IntVector2;

import java.lang.ref.WeakReference;

//.if DESKTOP
//|import grimhaus.com.G2Dj.Imp.Graphics.Window;
//.elseif ANDROID

//.endif



/**
 *
 * @author Joe
 */
public class Graphics 
{
    //*************
    // Data members
    //*************
    //.if DESKTOP
    //|private static final Window s_Window;
    //.endif
    
    private static final ShaderProgramCollection s_ShaderPrograms;
    private static final ModelCollection         s_Models;
    private static final TextureCollection       s_Textures;
    
    //********
    // Getters
    //********
    //.if DESKTOP
    //|public static Window getWindow(){return s_Window;}
    //.endif
    
    //*****************
    // Public interface
    //*****************
    //Shader funcs
    //public static void loadShader(ShaderProgram aShaderProgram){s_ShaderPrograms.add(aShaderProgram);}
    public static WeakReference<ShaderProgram> getShaderProgram(final String aName){return s_ShaderPrograms.get(aName);}
    public static WeakReference<ShaderProgram> getShaderProgram(){return s_ShaderPrograms.get();}
    //Model funcs
    public static WeakReference<Model> getModel(final String aName){return s_Models.get(aName);}
    public static WeakReference<Model> getModel(){return s_Models.get();}
    //Tex func
    public static WeakReference<Texture> getTexture(final String aName){return s_Textures.get(aName);}
    public static WeakReference<Texture> getTexture(){return s_Textures.get();}
    public static WeakReference<Texture> loadFromResource(String aAbsoluteResourcePath){return s_Textures.loadFromResource(aAbsoluteResourcePath);}
    public static WeakReference<Texture> loadFromFile(String aAbsoluteResourcePath){return s_Textures.loadFromFile(aAbsoluteResourcePath);}
    
    public static IntVector2 getScreenSize()
    {
        IntVector2 rValue;
        //.if DESKTOP
        //|rValue = new IntVector2(s_Window.getWidth(),s_Window.getHeight());
        //.elseif ANDROID
        rValue = Mobile.getScreenSize();
        //.endif
        return rValue;
        
    }
    
    //*******************
    // Static Constructor
    //*******************    
    protected static void init()
    {
        


    }
    
    protected static void draw()
    {
        //.if DESKTOP
        //|s_Window.draw();
        //.elseif ANDROID
        Mobile.mGLView.requestRender();
        //.endif

    }
    
    static
    {
        //.if DESKTOP
        //|s_Window = new Window(Input.s_KeyboardInputHandler);
        //.endif
        s_ShaderPrograms  = new ShaderProgramCollection();
        s_Models          = new ModelCollection();
        s_Textures        = new TextureCollection();

        
    }
    
}
