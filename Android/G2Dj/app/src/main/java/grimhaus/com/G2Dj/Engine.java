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
        do
        {
            Input.update();
            update();
            draw();
        
        }while(!Input.getKeyDown(KeyCode.Escape));
        
    }
    
    private static void update()
    {
        for(int i = 0, s = s_Scenes.size(); i < s; i++) //s_Scenes.forEach((currentScene)->{currentScene.update();});
                s_Scenes.get(i).update();
        
    }
    
    private static void draw()
    {
        for(int i = 0, s = s_Scenes.size(); i < s; i++) //s_Scenes.forEach((currentScene)->{currentScene.update();});
                s_Scenes.get(i).draw();
        
    }
    
    //
    //
    //
    public static void init()
    {
        Input.init();
        Graphics.init();
        //etc
                
        
    }
    
}
