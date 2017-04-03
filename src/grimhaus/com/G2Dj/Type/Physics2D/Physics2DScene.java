/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Engine.SceneEventCallbacks;
import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Engine.SceneGraph;
import java.lang.ref.WeakReference;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

/**
 *
 * @author Joseph Cameron
 */
public class Physics2DScene extends SceneGraph 
{
    //
    // Data members
    //
    private final World m_B2DWorld = new World(new Vec2(0,0));
    private final float c_UpdateInterval = (float)Time.getFixedUpdateTargetInterval();
    
//buffers
    private final Vec2 b_B2Vec2 = new Vec2();
    
    //
    // Accessors
    //
    public World getB2DWorld(){return m_B2DWorld;}    
    public void setGravity(final float aX, final float aY){m_B2DWorld.setGravity(b_B2Vec2.set(aX,aY));}
    
    //
    //
    //
    public void raycastTest()
    {
        /*RayCastOutput callback = new RayCastOutput();
        m_B2DWorld.raycast(callback, b_B2Vec2, b_B2Vec2);*/
        
    }
    
    //
    //
    //
    @Override
    public void fixedUpdate() 
    {
        m_B2DWorld.step(c_UpdateInterval, 1, 1);
        
    }
    
    @Override public void update(){}
    @Override public void draw(){}

    @Override
    protected void OnComponentAdded(Component aComponent) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) {}
    
    //************
    // Constructor
    //************
    public Physics2DScene(WeakReference<Scene> aScene)
    {
        super(aScene/*,new SceneEventCallbacks()*/);
        m_B2DWorld.setContactListener(new GlobalContactListener());
        
    }
    
    //
    // Handles all contact events in the world.
    //
    private class GlobalContactListener implements ContactListener
    {

        @Override
        public void beginContact(Contact contact) 
        {
            Debug.log("***begin contact***");
            Debug.log
            (
                ((Rigidbody)((WeakReference)contact.m_fixtureA.getUserData()).get()).getGameObject().get().getName(),
                ((Rigidbody)((WeakReference)contact.m_fixtureB.getUserData()).get()).getGameObject().get().getName()
            
            );
            
            
            
            //Detect type
            boolean isSensor = contact.m_fixtureA.isSensor() | contact.m_fixtureB.isSensor();
            
            Debug.log("Collision type is: "+((isSensor==true)?"Trigger":"Collider"));
            
            //TODO: distribute col data to affected gameobjects
            
        }

        @Override
        public void endContact(Contact contact) 
        {
            
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) 
        {
            
        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) 
        {
            
        }
        
    }
    
}
