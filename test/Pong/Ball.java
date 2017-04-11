/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Pong;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Engine.RequireComponents;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Physics2D.CircleCollider;
import grimhaus.com.G2Dj.Type.Physics2D.CollisionInfo;
import grimhaus.com.G2Dj.Type.Physics2D.Rigidbody;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
@RequireComponents({CircleCollider.class,Rigidbody.class})
public class Ball extends Component
{
    //
    //
    //
    private Rigidbody m_Rigidbody = null;
    private float m_Speed = 14f;
    
    //
    //
    //
    @Override protected void initialize() 
    {
        m_Rigidbody = (Rigidbody)getGameObject().get().getComponent(Rigidbody.class);
        m_Rigidbody.setLinearDamping(0);
        m_Rigidbody.setAngularDamping(0);
        m_Rigidbody.applyForce(new Vector2(0,-0.75f).unit().multiply(m_Speed));
        
        CircleCollider cc = (CircleCollider)getGameObject().get().getComponent(CircleCollider.class);
        cc.setRestitution(1.0f);
        
    }

    @Override protected void update() 
    {
        
    
    }

    @Override protected void fixedUpdate() 
    {
        m_Rigidbody.normalizeVelocity();
        m_Rigidbody.scaleVelocity(m_Speed);
            
    }
    
    public void OnCollisionEnter(final CollisionInfo info)
    {
        String name = info.other.get().getName();
        
        if (name.equals("Player1") || name.equals("Player2"))
        {
            //TODO: pos reflect behaviour
            float diff = (getTransform().get().getPosition().x - info.other.get().getTransform().get().getPosition().x)/(6f);
            
            m_Rigidbody.clearForces();
            
            Vector2 forceBuffer = new Vector2();
            forceBuffer.x = diff;
            forceBuffer.y = (1-Math.abs(diff)); 

            if (name.equals("Player2"))
                forceBuffer.y *=-1f;
            
            m_Rigidbody.applyForce(forceBuffer.multiplyInPlace(1E3f));
        
        }
        
    }
    
    public void OnCollisionExit(final CollisionInfo info)
    {
        m_Speed += 0.1f;
                
    }

    //
    //
    //
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}
