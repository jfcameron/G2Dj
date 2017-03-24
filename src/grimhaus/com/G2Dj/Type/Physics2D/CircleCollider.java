/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Type.Graphics.LineVisualizer;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public class CircleCollider extends Component implements Collider
{
    //*************
    // Data members
    //*************
    private       LineVisualizer m_LineVisualizer;
    private final FixtureDef     m_FixtureDefinition = new FixtureDef();
    private final CircleShape    m_Shape             = new CircleShape();
    private final Vector2        m_Offset            = new Vector2();
    
    private boolean m_RebuildShape = false;
    
    //buffers
    private Vector3 b_ScaleBuffer;
    
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
        
        float scale = getGameObject().get().getTransform().get().getScale().x;
        
        m_Shape.setRadius(scale/2);
        
        for(int i=0,s=m_Shape.getVertexCount();i<s;i++)
            m_Shape.getVertex(i).addLocal(m_Offset.x,m_Offset.y);
        
        m_Shape.m_p.set(m_Offset.x*scale,m_Offset.y*scale);
        
        m_LineVisualizer.setVertexData(LineVisualizer.lineCircle((m_Offset.x*scale)/scale,(m_Offset.y*scale)/scale,1));
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
    public CircleCollider(){m_FixtureDefinition.shape = m_Shape;}

    @Override
    protected void initialize() 
    {
        m_RebuildShape = true;
        
    }
    
}
