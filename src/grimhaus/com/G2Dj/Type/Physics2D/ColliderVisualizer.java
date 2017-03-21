/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Type.Graphics.Camera;
import grimhaus.com.G2Dj.Type.Graphics.Drawable;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class ColliderVisualizer implements Drawable
{
    
    
    //
    // Drawable interface
    //
    @Override
    public void draw(WeakReference<Camera> aCamera) 
    {
        Debug.log("ColliderVisualizer is drawing");
    
    }
    
}
