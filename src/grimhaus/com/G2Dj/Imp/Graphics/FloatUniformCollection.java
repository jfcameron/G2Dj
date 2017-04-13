/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import java.util.Map;

/**
 *
 * @author Joseph Cameron
 */
public class FloatUniformCollection extends UniformCollection<Float>
{ 
    @Override
    public void bind(final int aProgramHandle)
    {
        for(Map.Entry<String,Float> entry : m_HashMap.entrySet())
            Uniforms.load1Float(aProgramHandle, entry.getKey(), entry.getValue());
        
    }
    
    @Override
    public void unbind(final int aProgramHandle)
    {
        for(Map.Entry<String,Float> entry : m_HashMap.entrySet())
            Uniforms.load1Float(aProgramHandle, entry.getKey(),0);
        
    }
    
    public FloatUniformCollection()
    {
        
        
    }
    
}
