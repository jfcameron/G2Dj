/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Audio;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 *
 * @author Joseph Cameron
 */
public class SoundCollection 
{
    //*************
    // Data members
    //*************
    private final ArrayList<Sound> m_Sounds = new ArrayList<>();
    
    //*****************
    // Public interface
    //*****************
    public WeakReference<Sound> getSound(final String aName)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    
    }
    
    public WeakReference<Sound> loadFromResource(String aAbsoluteResourcePath)
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    
    }
    
    //************
    // Constructor
    //************
    public SoundCollection()
    {
        
        
    }
    
}
