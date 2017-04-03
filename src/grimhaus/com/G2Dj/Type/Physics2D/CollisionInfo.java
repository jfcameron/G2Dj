/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Imp.Physics2D.ColliderType;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class CollisionInfo 
{
    public final WeakReference<GameObject> other;
    
    CollisionInfo
    (
        final WeakReference<GameObject> aOther
            
    )
    {
        other = aOther;
        
    }
    
}
