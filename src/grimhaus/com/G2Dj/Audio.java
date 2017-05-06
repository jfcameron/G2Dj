/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

import grimhaus.com.G2Dj.Type.Audio.Sound;
import grimhaus.com.G2Dj.Type.Audio.SoundCollection;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class Audio
{
    //*************
    // Data members
    //*************
    private static final SoundCollection s_SoundCollection = new SoundCollection();
    
    //*****************
    // Public interface
    //*****************
    public static WeakReference<Sound> getSound(final String aName){return s_SoundCollection.getSound(aName);}
    public static WeakReference<Sound> loadFromResource(String aAbsoluteResourcePath){return s_SoundCollection.loadFromResource(aAbsoluteResourcePath);}
    
}
