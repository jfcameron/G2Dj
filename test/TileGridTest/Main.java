/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TileGridTest;

import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.CameraProjectionMode;
import grimhaus.com.G2Dj.Imp.Graphics.Color;
import grimhaus.com.G2Dj.Imp.Physics2D.BodyType;
import grimhaus.com.G2Dj.Type.Engine.Game;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.SpriteSheet;
import grimhaus.com.G2Dj.Type.Graphics.TextMesh;
import grimhaus.com.G2Dj.Type.Graphics.TileGrid;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Physics2D.BoxCollider;
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
        WeakReference<Scene> scene = Engine.createScene("Main");
        Physics2DScene p2d = (Physics2DScene)scene.get().getSceneGraph(Physics2DScene.class).get();
        p2d.setGravity(0, -9.81f);
        
        createTestTileGrid(scene);
        createSpriteSheet(scene);
        createTextMesh(scene);
        createTestCamera(scene);
        
    }
    
    //
    // Gameobjects
    //
    private static void createTestCamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName("MainCamera");
        
        gameObject.get().getTransform().get().setPosition(-2f,10,+2.5f);
        gameObject.get().getTransform().get().setRotation(-90,180,0);
        
        Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
        camera.setProjectionMode(CameraProjectionMode.Orthographic);
        camera.setFarClippingPlane(15);
        camera.setOrthoSize(7.5f, 7.5f);
        camera.setClearColor(Color.Black());
        
        gameObject.get().addComponent(PlayerCamera.class);
        
    }
    
    private static void createTestTileGrid(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("TileGridTest/bloo.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("SpriteSheetTest");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(+1,0,0);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        TileGrid tilegrid = (TileGrid)gameObject.get().addComponent(TileGrid.class);
        tilegrid.setTileSet("bloo.png",16,16);
        tilegrid.setTileData(21,6,new int[]
        {
            5,0, 6,0, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1,  8,1,  8,1,  8,1, 8,1, 8,1, 8,1, 8,1, 5,0, 6,0, 1,2,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 5,0, 6,0, 8,1,  7,0,  8,0,  8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 1,2,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 10,2, 11,2, 12,2, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 1,2,
            8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 1,2, 8,1, 3,1, 4,1, 3,2, 10,3, 11,3, 12,3, 8,1, 8,1, 8,1, 8,1, 8,1, 8,1, 1,2,
            1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  1,2,  1,2,  1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2, 1,2,  
            1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  1,3,  1,3,  1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3, 1,3,  
            
        });
        
        GridCollider gridcollider = (GridCollider)gameObject.get().addComponent(GridCollider.class);
        gridcollider.setColliderDefinitions(new GridColliderDefinition[]
        {
            //Empty tile collider
            new GridColliderDefinition(new Vector2[]
            {
               
            }),
            
            //Full tile collider
            new GridColliderDefinition(new Vector2[]
            {
                new Vector2(0,0),
                new Vector2(1,0),
                new Vector2(1,1),
                new Vector2(0,1)
            
            }),
        
        });
        
        gridcollider.setGridData(21,6,new int[]
        {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            
        });
        
        gridcollider.setDrawDebugLines(false);
        
        Rigidbody rb = (Rigidbody)gameObject.get().addComponent(Rigidbody.class);
        rb.setType(BodyType.Static);
        
        
    }
    
    private static void createSpriteSheet(final WeakReference<Scene> aScene)
    {
        Graphics.loadFromResource("SpriteSheetTest/Blocky.png");
        
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("Player");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(0,1,4);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        gameObject.get().addComponent(PlayerController.class);
        
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
    
}
