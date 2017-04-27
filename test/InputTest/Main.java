/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package InputTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Imp.Graphics.CameraClearMode;
import grimhaus.com.G2Dj.Imp.Graphics.CameraProjectionMode;
import grimhaus.com.G2Dj.Type.Engine.Game;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class Main 
{
    public static void main(String[] args){ Engine.init(new Game(){@Override public void init(){InputTestInit();}});}
    
    //
    // The test
    //
    private static void InputTestInit()
    {
        WeakReference<Scene> theScene = Engine.createScene("InputTestScene");
        createMainCamera(theScene.get());
        createKeyboardTestGameObject(theScene.get());
        createMouseTestGameObject(theScene.get());
        createGamepadTestGameObject(theScene.get());
        createInputConfigTestGameObject(theScene.get());
        
    }
    
    //
    // gameobject creation
    //
    private static void createMainCamera(final Scene aScene)
    {
        GameObject gameObject = aScene.addGameObject().get();
        gameObject.setName("MainCamera");
        gameObject.getTransform().get().setPosition(0,5,0);
        gameObject.getTransform().get().setRotation(-90,0,0);
        
        Camera camera = (Camera)gameObject.addComponent(Camera.class);
        camera.setProjectionMode(CameraProjectionMode.Perspective);
        camera.setFarClippingPlane(20);
        //camera.setOrthoSize(40, 40);
        camera.setClearMode(CameraClearMode.Color);
                
    }
    
    private static void createKeyboardTestGameObject(final Scene aScene)
    {
        GameObject gameObject = aScene.addGameObject().get();
        gameObject.addComponent(KeyboardTest.class);
    
    }
    
    private static void createMouseTestGameObject(final Scene aScene)
    {
        GameObject gameObject = aScene.addGameObject().get();
        gameObject.addComponent(MouseTest.class);
    
    }
    
    private static void createGamepadTestGameObject(final Scene aScene)
    {
        GameObject gameObject = aScene.addGameObject().get();
        gameObject.addComponent(GamepadTest.class);
    
    }
    
    private static void createInputConfigTestGameObject(final Scene aScene)
    {
        GameObject gameObject = aScene.addGameObject().get();
        gameObject.addComponent(InputConfigTest.class);
    
    }
    
    
}
