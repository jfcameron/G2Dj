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

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public class Rigidbody extends Component
{
    private Body m_Body = null;
    private Physics2DScene m_Physics2DScene = null;
    
    public void setType(final grimhaus.com.G2Dj.Imp.Physics2D.BodyType aType)
    {
        if (null != aType)
            switch (aType) 
            {
                case Dynamic:
                m_Body.m_type = BodyType.DYNAMIC;
                break;
                case Kinematic:
                m_Body.m_type = BodyType.KINEMATIC;
                break;
                case Static:
                m_Body.m_type = BodyType.STATIC;
                break;
                default:
                break;
                
            }
        
    }
    
    public void setVelocity(final Vector2 aVelocity){setVelocity(aVelocity.x,aVelocity.y);}
    public void setVelocity(final float aX,final float aY){m_Body.setLinearVelocity(new Vec2(aX,aY));}
    
    @Override
    protected void finalize()
    {
        if (m_Body != null && m_Physics2DScene != null)
            m_Physics2DScene.getB2DWorld().destroyBody(m_Body);
        
    }
    
    //
    // Component
    //
    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        m_Physics2DScene = (Physics2DScene)getGameObject().get().getScene().get().getSceneGraph(Physics2DScene.class);
        
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
            
        }
        
        //Create the body in the world
        if (m_Body != null && m_Physics2DScene != null)
            m_Physics2DScene.getB2DWorld().destroyBody(m_Body);
        
        m_Body = m_Physics2DScene.getB2DWorld().createBody(myBodyDef);
        
        buildFixtures();
        
    }

    void buildFixtures()
    {
        ArrayList<Component> colliders = getGameObject().get().getComponents(Collider.class);
        
        for(int i=0,s=colliders.size();i<s;i++)
        {
            m_Body.createFixture(((Collider)colliders.get(i)).getB2DFixture());
        
        }
            
    }
    
    @Override
    protected void OnRemovedFromGameObject() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            
            
            //getTransform().get().getRotation().y = rot;
            
            break;
            case KINEMATIC:
                
            break;
            case STATIC:

            break;
            default:
            break;
        
        }
    
    }

    @Override
    protected void OnComponentAdded(Component aComponent) 
    {
        
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) {
    }

    @Override
    protected void OnScaleChanged() {
    }
    
}
