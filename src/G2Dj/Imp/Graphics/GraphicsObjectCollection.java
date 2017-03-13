/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Resources;
import java.util.ArrayList;
import java.lang.ref.WeakReference;

public abstract class GraphicsObjectCollection<T extends GraphicsObject>
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
    protected void add(final T aItem)
    {
        //todo: sanitize...
        
        m_Vector.add(aItem);
        
    }
    
    private WeakReference<T> getDefault()
    {
        if (m_Vector.isEmpty())
            throw new RuntimeException("No default exists.");
        
        return new WeakReference<>(m_Vector.get(0));
        
    }
        
}
