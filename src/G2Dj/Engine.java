/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package G2Dj;

import G2Dj.Imp.Input.KeyCode;
import G2Dj.Type.Engine.Scene;
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
    
    public static void update()
    {
        do
        {
            s_Scenes.forEach((currentScene)->{currentScene.update();});
            Input.update();
        
        }while(!Input.getKeyDown(KeyCode.Escape));
        
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
