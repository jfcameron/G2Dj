/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

import grimhaus.com.G2Dj.Type.Engine.Game;
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
    // Data members
    //
    private static final ArrayList<Scene> s_Scenes = new ArrayList<>();
    
    //
    // Accessors
    //
    public static WeakReference<Scene> createScene(final String aSceneName)
    {
        Scene newScene = new Scene(aSceneName);
        s_Scenes.add(newScene);
        
        return new WeakReference<>(newScene);
        
    }
    
    //
    // Public interface
    //
    public static void init(final Game aGame)
    {
        aGame.init();
        mainLoop();
    
    }
    
    //
    // Implementation
    //
    private static void mainLoop()
    {
        for(;;)
        {
            fixedUpdate();   
            update();
            draw();
        
        }
        
    }
    
    private static void update()
    {
        Time.update();
        Input.update();
        
        for(int i = 0, s = s_Scenes.size(); i < s; i++)
            s_Scenes.get(i).update();
        
    }
    
    private static void fixedUpdate()
    {
        for(int i = 0, s = s_Scenes.size(); i < s; i++)
            s_Scenes.get(i).fixedUpdate();
        
    }
    
    private static void draw()
    {
        for(int i = 0, s = s_Scenes.size(); i < s; i++)
            s_Scenes.get(i).draw();
        
        Graphics.draw();
        
    }
        
}
