/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package SpriteSheetTest;

import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.CameraProjectionMode;
import grimhaus.com.G2Dj.Type.Engine.Game;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.SpriteSheet;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class Main 
{
    public static void main(String[] args){Engine.init(new Game(){@Override public void init(){initSpriteRendererTest();}});}
    
    public static void initSpriteRendererTest()
    {
        WeakReference<Scene> scene = Engine.createScene("Main");
        createTestCamera(scene);
        createSpriteSheet(scene);
        createSpriteSheet2(scene);
        
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
    
    private static void createSpriteSheet(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("SpriteSheetTest/Blocky.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("SpriteSheetTest");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(0,0,0);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        gameObject.get().addComponent(Blocky.class);
        
    }
    
    private static void createSpriteSheet2(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("SpriteSheetTest/Blocky.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("SpriteSheetTest");
        gameObject.get().getTransform().get().setRotation(90,0,0);
        gameObject.get().getTransform().get().setPosition(-2,0,0);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        SpriteSheet spriteSheet = (SpriteSheet)gameObject.get().addComponent(SpriteSheet.class);
        spriteSheet.setTexture("_Texture", "Blocky.png");
        spriteSheet.setCellSizeByPixel(16,17);
        spriteSheet.setCurrentCell(2, 0);
        
    }
    
}
