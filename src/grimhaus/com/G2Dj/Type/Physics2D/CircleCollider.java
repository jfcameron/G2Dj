/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Imp.Physics2D.SimpleCollider;
import grimhaus.com.G2Dj.Type.Graphics.LineVisualizer;
import org.jbox2d.collision.shapes.CircleShape;

/**
 *
 * @author Joseph Cameron
 */
public class CircleCollider extends SimpleCollider<CircleShape>
{
    //
    // Implementation
    //    
    @Override
    protected void buildShape()
    {   
        
        
        float scale = getGameObject().get().getTransform().get().getScale().x;
        
        m_Shape.setRadius(scale/2);
        
        for(int i=0,s=m_Shape.getVertexCount();i<s;i++)
            m_Shape.getVertex(i).addLocal(m_Offset.x,m_Offset.y);
        
        m_Shape.m_p.set(m_Offset.x*scale,m_Offset.y*scale);
        
        super.buildShape();
        
        if (m_LineVisualizer != null)
            m_LineVisualizer.setVertexData(LineVisualizer.lineStripCircle((m_Offset.x*scale)/scale,(m_Offset.y*scale)/scale,1));
        
        
                
    }
    
    //
    // Constructors
    //
    public CircleCollider(){super(new CircleShape());}
    
}
