/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Imp.Physics2D.Collider;
import grimhaus.com.G2Dj.Imp.Physics2D.ColliderType;
import grimhaus.com.G2Dj.Type.Graphics.LineVisualizer;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public class CompositeCollider extends Collider
{
    //*************
    // Data members
    //*************
    private   Vector2[][]      m_VertexArrays = new Vector2[][]{};//good
    protected FixtureDef[]     m_FixtureDefinitions;
    protected LineVisualizer[] m_LineVisualizers; 
        
    //
    // Accessors
    //
    @Override public void setType(final ColliderType aColliderType){for(int i=0,s=m_FixtureDefinitions.length;i<s;i++)m_FixtureDefinitions[i].setSensor(aColliderType.toB2TriggerBool());}
    @Override public void setDensity(final float aDensity){for(int i=0,s=m_FixtureDefinitions.length;i<s;i++)m_FixtureDefinitions[i].setDensity(aDensity);}
    @Override public void setFriction(final float aFriction){for(int i=0,s=m_FixtureDefinitions.length;i<s;i++)m_FixtureDefinitions[i].setFriction(aFriction);}
    @Override public void setRestitution(final float aRestitution){for(int i=0,s=m_FixtureDefinitions.length;i<s;i++)m_FixtureDefinitions[i].setRestitution(aRestitution);}
    
    public void setVerticies(final Vector2[][] aCounterClockwiseVerticies)
    {
        requestShapeRebuildOnNextTick();
        m_VertexArrays = aCounterClockwiseVerticies;
        
        //Init/Reinit Fixtures
        {
            m_FixtureDefinitions = new FixtureDef[m_VertexArrays.length];
            for(int i=0,s=m_FixtureDefinitions.length;i<s;i++)
            {
                m_FixtureDefinitions[i] = new FixtureDef();
                m_FixtureDefinitions[i].shape =  new PolygonShape();
        
            }
                
        }
        
        //Init/Reinit Visualizers
        {
            //destroy visualizers
            if (m_LineVisualizers!=null)
                for(int i=0,s=m_LineVisualizers.length;i<s;i++)
                    getGameObject().get().removeComponent(m_LineVisualizers[i]);
            
            //create visualizers
            m_LineVisualizers = new LineVisualizer[m_VertexArrays.length];
            for(int i=0,s=m_VertexArrays.length;i<s;i++)
                m_LineVisualizers[i] = (LineVisualizer)getGameObject().get().addComponent(LineVisualizer.class);
        
        }
    
    }
    
    @Override public FixtureDef[] getB2DFixtures(){return m_FixtureDefinitions;}
    @Override public ColliderType getType(){return ColliderType.fromB2TriggerBool(m_FixtureDefinitions[0].isSensor);}
    @Override public float getDensity(){return m_FixtureDefinitions[0].getDensity();}
    @Override public float getFriction(){return m_FixtureDefinitions[0].getFriction();}
    @Override public float getRestitution(){return m_FixtureDefinitions[0].getRestitution();}
    
    //
    // Collider implementation
    //
    @Override
    protected void buildShape()
    {
        if (m_VertexArrays != null)
            for(int i=0,s=m_VertexArrays.length;i<s;i++)
                if (m_VertexArrays[i] != null)
                    buildAFixture(m_VertexArrays[i],m_LineVisualizers[i],m_FixtureDefinitions[i]);        
        
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
    
    private void buildAFixture(Vector2[] m_Vertices, LineVisualizer m_LineVisualizer, FixtureDef m_FixtureDefinition)
    {
        Vec2[] b2verts;
        PolygonShape m_Shape = (PolygonShape)m_FixtureDefinition.shape;
                
        Vector3 scale = getGameObject().get().getTransform().get().getScale();
        
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
                
        m_LineVisualizer.setVertexData(visualVerts);
        m_FixtureDefinition.density = 1;
        
    }
    
}
