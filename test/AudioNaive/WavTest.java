/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package AudioNaive;

import com.jogamp.openal.ALException;
import com.jogamp.openal.util.ALut;
import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Imp.Audio.AL;
import grimhaus.com.G2Dj.Resources;
import grimhaus.com.G2Dj.Type.Engine.Game;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Joseph Cameron
 */
public class WavTest 
{
    //Entry point
    public static void main(String[] args){Engine.init(new Game(){@Override public void init(){soundTest();}});}
    
    public static void soundTest()
    {
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
        
        ByteBuffer[] data = new ByteBuffer[1];
        int[] format = new int[1];
        int[] size   = new int[1];
        int[] freq   = new int[1];
        int[] loop   = new int[1];
        
        // Load wav data into a buffer.
        AL.alGenBuffers(1, IntBuffer.wrap(buffer));
        
        if (AL.alGetError() != AL.AL_NO_ERROR)
          throw new ALException("Error generating OpenAL buffers");

        ALut.alutLoadWAVFile(
        Resources.class.getResourceAsStream("/AudioNaive/Test1.wav"),
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
        AL.alBufferData(buffer[0], format[0], data[0], size[0], freq[0]);

        // Bind buffer with a source.
        AL.alGenSources(1, IntBuffer.wrap(source));

        if (AL.alGetError() != AL.AL_NO_ERROR)
          throw new ALException("Error generating OpenAL source");

        AL.alSourcei(source[0], AL.AL_BUFFER, buffer[0]);
        AL.alSourcef(source[0], AL.AL_PITCH, 1.0f);
        AL.alSourcef(source[0], AL.AL_GAIN, 1.0f);
        AL.alSourcei(source[0], AL.AL_LOOPING, loop[0]);

        // Do another error check
        if (AL.alGetError() != AL.AL_NO_ERROR)
            throw new ALException("Error setting up OpenAL source");

        // Note: for some reason the following two calls are producing an
        // error on one machine with NVidia's OpenAL implementation. This
        // appears to be harmless, so just continue past the error if one
        // occurs.
        AL.alSourcefv(source[0], AL.AL_POSITION, FloatBuffer.wrap(sourcePos));
        AL.alSourcefv(source[0], AL.AL_VELOCITY, FloatBuffer.wrap(sourceVel));
        AL.alGetError();
        
        AL.alListenerfv(AL.AL_POSITION,    FloatBuffer.wrap(listenerPos));
        AL.alListenerfv(AL.AL_VELOCITY,    FloatBuffer.wrap(listenerVel));
        AL.alListenerfv(AL.AL_ORIENTATION, FloatBuffer.wrap(listenerOri));
        
        AL.alSourcePlay(source[0]);
        
        for (int i = 0;; i++)
        {
            if (i >= 9E8)
            {
                Debug.log("Cleanup all data");
        
                AL.alDeleteBuffers(1, IntBuffer.wrap(buffer));
                AL.alDeleteSources(1, IntBuffer.wrap(source));
                System.exit(0);
                
            }
            
        }
        
    }        
    
}