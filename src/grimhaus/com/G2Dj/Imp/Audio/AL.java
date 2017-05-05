/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Audio;

import com.jogamp.openal.ALException;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Joseph Cameron
 */
public class AL 
{
    public static final com.jogamp.openal.AL al; //HIDE ME
    
    //AL API
    public static int  alGetError(){return al.alGetError();}
    
    //Buffer calls
    public static void alGenBuffers(int aNumberOfBuffersToGenerate, IntBuffer aBufferHandles){al.alGenBuffers(aNumberOfBuffersToGenerate,aBufferHandles);}
    public static void alBufferData(int aBufferHandle, int aDataFormat, Buffer aData, int aDataSize, int aDataFrequency){al.alBufferData(aBufferHandle,aDataFormat,aData,aDataSize,aDataFrequency);}
    public static void alDeleteBuffers(int i, IntBuffer ib){al.alDeleteBuffers(i,ib);}
    
    //Source calls
    public static void alGenSources(int i, IntBuffer ib){al.alGenSources(i,ib);}
    public static void alDeleteSources(int i, IntBuffer ib){al.alDeleteSources(i,ib);}
    
    public static void alGetSourcei(int i, int i1, IntBuffer ib){al.alGetSourcei(i, i1, ib);}
    
    public static void alSourcei(int i, int i1, int i2){al.alSourcei(i,i1,i2);}
    public static void alSourcef(int i, int i1, float f){al.alSourcef(i,i1,f);}
    public static void alSourcefv(int i, int i1, FloatBuffer fb){al.alSourcefv(i,i1,fb);}
    
    public static void alSourceQueueBuffers(int i, int i1, IntBuffer ib){al.alSourceQueueBuffers(i,i1,ib);}
    public static void alSourceUnqueueBuffers(int i, int i1, IntBuffer ib){al.alSourceUnqueueBuffers(i,i1,ib);}
    
    //Listener calls
    public static void alListenerfv(int i, FloatBuffer fb){al.alListenerfv(i,fb);}
    
    //Playback calls
    public static void alSourcePlay(int i){al.alSourcePlay(i);}
    public static void alSourceStop(int i){al.alSourceStop(i);}
    
    //AL Constants
    public static final int 
    AL_NO_ERROR,
    AL_BUFFER,
    AL_PITCH,
    AL_GAIN,
    AL_LOOPING,
    AL_POSITION,
    AL_VELOCITY,
    AL_ORIENTATION,
    AL_FORMAT_MONO16,
    AL_FORMAT_STEREO16,
    AL_DIRECTION,
    AL_ROLLOFF_FACTOR,
    AL_SOURCE_RELATIVE,
    AL_TRUE,
    AL_SOURCE_STATE,
    AL_PLAYING,
    AL_BUFFERS_PROCESSED,
    AL_BUFFERS_QUEUED;
    
    static
    {
        //Get a context
        try
        {
            ALut.alutInit();
        
        } 
        catch (ALException e){e.printStackTrace();}
        
        al = ALFactory.getAL();
        al.alGetError();//make sure geterror is zeroed
        
        //Setup a cleanup thread to be run at program's end
        Runtime.getRuntime().addShutdownHook(new Thread(){public void run() 
        {
            System.out.println("AL cleanup");
            ALut.alutExit();
            
        }});
        
        //Constants
        AL_NO_ERROR          = com.jogamp.openal.AL.AL_NO_ERROR;
        AL_BUFFER            = com.jogamp.openal.AL.AL_BUFFER;
        AL_PITCH             = com.jogamp.openal.AL.AL_PITCH;
        AL_GAIN              = com.jogamp.openal.AL.AL_GAIN;
        AL_LOOPING           = com.jogamp.openal.AL.AL_LOOPING;
        AL_POSITION          = com.jogamp.openal.AL.AL_POSITION;
        AL_VELOCITY          = com.jogamp.openal.AL.AL_VELOCITY;
        AL_ORIENTATION       = com.jogamp.openal.AL.AL_ORIENTATION;
        AL_FORMAT_MONO16     = com.jogamp.openal.AL.AL_FORMAT_MONO16;
        AL_FORMAT_STEREO16   = com.jogamp.openal.AL.AL_FORMAT_STEREO16;
        AL_DIRECTION         = com.jogamp.openal.AL.AL_DIRECTION;
        AL_ROLLOFF_FACTOR    = com.jogamp.openal.AL.AL_ROLLOFF_FACTOR;
        AL_SOURCE_RELATIVE   = com.jogamp.openal.AL.AL_SOURCE_RELATIVE;
        AL_TRUE              = com.jogamp.openal.AL.AL_TRUE;
        AL_SOURCE_STATE      = com.jogamp.openal.AL.AL_SOURCE_STATE;
        AL_PLAYING           = com.jogamp.openal.AL.AL_PLAYING;
        AL_BUFFERS_PROCESSED = com.jogamp.openal.AL.AL_BUFFERS_PROCESSED;
        AL_BUFFERS_QUEUED    = com.jogamp.openal.AL.AL_BUFFERS_QUEUED;
                
    } 
    
}
