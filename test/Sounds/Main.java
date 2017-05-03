/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Sounds;

import com.jogamp.openal.AL;
import com.jogamp.openal.ALException;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;
import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Resources;
import grimhaus.com.G2Dj.Type.Engine.Game;
import java.nio.ByteBuffer;

/**
 *
 * @author Joseph Cameron
 */
public class Main 
{
    //Entry point
    public static void main(String[] args){Engine.init(new Game(){@Override public void init(){soundTest();}});}
    
    private static AL al;
    
    public static void soundTest()
    {
        // Initialize OpenAL and clear the error bit.
        try 
        {
            ALut.alutInit();
            al = ALFactory.getAL();
            al.alGetError();
        
        } 
        catch (ALException e) {e.printStackTrace();}
        
        ///////////////////////////////////////////////
         // Position of the source sound.
        float[] sourcePos = { 0.0f, 0.0f, 0.0f };

        // Velocity of the source sound.
        float[] sourceVel = { 0.0f, 0.0f, 0.0f };

        // Position of the listener.
        float[] listenerPos = { 0.0f, 0.0f, 0.0f };

        // Velocity of the listener.
        float[] listenerVel = { 0.0f, 0.0f, 0.0f };

        // Orientation of the listener. (first 3 elems are "at", second 3 are "up")
        float[] listenerOri = { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f };
        
        
        // variables to load into
        int[] buffer = new int[1];
        int[] source = new int[1];
        

        int[] format = new int[1];
        int[] size = new int[1];
        ByteBuffer[] data = new ByteBuffer[1];
        int[] freq = new int[1];
        int[] loop = new int[1];

        // Load wav data into a buffer.
        al.alGenBuffers(1, buffer, 0);
        if (al.alGetError() != AL.AL_NO_ERROR)
          throw new ALException("Error generating OpenAL buffers");

        ALut.alutLoadWAVFile(
        Resources.class.getResourceAsStream("/Sounds/Test1.wav"),
        format,
        data,
        size,
        freq,
        loop);
        if (data[0] == null) 
        {
            throw new RuntimeException("Error loading WAV file");
        
        }
        
        System.out.println("sound format = " + format[0]);
        System.out.println("sound data = " + data[0]);
        System.out.println("sound size = " + size[0]);
        System.out.println("sound freq = " + freq[0]);
        System.out.println("sound loop = " + loop[0]);
        al.alBufferData(buffer[0], format[0], data[0], size[0], freq[0]);

        // Bind buffer with a source.
        al.alGenSources(1, source, 0);

        if (al.alGetError() != AL.AL_NO_ERROR)
          throw new ALException("Error generating OpenAL source");

        al.alSourcei(source[0], AL.AL_BUFFER, buffer[0]);
        al.alSourcef(source[0], AL.AL_PITCH, 1.0f);
        al.alSourcef(source[0], AL.AL_GAIN, 1.0f);
        al.alSourcei(source[0], AL.AL_LOOPING, loop[0]);

        // Do another error check
        if (al.alGetError() != AL.AL_NO_ERROR)
            throw new ALException("Error setting up OpenAL source");

        // Note: for some reason the following two calls are producing an
        // error on one machine with NVidia's OpenAL implementation. This
        // appears to be harmless, so just continue past the error if one
        // occurs.
        al.alSourcefv(source[0], AL.AL_POSITION, sourcePos, 0);
        al.alSourcefv(source[0], AL.AL_VELOCITY, sourceVel, 0);
        
        al.alListenerfv(AL.AL_POSITION, listenerPos, 0);
        al.alListenerfv(AL.AL_VELOCITY, listenerVel, 0);
        al.alListenerfv(AL.AL_ORIENTATION, listenerOri, 0);
        
        
        al.alSourcePlay(source[0]);
        
    }
    
        //
        // Implementation
        //
        
    
}