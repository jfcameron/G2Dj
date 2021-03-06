/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package InputTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Input.MouseButtonCode;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.Mesh;
import grimhaus.com.G2Dj.Type.Math.IntVector2;
import grimhaus.com.G2Dj.Type.Math.Mat4x4;
import grimhaus.com.G2Dj.Type.Math.Plane;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;
import javax.management.openmbean.CompositeData;

/**
 *
 * @author Joseph Cameron
 */
public class MouseTest extends Component 
{
    //
    private Camera m_Camera;
    
    //
    @Override
    protected void initialize() 
    {
        m_Camera = (Camera)getGameObject().get().getScene().get().getGameObject("MainCamera").get().getComponent(Camera.class);
        
        GCTest.installGCMonitoring();
        
    }

    @Override
    protected void update() 
    {
        //buttonsTest();
        leftClickProjectionTest();

    }

    @Override
    protected void fixedUpdate() 
    {
    
    
    }

    //
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
    //
    //
    //
    private void leftClickProjectionTest()
    {
        if (Input.getMouseButtonDown(MouseButtonCode.Left))
        {
            //marshall
            IntVector2 mousePos = Input.getMousePosition();
            float sceneDepth = 3;
            
            Vector3 wpos = m_Camera.getWorldPointFromScreenPoint(mousePos,4);//getWorldPointFromScreenPoint(mousePos, sceneDepth);
            
            //Debug.log("LeftClicked at: "+mousePos,"World position is: "+wpos);
            
            GameObject gameObject = getGameObject().get().getScene().get().addGameObject().get();
            
            //gameObject.getTransform().get().setRotation(-90,180,0);//m_Camera
            Vector3 rot = new Vector3(m_Camera.getTransform().get().getRotation());
            rot.x -=90;
            
            gameObject.getTransform().get().setRotation(rot);
            gameObject.getTransform().get().setPosition(wpos.x,wpos.y,wpos.z);
            gameObject.getTransform().get().setScale(1.5f,1.5f,1.5f);
            
            gameObject.addComponent(Mesh.class);
            
        }
        
        if (Input.getMouseButtonDown(MouseButtonCode.Right))
        {
            //marshall
            IntVector2 mousePos = Input.getMousePosition();
            //float sceneDepth = 3;
            
            Vector3 wpos = m_Camera.getWorldPlanePointFromScreenPoint(mousePos,Plane.AxisAligned.XZ);
            
            GameObject gameObject = getGameObject().get().getScene().get().addGameObject().get();
            Vector3 rot = new Vector3(m_Camera.getTransform().get().getRotation());
            rot.x -=90;
            
            gameObject.getTransform().get().setRotation(rot);
            gameObject.getTransform().get().setPosition(wpos.x,wpos.y,wpos.z);
            gameObject.getTransform().get().setScale(1.5f,1.5f,1.5f);
            
            gameObject.addComponent(Mesh.class);
            
        }
        
    }
    
    private void buttonsTest()
    {
        if (Input.getMouseButton(MouseButtonCode.Left))
            Debug.log("Left button held");
        
        if (Input.getMouseButton(MouseButtonCode.Middle))
            Debug.log("Middle button held");
        
        if (Input.getMouseButton(MouseButtonCode.Right))
            Debug.log("Right button held");
        
        
        if (Input.getMouseButtonDown(MouseButtonCode.Left))
            Debug.log("Left button clicked");
        
        if (Input.getMouseButtonDown(MouseButtonCode.Middle))
            Debug.log("Middle button clicked");
        
        if (Input.getMouseButtonDown(MouseButtonCode.Right))
            Debug.log("Right button clicked");
        
    }
    
}
