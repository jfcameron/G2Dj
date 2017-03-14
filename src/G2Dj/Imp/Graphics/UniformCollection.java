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
public abstract class UniformCollection<T extends GraphicsResource>
{
    //
    // Data members
    //
    protected final HashMap<String,WeakReference<T>> m_HashMap = new HashMap<>();
    
    //
    // Public interface
    //
    public final void put(final String aName, final WeakReference<T> aGraphicsResource)
    {
        m_HashMap.put(aName, aGraphicsResource);
        
    }
    
    public WeakReference<T> get(final String aName)
    {
        return m_HashMap.get(aName);
        
    }
    
    public abstract void bind(final int aProgramHandle);
    
}
