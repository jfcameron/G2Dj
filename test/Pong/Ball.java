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
    
    private static float s_Speed = 1E3f;
    
    //
    //
    //
    @Override protected void initialize() 
    {
        m_Rigidbody = (Rigidbody)getGameObject().get().getComponent(Rigidbody.class);
    
        m_Rigidbody.setLinearDamping(0);
        m_Rigidbody.setAngularDamping(0);
        
        m_Rigidbody.applyForce(new Vector2(2,-0.5f).unit().multiply(s_Speed));
        
        CircleCollider cc = (CircleCollider)getGameObject().get().getComponent(CircleCollider.class);
        cc.setRestitution(1.0f);
        
    }

    @Override protected void update() 
    {
        
    
    }

    @Override protected void fixedUpdate() 
    {
            
    }
    
    public void OnCollisionEnter(final CollisionInfo info)
    {
        
    }

    //
    //
    //
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}
