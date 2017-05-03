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
    
    private static double s_FixedUpdateTimer = 0;
    
    //
    // Accessors
    //
    public static WeakReference<Scene> getScene(final String aSceneName)
    {
        for(int i=0,s=s_Scenes.size();i<s;i++)
            if (s_Scenes.get(i).getName().equals(aSceneName))
                return new WeakReference<>(s_Scenes.get(i));
        
        return new WeakReference<>(null);
        
    }
    
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
        Audio.test();
        
        Graphics.getModel();//
        
        aGame.init();        
        mainLoop();
    
    }
    
    //
    // Implementation
    //
    protected static void mainLoop()
    {
        //.if DESKTOP
        for(;;)
        //.endif
        {
            //Call fixedUpdate only if the timer is > than interval and then call it as many times as it has been exceeded
            if ((s_FixedUpdateTimer += Time.getDeltaTime()) > Time.getFixedUpdateTargetInterval())
            {
                int timesToEvokeFixedUpdate = (int)Math.floor(((s_FixedUpdateTimer += Time.getDeltaTime()) > Time.getFixedUpdateTargetInterval())? (s_FixedUpdateTimer/Time.getFixedUpdateTargetInterval()) : 0);
                
                //timesToEvokeFixedUpdate=1;
                
                if (timesToEvokeFixedUpdate>1)
                    Debug.log("FixedUpdate is catching up!");
                
                for(int i=0,s=timesToEvokeFixedUpdate;i<s;i++)
                    fixedUpdate(); 
                
                s_FixedUpdateTimer = 0;
                
            }
            
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
