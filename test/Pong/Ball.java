/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Pong;

import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Engine.RequireComponents;
import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.Mesh;
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
    private static float s_MaxSpeed = 20f;
    
    private Rigidbody m_Rigidbody = null;
    private ScoreCounter m_ScoreCounter = null;
    private WeakReference<GameObject> m_Graphic = null;    
    
    private float m_Speed = 14f;
    
    //
    //
    //
    private void resetVolley()
    {
        m_Rigidbody.setPosition(0,0,0);
        m_Rigidbody.clearForces();
        
        Vector2 volleyForce = new Vector2(0,-0.75f).unit().multiply(m_Speed);
        
        if (m_ScoreCounter.getScore() % 2 == 0)
            volleyForce.multiplyInPlace(-1f);
        
        m_Rigidbody.applyForce(volleyForce);
        
    }
    
    @Override protected void initialize() 
    {
        //init rigidbody
        m_Rigidbody = (Rigidbody)getGameObject().get().getComponent(Rigidbody.class);
        m_Rigidbody.setLinearDamping(0);
        m_Rigidbody.setAngularDamping(0);
        //init cc
        CircleCollider cc = (CircleCollider)getGameObject().get().getComponent(CircleCollider.class);
        cc.setRestitution(1.0f);
        //init scorecounter
        m_ScoreCounter = (ScoreCounter)Engine.getScene(Constants.GUISceneName).get().getGameObject(Constants.ScoreCounterName).get().getComponent(ScoreCounter.class);
        //Init graphic
        m_Graphic = getGameObject().get().getScene().get().addGameObject();
        m_Graphic.get().getTransform().get().setRotation(-90,180,0);
        m_Graphic.get().getTransform().get().setPosition(0,+1,0);
        m_Graphic.get().getTransform().get().setScale(1.5f,1.5f,1.5f);
        
        Mesh mesh = (Mesh)m_Graphic.get().addComponent(Mesh.class);
        mesh.setTexture("_Texture", "BallGuy.png");
        
        resetVolley();
        
    }

    @Override protected void update() 
    {
        m_Graphic.get().getTransform().get().setPosition(getTransform().get().getPosition());
        //m_Graphic.get().getTransform().get().rotate(0,0,200*(float)Time.getDeltaTime());
    
    }

    @Override protected void fixedUpdate() 
    {
        m_Rigidbody.normalizeVelocity();
        m_Rigidbody.scaleVelocity(m_Speed);
        
        if (Math.abs(getTransform().get().getPosition().z) > 20)
            resetVolley();
            
    }
    
    public void OnCollisionEnter(final CollisionInfo info)
    {
        String name = info.other.get().getName();
        
        if (name.equals(Constants.Player1Name) || name.equals(Constants.Player2Name))
        {
            //TODO: pos reflect behaviour
            float diff = (getTransform().get().getPosition().x - info.other.get().getTransform().get().getPosition().x)/(6f);
            
            m_Rigidbody.clearForces();
            
            Vector2 forceBuffer = new Vector2();
            forceBuffer.x = diff;
            forceBuffer.y = (1-Math.abs(diff)); 

            if (name.equals(Constants.Player1Name))
                forceBuffer.y *=-1f;
            
            m_Rigidbody.applyForce(forceBuffer.multiplyInPlace(1E3f));
        
        }
        
        m_ScoreCounter.incrementScore();
        
    }
    
    public void OnCollisionExit(final CollisionInfo info)
    {
        if (m_Speed<s_MaxSpeed)
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
