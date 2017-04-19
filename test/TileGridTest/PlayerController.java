/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TileGridTest;

import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Physics2D.CollisionInfo;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class PlayerController extends CharacterController
{    
    protected int test = 0;
    
    private static final float s_TranslationSpeed = 1E3f;
    private float m_AnimationTimer = 0;
    
    private final KeyCode m_LeftKey = KeyCode.A;
    private final KeyCode m_RightKey = KeyCode.D;
    
    //buffers
    private final Vector2 b_InputBuffer   = Vector2.Zero();
    
    //
    // States
    //
    public class Idle extends CharacterState
    {
        @Override public void OnEnter()
        {
            m_Graphic.setCurrentCell(1, 0);
            
        }
        
        @Override public void OnUpdate()
        {
            if (Input.getKey(m_LeftKey) || Input.getKey(m_RightKey))
                setState(Walk.class);

        }
        
    }
    
    protected class Walk extends CharacterState
    {
        @Override public void OnUpdate()
        {
            
            
        }
        
    }
    
    public PlayerController()
    {
        initStates
        (
            Idle.class,
            new Idle(),
            new Walk()
        
        );
        
    }
    
    //
    // Implementation
    //    
    /*private void handleInputs()
    {
        private static final float s_TranslationSpeed = 1E3f;
        private float m_AnimationTimer = 0;
        //buffers
        private final Vector2 b_InputBuffer   = Vector2.Zero();
        
        b_InputBuffer.zero();
        
        if (Input.getKey(KeyCode.A))
            b_InputBuffer.x += 1;
        
        if (Input.getKey(KeyCode.D))
            b_InputBuffer.x -= 1;
        
        b_InputBuffer.multiplyInPlace(s_TranslationSpeed);
        b_InputBuffer.multiplyInPlace((float)Time.getDeltaTime());

        m_Rigidbody.applyForce(b_InputBuffer.x,b_InputBuffer.y);
        
        /*if (Input.getKeyDown(KeyCode.W) && jumptime-- > 0)
        {            
            Debug.log("hi"); 
            m_Rigidbody.applyImpulse(0, jumptime*2E3f*(float)Time.getDeltaTime());
        
        }*/
            
    /*}
    private void updateGraphic()
    {
        m_AnimationTimer += Time.getDeltaTime()*Math.abs(m_Rigidbody.getVelocity().x);
                
        if (m_AnimationTimer > 1)
        {
            m_SpriteSheet.setCurrentCell(0, 0);
            
            if (m_AnimationTimer > 2)
            {
                m_SpriteSheet.setCurrentCell(1, 0);
                m_AnimationTimer = 0;
            
            }
            
        }
        
        if (Math.abs(m_Rigidbody.getVelocity().x) < 0.1f)
        {
            m_SpriteSheet.setCurrentCell(1, 0);
            m_AnimationTimer=0;
                        
        }
        
        //Direction
        if(m_Rigidbody.getVelocity().x > 0.1f)
            m_Graphic.getTransform().get().setRotation(90,0,0);
        else if(m_Rigidbody.getVelocity().x < -0.1f) 
            m_Graphic.getTransform().get().setRotation(90,180,0);
        
        //Position
        Vector3 pos = getTransform().get().getPosition();
        m_Graphic.getTransform().get().setPosition(pos);
        
    }*/
    
}
