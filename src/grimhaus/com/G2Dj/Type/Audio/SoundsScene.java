/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Audio;

import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Engine.SceneGraph;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class SoundsScene extends SceneGraph
{
    @Override
    public void fixedUpdate() {}

    @Override
    public void update() {}

    @Override
    public void draw() {}

    @Override
    protected void OnComponentAdded(Component aComponent) {}

    @Override
    protected void OnComponentRemoved(Component aComponent) {}
    
    //************
    // Constructor
    //************
    public SoundsScene(WeakReference<Scene> aScene){super(aScene);}
    
}
