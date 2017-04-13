/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.util.Map;

/**
 *
 * @author Joseph Cameron
 */
public class Vector3UniformCollection extends UniformCollection<Vector3>
{ 
    @Override
    public void bind(final int aProgramHandle)
    {
        for(Map.Entry<String,Vector3> entry : m_HashMap.entrySet())
            Uniforms.load3Float(aProgramHandle, entry.getKey(), entry.getValue().x, entry.getValue().y, entry.getValue().z);
        
    }
    
    @Override
    public void unbind(final int aProgramHandle)
    {
        for(Map.Entry<String,Vector3> entry : m_HashMap.entrySet())
            Uniforms.load3Float(aProgramHandle, entry.getKey(),0,0,0);
        
    }
    
    public Vector3UniformCollection()
    {
        
        
    }
    
}
