/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TileGridTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Engine.RequireComponents;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Imp.Physics2D.Collider;
import grimhaus.com.G2Dj.Imp.Physics2D.ColliderType;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.SpriteSheet;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import grimhaus.com.G2Dj.Type.Physics2D.CollisionInfo;
import grimhaus.com.G2Dj.Type.Physics2D.PolygonCollider;
import grimhaus.com.G2Dj.Type.Physics2D.Rigidbody;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
@RequireComponents({Rigidbody.class})
public class PlayerController extends Component
{
    //
    //
    //
    private Rigidbody m_Rigidbody = null;
    private SpriteSheet m_SpriteSheet = null;
    private float m_AnimationTimer = 0;
    private GameObject m_Graphic = null;
    
    //Cardinal Sensors
    private Collider m_NorthSensor = null;
    private Collider m_EastSensor  = null;
    private Collider m_SouthSensor = null;
    private Collider m_WestSensor  = null;
    
    //buffers
    private final Vector2 b_InputBuffer   = Vector2.Zero();
    //const
    private static final float s_TranslationSpeed = 1E3f;

    @Override
    protected void initialize() 
    {
        initBodyColliderAndCardinalSensors();
        
        m_Rigidbody = (Rigidbody)getGameObject().get().getComponent(Rigidbody.class);
        m_Rigidbody.freezeRotation(true);
        m_Rigidbody.setLinearDamping(0);
        
        //Init graphic
        m_Graphic = getGameObject().get().getScene().get().addGameObject().get();
        m_Graphic.getTransform().get().setRotation(90,180,0);
        m_Graphic.getTransform().get().setPosition(+1,0,0);
        m_SpriteSheet = (SpriteSheet)m_Graphic.addComponent(SpriteSheet.class);
        m_SpriteSheet.setSpriteSheet("Blocky.png", 16, 17);
        m_SpriteSheet.setCurrentCell(0, 0);
        
        
        
    }
    
    private void initBodyColliderAndCardinalSensors()
    {
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
        
        PolygonCollider northSensor  = (PolygonCollider)getGameObject().get().addComponent(PolygonCollider.class);
        northSensor.setVerticies(new Vector2[]
        {
            new Vector2(0.2f+offset,0.0f+offset),
            new Vector2(0.2f+offset,0.1f+offset),
            new Vector2(0.8f+offset,0.1f+offset),
            new Vector2(0.8f+offset,0.0f+offset),
        
        });
        northSensor.setDensity(0);
        northSensor.setType(ColliderType.Trigger);
        m_NorthSensor = northSensor;
        
        PolygonCollider southSensor  = (PolygonCollider)getGameObject().get().addComponent(PolygonCollider.class);
        southSensor.setVerticies(new Vector2[]
        {
            new Vector2(0.2f+offset,1.0f+offset),
            new Vector2(0.2f+offset,1.1f+offset),
            new Vector2(0.8f+offset,1.1f+offset),
            new Vector2(0.8f+offset,1.0f+offset),
        
        });
        southSensor.setDensity(0);
        southSensor.setType(ColliderType.Trigger);
        m_SouthSensor = southSensor;
        
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
        
    }
    
    public void OnTriggerEnter(final CollisionInfo info)
    {
        if (m_NorthSensor.equals(info.mine.get()))
            Debug.log("NORTH");
        
        if (m_EastSensor.equals(info.mine.get()))
            Debug.log("EAST");
        
        if (m_SouthSensor.equals(info.mine.get()))
            Debug.log("SOUTH");
        
        if (m_WestSensor.equals(info.mine.get()))
            Debug.log("WEST");
        
    }

    @Override
    protected void update() 
    {
        handleInputs();
        updateGraphic();
                                
    }
    
    @Override protected void fixedUpdate() { }
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
    //
    // Implementation
    //
    private void handleInputs()
    {
        b_InputBuffer.zero();
        
        if (Input.getKey(KeyCode.A))
            b_InputBuffer.x += 1;
        
        if (Input.getKey(KeyCode.D))
            b_InputBuffer.x -= 1;
        
        if (Input.getKey(KeyCode.W))
            b_InputBuffer.y += 1;
       
        b_InputBuffer.multiplyInPlace(s_TranslationSpeed);
        b_InputBuffer.multiplyInPlace((float)Time.getDeltaTime());

        m_Rigidbody.applyForce(b_InputBuffer.x,b_InputBuffer.y);
        
    }
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
        
    }
    
}
