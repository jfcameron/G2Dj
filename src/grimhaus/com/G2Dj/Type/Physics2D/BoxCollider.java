/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

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
    
    //buffers
    private Vector3 b_ScaleBuffer;
    private final Vec2 b_Vec2Buffer = new Vec2();
    
    //
    // Accessors
    //
    
    //
    // Implementation
    //    
    private void buildShape()
    {
        Vector3 scale = getGameObject().get().getTransform().get().getScale();
        m_Shape.setAsBox(scale.x/2,scale.z/2,b_Vec2Buffer.set(m_Offset.x,m_Offset.y),0);
        m_FixtureDefinition.density = 1;
        m_LineVisualizer.setVertexData(LineVisualizer.lineBox(m_Offset.x/scale.x,m_Offset.y/scale.z,1));
        
    }
    
    //
    // Component interface
    //
    @Override
    public void update() 
    {
        Vector3 scale = getTransform().get().getScale();
        
        if (b_ScaleBuffer == null)
        {
            b_ScaleBuffer = new Vector3();
            b_ScaleBuffer.copy(scale);
        
        }
                    
        if (!b_ScaleBuffer.equals(scale))
            buildShape();
        
        b_ScaleBuffer.copy(scale);
    
    }

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        m_LineVisualizer = (LineVisualizer)getGameObject().get().addComponent(LineVisualizer.class);
        buildShape();
        
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
        buildShape();
        
    }
    
    //
    // Constructors
    //
    public BoxCollider(){m_FixtureDefinition.shape = m_Shape;}
    
}
