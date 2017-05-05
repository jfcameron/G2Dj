/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package AudioNaive;

import grimhaus.com.G2Dj.Imp.Audio.OggStreamer;
import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Imp.Audio.OggDecoder;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Resources;
import grimhaus.com.G2Dj.Type.Engine.Game;
import java.net.URL;

/**
 *
 * @author Joseph Cameron
 */
public class OggStreamTest 
{
    //Entry point
    public static void main(String[] args){Engine.init(new Game(){@Override public void init(){soundTest();}});}
    
    private static OggDecoder  m_OggDecoder;
    private static OggStreamer[] m_OggStream;
    //private static OggStreamer m_OggStream2;
    
    public static void soundTest()
    {
        //m_OggDecoder = new OggDecoder(Resources.class.getClassLoader().getResource("AudioNaive/AMemoryAway.ogg"));
        
        URL url = Resources.class.getClassLoader().getResource("AudioNaive/Example.ogg");
        URL url2 = Resources.class.getClassLoader().getResource("AudioNaive/AMemoryAway.ogg");
        
        m_OggStream = new OggStreamer[3];
        
        for (int i=0;i<m_OggStream.length;i++)
            m_OggStream[i] = new OggStreamer(url);
        
        m_OggStream[0] = new OggStreamer(url2);
        
        
        createEscapeKeyThread();
        
    }
    
    private static void createEscapeKeyThread()
    {
        Thread thread = new Thread()
        {
            boolean a = false;
            boolean s = false;
            
            @Override public void run()
        {            
            for(;;)
            {
                if (Input.getKey(KeyCode.Escape))
                    System.exit(0);
             
                if (Input.getKey(KeyCode.A))
                {
                    if (!a)
                    m_OggStream[0].play();
                    
                    a=true;
                    
                }
                
                if (Input.getKey(KeyCode.S))
                {
                    if (!s)
                    m_OggStream[1].play();
                    
                    s=true;
                    
                }
                
                
            }
            
        }};
        
        thread.start();
        
    }
    
    
}
