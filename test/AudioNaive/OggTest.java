/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package AudioNaive;

import grimhaus.com.G2Dj.Type.Audio.OggStreamer;
import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Imp.Audio.OggDecoder;
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
    
    private static OggDecoder  m_OggDecoder;
    private static OggStreamer m_OggStream;
    private static OggStreamer m_OggStream2;
    
    public static void soundTest()
    {
        m_OggDecoder = new OggDecoder(Resources.class.getClassLoader().getResource("AudioNaive/AMemoryAway.ogg"));
        
        m_OggStream  = new OggStreamer(m_OggDecoder);
        m_OggStream2 = new OggStreamer(m_OggDecoder);
        
        createEscapeKeyThread();
        
    }
    
    private static void createEscapeKeyThread()
    {
        Thread thread = new Thread(){@Override public void run()
        {
            for(;;)
            {
                if (Input.getKey(KeyCode.Escape))
                    System.exit(0);
             
                if (Input.getKeyDown(KeyCode.A))
                {
                    m_OggStream.play();
                    
                }
                
                if (Input.getKeyDown(KeyCode.S))
                {
                    m_OggStream2.play();
                    
                }
                
                
            }
            
        }};
        
        thread.start();
        
    }
    
    
}
