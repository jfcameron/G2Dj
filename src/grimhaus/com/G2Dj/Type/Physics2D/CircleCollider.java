/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public class CircleCollider extends Component implements Collider
{
    //
    //
    //
    private       LineVisualizer m_LineVisualizer;
    private final FixtureDef     m_FixtureDefinition = new FixtureDef();
    private final CircleShape    m_Shape = new CircleShape();
    
    //
    //
    //
    @Override public FixtureDef getB2DFixture(){return m_FixtureDefinition;}
    
    //
    //
    //
    public CircleCollider(){m_FixtureDefinition.shape = m_Shape;}
    
    private void buildShape()
    {
        Vector3 scale = getGameObject().get().getTransform().get().getScale();
        m_Shape.setRadius(scale.x/2);
        m_FixtureDefinition.density = 1;
        
        
        
    }

    Vector3 b_ScaleBuffer;
    
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
        buildShape();
        
        m_LineVisualizer = (LineVisualizer)getGameObject().get().addComponent(LineVisualizer.class);
        m_LineVisualizer.setVertexData(LineVisualizer.lineCircle());
        
    }

    @Override
    protected void OnRemovedFromGameObject() {}

    @Override
    protected void OnComponentAdded(Component aComponent) {}

    @Override
    protected void OnComponentRemoved(Component aComponent) {}
    
}
