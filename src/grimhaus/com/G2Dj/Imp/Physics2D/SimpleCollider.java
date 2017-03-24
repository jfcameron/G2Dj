/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Physics2D;

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
    
    @Override
    public FixtureDef[] getB2DFixtures(){return new FixtureDef[]{m_FixtureDefinition};}
    
    public SimpleCollider(final T aShape)
    {
        m_Shape = aShape;
        m_FixtureDefinition.shape = aShape;
    
    }
    
}
