/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Graphics;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Joe
 */
public class TextureUniformCollection 
{
    //
    // Data members
    //
    private final HashMap<String,WeakReference<Texture>> m_HashMap = new HashMap<>();
    
    //
    // Public interface
    //
    public final void put(final String aName, final WeakReference<Texture> aTexture)
    {
        m_HashMap.put(aName, aTexture);
        
    }
    
    public WeakReference<Texture> get(final String aName)
    {
        return m_HashMap.get(aName);
        
    }
    
    public void bind(final int aProgramHandle)
    {
        int i = 0;
        
        for(Map.Entry<String,WeakReference<Texture>> entry : m_HashMap.entrySet())
        { 
            Uniforms.loadTexture(entry.getValue().get().getHandle(), entry.getKey(), aProgramHandle, i++);
        
        }

        
    }
    
    //
    // Ctor
    //
    public TextureUniformCollection()
    {
        put(Uniforms.s_StandardDiffuseTextureName,Graphics.getTexture());
        
    }
    
}
