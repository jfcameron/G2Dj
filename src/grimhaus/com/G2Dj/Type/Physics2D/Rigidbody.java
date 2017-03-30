/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Physics2D.Collider;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector3;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public class Rigidbody extends Component
{
    //
    // Data members
    //
    private Body m_Body = null; //Pos, rot, acc etc.
    private Physics2DScene m_Physics2DScene = null; //the scene
    private final ArrayList<Fixture> m_Fixtures = new ArrayList<>(); //the body
    private final BodyDef m_BodyDef = new BodyDef();
    private boolean m_RebuildRequired = true;
    //buffers
    private final Vec2 b_B2VecBuffer = new Vec2();
    private Vector3 b_ScaleBuffer;
    private Vector3 b_RotationBuffer = new Vector3();
    private Vector3 b_TranslationBuffer = new Vector3();
    
    
    //
    // Accessors
    //
    public void setVelocity(final Vector2 aVelocity){setVelocity(aVelocity.x,aVelocity.y);}
    public void setVelocity(final float aX,final float aY){m_Body.setLinearVelocity(b_B2VecBuffer.set(aX,aY));}

    public void applyImpulse(final float aX,final float aY){m_Body.applyLinearImpulse(b_B2VecBuffer.set(aX,aY),b_B2VecBuffer.set(0,0),true);}
    public void applyForce(final float aX,final float aY){m_Body.applyForceToCenter(b_B2VecBuffer.set(aX,aY));}
    
    public void applyTorque(final float aTorque){m_Body.applyTorque(-aTorque);}
    
    public void setType(final grimhaus.com.G2Dj.Imp.Physics2D.BodyType aType){m_Body.m_type = aType.toB2BodyType();}
    
    
    public void setPosition(final float aX,final float aY,final float aZ)
    {
        m_Body.setTransform(b_B2VecBuffer.set(aX,aZ), m_Body.getAngle());
        getTransform().get().setPosition(aX,aY,aZ);
        
    }
    
    public void setRotation(final float aRotation)
    {
        m_Body.setTransform(m_Body.getPosition(), -aRotation);
        getTransform().get().getRotation().y = aRotation;
        
    }
    
    //
    // Component interface
    //
    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        m_Physics2DScene = (Physics2DScene)getGameObject().get().getScene().get().getSceneGraph(Physics2DScene.class);
        buildBody();//m_RebuildRequired = true;
        
    }

    @Override
    protected void OnRemovedFromGameObject() 
    {
        if (m_Body != null && m_Physics2DScene != null)
            m_Physics2DScene.getB2DWorld().destroyBody(m_Body);
        
    }
        
    @Override
    public void fixedUpdate() 
    {
        if (m_RebuildRequired)
            buildBody();
        
        switch (m_Body.m_type) 
        {
            case DYNAMIC:
            {
                Vec2  b2Pos = m_Body.getPosition();
                float b2Rot = -m_Body.getAngle();
                
                getTransform().get().setPosition(b2Pos.x,0,b2Pos.y);
                getTransform().get().getRotation().y = b2Rot;
            
            } break;
            
            case KINEMATIC:
            case STATIC:
            {
                Vector3 tPos = getTransform().get().getPosition();
                Vector3 tRot = getTransform().get().getRotation();
            
                //Check for rotation changes
                if (!getTransform().get().getRotation().equals(b_RotationBuffer))
                    m_Body.setTransform(b_B2VecBuffer.set(tPos.x,tPos.z), -tRot.y);
                
                b_RotationBuffer.setInPlace(getTransform().get().getRotation());
                
                //Check for translation changes
                if (!getTransform().get().getPosition().equals(b_TranslationBuffer))
                    m_Body.setTransform(b_B2VecBuffer.set(tPos.x,tPos.z), -tRot.y);
                
                b_TranslationBuffer.setInPlace(getTransform().get().getPosition());
                              
                              
            } break;
                        
            default:
            break;
        
        }
        
        Vector3 scale = getTransform().get().getScale();
        
        if (b_ScaleBuffer == null)
        {
            b_ScaleBuffer = new Vector3();
            b_ScaleBuffer.copy(scale);
        
        }
            
        if (!b_ScaleBuffer.equals(scale))
            buildFixtures();
        
        b_ScaleBuffer.copy(scale);
    
    }
    
    @Override
    public void update() {}

    @Override
    protected void OnComponentAdded(Component aComponent) 
    {
        buildFixtures();
        
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) 
    {
        
    }
    
    //
    // Implementation
    //
    private void buildBody()
    {
        m_RebuildRequired = false;
        
        Vector3 position = getTransform().get().getPosition();
        Vector3 scale    = getTransform().get().getScale();
        Vector3 rotation = getTransform().get().getRotation();
        
        //Create body data...
        {
            m_BodyDef.type = BodyType.DYNAMIC;
            m_BodyDef.linearDamping = 1.0f;
            m_BodyDef.angularDamping = 1.0f;
            m_BodyDef.fixedRotation = false;
                                    
            m_BodyDef.position = new Vec2(position.x,position.z);
            m_BodyDef.angle = -rotation.y; //TODO: implement
            Debug.log(getGameObject().get().getName(),rotation.y);
            
            
        }
        
        //Create the body in the world
        if (m_Body != null && m_Physics2DScene != null)
            m_Physics2DScene.getB2DWorld().destroyBody(m_Body);
        
        m_Body = m_Physics2DScene.getB2DWorld().createBody(m_BodyDef);
        
        
        buildFixtures();
                
    }
    
    private void buildFixtures()
    {
        ArrayList<Component> colliders = getGameObject().get().getComponents(Collider.class);
        
        //Destroy the fixtures
        for(int i=0,s=m_Fixtures.size();i<s;i++)
            m_Body.destroyFixture(m_Fixtures.get(i));
            
        m_Fixtures.clear();
        
        //Build / Rebuild the fixtures
        for(int i=0,s=colliders.size();i<s;i++)
        {
            FixtureDef[] fixtures = ((Collider)colliders.get(i)).getB2DFixtures();
            
            if (fixtures != null && m_Fixtures != null && m_Body != null)
                for(int j=0,t=fixtures.length;j<t;j++)
                    if (fixtures[j] != null)
                        m_Fixtures.add(m_Body.createFixture(fixtures[j]));
            
        }
            
    }
    
    //
    // Constructors
    //
    public Rigidbody()
    {
        m_BodyDef.userData = new WeakReference<>(this);
        
    }

    @Override
    protected void initialize() 
    {
        b_RotationBuffer.copy(getTransform().get().getRotation());
        b_TranslationBuffer.copy(getTransform().get().getPosition());
        
        buildFixtures();
        
    }
    
}
