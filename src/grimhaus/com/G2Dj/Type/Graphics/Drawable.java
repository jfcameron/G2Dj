/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import java.lang.ref.WeakReference;

/**
 * A drawable can be added to a graphics scene and drawn by the renderer
 * @author Joseph Cameron
 */
public interface Drawable 
{
    void draw(final WeakReference<Camera> aCamera);
    
}
