/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Physics2D.Collider;
import grimhaus.com.G2Dj.Imp.Physics2D.Physics2DComponent;
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
import org.jbox2d.dynamics.joints.PrismaticJoint;
import org.jbox2d.dynamics.joints.PrismaticJointDef;

/**
 *
 * @author Joseph Cameron
 */
public class Rigidbody extends Physics2DComponent
{
    //
    // Data members
    //
    private Body m_Body = null; //Pos, rot, acc etc.
    private Physics2DScene m_Physics2DScene = null; //the scene
    private final ArrayList<Fixture> m_Fixtures = new ArrayList<>(); //the body
    private final BodyDef m_BodyDef = new BodyDef();
    private PrismaticJoint m_AxisFreezeJoint = null;
    private boolean m_RebuildRequired = true;
    
    //buffers
    private final Vec2 b_B2VecBuffer = new Vec2();
    private Vector3 b_ScaleBuffer;
    private final Vector3 b_RotationBuffer = new Vector3();
    private final Vector3 b_TranslationBuffer = new Vector3();
    
    //
    // Accessors
    //
    public Vector2 getVelocity(){return new Vector2(m_Body.m_linearVelocity);}
    
    public void setVelocity(final Vector2 aVelocity){setVelocity(aVelocity.x,aVelocity.y);}
    public void setVelocity(final float aX,final float aY){m_Body.setLinearVelocity(b_B2VecBuffer.set(aX,aY));}

    public void normalizeVelocity(){m_Body.m_linearVelocity.normalize();}
    public void scaleVelocity(final float aScalar){m_Body.m_linearVelocity.x*=aScalar;m_Body.m_linearVelocity.y*=aScalar;}
    
    public void applyImpulse(final float aX,final float aY){m_Body.applyLinearImpulse(b_B2VecBuffer.set(aX,aY),b_B2VecBuffer.set(0,0),true);}
    
    public void applyForce(final Vector2 aForce){m_Body.applyForceToCenter(b_B2VecBuffer.set(aForce.x,aForce.y));}
    public void applyForce(final float aX,final float aY){m_Body.applyForceToCenter(b_B2VecBuffer.set(aX,aY));}
    
    public void applyTorque(final float aTorque){m_Body.applyTorque(-aTorque);}
    
    public void setType(final grimhaus.com.G2Dj.Imp.Physics2D.BodyType aType){m_Body.m_type = aType.toB2BodyType();}
    
    public void freezeRotation(final boolean aFreeze){m_Body.setFixedRotation(aFreeze);}
    //public void freezeX
    //public void setFreezeY(final boolean aFreezeY){m_FreezeY = aFreezeY;}
    
    public void setLinearDamping(final float aLinearDamping){m_Body.setLinearDamping(aLinearDamping);}
    public void setAngularDamping(final float aAngularDamping){m_Body.setAngularDamping(aAngularDamping);}
    
    //public void setRestitution(final float aRestitution){m_Body.}
    
    public boolean isRotationFrozen(){return m_Body.isFixedRotation();}
    
    public void setLinearVelocity(final float aX, final float aY){m_Body.setLinearVelocity(b_B2VecBuffer.set(aX,aY));}
    
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
    
    public void clearForces()
    {
        m_Body.m_linearVelocity.set(0, 0);
        m_Body.m_angularVelocity = 0;
    
    }
    
    public void freezeAxis(AxisFreezeMode aFreezeMode)
    {
        if (m_AxisFreezeJoint != null)
        {
            m_Physics2DScene.getB2DWorld().destroyJoint(m_AxisFreezeJoint);
            m_AxisFreezeJoint.destructor();
                
        }
                
        //Testing axis lock
        PrismaticJointDef prismaticJointDef = new PrismaticJointDef();
        prismaticJointDef.bodyA = m_Physics2DScene.getB2DWorldOriginBody();
        prismaticJointDef.bodyB = m_Body;
        prismaticJointDef.collideConnected = false;
        prismaticJointDef.localAnchorA.set(b_B2VecBuffer.set(0,m_Body.getPosition().y));
        
        if (aFreezeMode == AxisFreezeMode.X)        
            prismaticJointDef.localAxisA.set(b_B2VecBuffer.set(1,0));
        
        if (aFreezeMode == AxisFreezeMode.Y)
            prismaticJointDef.localAxisA.set(b_B2VecBuffer.set(0,1));
        
        m_AxisFreezeJoint = (PrismaticJoint)m_Physics2DScene.getB2DWorld().createJoint(prismaticJointDef);
        
    }
    
    //
    // Component interface
    //
    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        m_Physics2DScene = (Physics2DScene)(getGameObject().get().getScene().get().getSceneGraph(Physics2DScene.class).get());
        buildBody();
        
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
                
                /*if (m_FreezeY)
                {
                    b2Pos.y = getTransform().get().getPosition().z;
                    m_Body.m_xf.set(b2Pos, m_Body.getAngle());
                    
                }*/
                
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
            
            //m_BodyDef.bullet=true;/////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    
            m_BodyDef.position = new Vec2(position.x,position.z);
            m_BodyDef.angle = -rotation.y;
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
                    {
                        fixtures[j].setUserData(new WeakReference<>(this));
                        //fixtures[j].setRestitution(1);/////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        m_Fixtures.add(m_Body.createFixture(fixtures[j]));
                    
                    }
            
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

    private Vec2 Vec2(int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
