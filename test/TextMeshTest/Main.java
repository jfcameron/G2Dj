/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TextMeshTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Imp.Graphics.CameraProjectionMode;
import grimhaus.com.G2Dj.Imp.Graphics.Color;
import grimhaus.com.G2Dj.Imp.Graphics.HorizontalTextAlignment;
import grimhaus.com.G2Dj.Imp.Graphics.VerticalTextAlignment;
import grimhaus.com.G2Dj.Type.Engine.Game;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.Mesh;
import grimhaus.com.G2Dj.Type.Graphics.TextMesh;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class Main 
{
    public static void main(String[] args) {Engine.init(new Game(){@Override public void init(){testMain();}});}
    
    private static void testMain()
    {
        WeakReference<Scene> scene = Engine.createScene("Main");
        createTestCamera(scene);
        createTextMeshTest(scene);
        createTextMeshTest2(scene);
                
    }
    
    private static void createTestCamera(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        
        gameObject.get().setName("MainCamera");
        
        gameObject.get().getTransform().get().setPosition(0,10,0);
        gameObject.get().getTransform().get().setRotation(-90,180,0);
        
        Camera camera = (Camera)gameObject.get().addComponent(Camera.class);
        
        camera.setProjectionMode(CameraProjectionMode.Orthographic);
        camera.setFarClippingPlane(15);
        camera.setOrthoSize(30, 30);
        
        //camera.setClearColor(new Color(0,0,0,1));
        
    }
    
    private static void createTextMeshTest(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("TextMesh");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(0,0,0);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        TextMesh mesh = (TextMesh)gameObject.get().addComponent(TextMesh.class);
        mesh.setText("！123お早う,\rWhat's good,שלום,\nЗдравствуйте,\n안녕하세요\r走れ！");
        
        //gameObject.get().addComponent(Incrementer.class);
        
    }
    
    private static void createTextMeshTest2(final WeakReference<Scene> aScene)
    {
        WeakReference<GameObject> gameObject = aScene.get().addGameObject();
        gameObject.get().setName("TextMesh");
        gameObject.get().getTransform().get().setRotation(90,180,0);
        gameObject.get().getTransform().get().setPosition(0.0f,0,0);
        gameObject.get().getTransform().get().setScale(1,1,1);
        
        TextMesh mesh = (TextMesh)gameObject.get().addComponent(TextMesh.class);
        mesh.setHorizontalTextAlignment(HorizontalTextAlignment.Right);
        mesh.setVerticalTextAlignment(VerticalTextAlignment.Bottom);
                
        gameObject.get().addComponent(Incrementer.class);
        
    }
    
}
