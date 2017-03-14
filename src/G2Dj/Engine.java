/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj;

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
        s_Scenes.forEach((currentScene)->{currentScene.update();});
        
        
        Input.update();
        
    }
    
    //
    //
    //
    public static void init()
    {
        Input.init();
        Graphics.init();
        
    }
    
}
