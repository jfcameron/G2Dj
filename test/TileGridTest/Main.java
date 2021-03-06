/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TileGridTest;

import SpriteSheetTest.Slime;
import static TileGridTest.PlayerController.s_Action;
import static TileGridTest.PlayerController.s_Down;
import static TileGridTest.PlayerController.s_Left;
import static TileGridTest.PlayerController.s_Right;
import static TileGridTest.PlayerController.s_Up;
import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Input.Gamepad;
import grimhaus.com.G2Dj.Imp.Input.InputConfig;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Imp.Physics2D.BodyType;
import grimhaus.com.G2Dj.Type.Engine.Game;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Mesh;
import grimhaus.com.G2Dj.Type.Graphics.TextMesh;
import grimhaus.com.G2Dj.Type.Graphics.TileGrid;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Physics2D.BoxCollider;
import grimhaus.com.G2Dj.Type.Physics2D.EdgeDefinition;
import grimhaus.com.G2Dj.Type.Physics2D.GridCollider;
import grimhaus.com.G2Dj.Type.Physics2D.GridColliderDefinition;
import grimhaus.com.G2Dj.Type.Physics2D.Physics2DScene;
import grimhaus.com.G2Dj.Type.Physics2D.Rigidbody;
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
        Debug.enableGarbageCollectionLogging();
        
        WeakReference<Scene> scene = Engine.createScene("Main");
        Physics2DScene p2d = (Physics2DScene)scene.get().getSceneGraph(Physics2DScene.class).get();
        p2d.setGravity(0, -30f);//-9.81f
        
        createTestTileGrid(scene);
        createSpriteSheet(scene);
        createTextMesh(scene);
        createTestCamera(scene);
        createBox(scene);
        createSlime(scene);
        //createSpriteSheet(scene);
                
    }
    
    //
    // Gameobjects
    //
    private static void createTestCamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName("MainCamera");
        
        gameObject.get().getTransform().get().setPosition(-8f,0,+2.5f);
        gameObject.get().getTransform().get().setRotation(-90,180,10);
        
        gameObject.get().addComponent(PlayerCamera.class);
        
    }
    
    private static void createTestTileGrid(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("TileGridTest/bloo.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("TileGrid");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(+1,0,0);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        TileGrid tilegrid = (TileGrid)gameObject.get().addComponent(TileGrid.class);
        //tilegrid.set
        tilegrid.setTileSet("bloo.png",16,16);
        tilegrid.setTileData(256,16,new int[]
        {
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            
            5,0, 6,0, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 9,0, 8,1, 8,1, 5,0, 6,0, 1,2, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 5,0, 6,0, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 5,0, 6,0, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 9,0, 8,1, 8,1, 5,0, 6,0, 1,2, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 5,0, 6,0, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 5,0, 6,0, 8,1,  7,0,  8,0,  8,1, 8,1, 9,2, 8,1, 8,1, 8,1, 8,1, 1,2, 8,1, 8,1, 8,1, 8,1, 8,1, 5,0, 6,0, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 5,0, 6,0, 8,1,  7,0,  8,0,  8,1, 8,1, 9,2, 8,1, 8,1, 8,1, 8,1, 1,2, 8,1, 8,1, 8,1, 8,1, 8,1, 5,0, 6,0, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 10,2, 11,2, 12,2, 8,1, 9,3, 8,1, 8,1, 8,1, 8,1, 1,2, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 5,0, 6,0, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 10,2, 11,2, 12,2, 8,1, 9,3, 8,1, 8,1, 8,1, 8,1, 1,2, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 5,0, 6,0, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 1,2, 8,1, 3,1, 4,1, 3,2, 10,3, 11,3, 12,3, 8,1, 9,4, 8,1, 8,1, 8,1, 8,1, 1,2, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 1,2, 8,1, 3,1, 4,1, 3,2, 10,3, 11,3, 12,3, 8,1, 9,4, 8,1, 8,1, 8,1, 8,1, 1,2, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,
            1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  
            1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3,  1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3,  1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3,  1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3,  1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3 , 1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3,  1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3,  1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3,  1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3,  1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3,  1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3 , 1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3,  1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3  
              
        });
        
        GridCollider gridcollider = (GridCollider)gameObject.get().addComponent(GridCollider.class);
        gridcollider.setDrawDebugLines(true);
        
        float 
        hStart = 0.01f, //prevent overlaps
        hEnd   = 0.99f, //
        vStart = 0.01f;
        
        gridcollider.setColliderDefinitions(new GridColliderDefinition[]
        {
            //Empty tile collider
            new GridColliderDefinition(new EdgeDefinition[]{}),
            
            //Full tile collider
            new GridColliderDefinition(new EdgeDefinition[]
            {
                //north edge
                new EdgeDefinition(new Vector2[]{new Vector2(hEnd,0),new Vector2(hStart,0)},1f,0,0),//{new Vector2(1,0),new Vector2(0,0)}
                //east edge
                new EdgeDefinition(new Vector2[]{new Vector2(1,vStart),new Vector2(1,1)},0f,0,0),    //{new Vector2(1,0),new Vector2(1,1)}
                //south edge
                new EdgeDefinition(new Vector2[]{new Vector2(1,1),new Vector2(0,1)},0f,0,0),    //{new Vector2(1,1),new Vector2(0,1)}
                //west edge
                new EdgeDefinition(new Vector2[]{new Vector2(0,vStart),new Vector2(0,1)},0f,0,0) //{new Vector2(0,0),new Vector2(0,1)}
            
            })
        
        });
        
        
        gridcollider.setGridData(80,16,new int[]
        {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            
        });
        
        //gridcollider.setDrawDebugLines(false);
        
        Rigidbody rb = (Rigidbody)gameObject.get().addComponent(Rigidbody.class);
        rb.setType(BodyType.Static);
        
        
    }
    
    private static void createSpriteSheet(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("TileGridTest/Blocky.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("Player");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(-12,1,4);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        PlayerController pc = (PlayerController)gameObject.get().addComponent(PlayerController.class);
        
        InputConfig inputConfig = new InputConfig
        (
            new InputConfig.InputEvent(s_Up    ),
            new InputConfig.InputEvent(s_Down  ),
            new InputConfig.InputEvent(s_Left  ),
            new InputConfig.InputEvent(s_Right ),
            new InputConfig.InputEvent(s_Action)
                
        );
        
        //Keyboard binds
        inputConfig.addKeysToAEvent(s_Up    , KeyCode.W    );
        inputConfig.addKeysToAEvent(s_Down  , KeyCode.S    );
        inputConfig.addKeysToAEvent(s_Left  , KeyCode.A    );
        inputConfig.addKeysToAEvent(s_Right , KeyCode.D    );
        inputConfig.addKeysToAEvent(s_Action, KeyCode.Space);
        //Gamepad binds
        inputConfig.addGamepadHatToAEvent(s_Up   ,"Hat Switch",Gamepad.Hat.Direction.Up,Gamepad.Hat.Direction.UpLeft);
        inputConfig.addGamepadHatToAEvent(s_Down ,"Hat Switch",Gamepad.Hat.Direction.Down);
        inputConfig.addGamepadHatToAEvent(s_Left ,"Hat Switch",Gamepad.Hat.Direction.Left,Gamepad.Hat.Direction.UpLeft,Gamepad.Hat.Direction.DownLeft);
        inputConfig.addGamepadHatToAEvent(s_Right,"Hat Switch",Gamepad.Hat.Direction.Right,Gamepad.Hat.Direction.UpRight,Gamepad.Hat.Direction.DownRight);
        inputConfig.addGamepadButtonsToAEvent(s_Action,"Button 1");
        
        pc.setControls(inputConfig);
        
    }
    
    private static void createTextMesh(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("TextMesh");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(0,1,0.5f);
        gameObject.get().getTransform().get().setScale(0.5f,0.5f,0.5f);
        
        TextMesh mesh = (TextMesh)gameObject.get().addComponent(TextMesh.class);
        mesh.setText("走れ！");
        
    }
    
    private static void createFloor(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("Floor");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(-5,0,-1);
        gameObject.get().getTransform().get().setScale(20.0f,1.0f,1.0f);
        
        gameObject.get().addComponent(BoxCollider.class);
        
        Rigidbody rb = (Rigidbody)gameObject.get().addComponent(Rigidbody.class);
        rb.setType(BodyType.Static);
        
    }
    
    private static void createBox(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("Floor");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(-6,1,4);
        gameObject.get().getTransform().get().setScale(1.0f,1.0f,1.0f);
        
        gameObject.get().addComponent(Mesh.class);
        BoxCollider bc = (BoxCollider)gameObject.get().addComponent(BoxCollider.class);
        //bc.setDensity(0.5f);
        
        Rigidbody rb = (Rigidbody)gameObject.get().addComponent(Rigidbody.class);
        
    }
    
    private static void createSlime(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("SpriteSheetTest/Slime.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("Slime");
        gameObject.get().getTransform().get().setRotation(90,0,0);
        gameObject.get().getTransform().get().setPosition(-15,1,2);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        gameObject.get().addComponent(Slime.class);
        
    }
    
}
