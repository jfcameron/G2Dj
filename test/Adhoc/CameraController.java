/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adhoc;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
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
            
        getTransform().get().translate(inputBuffer);
            
        Vector3 rotationBuffer = new Vector3();
            
        if (Input.getKey(KeyCode.O))
            rotationBuffer.y +=0.1f;
            
        if (Input.getKey(KeyCode.U))
            rotationBuffer.y -=0.1f;
            
        getTransform().get().rotate(rotationBuffer);
    
    }

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}

    @Override
    protected void OnRemovedFromGameObject() {}
    
}
