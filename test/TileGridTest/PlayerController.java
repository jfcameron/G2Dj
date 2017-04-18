/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package TileGridTest;

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
    
    //buffers
    private final Vector2 b_InputBuffer   = Vector2.Zero();
    //const
    private static final float s_TranslationSpeed = 1E3f;

    @Override
    protected void initialize() 
    {
        
        SpriteSheet spriteSheet = (SpriteSheet)getGameObject().get().addComponent(SpriteSheet.class);
        spriteSheet.setSpriteSheet("Blocky.png", 16, 17);
        spriteSheet.setCurrentCell(0, 0);
        
        getGameObject().get().addComponent(BoxCollider.class);
        
        Rigidbody rb = (Rigidbody)getGameObject().get().addComponent(Rigidbody.class);
        rb.freezeRotation(true);
        
        m_Rigidbody = rb;
        
    }

    @Override
    protected void update() 
    {
        b_InputBuffer.zero();
        
        if (Input.getKey(KeyCode.A))
            b_InputBuffer.x += 1;
        
        if (Input.getKey(KeyCode.D))
            b_InputBuffer.x -= 1;
       
        b_InputBuffer.multiplyInPlace(s_TranslationSpeed);
        b_InputBuffer.multiplyInPlace((float)Time.getDeltaTime());

        m_Rigidbody.applyForce(b_InputBuffer.x,b_InputBuffer.y);
                        
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
