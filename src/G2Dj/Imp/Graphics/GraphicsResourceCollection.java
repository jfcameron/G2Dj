/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package G2Dj.Imp.Graphics;

import java.util.ArrayList;
import java.lang.ref.WeakReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GraphicsResourceCollection<T extends GraphicsResource>
{
    //*************
    // Data members
    //*************
    private final ArrayList<T>      m_Vector    = new ArrayList<>();
    private final ArrayList<String> m_FileTypes = new ArrayList<>();
    
    //*****************
    // Public interface
    //*****************
    public WeakReference<T> find(final String aItemName)
    {
        for(int i =0, s = m_Vector.size(); i < s; i++)
            if (m_Vector.get(i).getName().equals(aItemName))
                return new WeakReference<>(m_Vector.get(i));
            
        return getDefault();
        
    }
    
    public WeakReference<T> get(){return getDefault();}
    
    public WeakReference<T> get(final int i)
    {
        if (i >= m_Vector.size())
            return getDefault();
    
        return new WeakReference<>(m_Vector.get(i));
        
    }
    
    public WeakReference<T> get(final String aItemName)
    {
        if (aItemName != null)
            for (int i = 0, s = m_Vector.size(); i < s; i++)
                if (m_Vector.get(i).getName().equals(aItemName))
                    return new WeakReference<>(m_Vector.get(i));
                
        return getDefault();
        
    }
    
    public abstract WeakReference<T> loadFromResource(final String aAbsoluteResourcePath);
    public abstract WeakReference<T> loadFromFile    (final String aRelativeFilePath    );
    
    //
    // Implementation
    //
    protected void addClass(final Class<? extends T> aItem)
    {
        T newItem = null;
        try 
        {
            newItem = aItem.newInstance();
        }
        catch (InstantiationException | IllegalAccessException ex) {Logger.getLogger(GraphicsResourceCollection.class.getName()).log(Level.SEVERE, null, ex);}
        
        if (newItem == null)
            return;
        
        addInstance(newItem);
        
    }
    
    protected void addInstance(final T aItem)
    {
        m_Vector.add(aItem);
        
    }
    
    private WeakReference<T> getDefault()
    {
        if (m_Vector.isEmpty())
            throw new RuntimeException("No default exists.");
        
        return new WeakReference<>(m_Vector.get(0));
        
    }
        
}
