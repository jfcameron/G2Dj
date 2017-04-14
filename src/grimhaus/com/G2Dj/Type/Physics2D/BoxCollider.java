/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Imp.Physics2D.SimpleCollider;
import grimhaus.com.G2Dj.Type.Graphics.LineVisualizer;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

/**
 *
 * @author Joseph Cameron
 */
public class BoxCollider extends SimpleCollider<PolygonShape>
{
    //
    // Implementation
    //    
    @Override
    protected void buildShape()
    {
        Vector3 scale = getGameObject().get().getTransform().get().getScale();
        
        final float hx = 0.5f;
        final float hy = 0.5f;
        final int m_count = 4;
        
        Vec2[] m_vertices = new Vec2[m_count];
        
        for(int i=0,s=m_count;i<s;i++)
            m_vertices[i] = new Vec2();
        
        m_vertices[0].set((-hx +m_Offset.x)*scale.x, (-hy +m_Offset.y)*scale.z);
        m_vertices[1].set(( hx +m_Offset.x)*scale.x, (-hy +m_Offset.y)*scale.z);
        m_vertices[2].set(( hx +m_Offset.x)*scale.x, ( hy +m_Offset.y)*scale.z);
        m_vertices[3].set((-hx +m_Offset.x)*scale.x, ( hy +m_Offset.y)*scale.z);
        
        m_Shape.set(m_vertices, m_count);
                
        m_Shape.m_centroid.set(b_Vec2Buffer.set((m_Offset.x),(m_Offset.y)));

        
        super.buildShape();
        
        if (m_LineVisualizer != null)
            m_LineVisualizer.setVertexData(LineVisualizer.lineBox((m_Offset.x*scale.x)/scale.x,(m_Offset.y*scale.z)/scale.z,1));
        
        //m_FixtureDefinition.density = 1;
        
        
    }

    //
    // Constructors
    //
    public BoxCollider(){super(new PolygonShape());}
    
}
