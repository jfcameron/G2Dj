/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import grimhaus.com.G2Dj.Type.Math.Vector2;
import java.util.Map;

/**
 *
 * @author Joseph Cameron
 */
public class Vector2UniformCollection extends UniformCollection<Vector2>
{ 
    @Override
    public void bind(final int aProgramHandle)
    {
        for(Map.Entry<String,Vector2> entry : m_HashMap.entrySet())
            Uniforms.load2Float(aProgramHandle, entry.getKey(), entry.getValue().x, entry.getValue().y);
        
    }
    
    @Override
    public void unbind(final int aProgramHandle)
    {
        for(Map.Entry<String,Vector2> entry : m_HashMap.entrySet())
            Uniforms.load2Float(aProgramHandle, entry.getKey(),0,0);
        
    }
    
    public Vector2UniformCollection()
    {
        
        
    }
    
}
