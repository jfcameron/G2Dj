/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Debug;
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
    
    //buffers
    Vector3 b_ScaleBuffer;
    
    //
    // Accessors
    //
    public void setType(final grimhaus.com.G2Dj.Imp.Physics2D.BodyType aType){m_Body.m_type = aType.toB2BodyType();}
    public void setVelocity(final Vector2 aVelocity){setVelocity(aVelocity.x,aVelocity.y);}
    public void setVelocity(final float aX,final float aY){m_Body.setLinearVelocity(new Vec2(aX,aY));}
    
    //
    // Component interface
    //
    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        m_Physics2DScene = (Physics2DScene)getGameObject().get().getScene().get().getSceneGraph(Physics2DScene.class);
        
        buildBody();
        
    }

    @Override
    protected void OnRemovedFromGameObject() 
    {
        if (m_Body != null && m_Physics2DScene != null)
            m_Physics2DScene.getB2DWorld().destroyBody(m_Body);
        
    }
        
    @Override
    public void update() 
    {
        Vec2 pos = m_Body.getPosition();
        float rot = m_Body.getAngle();
        
        switch (m_Body.m_type) 
        {
            case DYNAMIC:
            getTransform().get().setPosition(pos.x,0,pos.y);
            
            //Debug.log("Transform: "+getTransform().get().getRotation());
            
            break;
            case KINEMATIC:
            
                
            break;
            case STATIC:

            break;
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
    protected void OnComponentAdded(Component aComponent) 
    {
        buildFixtures();
        
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) {
    }
    
    //
    // Implementation
    //
    private void buildBody()
    {
        Vector3 position = getTransform().get().getPosition();
        Vector3 scale    = getTransform().get().getScale();
        Vector3 rotation = getTransform().get().getRotation();
        
        //Create body data...
        BodyDef myBodyDef = new BodyDef();
        {
            myBodyDef.type = BodyType.DYNAMIC;
            myBodyDef.position = new Vec2(position.x,position.z);
            myBodyDef.angle = rotation.y*0.0174532925199432957f; //TODO: implement
            myBodyDef.linearDamping = 1.0f;
            myBodyDef.angularDamping = 1.0f;
            myBodyDef.fixedRotation = true;
            
        }
        
        //Create the body in the world
        if (m_Body != null && m_Physics2DScene != null)
            m_Physics2DScene.getB2DWorld().destroyBody(m_Body);
        
        m_Body = m_Physics2DScene.getB2DWorld().createBody(myBodyDef);
        
        buildFixtures();
        
    }
    
    private void buildFixtures()
    {
        ArrayList<Component> colliders = getGameObject().get().getComponents(Collider.class);
        
        Debug.log("**************BUILDING FXITURES****",m_Body);
        
        //Destroy the fixtures
        for(int i=0,s=m_Fixtures.size();i<s;i++)
            m_Body.destroyFixture(m_Fixtures.get(i));
            
        m_Fixtures.clear();
        
        //Build / Rebuild the fixtures
        for(int i=0,s=colliders.size();i<s;i++)
            m_Fixtures.add(m_Body.createFixture(((Collider)colliders.get(i)).getB2DFixture()));
            
    }
    
    //
    // Constructors
    //
    
}
