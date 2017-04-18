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
import org.jbox2d.collision.shapes.EdgeShape;
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
    private FixtureDef[]             m_FixtureDefinitions      = null;
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
        m_FixtureDefinitions = new FixtureDef[m_Data.length*4];
        for(int i=0,s=m_FixtureDefinitions.length;i<s;i++)
        {
            m_FixtureDefinitions[i] = new FixtureDef();
            m_FixtureDefinitions[i].shape = new EdgeShape();  //new PolygonShape();
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
                m_LineVisualizers = new LineVisualizer[m_Data.length*4];
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
                        int index = (y*m_DataWidth) +x;
                        
                        LineVisualizer visN = null, visE = null, visS = null, visW = null;
                        
                        if (m_LineVisualizers != null)
                        {
                            visN = m_LineVisualizers[index+0];
                            visE = m_LineVisualizers[index+1];
                            visS = m_LineVisualizers[index+2];
                            visW = m_LineVisualizers[index+3];
                                    
                        }
                        
                        // BUG NOTE: these each work individually but not all at ocne
                        
                        
                        buildAFixture(x,y,m_GridColliderDefinitions[m_Data[index]].northSurface,visN,m_FixtureDefinitions[index+0]);//n
                        buildAFixture(x,y,m_GridColliderDefinitions[m_Data[index]].eastSurface ,visE,m_FixtureDefinitions[index+1]);//e
                        buildAFixture(x,y,m_GridColliderDefinitions[m_Data[index]].southSurface,visS,m_FixtureDefinitions[index+2]);//s
                        buildAFixture(x,y,m_GridColliderDefinitions[m_Data[index]].westSurface ,visW,m_FixtureDefinitions[index+3]);//w   
        
                    }
        
        //Debug.log("ASFFASAF: "+m_LineVisualizers);
           
    }
    
    private void buildAFixture(final int aTileX, final int aTileY, Vector2[] m_Vertices, LineVisualizer aLineVisualizer, FixtureDef m_FixtureDefinition)
    {
        Vec2[] b2verts;
        EdgeShape currentShape = (EdgeShape)m_FixtureDefinition.shape;//PolygonShape m_Shape = (PolygonShape)m_FixtureDefinition.shape;
                
        Vector3 scale = getGameObject().get().getTransform().get().getScale();
        
        //some kind of sanity check?
        if (m_Vertices == null || m_Vertices.length == 0)
        {
            if (aLineVisualizer!=null)
                getGameObject().get().removeComponent(aLineVisualizer);
            
            return;
        
        }
        else
        {
            b2verts = new Vec2[m_Vertices.length];
            
            for(int i=0,s=m_Vertices.length;i<s;i++)
                b2verts[i] = new Vec2((m_Vertices[i].x+m_Offset.x-0.5f+aTileX)*scale.x,(m_Vertices[i].y+m_Offset.y-0.5f+(aTileY-m_DataHeight+1))*scale.z);
            
        }
        
        //HERE
        //create top l
        currentShape.set(b2verts[0],b2verts[1]);//m_Shape.set(b2verts, b2verts.length);
                
        //m_Shape.m_centroid.set(b_Vec2Buffer.set((m_Offset.x),(m_Offset.y)));

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
                
        if (aLineVisualizer != null)
        {
            aLineVisualizer.setVertexData(visualVerts);

        }
            
        m_FixtureDefinition.density = 1;
        
    }
    
}
