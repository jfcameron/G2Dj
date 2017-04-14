/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Imp.Physics2D.SimpleCollider;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

/**
 *
 * @author Joseph Cameron
 */
public class PolygonCollider extends SimpleCollider<PolygonShape>  
{
    //*************
    // Data members
    //*************
    private Vector2[] m_Vertices = new Vector2[]{};
    
    //
    // Accessors
    //
    public void setVerticies(final Vector2[] aCounterClockwiseVerticies){m_Vertices = aCounterClockwiseVerticies;requestShapeRebuildOnNextTick();}
    
    //
    // Collider implementation
    //
    @Override
    protected void buildShape()
    {
        Vector3 scale = getGameObject().get().getTransform().get().getScale();
        
        Vec2[] b2verts;
        
        if (m_Vertices == null || m_Vertices.length == 0)
            b2verts = generateDefaultVertexData();
        else
        {
            b2verts = new Vec2[m_Vertices.length];
            
            for(int i=0,s=m_Vertices.length;i<s;i++)
                b2verts[i] = new Vec2((m_Vertices[i].x+m_Offset.x)*scale.x,(m_Vertices[i].y+m_Offset.y)*scale.z);
            
        }
        
        m_Shape.set(b2verts, b2verts.length);
                
        m_Shape.m_centroid.set(b_Vec2Buffer.set((m_Offset.x),(m_Offset.y)));

        //Generate the line mesh
        float[] visualVerts = new float[(b2verts.length*3)+3];
        for(int i=0,s=b2verts.length,j=0;i<s;++i,j=i*3)
        {
            visualVerts[j+0] = b2verts[i].x/scale.x; 
            visualVerts[j+1] = 0.0f; 
            visualVerts[j+2] = b2verts[i].y/scale.z;
            
        }
        //The first vert has to be repeated due to visualizer using GL_LINE_STRIP not "_LOOP
        visualVerts[visualVerts.length-3] = b2verts[0].x/scale.x; 
        visualVerts[visualVerts.length-2] = 0.0f; 
        visualVerts[visualVerts.length-1] = b2verts[0].y/scale.z;
              
        
        super.buildShape();
        
        
        if (m_LineVisualizer != null)
            m_LineVisualizer.setVertexData(visualVerts);
        
        
    }
    
    //
    // Implementation
    //    
    private Vec2[] generateDefaultVertexData()
    {
        Vector3 scale = getGameObject().get().getTransform().get().getScale();
        
        final float hx = 0.5f;
        final float hy = 0.5f;
        final int m_count = 4;
        
        Vec2[] b2verts = new Vec2[m_count];
        
        for(int i=0,s=m_count;i<s;i++)
            b2verts[i] = new Vec2();
        
        b2verts[0].set((-hx +m_Offset.x)*scale.x, (-hy +m_Offset.y)*scale.z);
        b2verts[1].set(( hx +m_Offset.x)*scale.x, (-hy +m_Offset.y)*scale.z);
        b2verts[2].set(( hx +m_Offset.x)*scale.x, ( hy +m_Offset.y)*scale.z);
        b2verts[3].set((-hx +m_Offset.x)*scale.x, ( hy +m_Offset.y)*scale.z);
        
        return b2verts;
        
    }
    
    //
    // Constructors
    //
    public PolygonCollider(){super(new PolygonShape());}
    
}
