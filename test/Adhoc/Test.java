package Adhoc;

import G2Dj.Debug;
import G2Dj.Engine;
import G2Dj.Graphics;
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
            
            Graphics.loadFromResource("/Adhoc/Water.png");
            
            
            //Mesh someMesh = mainScene.
            
            WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
            
            aGameObject.get().addComponent(Mesh.class);
            
            Mesh aMesh = (Mesh)aGameObject.get().getComponent(Mesh.class);
                        
            Debug.log("hi");
            
            if (aMesh != null)
                aMesh.setTexture("_Texture", "Water.png");
        
        }
        
        Engine.update();
        
    }
    
}
