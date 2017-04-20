/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TileGridTest;

import grimhaus.com.G2Dj.Debug;
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
    private static final float s_TranslationSpeed = 1E3f;
    private float m_AnimationTimer = 0;
    
    private final KeyCode m_LeftKey   = KeyCode.A;
    private final KeyCode m_RightKey  = KeyCode.D;
    private final KeyCode m_DownKey   = KeyCode.S;
    private final KeyCode m_UpKey     = KeyCode.W;
    private final KeyCode m_ActionKey = KeyCode.Space;
    
    //buffers
    private final Vector2 b_InputBuffer   = Vector2.Zero();
    
    //
    // States
    //
    public class Idle extends CharacterState
    {
        @Override public void OnEnter()
        {
            m_Graphic.setCurrentCell(0, 0);
            
        }
        
        @Override public void OnUpdate()
        {
            tryJump();
            tryWalk();
            tryCrouch();
            
            if (Input.getKey(m_UpKey))
                m_Graphic.setCurrentCell(3, 0);
            else if (!Input.getKey(m_UpKey))
                m_Graphic.setCurrentCell(0, 0);

        }
        
        private void tryWalk()
        {
            if (Input.getKey(m_LeftKey) || Input.getKey(m_RightKey))
                setState(Walk.class);
            
        }
        
        private void tryJump()
        {
            if (Input.getKey(m_ActionKey))
                setState(Jump.class);
                
        }
        
        private void tryCrouch()
        {
            if (Input.getKey(m_DownKey))
                setState(Crouch.class);
            
        }
        
    }
    
    protected class Walk extends CharacterState
    {
        private float idleSpeedThreshold = 3f; 
        private static final float s_TranslationSpeed = 1E3f;
        private float m_AnimationTimer = 0;
        private final float walkInterval = 1f;
        //buffers
        private final Vector2 b_InputBuffer   = Vector2.Zero();
        
        
        @Override public void OnEnter()
        {
            //m_Graphic.setCurrentCell(0, 0);
            m_AnimationTimer=0;
            
        }
        
        @Override public void OnUpdate()
        {
            tryIdle();
            tryJump();
            tryCrouch();
            
            handleInputs();
            animate();
            
        }
        
        //
        // Implementation
        //
        private void tryIdle()
        {
            if (!Input.getKey(m_LeftKey) && !Input.getKey(m_RightKey))
                if (m_Rigidbody.getVelocity().length() <= idleSpeedThreshold)
                    setState(Idle.class);
            
        }
        
        private void tryJump()
        {
            if (Input.getKey(m_ActionKey))
                setState(Jump.class);
                
        }
        
        private void handleInputs()
        {
            b_InputBuffer.zero();
            
            if (Input.getKey(KeyCode.A))
                b_InputBuffer.x += 1;
            
            if (Input.getKey(KeyCode.D))
                b_InputBuffer.x -= 1;
            
            b_InputBuffer.multiplyInPlace(s_TranslationSpeed);
            b_InputBuffer.multiplyInPlace((float)Time.getDeltaTime());
            
            m_Rigidbody.applyForce(b_InputBuffer.x,b_InputBuffer.y);
            
        }
        
        private void animate()
        {
            if (m_AnimationTimer > 2*walkInterval)
            {
                m_Graphic.setCurrentCell(2, 0);
                m_AnimationTimer = 0;
            
            }
            else if (m_AnimationTimer > walkInterval)
                m_Graphic.setCurrentCell(1, 0);

            //m_AnimationTimer += (float)Time.getDeltaTime()*Math.abs(m_Rigidbody.getVelocity().x+1);
            
            float speedFactor = Math.abs(m_Rigidbody.getVelocity().x+1);
            
            if (speedFactor < 5)
                speedFactor = 5;
            
            Debug.log(speedFactor);
            
            m_AnimationTimer += (float)Time.getDeltaTime()*speedFactor;
            
            if (m_Rigidbody.getVelocity().x < -0.1f)
                m_Graphic.getTransform().get().setRotation(90,180,0);
            else if (m_Rigidbody.getVelocity().x > 0.1f)
                m_Graphic.getTransform().get().setRotation(90,0,0);
            
        }
        
        private void tryCrouch()
        {
            if (Input.getKey(m_DownKey))
                setState(Crouch.class);
            
        }
        
    }
    
    protected class Jump extends CharacterState
    {
        @Override public void OnEnter()
        {
            m_Graphic.setCurrentCell(2, 1);
            
        }
        
        @Override public void OnUpdate()
        {
            if (Input.getKey(m_ActionKey))
                handleMultiframeJumpMechanics();
            else if (!Input.getKey(m_ActionKey))
                setState(Idle.class);
            
        }
        
        private void handleMultiframeJumpMechanics()
        {
            
            
        }
        
    }
    
    protected class Crouch extends CharacterState
    {
        @Override public void OnEnter()
        {
            m_Graphic.setCurrentCell(3, 1);
            
        }
        
        @Override public void OnUpdate()
        {
            if (!Input.getKey(m_DownKey))
                setState(Idle.class);
            
        }
        
    }
    
    public PlayerController()
    {
        initStates
        (
            Idle.class,
                
            new Idle(),
            new Walk(),
            new Jump(),
            new Crouch()
        
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
