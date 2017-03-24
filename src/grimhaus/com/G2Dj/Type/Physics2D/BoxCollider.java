/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Type.Graphics.LineVisualizer;
import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public class BoxCollider extends Component implements Collider
{
    //*************
    // Data members
    //*************
    private       LineVisualizer m_LineVisualizer;
    private final FixtureDef     m_FixtureDefinition = new FixtureDef();
    private final PolygonShape   m_Shape             = new PolygonShape();
    private final Vector2        m_Offset            = new Vector2();
    
    private boolean m_RebuildShape = false;
    
    //buffers
    private Vector3 b_ScaleBuffer;
    private final Vec2 b_Vec2Buffer = new Vec2();
    
    //
    // Accessors
    //
    //public void rebuildShape(){}
    
    //
    // Implementation
    //    
    private void buildShape()
    {
        m_RebuildShape = false; 
        
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

        m_LineVisualizer.setVertexData(LineVisualizer.lineBox((m_Offset.x*scale.x)/scale.x,(m_Offset.y*scale.z)/scale.z,1));
        m_FixtureDefinition.density = 1;
        
    }
    
    //
    // Component interface
    //
    @Override
    public void update() 
    {
        checkForTransformScaleChange();
        
        if (m_RebuildShape)
            buildShape();
        
    }
    
    private void checkForTransformScaleChange()
    {
        Vector3 scale = getTransform().get().getScale();
        
        if (b_ScaleBuffer == null)
        {
            b_ScaleBuffer = new Vector3();
            b_ScaleBuffer.copy(scale);
        
        }
                    
        if (!b_ScaleBuffer.equals(scale))
            m_RebuildShape = true;
        
        b_ScaleBuffer.copy(scale);
        
    }

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        m_LineVisualizer = (LineVisualizer)getGameObject().get().addComponent(LineVisualizer.class);
        m_RebuildShape = true;
        
    }

    @Override 
    protected void OnRemovedFromGameObject() {}

    @Override 
    protected void OnComponentAdded(Component aComponent) {}

    @Override 
    protected void OnComponentRemoved(Component aComponent) {}
    
    @Override 
    public FixtureDef getB2DFixture(){return m_FixtureDefinition;}
    
    @Override
    public void setOffset(final float aX, final float aY)
    {
        m_Offset.setInPlace(aX, aY);
        m_RebuildShape = true;
        
    }
    
    //
    // Constructors
    //
    public BoxCollider(){m_FixtureDefinition.shape = m_Shape;}

    @Override
    protected void initialize() 
    {
        m_RebuildShape = true;
        
    }
    
}
