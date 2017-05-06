/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Audio;

/**
 *
 * @author Joseph Cameron
 */
public abstract class Sound 
{
    //*************
    // Data members
    //*************
    private final String m_Name;
    
    //*****************
    // Public interface
    //*****************
    public abstract void play();
    
    //************
    // Constructor
    //************
    public Sound(final String aName)
    {
        m_Name = aName;
        
    }
    
}
