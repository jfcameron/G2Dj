/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Graphics;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 *
 * @author Joe
 */
public class TextureUniformCollection extends UniformCollection<WeakReference<Texture>>
{ 
    private int m_TextureUnitIncrementer = 0;
    
    @Override
    public void bind(final int aProgramHandle)
    {
        for(Map.Entry<String,WeakReference<Texture>> entry : m_HashMap.entrySet())
            Uniforms.loadTexture(aProgramHandle, entry.getKey(), entry.getValue().get().getHandle(), m_TextureUnitIncrementer++);
        
        m_TextureUnitIncrementer = 0;
        
    }
    
    @Override
    public void unbind(final int aProgramHandle)
    {
        for(Map.Entry<String,WeakReference<Texture>> entry : m_HashMap.entrySet())
            Uniforms.loadTexture(aProgramHandle, entry.getKey(), 0, m_TextureUnitIncrementer++);
        
        m_TextureUnitIncrementer = 0;
        
    }
    
    public TextureUniformCollection()
    {
        put(Uniforms.s_StandardDiffuseTextureName,Graphics.getTexture());
        
    }
    
}
