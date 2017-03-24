/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Physics2D;

import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.LineVisualizer;
import java.lang.ref.WeakReference;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 * @param <T>
 */
public abstract class SimpleCollider<T extends Shape> extends Collider
{
    protected final FixtureDef m_FixtureDefinition = new FixtureDef();
    protected final T          m_Shape;
    protected LineVisualizer m_LineVisualizer;    
    
    @Override
    public FixtureDef[] getB2DFixtures(){return new FixtureDef[]{m_FixtureDefinition};}
    
    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        super.OnAddedToGameObject(aGameObject);
        
        m_LineVisualizer = (LineVisualizer)getGameObject().get().addComponent(LineVisualizer.class);
        
    }
    public SimpleCollider(final T aShape)
    {
        m_Shape = aShape;
        m_FixtureDefinition.shape = aShape;
    
    }
    
}
