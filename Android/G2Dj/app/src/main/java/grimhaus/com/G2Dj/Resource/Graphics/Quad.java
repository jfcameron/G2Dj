/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Resource.Graphics;

import grimhaus.com.G2Dj.Imp.Graphics.Model;
import grimhaus.com.G2Dj.Imp.Graphics.VertexFormat;

/**
 *
 * @author Joe
 */
public class Quad extends Model
{
    private static final float size = 1.0f;
    
    public Quad() 
    {
        //String aName, float[] aVertexData, VertexFormat aVertexFormat
        super
        (
            "Quad"
            , 
            new float[]
            {
                //           x,              y,    z,    u,    v,
                size -(size/2), size -(size/2), 0.0f, 1.0f, 0.0f, // 1--0
                0.0f -(size/2), size -(size/2), 0.0f, 0.0f, 0.0f, // | /
                0.0f -(size/2), 0.0f -(size/2), 0.0f, 0.0f, 1.0f, // 2

                size -(size/2), size -(size/2), 0.0f, 1.0f, 0.0f, //    0
                0.0f -(size/2), 0.0f -(size/2), 0.0f, 0.0f, 1.0f, //  / |
                size -(size/2), 0.0f -(size/2), 0.0f, 1.0f, 1.0f, // 1--2
                
            }
            , 
            VertexFormat.pos3uv2
        
        );
    
    }
    
}
