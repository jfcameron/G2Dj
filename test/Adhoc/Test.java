package Adhoc;

import G2Dj.Engine;
import G2Dj.Type.Engine.GameObject;
import G2Dj.Type.Engine.Scene;
import G2Dj.Type.Graphics.Mesh;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class Test 
{    
    public static void main(String[] args) 
    {
        Engine.init();
        {
            WeakReference<Scene> mainScene = Engine.createScene("Main");
            
            //Mesh someMesh = mainScene.
            
            GameObject aGameObject = new GameObject("hello");
            
            Mesh aMesh = (Mesh)aGameObject.getComponent(Mesh.class);
        
        }
        
        Engine.update();
        
    }
    
}
