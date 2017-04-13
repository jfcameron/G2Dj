/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import java.util.HashMap;

/**
 *
 * @author Joe
 */
public abstract class UniformCollection<T>
{
    //
    // Data members
    //
    protected final HashMap<String,T> m_HashMap = new HashMap<>();
    
    //
    // Public interface
    //
    public final void put(final String aName, final T aGraphicsResource)
    {
        m_HashMap.put(aName, aGraphicsResource);
        
    }
    
    public T get(final String aName)
    {
        return m_HashMap.get(aName);
        
    }
    
    public abstract void bind(final int aProgramHandle);
    public abstract void unbind(final int aProgramHandle);
    
}
