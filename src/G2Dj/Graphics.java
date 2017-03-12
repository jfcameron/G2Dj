/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj;

import G2Dj.Imp.Graphics.AlphaCutOff;
import G2Dj.Imp.Graphics.Camera;
import G2Dj.Imp.Graphics.CameraClearMode;
import G2Dj.Imp.Graphics.Model;
import G2Dj.Imp.Graphics.Quad;
import G2Dj.Imp.Graphics.ShaderProgram;
import G2Dj.Imp.Graphics.ShaderProgramCollection;
import G2Dj.Imp.Graphics.Window;
import G2Dj.Math.Vector2;
import G2Dj.Type.Graphics.Color;

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
    private static final Window                  s_Window;
    private static final GL                      s_GL;
    private static final ShaderProgramCollection s_ShaderPrograms;
    
    //********
    // Getters
    //********
    public static Window getWindow(){return s_Window;}
    public static GL     getGL    (){return s_GL;    }
    
    //*****************
    // Public interface
    //*****************
    //Shader funcs
    public static void loadShader(ShaderProgram aShaderProgram){s_ShaderPrograms.add(aShaderProgram);}
    
    public static WeakReference<ShaderProgram> getShaderProgram(final String aShaderName){return s_ShaderPrograms.getDefault();}
            
    public static void _TEST_FUNCTION_(){}
    
    protected static void init(){}
    
    protected static void draw()
    {
        //CAMERA TEST AREA
        {
            camera1.draw(s_GL);
            //camera2.draw(s_GL);
            //camera3.draw(s_GL);
            //camera4.draw(s_GL);
        
        }
        
        //MODEL TEST AREA
        {
            shader1.draw();
            model1.draw(shader1.getProgramHandle());
            
        }
        
        
        
        //update canvas, swap buffers
        s_Window.draw();
        
    }
    
    //*******************
    // Static Constructor
    //*******************
    static
    {
        s_Window = new Window(Input.s_KeyboardInputHandler);
        s_GL     = s_Window.getGL();
        
        s_ShaderPrograms = new ShaderProgramCollection();
        
        //TEST AREA
        camera1 = new Camera(new Vector2(0.0f,0.0f), new Vector2(1.0f,1.0f),Color.CornflowerBlue(),CameraClearMode.Color);
        camera2 = new Camera(new Vector2(0.0f,0.5f), new Vector2(0.5f,0.5f),Color.DeathlyPink()   ,CameraClearMode.Color);
        camera3 = new Camera(new Vector2(0.5f,0.0f), new Vector2(0.5f,0.5f),Color.Red()           ,CameraClearMode.Color);
        camera4 = new Camera(new Vector2(0.5f,0.5f), new Vector2(0.5f,0.5f),Color.Green()         ,CameraClearMode.Color);
        
        shader1 = new AlphaCutOff();
        
        model1 = new Quad();
        
    }
    
    //TEST AREA
    private static final Camera camera1;// = new Camera(new Vector2(0.0f,0.0f), new Vector2(0.5f,0.5f),Color.CornflowerBlue(),CameraClearMode.Color);
    private static final Camera camera2;// = new Camera(new Vector2(0.0f,0.5f), new Vector2(0.5f,0.5f),Color.DeathlyPink(),CameraClearMode.Color);
    private static final Camera camera3;// = new Camera(new Vector2(0.5f,0.0f), new Vector2(0.5f,0.5f),Color.Red(),CameraClearMode.Color);
    private static final Camera camera4;// = new Camera(new Vector2(0.5f,0.5f), new Vector2(0.5f,0.5f),Color.Green(),CameraClearMode.Color);
    
    private static final Model model1;
    
    private static final ShaderProgram shader1;
    
}
