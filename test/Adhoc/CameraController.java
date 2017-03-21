/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adhoc;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Imp.Input.TouchState;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Input.Touch;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import grimhaus.com.G2Dj.Type.Physics2D.BoxCollider;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class CameraController extends Component
{
    private static final float s_Speed =
    //.if DESKTOP
    0.001f;
    //.elseif ANDROID
    //|0.001f*1000;
    //.endif
    
    private BoxCollider m_Rigidbody;
    
    @Override
    public void update() 
    {
        Vector3 inputBuffer = new Vector3();
        
            
        if (Input.getKey(KeyCode.D))
        {
            //inputBuffer.x-=s_Speed;
            inputBuffer.x += (float)sin(getTransform().get().getRotation().y + (90.0f * Math.PI / 180));
            inputBuffer.z -= (float)cos(getTransform().get().getRotation().y + (90.0f * Math.PI / 180));
            
        }
        
        if (Input.getKey(KeyCode.A))
        {
            //inputBuffer.x+=s_Speed;
            inputBuffer.x -= (float)sin(getTransform().get().getRotation().y + (90.0f * Math.PI / 180));
            inputBuffer.z += (float)cos(getTransform().get().getRotation().y + (90.0f * Math.PI / 180));
         
        }
            
        if (Input.getKey(KeyCode.S))
        {
            //inputBuffer.z-=s_Speed;
            inputBuffer.x -= (float)sin(getTransform().get().getRotation().y);
            inputBuffer.z += (float)cos(getTransform().get().getRotation().y);
            
        }
        
        if (Input.getKey(KeyCode.W))
        {
            //inputBuffer.z+=s_Speed;
            inputBuffer.x += sin(getTransform().get().getRotation().y);
            inputBuffer.z -= cos(getTransform().get().getRotation().y);

        }
            
        //Touchscreen input
        if (Input.getTouches()[0].isActive() && (Input.getTouches()[0].position.x / Graphics.getScreenSize().x)< 0.5f)
        {
            Touch touch = Input.getTouches()[0];

            if (touch.state == TouchState.Began || touch.state == TouchState.Moved)
            {
                float forward =  touch.deltaPosition.y/ Graphics.getScreenSize().y ;
                float side    = touch.deltaPosition.x/ Graphics.getScreenSize().x ;

                inputBuffer.x -= forward * sin(getTransform().get().getRotation().y);
                inputBuffer.z += forward * cos(getTransform().get().getRotation().y);

                inputBuffer.x += side * sin(getTransform().get().getRotation().y + (90.0f * Math.PI / 180))  *2; //Mul by 2 because the screen is bisected
                inputBuffer.z -= side * cos(getTransform().get().getRotation().y + (90.0f * Math.PI / 180))  *2;



            }


        }
        
        inputBuffer.multiplyInPlace(s_Speed*1000);
            
        //getTransform().get().translate(inputBuffer);
        m_Rigidbody.setVelocity(inputBuffer.x,inputBuffer.z);  
        
        Vector3 rotationBuffer = new Vector3();
            
        if (Input.getKey(KeyCode.E))
            rotationBuffer.y +=0.1f;
            
        if (Input.getKey(KeyCode.Q))
            rotationBuffer.y -=0.1f;

        //Touchscreen input
        if (Input.getTouches()[0].isActive() && (Input.getTouches()[0].position.x / Graphics.getScreenSize().x)>= 0.5f)
        {
            Touch touch = Input.getTouches()[0];

            if (touch.state == TouchState.Began || touch.state == TouchState.Moved)
                rotationBuffer.y += touch.deltaPosition.x/Graphics.getScreenSize().x * 200;


        }
            
        getTransform().get().rotate(rotationBuffer);
    
    }

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        Debug.log("Try to get the collider");
        m_Rigidbody = (BoxCollider)getGameObject().get().getComponent(BoxCollider.class);
        Debug.log("The rigidbody: "+m_Rigidbody);
        
    }

    @Override
    protected void OnRemovedFromGameObject() {}
    
}
