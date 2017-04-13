/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Pong;

import grimhaus.com.G2Dj.Imp.Engine.RequireComponents;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.GameObject.Transform;
import grimhaus.com.G2Dj.Type.Graphics.Mesh;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Physics2D.AxisFreezeMode;
import grimhaus.com.G2Dj.Type.Physics2D.BoxCollider;
import grimhaus.com.G2Dj.Type.Physics2D.Rigidbody;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
@RequireComponents({BoxCollider.class,Rigidbody.class})
public class AIPaddleController extends Component
{
    //
    //
    //
    private Rigidbody m_Rigidbody = null;
    private WeakReference<Transform> m_Transform = null;
    private WeakReference<Transform> m_Ball = null;
    private WeakReference<GameObject> m_Graphic = null;
    //buffers
    private final Vector2 b_InputBuffer = Vector2.Zero();
    //const
    private static final float s_TranslationSpeed = 1E3f;
    
    //
    //
    //
    @Override
    protected void initialize() 
    {
        m_Transform = getGameObject().get().getTransform();
        
        m_Rigidbody = (Rigidbody)getGameObject().get().getComponent(Rigidbody.class);
        m_Rigidbody.setFrozenRotation(true);
        m_Rigidbody.freezeAxis(AxisFreezeMode.X);
        
        m_Ball = getGameObject().get().getScene().get().getGameObject(Constants.BallName).get().getTransform();
        
        //Init graphic
        m_Graphic = getGameObject().get().getScene().get().addGameObject();
        m_Graphic.get().getTransform().get().setRotation(-90,180,0);
        m_Graphic.get().getTransform().get().setPosition(0,-1,0);
        m_Graphic.get().getTransform().get().setScale(6.5f,4.5f,1f);
        Mesh mesh = (Mesh)m_Graphic.get().addComponent(Mesh.class);
        mesh.setTexture("_Texture", "Paddle.png");
        
    }

    @Override
    protected void update() 
    {
        m_Graphic.get().getTransform().get().setPosition(getTransform().get().getPosition());
        
    }

    @Override
    protected void fixedUpdate() 
    {
        b_InputBuffer.zero();
        
        if (m_Transform.get().getPosition().x < m_Ball.get().getPosition().x-0.5f)
            b_InputBuffer.x += 1;
        
        if (m_Transform.get().getPosition().x > m_Ball.get().getPosition().x+0.5f)
            b_InputBuffer.x -= 1;
       
        b_InputBuffer.multiplyInPlace(s_TranslationSpeed);
        m_Rigidbody.applyForce(b_InputBuffer.x,b_InputBuffer.y);
        
    }

    //
    //
    //
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}
