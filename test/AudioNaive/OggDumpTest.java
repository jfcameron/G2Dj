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
import grimhaus.com.G2Dj.Imp.Audio.OggDecoder;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Resources;
import grimhaus.com.G2Dj.Type.Engine.Game;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joseph Cameron
 */
public class OggDumpTest 
{
    //Entry point
    public static void main(String[] args){Engine.init(new Game(){@Override public void init(){soundTest();}});}
    
    public static void soundTest()
    {
        // Position of the source sound. // Velocity of the source sound.
        float[] sourcePos = { 0.0f, 0.0f, 0.0f };
        float[] sourceVel = { 0.0f, 0.0f, 0.0f };
        
        // Position of the listener. // Velocity of the listener. // Orientation of the listener. (first 3 elems are "at", second 3 are "up")
        float[] listenerPos = { 0.0f, 0.0f, 0.0f };
        float[] listenerVel = { 0.0f, 0.0f, 0.0f };
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

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        OggDecoder oggDecoder = new OggDecoder(Resources.class.getClassLoader().getResource("AudioNaive/Example.ogg"));
        
	int numChannels = oggDecoder.getNumChannels();
	int numBytesPerSample = 2;

        if (numChannels == 1)
	    format[0] = AL.AL_FORMAT_MONO16;
	else
	    format[0] = AL.AL_FORMAT_STEREO16;
        
        freq[0] = oggDecoder.getSampleRate();
        data[0] = ByteBuffer.wrap(oggDecoder.DUMPTEST());
        size[0] = data[0].array().length;
        
        Debug.log(data[0].array().length);
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*ALut.alutLoadWAVFile
        (
            Resources.class.getResourceAsStream("/AudioNaive/Test1.wav"),
            format,
            data,
            size,
            freq,
            loop
        
        );*/
        
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
        
        Thread thread = new Thread(){@Override public void run()
        {
            for(;;)
            {
                if (Input.getKey(KeyCode.Escape))
                    System.exit(0);
                if (Input.getKeyDown(KeyCode.A))
                    AL.alSourcePlay(source[0]);
            
            }
            
        }};
        thread.start();
        
    }
    
}