/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TileGridTest;

import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.CameraProjectionMode;
import grimhaus.com.G2Dj.Type.Engine.Game;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.TileGrid;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class Main 
{
    public static void main(String[] args){Engine.init(new Game(){@Override public void init(){initTest();}});}
    
    private static void initTest()
    {
        WeakReference<Scene> scene = Engine.createScene("Main");
        createTestCamera(scene);
        createTestTileGrid(scene);
        
    }
    
    //
    // Gameobjects
    //
    private static void createTestCamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName("MainCamera");
        
        gameObject.get().getTransform().get().setPosition(0,10,0);
        gameObject.get().getTransform().get().setRotation(-90,180,0);
        
        Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
        camera.setProjectionMode(CameraProjectionMode.Orthographic);
        camera.setFarClippingPlane(15);
        camera.setOrthoSize(5, 5);
        
    }
    
    private static void createTestTileGrid(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("TileGridTest/bloo.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("SpriteSheetTest");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(0,0,0);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        gameObject.get().addComponent(TileGrid.class);
        
    }
    
}
