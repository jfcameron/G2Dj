/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Pong;

import grimhaus.com.G2Dj.Imp.Engine.RequireComponents;
import grimhaus.com.G2Dj.Imp.Engine.SceneState;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Imp.Physics2D.BodyType;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import grimhaus.com.G2Dj.Type.Physics2D.AxisFreezeMode;
import grimhaus.com.G2Dj.Type.Physics2D.BoxCollider;
import grimhaus.com.G2Dj.Type.Physics2D.Rigidbody;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
@RequireComponents({BoxCollider.class,Rigidbody.class})
public class PlayerPaddleController extends Component
{
    //
    //
    //
    private Rigidbody m_Rigidbody = null;
    //buffers
    private final Vector2 b_InputBuffer = Vector2.Zero();
    //const
    private static final float s_TranslationSpeed = 1E4f;
    
    //
    //
    //
    @Override
    protected void initialize() 
    {
        m_Rigidbody = (Rigidbody)getGameObject().get().getComponent(Rigidbody.class);
        m_Rigidbody.setFrozenRotation(true);
        m_Rigidbody.freezeAxis(AxisFreezeMode.X);
        
        BoxCollider cc = (BoxCollider)getGameObject().get().getComponent(BoxCollider.class);
        cc.setRestitution(0.0f);
        
        
    }

    @Override
    protected void update() 
    {
        b_InputBuffer.zero();
        
        if (Input.getKey(KeyCode.A))
            b_InputBuffer.x += 1;
        
        if (Input.getKey(KeyCode.D))
            b_InputBuffer.x -= 1;
        
        if (Input.getKey(KeyCode.W))
            b_InputBuffer.y += 1;
        
        if (Input.getKey(KeyCode.S))
            b_InputBuffer.y -= 1;
       
        b_InputBuffer.multiplyInPlace(s_TranslationSpeed);
        b_InputBuffer.multiplyInPlace((float)Time.getDeltaTime());

        m_Rigidbody.applyForce(b_InputBuffer.x,b_InputBuffer.y);
                
    }

    @Override
    protected void fixedUpdate() 
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
