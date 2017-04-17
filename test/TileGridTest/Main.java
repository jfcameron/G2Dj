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
import grimhaus.com.G2Dj.Type.Graphics.SpriteSheet;
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
        createSpriteSheet(scene);
        
    }
    
    //
    // Gameobjects
    //
    private static void createTestCamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName("MainCamera");
        
        gameObject.get().getTransform().get().setPosition(-4.5f,10,+4.5f);
        gameObject.get().getTransform().get().setRotation(-90,180,0);
        
        Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
        camera.setProjectionMode(CameraProjectionMode.Orthographic);
        camera.setFarClippingPlane(15);
        camera.setOrthoSize(10, 10);
        
    }
    
    private static void createTestTileGrid(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("TileGridTest/bloo.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("SpriteSheetTest");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(0,0,0);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        TileGrid tilegrid = (TileGrid)gameObject.get().addComponent(TileGrid.class);
        tilegrid.setTexture("_Texture", "bloo.png");
        tilegrid.setTileSizeByPixel(16, 16);
        tilegrid.setTileData(5,5,new int[]
        {
            1,3, 1,3, 1,3,  1,3,  1,3,
            1,2, 1,2, 1,2,  1,2,  1,2,
            3,1, 4,1, 3,2, 10,3, 11,3,
            8,1, 8,1, 8,1, 10,2, 11,2,
            5,0, 6,0, 8,1,  7,0, 8,0,
        });
        
        
    }
    
    private static void createSpriteSheet(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("SpriteSheetTest/Blocky.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("SpriteSheetTest");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(0,1,2);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        SpriteSheet spriteSheet = (SpriteSheet)gameObject.get().addComponent(SpriteSheet.class);
        spriteSheet.setTexture("_Texture", "Blocky.png");
        spriteSheet.setCellSizeByPixel(16,17);
        spriteSheet.setCurrentCell(0, 0);
        
    }
    
}
