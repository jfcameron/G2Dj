/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package AudioNaive;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Resources;
import grimhaus.com.G2Dj.Type.Engine.Game;

/**
 *
 * @author Joseph Cameron
 */
public class OggTest 
{
    //Entry point
    public static void main(String[] args){Engine.init(new Game(){@Override public void init(){soundTest();}});}
    
    private static OggStreamer m_OggStream;
    
    public static void soundTest()
    {
        m_OggStream = new OggStreamer(Resources.class.getClassLoader().getResource("AudioNaive/AMemoryAway.ogg"));
        m_OggStream.playstream();
        
        createEscapeKeyThread();
        
    }
    
    private static void createEscapeKeyThread()
    {
        Thread thread = new Thread(){@Override public void run()
        {
            for(;;)
                if (Input.getKey(KeyCode.Escape))
                    System.exit(0);
            
        }};
        
        thread.start();
        
    }
    
    
}
