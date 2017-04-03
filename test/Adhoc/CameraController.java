/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adhoc;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Engine.RequireComponents;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Imp.Input.TouchState;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Input.Touch;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import grimhaus.com.G2Dj.Type.Physics2D.CollisionInfo;
import grimhaus.com.G2Dj.Type.Physics2D.Rigidbody;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
@RequireComponents({Camera.class,Rigidbody.class/*,CircleCollider.class*/})
public class CameraController extends Component
{
    private static final float s_TranslationSpeed = (float)2E3;
    private static final float s_RotationSpeed = (float)2E4;
    
    private Rigidbody m_Rigidbody;
    
    @Override
    public void update() 
    {
        Vector3 inputBuffer = new Vector3();
        
            
        if (Input.getKey(KeyCode.W))
        {
            inputBuffer.z += (float)sin(getTransform().get().getRotation().y - (90.0f * Math.PI / 180));
            inputBuffer.x -= (float)cos(getTransform().get().getRotation().y - (90.0f * Math.PI / 180));
            
        }
        
        if (Input.getKey(KeyCode.S))
        {
            inputBuffer.z -= (float)sin(getTransform().get().getRotation().y - (90.0f * Math.PI / 180));
            inputBuffer.x += (float)cos(getTransform().get().getRotation().y - (90.0f * Math.PI / 180));
         
        }
            
        if (Input.getKey(KeyCode.A))
        {
            inputBuffer.z += (float)sin(getTransform().get().getRotation().y);
            inputBuffer.x -= (float)cos(getTransform().get().getRotation().y);
            
        }
        
        if (Input.getKey(KeyCode.D))
        {
            inputBuffer.z -= (float)sin(getTransform().get().getRotation().y);
            inputBuffer.x += (float)cos(getTransform().get().getRotation().y);

        }
            
        //Touchscreen input
        if (Input.getTouches()[0].isActive() && (Input.getTouches()[0].position.x / Graphics.getScreenSize().x)< 0.5f)
        {
            Touch touch = Input.getTouches()[0];

            if (touch.state == TouchState.Began || touch.state == TouchState.Moved)
            {
                float forward = touch.deltaPosition.y/ Graphics.getScreenSize().y ;
                float side    = touch.deltaPosition.x/ Graphics.getScreenSize().x ;

                inputBuffer.z += forward * (float)sin(getTransform().get().getRotation().y + (90.0f * Math.PI / 180));
                inputBuffer.x -= forward * (float)cos(getTransform().get().getRotation().y + (90.0f * Math.PI / 180));

                inputBuffer.z -= side * 2 * (float)sin(getTransform().get().getRotation().y);// - (90.0f * Math.PI / 180));
                inputBuffer.x += side * 2 * (float)cos(getTransform().get().getRotation().y);

            }


        }

        inputBuffer.multiplyInPlace(s_TranslationSpeed);
        inputBuffer.multiplyInPlace((float)Time.getDeltaTime());

        m_Rigidbody.applyForce(inputBuffer.x,inputBuffer.z);
        
        Vector3 rotationBuffer = new Vector3();
            
        if (Input.getKey(KeyCode.Q))
            rotationBuffer.y +=0.1f;
            
        if (Input.getKey(KeyCode.E))
            rotationBuffer.y -=0.1f;

        //Touchscreen input
        if (Input.getTouches()[0].isActive() && (Input.getTouches()[0].position.x / Graphics.getScreenSize().x)>= 0.5f)
        {
            Touch touch = Input.getTouches()[0];

            if (touch.state == TouchState.Began || touch.state == TouchState.Moved)
                rotationBuffer.y -= touch.deltaPosition.x/Graphics.getScreenSize().x * 2;


        }

        rotationBuffer.multiplyInPlace(s_RotationSpeed);
        rotationBuffer.multiplyInPlace((float)Time.getDeltaTime());

        m_Rigidbody.applyTorque(rotationBuffer.y);
                
    }
    
    @Override public void fixedUpdate() {}
    @Override protected void initialize() {}

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        getGameObject().get().setName("PlayerCamera");
        getTransform().get().setScale(3,3,3);
        m_Rigidbody = (Rigidbody)getGameObject().get().getComponent(Rigidbody.class);
        m_Rigidbody.setPosition(-3,0,+5);
        //m_Rigidbody.setRotation(0,45,0);
        
    }
    
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
    public void OnCollisionEnter(final CollisionInfo info)
    {
        Debug.log("CameraController.OnCollisionEnter");
        Debug.log("Normal: "+info.collisionNormal,"Point: "+info.collisionPoint,"Other: "+info.other.get().getName());
        
    }
    
    public void OnTriggerEnter(final CollisionInfo info)
    {
        Debug.log("CameraController.OnTriggerEnter");
        
        Debug.log("Normal: "+info.collisionNormal,"Point: "+info.collisionPoint,"Other: "+info.other.get().getName());
    
    }
    
    public void OnCollisionExit(final CollisionInfo info)
    {
        Debug.log("CameraController.OnCollisionExit");
    
    }
    
    public void OnTriggerExit(final CollisionInfo info)
    {
        Debug.log("CameraController.OnTriggerExit");
    
    }
    
}
