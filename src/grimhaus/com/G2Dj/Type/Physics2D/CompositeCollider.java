/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Imp.Physics2D.Collider;
import grimhaus.com.G2Dj.Type.Graphics.LineVisualizer;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public class CompositeCollider extends Collider
{
    protected FixtureDef[]     m_FixtureDefinition;
    protected LineVisualizer[] m_LineVisualizer;    
    
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
        //TEST AREA
        int len = 3;
        m_FixtureDefinition = new FixtureDef    [len];
        m_LineVisualizer    = new LineVisualizer[len]; 
        
        for(int i=0,s=len;i<s;i++)
        {
            //m_LineVisualizer[i] = (LineVisualizer)getGameObject().get().addComponent(LineVisualizer.class);
            
            m_FixtureDefinition[i] = new FixtureDef();
            
            m_FixtureDefinition[i].setShape(new PolygonShape());
            
            
            m_FixtureDefinition[i].density = 1;
            
        }
        
    }
    
    
}
