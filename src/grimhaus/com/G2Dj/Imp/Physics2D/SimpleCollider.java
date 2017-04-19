/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Physics2D;

import grimhaus.com.G2Dj.Imp.Graphics.Color;
import grimhaus.com.G2Dj.Type.Graphics.LineVisualizer;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 * @param <T>
 */
public abstract class SimpleCollider<T extends Shape> extends Collider
{
    //*************
    // Data members
    //*************
    protected final FixtureDef m_FixtureDefinition = new FixtureDef();
    protected final T          m_Shape;
    protected LineVisualizer   m_LineVisualizer;    
    
    //**********
    // Accessors
    //**********
    @Override public FixtureDef[] getB2DFixtures(){return new FixtureDef[]{m_FixtureDefinition};}
    
    //
    // Collider interface
    //
    @Override
    protected void buildShape()
    {
        m_FixtureDefinition.density = m_Density;
        m_FixtureDefinition.friction = m_Friction;
        m_FixtureDefinition.restitution = m_Restitution;
        m_FixtureDefinition.setSensor(m_ColliderType.toB2TriggerBool());
        
        if (getDrawDebugLines())
        {
            m_LineVisualizer = (LineVisualizer)getGameObject().get().addComponent(LineVisualizer.class);
            
            if (!m_FixtureDefinition.isSensor)
                m_LineVisualizer.setColor(Color.Green());
            else
                m_LineVisualizer.setColor(Color.DarkGreen());
        
        }
        
    }
    
    public SimpleCollider(final T aShape)
    {
        m_FixtureDefinition.shape = m_Shape = aShape;
    
    }
    
}
