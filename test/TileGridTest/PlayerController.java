/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TileGridTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Input.Gamepad;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Physics2D.CollisionInfo;

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
    
    private Gamepad        m_Gamepad     ;
    private Gamepad.Hat    m_DirectionHat;
    private Gamepad.Button m_ActionButton;
    
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
            if (Input.getKeyDown(m_ActionKey))
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
        //private final Vector2 b_InputBuffer   = Vector2.Zero();
        
        
        @Override public void OnEnter()
        {
            m_Graphic.setCurrentCell(0, 0);
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
            if ((!Input.getKey(m_LeftKey) && !Input.getKey(m_RightKey)) || (Input.getKey(m_LeftKey) && Input.getKey(m_RightKey)))
                if (m_Rigidbody.getVelocity().length() <= idleSpeedThreshold)
                    setState(Idle.class);
            
        }
        
        private void tryJump()
        {
            if (Input.getKeyDown(m_ActionKey))
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
            
            if (speedFactor < 3)
                speedFactor = 3;
            
            m_AnimationTimer += (float)Time.getDeltaTime()*speedFactor;
            
            if (m_Rigidbody.getVelocity().x < -0.1f)
                m_Facing = Facing.Right;
            else if (m_Rigidbody.getVelocity().x > 0.1f)
                m_Facing = Facing.Left;
            
            //Debug.log(m_Rigidbody.getVelocity().x);
            
        }
        
        private void tryCrouch()
        {
            if (Input.getKey(m_DownKey))
                setState(Crouch.class);
            
        }
        
    }
    
    protected class Jump extends CharacterState
    {
        private final float multiFrameJumpFrameTimerOnEnterValue = 0.4f;
        private float multiFrameJumpFrameTimer = 0;
        private final float firstFrameJumpImpulseMagnitude = 5f;
        float jumpVelocityBuffer = 0;
        
        @Override public void OnEnter()
        {
            m_Graphic.setCurrentCell(2, 1);

            applyFirstFrameJumpImpulse();
            
            multiFrameJumpFrameTimer = multiFrameJumpFrameTimerOnEnterValue;
            
        }
        
        @Override public void OnExit()
        {
            //if (m_Rigidbody.getVelocity().y > 2)
            //  m_Rigidbody.setVelocity(b_InputBuffer.setInPlace(m_Rigidbody.getVelocity().x, 0.5f));
            
        }
        
        @Override public void OnUpdate()
        {
            if (Input.getKey(m_ActionKey) || m_ActionButton.get())
                handleMultiframeJumpMechanics();
            else if (!Input.getKey(m_ActionKey) && !m_ActionButton.getDown())
                multiFrameJumpFrameTimer = 0;
            
            handleLateralAirMovement();            
            
            if (multiFrameJumpFrameTimer <= 0)
                setState(Fall.class);
            
        }
        
        private void applyFirstFrameJumpImpulse()
        {
            //Debug.log(1E1f*(float)Time.getDeltaTime());
            m_Rigidbody.applyImpulse(0, firstFrameJumpImpulseMagnitude);
            
            jumpVelocityBuffer = m_Rigidbody.getVelocity().y;
            
        }
        
        private void handleMultiframeJumpMechanics()
        {
            if (multiFrameJumpFrameTimer > 0.0f)
                m_Rigidbody.setVelocity(b_InputBuffer.setInPlace(m_Rigidbody.getVelocity().x,jumpVelocityBuffer));
            
            multiFrameJumpFrameTimer -= (float)Time.getDeltaTime();
            
        }
        
    }
    
    protected class Fall extends CharacterState
    {
        private final float fallAnimationSpeedThreshold = -3.5f;
        
        private float timer = 0;
        
        @Override public void OnEnter()
        {
           timer = 0;
            
        }
        
        @Override public void OnUpdate()
        {
            if (m_Rigidbody.getVelocity().y < fallAnimationSpeedThreshold)
                m_Graphic.setCurrentCell(1, 1);
           
            handleLateralAirMovement();
            
            if (Input.getKey(m_DownKey))
                    setState(Cannonball.class);
            
        }
        
        @Override public void OnTriggerEnter(final CollisionInfo info)
        {
            if (m_SouthSensor.equals(info.mine.get()))
                if (m_Rigidbody.getVelocity().y <= 0.1f)
                {
                   if (Input.getKey(m_LeftKey) || Input.getKey(m_RightKey))
                       setState(Walk.class);
                   else
                       setState(Idle.class);
        
                }
                   
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
            
            if (Input.getKeyDown(m_ActionKey))
                setState(Jump.class);
            
        }
        
    }
    
    protected class Cannonball extends CharacterState
    {
        private float hoverTimer = 0;
        private float heightBuffer = 0;
        private boolean cannonballImpulse1frameLock = false;
        
        @Override public void OnEnter()
        {
            hoverTimer = 0;
            cannonballImpulse1frameLock = false;
            
            m_Graphic.setCurrentCell(3, 1);
            heightBuffer = m_Rigidbody.getTransform().get().getPosition().z;
            
            Vector2 velocity = m_Rigidbody.getVelocity();
            
            if (Math.abs(velocity.x) > 1)
                m_Rigidbody.setVelocity(velocity.x/Math.abs(velocity.x),velocity.y);
            
        }
        
        @Override public void OnUpdate()
        {
            if (hoverTimer < 0.2f)
                m_Rigidbody.setPositionY(heightBuffer);
            else if (!cannonballImpulse1frameLock)
            {
                m_Rigidbody.applyImpulse(0, -9.5f);
                cannonballImpulse1frameLock = true;
                
            }
                
            hoverTimer += (float)Time.getDeltaTime();
            
        }
        
        @Override public void OnTriggerEnter(final CollisionInfo info)
        {
            if (m_SouthSensor.equals(info.mine.get()))
                if (m_Rigidbody.getVelocity().y <= 0.1f)
                {
                   if (Input.getKey(m_LeftKey) || Input.getKey(m_RightKey))
                       setState(Walk.class);
                   else
                       setState(Idle.class);
        
                }
                   
        }
        
        
    }
    
    private final float forwardAirSpeed = 10;
    private final float backwardAirSpeed = 7.5f;
    private final float maxAirSpeed = 11f;
    private final float maxLandSpeed = 11f;
    private void handleLateralAirMovement()
    {
        //Debug.log(m_Rigidbody.getVelocity().x);
        
        if (m_Facing == Facing.Left)
        {
            if (Input.getKey(m_LeftKey)) 
            {
                if (m_Rigidbody.getVelocity().x < +maxAirSpeed)
                    m_Rigidbody.applyImpulse(+forwardAirSpeed*(float)Time.getDeltaTime()*Math.abs(m_Rigidbody.getVelocity().x/maxAirSpeed-0.75f), 0);
                
            }
            if (Input.getKey(m_RightKey))
                m_Rigidbody.applyImpulse(-backwardAirSpeed*(float)Time.getDeltaTime()*Math.abs(m_Rigidbody.getVelocity().x/maxAirSpeed-0.75f), 0);
            
        }
        else if (m_Facing == Facing.Right)
        {
            if (Input.getKey(m_RightKey)) 
            {
                if (m_Rigidbody.getVelocity().x > -maxAirSpeed)
                    m_Rigidbody.applyImpulse(-forwardAirSpeed*(float)Time.getDeltaTime()*Math.abs(m_Rigidbody.getVelocity().x/maxAirSpeed+0.75f), 0);
                
            }
            if (Input.getKey(m_LeftKey))
                m_Rigidbody.applyImpulse(+backwardAirSpeed*(float)Time.getDeltaTime()*Math.abs(m_Rigidbody.getVelocity().x/maxAirSpeed+0.75f), 0);
            
        }
                        
    }
    
    public PlayerController()
    {
        Gamepad[] gamepads = Input.getGamepads();
        
        if (gamepads.length > 0)
        {
            m_Gamepad = gamepads[0]; 
            
            m_ActionButton = m_Gamepad.getButton("Button 0");
            m_DirectionHat = m_Gamepad.getHat("Hat Switch");
            
        }
        
        initStates
        (
            Idle.class,
                
            new Idle(),
            new Walk(),
            new Jump(),
            new Fall(),
            new Crouch(),
            new Cannonball()
        
        );
        
    }
    
}
