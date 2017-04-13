/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import grimhaus.com.G2Dj.Type.Math.Vector4;
import java.util.Map;

/**
 *
 * @author Joseph Cameron
 */
public class Vector4UniformCollection extends UniformCollection<Vector4>
{ 
    @Override
    public void bind(final int aProgramHandle)
    {
        for(Map.Entry<String,Vector4> entry : m_HashMap.entrySet())
            Uniforms.load4Float(aProgramHandle, entry.getKey(), entry.getValue().x, entry.getValue().y, entry.getValue().z, entry.getValue().w);
        
    }
    
    @Override
    public void unbind(final int aProgramHandle)
    {
        for(Map.Entry<String,Vector4> entry : m_HashMap.entrySet())
            Uniforms.load4Float(aProgramHandle, entry.getKey(),0,0,0,0);
        
    }
    
    public Vector4UniformCollection()
    {
        
        
    }
    
}
