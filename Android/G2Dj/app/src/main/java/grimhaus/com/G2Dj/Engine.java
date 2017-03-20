/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 *
 * @author Joe
 */
public class Engine 
{    
    //
    //
    //
    private static final ArrayList<Scene> s_Scenes = new ArrayList<>();
    
    //
    //
    //
    public static WeakReference<Scene> createScene(final String aSceneName)
    {
        Scene newScene = new Scene(aSceneName);
        s_Scenes.add(newScene);
        
        return new WeakReference<>(newScene);
        
    }
    
    public static void mainLoop()
    {
        for(;;/*!Input.getKeyDown(KeyCode.Escape)*/)
        {
            
            update();
            draw();
        
        }
        
    }
    
    public static void update()
    {
        Input.update();
        
        for(int i = 0, s = s_Scenes.size(); i < s; i++)
            s_Scenes.get(i).update();
        
    }
    
    public static void draw()
    {
        for(int i = 0, s = s_Scenes.size(); i < s; i++)
            s_Scenes.get(i).draw();
        
        Graphics.draw();
        
    }
    
    //
    //
    //
    public static void init()
    {
        Debug.log("Engine init()");

        Input.init();
        Graphics.init();
        //etc
                
        
    }
    
}