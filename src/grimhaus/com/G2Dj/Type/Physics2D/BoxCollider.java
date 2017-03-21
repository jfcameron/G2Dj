/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author Joseph Cameron
 */
public class BoxCollider extends Component implements Collider
{
    //
    //
    //
    private       LineVisualizer m_LineVisualizer;
    private final FixtureDef     m_FixtureDefinition = new FixtureDef();
    private final PolygonShape   m_Shape = new PolygonShape();
    
    //
    //
    //
    @Override public FixtureDef getB2DFixture(){return m_FixtureDefinition;}
    
    //
    //
    //
    public BoxCollider()
    {
        m_FixtureDefinition.shape = m_Shape;
        
    }

    @Override
    public void update() {}

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) 
    {
        Vector3 scale = getGameObject().get().getTransform().get().getScale();
        
        m_Shape.setAsBox(scale.x/2,scale.z/2);
        m_FixtureDefinition.density = 1;
        
        m_LineVisualizer = (LineVisualizer)getGameObject().get().addComponent(LineVisualizer.class);
        m_LineVisualizer.setVertexData(LineVisualizer.lineBox());
        
    }

    @Override
    protected void OnRemovedFromGameObject() {}

    @Override
    protected void OnComponentAdded(Component aComponent) {}

    @Override
    protected void OnComponentRemoved(Component aComponent) {}

    @Override
    protected void OnScaleChanged() {}
    
}
