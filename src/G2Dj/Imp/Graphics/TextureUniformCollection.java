/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package G2Dj.Imp.Graphics;

import G2Dj.Debug;
import G2Dj.Graphics;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 *
 * @author Joe
 */
public class TextureUniformCollection extends UniformCollection<Texture>
{ 
    @Override
    public void bind(final int aProgramHandle)
    {
        int i = 0;
        
        for(Map.Entry<String,WeakReference<Texture>> entry : m_HashMap.entrySet())
        { 
            Uniforms.loadTexture(aProgramHandle, entry.getKey(), entry.getValue().get().getHandle(), i++);
        
        }

        
    }
    
    public TextureUniformCollection()
    {
        put(Uniforms.s_StandardDiffuseTextureName,Graphics.getTexture());
        
    }
    
}
