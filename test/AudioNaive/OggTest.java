/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package AudioNaive;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Resources;
import grimhaus.com.G2Dj.Type.Engine.Game;
import java.net.URL;

/**
 *
 * @author Joseph Cameron
 */
public class OggTest 
{
    //Entry point
    public static void main(String[] args){Engine.init(new Game(){@Override public void init(){soundTest();}});}
    
    public static void soundTest()
    {
        try 
        {
	    boolean played = false;
            
	    if (!played) 
            {
		URL url = Resources.class.getClassLoader().getResource("AudioNaive/Example.ogg");
                Debug.log(url);
                (new OggStreamer(url)).playstream();
                
	    }

        } 
        catch (Exception e) {e.printStackTrace();}

        System.exit(0);
        
    }
    
}
