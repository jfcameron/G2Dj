/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TileGridTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Engine.RequireComponents;
import grimhaus.com.G2Dj.Imp.Physics2D.Collider;
import grimhaus.com.G2Dj.Imp.Physics2D.ColliderType;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.SpriteSheet;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Physics2D.CollisionInfo;
import grimhaus.com.G2Dj.Type.Physics2D.PolygonCollider;
import grimhaus.com.G2Dj.Type.Physics2D.Rigidbody;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 *
 * @author Joseph Cameron
 */
@RequireComponents({Rigidbody.class})
public abstract class CharacterController extends Component
{
    public enum Facing
    {
        Left,
        Right;
        
    }
    
    protected class CharacterState
    {
        protected void OnEnter(){};
        protected void OnExit(){};
        protected void OnUpdate(){};
        protected void OnFixedUpdate(){};
        protected void OnTriggerEnter(final CollisionInfo info){};
        
    }
    
    protected HashMap<Class<? extends CharacterState>,CharacterState> m_States = new HashMap<>();
    
    private CharacterState m_LastState;
    private CharacterState m_CurrentState;
    protected Facing m_Facing = Facing.Right;
    
    protected final void initStates(final Class<? extends CharacterState> aState,final CharacterState... aStates)
    {
        m_States.clear();
        addStates(aStates);
        setState(aState);
        
    }
    
    
    protected final void setState(final Class<? extends CharacterState> aState)
    {
        m_LastState = m_CurrentState;
        m_CurrentState = m_States.get(aState);
        
    }
    
    protected final void addStates(final CharacterState... aStates)
    { 
        if (aStates == null)
            return;
        
        for (int i=0;i<aStates.length;i++)
            m_States.put(aStates[i].getClass(), aStates[i]);

    }

    
    //
    //
    //
    protected Rigidbody   m_Rigidbody = null;
    protected SpriteSheet m_Graphic   = null;
    //Cardinal Sensors
    protected Collider m_NorthSensor = null;
    protected Collider m_EastSensor  = null;
    protected Collider m_SouthSensor = null;
    protected Collider m_WestSensor  = null;

    

    @Override protected void update() 
    {
        //State updating
        if (m_CurrentState != null)
        {
            
                if (!m_CurrentState.equals(m_LastState))
                {
                    if (m_LastState != null)
                    {
                        //Debug.log(m_LastState.getClass().getSimpleName()+".OnExit");
                        m_LastState.OnExit();
                        
                    }
                    
                    m_CurrentState.OnEnter();
                    m_LastState = m_CurrentState;

                    //Debug.log(m_CurrentState.getClass().getSimpleName()+".OnEnter");
                    Debug.log(m_CurrentState.getClass().getSimpleName()+".Update ...");
                
                }
                
            if (m_Facing == Facing.Left)
                m_Graphic.getTransform().get().setRotation(90,0,0);
            else if (m_Facing == Facing.Right)
                m_Graphic.getTransform().get().setRotation(90,180,0);
                
            m_CurrentState.OnUpdate();
        
        }
        
        //update graphic object position
        m_Graphic.getTransform().get().setPosition(getTransform().get().getPosition());
    
    }

    @Override protected void fixedUpdate() 
    {
        if (m_CurrentState != null)
            m_CurrentState.OnFixedUpdate();
    
    }
    
    public void OnTriggerEnter(final CollisionInfo info)
    {
        if (m_CurrentState != null)
            m_CurrentState.OnTriggerEnter(info);
        
        /*if (m_NorthSensor.equals(info.mine.get()))
            Debug.log("NORTH");
        
        if (m_EastSensor.equals(info.mine.get()))
            Debug.log("EAST");
        
        if (m_SouthSensor.equals(info.mine.get()))
            Debug.log("SOUTH");

            
        if (m_WestSensor.equals(info.mine.get()))
            Debug.log("WEST");*/
        
    }

    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
    //
    //
    //
    private void initGraphic()
    {
        //Init graphic
        GameObject graphic = getGameObject().get().getScene().get().addGameObject().get();
        graphic.getTransform().get().setRotation(90,180,0);
        graphic.getTransform().get().setPosition(+1,0,0);
        m_Graphic = (SpriteSheet)graphic.addComponent(SpriteSheet.class);
        m_Graphic.setSpriteSheet("Blocky.png", 16, 17);
        m_Graphic.setCurrentCell(0, 0);
        
    }
    
    private void initRigidbody()
    {
        //Body
        PolygonCollider bodyCollider = (PolygonCollider)getGameObject().get().addComponent(PolygonCollider.class);
        float offset = -0.5f;
        bodyCollider.setVerticies(new Vector2[]
        {
            new Vector2(0.2f+offset,0.1f+offset),
            new Vector2(0.2f+offset,1.0f+offset),
            new Vector2(0.8f+offset,1.0f+offset),
            new Vector2(0.8f+offset,0.1f+offset),
        
        });
        bodyCollider.setDensity(1.5f);        
        
        //North sensor
        PolygonCollider northSensor  = (PolygonCollider)getGameObject().get().addComponent(PolygonCollider.class);
        northSensor.setVerticies(new Vector2[]
        {
            new Vector2(0.3f+offset,0.0f+offset),
            new Vector2(0.3f+offset,0.1f+offset),
            new Vector2(0.7f+offset,0.1f+offset),
            new Vector2(0.7f+offset,0.0f+offset),
        
        });
        northSensor.setDensity(0);
        northSensor.setType(ColliderType.Trigger);
        m_NorthSensor = northSensor;
        
        //South sensor
        PolygonCollider southSensor  = (PolygonCollider)getGameObject().get().addComponent(PolygonCollider.class);
        southSensor.setVerticies(new Vector2[]
        {
            new Vector2(0.3f+offset,1.0f+offset),
            new Vector2(0.3f+offset,1.1f+offset),
            new Vector2(0.7f+offset,1.1f+offset),
            new Vector2(0.7f+offset,1.0f+offset),
        
        });
        southSensor.setDensity(0);
        southSensor.setType(ColliderType.Trigger);
        m_SouthSensor = southSensor;
        
        //East sensor
        PolygonCollider eastSensor  = (PolygonCollider)getGameObject().get().addComponent(PolygonCollider.class);
        eastSensor.setVerticies(new Vector2[]
        {
            new Vector2(0.8f+offset,0.2f+offset),
            new Vector2(0.8f+offset,0.9f+offset),
            new Vector2(0.9f+offset,0.9f+offset),
            new Vector2(0.9f+offset,0.2f+offset),
        
        });
        eastSensor.setDensity(0);
        eastSensor.setType(ColliderType.Trigger);
        m_EastSensor = eastSensor;
        
        //West sensor
        PolygonCollider westSensor  = (PolygonCollider)getGameObject().get().addComponent(PolygonCollider.class);
        westSensor.setVerticies(new Vector2[]
        {
            new Vector2(0.1f+offset,0.2f+offset),
            new Vector2(0.1f+offset,0.9f+offset),
            new Vector2(0.2f+offset,0.9f+offset),
            new Vector2(0.2f+offset,0.2f+offset),
        
        });
        westSensor.setDensity(0);
        westSensor.setType(ColliderType.Trigger);
        m_WestSensor = westSensor;
        
        m_Rigidbody = (Rigidbody)getGameObject().get().getComponent(Rigidbody.class);
        m_Rigidbody.freezeRotation(true);
        m_Rigidbody.setLinearDamping(1);
        //m_Rigidbody.setGravityScale(2);
        
        //
        bodyCollider .setDrawDebugLines(false);
        
        m_NorthSensor.setDrawDebugLines(false);
        m_EastSensor .setDrawDebugLines(false);
        m_SouthSensor.setDrawDebugLines(true);
        m_WestSensor .setDrawDebugLines(false);
        
    }
    
    @Override protected void initialize() 
    {
        initGraphic();
        initRigidbody();
        /*
        //collect all states on this
        Class[] innerClasses = this.getClass().getDeclaredClasses();
        
        for(int i=0;i<innerClasses.length;i++)
            if (CharacterState.class.isAssignableFrom(innerClasses[i]))
                try 
                {
                    m_States.put(innerClasses[i], (CharacterState)innerClasses[i].newInstance());
                
                } 
                catch (InstantiationException ex) {Logger.getLogger(CharacterController.class.getName()).log(Level.SEVERE, null, ex);} 
                catch (IllegalAccessException ex) {Logger.getLogger(CharacterController.class.getName()).log(Level.SEVERE, null, ex);}*/
        
    }
    
    protected CharacterController()
    {
        
        
        
        
               
        //Set initial state
       // m_CurrentState = setInitialState();
     //   m_States.put(m_CurrentState.getClass(),m_CurrentState);
        
        
    }
    
    
}
