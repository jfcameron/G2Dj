/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Type.Math.IntVector2;
import grimhaus.com.G2Dj.Type.Math.Vector2;

/**
 *
 * @author Joseph Cameron
 */
public class GridColliderDefinition 
{
    public final Vector2[]    colliderVertexes;
    //private final float      friction;
    //private final float      restitution;
    
    public GridColliderDefinition(final Vector2[] aCounterclockwiseVertexArray)
    {
        colliderVertexes = aCounterclockwiseVertexArray;
        
    }
    
}
