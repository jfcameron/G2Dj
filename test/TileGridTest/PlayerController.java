/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TileGridTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Engine.RequireComponents;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.SpriteSheet;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Physics2D.BoxCollider;
import grimhaus.com.G2Dj.Type.Physics2D.Rigidbody;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
@RequireComponents({Rigidbody.class})
public class PlayerController extends Component
{
    //
    //
    //
    private Rigidbody m_Rigidbody = null;
    private SpriteSheet m_SpriteSheet = null;
    private float m_AnimationTimer = 0;
    
    //buffers
    private final Vector2 b_InputBuffer   = Vector2.Zero();
    //const
    private static final float s_TranslationSpeed = 1E3f;

    @Override
    protected void initialize() 
    {
        getGameObject().get().addComponent(BoxCollider.class);
        
        m_SpriteSheet = (SpriteSheet)getGameObject().get().addComponent(SpriteSheet.class);
        m_SpriteSheet.setSpriteSheet("Blocky.png", 16, 17);
        m_SpriteSheet.setCurrentCell(0, 0);
        
        m_Rigidbody = (Rigidbody)getGameObject().get().addComponent(Rigidbody.class);
        m_Rigidbody.freezeRotation(true);
        m_Rigidbody.setLinearDamping(0);
                
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
       
        b_InputBuffer.multiplyInPlace(s_TranslationSpeed);
        b_InputBuffer.multiplyInPlace((float)Time.getDeltaTime());

        m_Rigidbody.applyForce(b_InputBuffer.x,b_InputBuffer.y);
        
        ////////////////////////////////////////////////////////////////////////////////
        m_AnimationTimer += Time.getDeltaTime()*Math.abs(m_Rigidbody.getVelocity().x);
                
        if (m_AnimationTimer > 1)
        {
            m_SpriteSheet.setCurrentCell(0, 0);
            
            if (m_AnimationTimer > 2)
            {
                m_SpriteSheet.setCurrentCell(1, 0);
                m_AnimationTimer = 0;
            
            }
            
        }
        
        if (Math.abs(m_Rigidbody.getVelocity().x) < 0.1f)
        {
            m_SpriteSheet.setCurrentCell(1, 0);
            m_AnimationTimer=0;
            
        }
                                
    }

    @Override
    protected void fixedUpdate() {
    }

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {
    }

    @Override
    protected void OnRemovedFromGameObject() {
    }

    @Override
    protected void OnComponentAdded(Component aComponent) {
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) {
    }
    
}
