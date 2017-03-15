/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adhoc;

import G2Dj.Debug;
import G2Dj.Imp.Input.KeyCode;
import G2Dj.Input;
import G2Dj.Type.Engine.GameObject;
import G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class PlayerController extends G2Dj.Type.Engine.Component
{
    //
    //
    //
    private static final float s_Speed = 0.001f; 
    
    //
    //
    //    
    @Override
    public void update() 
    {
        //Translation
        {
            Vector3 inputBuffer = new Vector3();
            
            if (Input.getKey(KeyCode.A))
                inputBuffer.x-=s_Speed;
            
            if (Input.getKey(KeyCode.D))
                inputBuffer.x+=s_Speed;
            
            if (Input.getKey(KeyCode.W))
                inputBuffer.z-=s_Speed;
            
            if (Input.getKey(KeyCode.S))
                inputBuffer.z+=s_Speed;
            
            getTransform().get().translate(inputBuffer);
            
        }
        
        //Rotation
        {
            Vector3 rotationBuffer = new Vector3();
            
            if (Input.getKey(KeyCode.Q))
                rotationBuffer.z +=0.1f;
            
            if (Input.getKey(KeyCode.E))
                rotationBuffer.z -=0.1f;
            
            getTransform().get().rotate(rotationBuffer);
            
        }
        
        //Scale
        {
            Vector3 scaleBuffer = new Vector3();
            
            if (Input.getKey(KeyCode.R))
                scaleBuffer.addInPlace(0.01f);
            
            if (Input.getKey(KeyCode.F))
                scaleBuffer.addInPlace(-0.01f);
            
            getTransform().get().scale(scaleBuffer);
            
            
        }
        
    }

    @Override
    protected void OnRemovedFromGameObject(){}

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    
}
