/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Graphics.Color;
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
public class GridCollider extends Collider
{
    //*************
    // Data members
    //*************
    //collider info
    private GridColliderDefinition[] m_GridColliderDefinitions = null;
    private FixtureDef[] m_FixtureDefinitions = null;
    //grid data
    private int m_DataWidth = 0, m_DataHeight = 0;
    private int[] m_Data = null;
    //debug info
    private LineVisualizer[] m_LineVisualizers = null;
    
    //
    //
    //
    public void setColliderDefinitions(final GridColliderDefinition[] aGridColliderDefinitions){m_GridColliderDefinitions=aGridColliderDefinitions;}
    public void setGridData(final int aDataWidth, final int aDataHeight, final int[] aGridData)
    {
        m_DataWidth = aDataWidth;
        m_DataHeight = aDataHeight;   
        m_Data = aGridData;
        requestShapeRebuildOnNextTick();
    
    }
    
    @Override public FixtureDef[] getB2DFixtures(){return m_FixtureDefinitions;}
    
    //
    //
    //
    @Override
    protected void buildShape() 
    {        
        if (m_Data == null)
            return;
            
        //Init/Reinit Fixtures
        m_FixtureDefinitions = new FixtureDef[m_Data.length];
        for(int i=0,s=m_FixtureDefinitions.length;i<s;i++)
        {
            m_FixtureDefinitions[i] = new FixtureDef();
            m_FixtureDefinitions[i].shape =  new PolygonShape();
            m_FixtureDefinitions[i].setDensity(m_Density);
            m_FixtureDefinitions[i].setFriction(m_Friction);
            m_FixtureDefinitions[i].setRestitution(m_Restitution);
            m_FixtureDefinitions[i].setSensor(m_ColliderType.toB2TriggerBool());
        
        }
        
        //Init/Reinit Visualizers
        if (getDrawDebugLines())
        {
            //destroy visualizers
            if (m_LineVisualizers!=null)
                for(int i=0,s=m_LineVisualizers.length;i<s;i++)
                    getGameObject().get().removeComponent(m_LineVisualizers[i]);
            
            //if (m_Data != null)
            {
                //create visualizers
                m_LineVisualizers = new LineVisualizer[m_Data.length];
                for(int i=0,s=m_Data.length;i<s;i++)
                {
                    m_LineVisualizers[i] = (LineVisualizer)getGameObject().get().addComponent(LineVisualizer.class);
                    
                    if (m_ColliderType == ColliderType.Collidable)
                        m_LineVisualizers[i].setColor(Color.Green());
                    else
                        m_LineVisualizers[i].setColor(Color.DarkGreen());
                    
                }
            
            }
        
        }
        
        //craete fixtures m_GridColliderDefinitions
        //if (m_Data != null)
            for(int y=0;y<m_DataHeight;y++)
                for(int x=0;x<m_DataWidth;x++)
                    {
                        LineVisualizer currentVisualizer = null;
                    
                        if (m_LineVisualizers != null && (y*m_DataWidth +x) < m_LineVisualizers.length)
                            currentVisualizer = m_LineVisualizers[(y*m_DataWidth +x)];
                    
                        buildAFixture(x,y,m_GridColliderDefinitions[m_Data[(y*m_DataWidth +x)]].colliderVertexes,currentVisualizer,m_FixtureDefinitions[(y*m_DataWidth +x)]);        
        
                    }
        
        //Debug.log("ASFFASAF: "+m_LineVisualizers);
           
    }
    
    private void buildAFixture(final int aTileX, final int aTileY, Vector2[] m_Vertices, LineVisualizer m_LineVisualizer, FixtureDef m_FixtureDefinition)
    {
        Vec2[] b2verts;
        PolygonShape m_Shape = (PolygonShape)m_FixtureDefinition.shape;
                
        Vector3 scale = getGameObject().get().getTransform().get().getScale();
        
        if (m_Vertices == null || m_Vertices.length == 0)
        {
            if (m_LineVisualizer!=null)
                getGameObject().get().removeComponent(m_LineVisualizer);
            
            return;//b2verts = generateDefaultVertexData();
        
        }
        else
        {
            b2verts = new Vec2[m_Vertices.length];
            
            for(int i=0,s=m_Vertices.length;i<s;i++)
                b2verts[i] = new Vec2((m_Vertices[i].x+m_Offset.x-0.5f+aTileX)*scale.x,(m_Vertices[i].y+m_Offset.y-0.5f+(aTileY-m_DataHeight+1))*scale.z);
            
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
                
        if (m_LineVisualizer != null)
            m_LineVisualizer.setVertexData(visualVerts);
        
        m_FixtureDefinition.density = 1;
        
    }
    
}
