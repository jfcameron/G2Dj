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
    public final Vector2[]    
    northSurface,
    eastSurface ,
    southSurface,
    westSurface ;
    //private final float      friction;
    //private final float      restitution;
    
    public GridColliderDefinition(final Vector2[] aNorthSurface,final Vector2[] aEastSurface,final Vector2[] aSouthSurface,final Vector2[] aWestSurface)
    {
        northSurface = aNorthSurface;
        eastSurface  = aEastSurface;
        southSurface = aSouthSurface;
        westSurface  = aWestSurface;
        
    }
    
}
