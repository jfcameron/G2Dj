/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Imp.Physics2D.Collider;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public class CompositeCollider extends Collider
{
    protected FixtureDef[] m_FixtureDefinition;
    protected Shape[]      m_Shape;
    
    @Override
    public FixtureDef[] getB2DFixtures()
    {
        if (m_FixtureDefinition == null || m_Shape == null)
            return null;
        
        return null;        
        
    }

    @Override
    protected void buildShape() 
    {
        
        
    }
    
    
}
