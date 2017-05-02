/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj;

import com.jogamp.openal.AL;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;
import java.nio.ByteBuffer;

/**
 *
 * @author Joseph Cameron
 */
public class Sounds
{
    //context object
    static AL al;// al = ALFactory.getAL();
    
    // Buffers hold sound data.
   // static int[] buffer = new int[1];
    
    // Sources are points emitting sound.
    static int[] source = new int[1];
    // Position of the source sound.
    static float[] sourcePos = { 0.0f, 0.0f, 0.0f };
    // Velocity of the source sound.
    static float[] sourceVel = { 0.0f, 0.0f, 0.0f };

    // Position of the listener.
    static float[] listenerPos = { 0.0f, 0.0f, 0.0f };
    // Velocity of the listener.
    static float[] listenerVel = { 0.0f, 0.0f, 0.0f };
    // Orientation of the listener. (first 3 elements are "at", second 3 are "up")
    static float[] listenerOri = { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f };
    
    static public void test()
    {/*
        Debug.log("*************\n*************SOUNDTEST\n*************\n*************");
        
        al = ALFactory.getAL();
        Debug.log(al.alGetError());
        
        int[] buffer = new int[1];
        al.alGenBuffers(1, buffer, 0);
        Debug.log(al.alGetError());
        
        
        Debug.log("*************\n*************ENDSOUNDTEST\n*************\n*************");
        */
    }
    
}
