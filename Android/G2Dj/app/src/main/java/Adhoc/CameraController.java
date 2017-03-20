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
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class CameraController extends Component
{
    private static final float s_Speed = 0.001f; 
    
    @Override
    public void update() 
    {
        Vector3 inputBuffer = new Vector3();
            
        if (Input.getKey(KeyCode.J))
            inputBuffer.x-=s_Speed;
        
        if (Input.getKey(KeyCode.L))
            inputBuffer.x+=s_Speed;
            
        if (Input.getKey(KeyCode.I))
            inputBuffer.z-=s_Speed;
            
        if (Input.getKey(KeyCode.K))
            inputBuffer.z+=s_Speed;

        //Touchscreen input
        if (Input.getTouches()[0].isActive() && (Input.getTouches()[0].position.x / Graphics.getScreenSize().x)< 0.5f)
        {
            Touch touch = Input.getTouches()[0];

            if (touch.state == TouchState.Began || touch.state == TouchState.Moved)
            {
                inputBuffer.x += touch.deltaPosition.x*s_Speed*1000/ Graphics.getScreenSize().x ;
                inputBuffer.z += touch.deltaPosition.y*s_Speed*1000/ Graphics.getScreenSize().x ;

            }


        }
            
        getTransform().get().translate(inputBuffer);
            
        Vector3 rotationBuffer = new Vector3();
            
        if (Input.getKey(KeyCode.O))
            rotationBuffer.y +=0.1f;
            
        if (Input.getKey(KeyCode.U))
            rotationBuffer.y -=0.1f;

        //Touchscreen input
        if (Input.getTouches()[0].isActive() && (Input.getTouches()[0].position.x / Graphics.getScreenSize().x)>= 0.5f)
        {
            Touch touch = Input.getTouches()[0];

            if (touch.state == TouchState.Began || touch.state == TouchState.Moved)
                rotationBuffer.y += touch.deltaPosition.x/Graphics.getScreenSize().x * 100;


        }
            
        getTransform().get().rotate(rotationBuffer);
    
    }

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}

    @Override
    protected void OnRemovedFromGameObject() {}
    
}
