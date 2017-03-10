/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj;

import G2Dj.Imp.Graphics.Camera;
import G2Dj.Imp.Graphics.CameraClearMode;
import G2Dj.Imp.Graphics.Window;
import G2Dj.Imp.Input.KeyCode;
import G2Dj.Math.Vector2;
import G2Dj.Type.Graphics.Color;
import com.jogamp.opengl.GL;

/**
 *
 * @author Joe
 */
public class Graphics 
{
    protected static final Window s_Window;// = new Window(Input.s_KeyboardInputHandler);
    private   static final GL     s_GL;
    
    static
    {
        s_Window = new Window(Input.s_KeyboardInputHandler);
        s_GL = s_Window.getGL();
        
    }
    
    //getters
    public static Window getWindow(){return s_Window;}
    public static GL     getGL    (){return s_GL;    }
    
    protected static void init(){}
    
    //TEST AREA
    private static Camera camera1 = new Camera(new Vector2(0.0f,0.0f), new Vector2(0.5f,0.5f),Color.CornflowerBlue(),CameraClearMode.Color);
    private static Camera camera2 = new Camera(new Vector2(0.0f,0.5f), new Vector2(0.5f,0.5f),Color.DeathlyPink(),CameraClearMode.Color);
    private static Camera camera3 = new Camera(new Vector2(0.5f,0.0f), new Vector2(0.5f,0.5f),Color.Red(),CameraClearMode.Color);
    private static Camera camera4 = new Camera(new Vector2(0.5f,0.5f), new Vector2(0.5f,0.5f),Color.Green(),CameraClearMode.Color);
    
    protected static void draw()
    {
        //TEST AREA
        camera1.draw(s_GL);
        camera2.draw(s_GL);
        camera3.draw(s_GL);
        camera4.draw(s_GL);
                
        //update canvas, swap buffers
        s_Window.draw();
        
    }
    
}
