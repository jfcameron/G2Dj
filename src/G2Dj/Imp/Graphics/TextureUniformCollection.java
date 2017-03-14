/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
