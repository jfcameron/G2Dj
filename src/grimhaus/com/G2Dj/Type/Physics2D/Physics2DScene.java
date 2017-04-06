/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Engine.SceneGraph;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
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
    private final ArrayList<WeakReference<Rigidbody>> m_Rigidbodies = new ArrayList<>();
    private final Body m_WorldOriginBody;
    
    //buffers
    private final Vec2 b_B2Vec2 = new Vec2();
    
    //
    // Accessors
    //
    public Body getB2DWorldOriginBody(){return m_WorldOriginBody;}
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

    @Override protected void OnComponentAdded(Component aComponent){}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
    //************
    // Constructor
    //************
    public Physics2DScene(WeakReference<Scene> aScene)
    {
        super(aScene/*,new SceneEventCallbacks()*/);
        m_B2DWorld.setContactListener(new GlobalContactListener());
        
        //Build origin body (used for rigidbody axis locking etc)
        BodyDef bodyDef = new BodyDef();
        bodyDef.angle = 0;
        bodyDef.position = new Vec2(0,0);
        bodyDef.type = BodyType.STATIC;
        m_WorldOriginBody = m_B2DWorld.createBody(bodyDef);
        
    }
    
    //
    // Translates Box2D collison events into G2Dj On Collision/Trigger Enter/Exit events
    //
    private class GlobalContactListener implements ContactListener
    {
        //
        // ContactListener interface
        //
        @Override public void beginContact(Contact contact){handleContact(true,contact);}
        @Override public void endContact(Contact contact){handleContact(false,contact);}
        
        @Override public void preSolve(Contact contact, Manifold oldManifold){}
        @Override public void postSolve(Contact contact, ContactImpulse impulse){}
        
        //***************
        // Implementation
        //***************
        private void handleContact(final boolean isBegin, final Contact contact)
        {
            WeakReference<GameObject> 
                gameObjectA = ((Rigidbody)((WeakReference)contact.m_fixtureA.getUserData()).get()).getGameObject(), 
                gameObjectB = ((Rigidbody)((WeakReference)contact.m_fixtureB.getUserData()).get()).getGameObject();
            
            Vector2 collisionNormal = new Vector2(contact.m_manifold.localNormal);
            Vector2 collisionPoint = new Vector2(contact.m_manifold.localPoint);
            
            CollisionInfo collisionInfoA = new CollisionInfo(gameObjectB,collisionNormal,collisionPoint);
            CollisionInfo collisionInfoB = new CollisionInfo(gameObjectA,collisionNormal,collisionPoint);
            
            if (contact.m_fixtureA.isSensor() | contact.m_fixtureB.isSensor())
            {
                if (isBegin)
                {
                    invokeOnTriggerEnter(((Rigidbody)((WeakReference)contact.m_fixtureA.getUserData()).get()).getGameObject().get(),collisionInfoA);
                    invokeOnTriggerEnter(((Rigidbody)((WeakReference)contact.m_fixtureB.getUserData()).get()).getGameObject().get(),collisionInfoB);
                
                }
                else
                {
                    invokeOnTriggerExit(((Rigidbody)((WeakReference)contact.m_fixtureA.getUserData()).get()).getGameObject().get(),collisionInfoA);
                    invokeOnTriggerExit(((Rigidbody)((WeakReference)contact.m_fixtureB.getUserData()).get()).getGameObject().get(),collisionInfoB);
                    
                }
                
            }
            else
            {
                if (isBegin)
                {
                    invokeOnCollisionEnter(((Rigidbody)((WeakReference)contact.m_fixtureA.getUserData()).get()).getGameObject().get(),collisionInfoA);
                    invokeOnCollisionEnter(((Rigidbody)((WeakReference)contact.m_fixtureB.getUserData()).get()).getGameObject().get(),collisionInfoB);
                    
                }
                else
                {
                    invokeOnCollisionExit(((Rigidbody)((WeakReference)contact.m_fixtureA.getUserData()).get()).getGameObject().get(),collisionInfoA);
                    invokeOnCollisionExit(((Rigidbody)((WeakReference)contact.m_fixtureB.getUserData()).get()).getGameObject().get(),collisionInfoB);
                    
                }
                
            }
            
            
        }
        
        private void invokeOnCollisionExit(final GameObject aGameObject, final CollisionInfo aCollisionInfo)
        {
            invokeCollisionCallback("OnCollisionExit",aGameObject,aCollisionInfo);
            
        }
        
        private void invokeOnTriggerExit(final GameObject aGameObject, final CollisionInfo aCollisionInfo)
        {
            invokeCollisionCallback("OnTriggerExit",aGameObject,aCollisionInfo);
            
        }
        
        private void invokeOnCollisionEnter(final GameObject aGameObject, final CollisionInfo aCollisionInfo)
        {
            invokeCollisionCallback("OnCollisionEnter",aGameObject,aCollisionInfo);
            
        }
        
        private void invokeOnTriggerEnter(final GameObject aGameObject, final CollisionInfo aCollisionInfo)
        {
            invokeCollisionCallback("OnTriggerEnter",aGameObject,aCollisionInfo);
            
        }
                
        private void invokeCollisionCallback(final String aCallbackName,final GameObject aGameObject, final CollisionInfo aCollisionInfo)
        {
            for(int i=0,s=aGameObject.getComponentCount();i<s;i++)
            {
                try 
                {
                    aGameObject.getComponent(i).getClass().getMethod(aCallbackName,CollisionInfo.class).invoke(aGameObject.getComponent(i),aCollisionInfo);
                
                } 
                catch (NoSuchMethodException ex){} 
                catch (SecurityException ex){} 
                catch (IllegalArgumentException ex){} 
                catch (InvocationTargetException ex) {Logger.getLogger(Physics2DScene.class.getName()).log(Level.SEVERE, null, ex);} 
                catch (IllegalAccessException ex) {Logger.getLogger(Physics2DScene.class.getName()).log(Level.SEVERE, null, ex);} 
                
            }
            
        }    
        
    }
    
}
