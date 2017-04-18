/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Physics2D;

import grimhaus.com.G2Dj.Type.Math.Vector2;

/**
 *
 * @author Joseph Cameron
 */
public class EdgeDefinition 
{
    public final Vector2[] vertexes;
    public final float 
    friction,
    restitution,
    density;
    
    public EdgeDefinition(final Vector2[]aVertexes){this(aVertexes,1,1,1);}
    public EdgeDefinition(final Vector2[]aVertexes,final float aFriction,final float aRestitution,final float aDensity)
    {
        vertexes    = aVertexes;
        friction    = aFriction;
        restitution = aRestitution;
        density     = aDensity;
        
    }
    
}
