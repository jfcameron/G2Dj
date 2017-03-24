/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Imp.Physics2D.Collider;
import grimhaus.com.G2Dj.Type.Graphics.LineVisualizer;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public class CompositeCollider extends Collider
{
    protected FixtureDef[]     m_FixtureDefinition;
    protected LineVisualizer[] m_LineVisualizer;    
    protected PolygonShape[]   m_Shape;
    
    @Override
    public FixtureDef[] getB2DFixtures()
    {
        if (m_FixtureDefinition == null)
            return null;
        
        return m_FixtureDefinition;        
        
    }

    @Override
    protected void buildShape() 
    {
        ///TEST AREA
        //m_Shape = new PolygonShape[]{};
        //m_Shape[0].set
        
        ///ENDOFTSETAREA
        if (m_FixtureDefinition==null || m_Shape==null || m_LineVisualizer==null)
            return;
        
        for(int i=0,s=m_FixtureDefinition.length;i<s;i++)
            if (m_Shape[i] != null)
                m_FixtureDefinition[i].setShape(m_Shape[i]);
        
    }
    
    
}
