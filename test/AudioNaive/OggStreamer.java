/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package AudioNaive;

import com.jogamp.openal.ALException;

import grimhaus.com.G2Dj.Imp.Audio.AL;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class OggStreamer 
{    
    private static void debugMsg(String str) 
    {
	if (debug) System.err.println(str);
        
    }
    
    ///////////////////////////////////////////////////////////////////////////////////
    private static boolean debug = false;
    
    private OggDecoder oggDecoder;
    
    private static int totalBytes = 0;
    private static int BUFFER_SIZE = 4096*16;// The size of a chunk from the stream that we want to read for each update.    
    private static int NUM_BUFFERS = 2;// The number of buffers used in the audio pipeline
    
    private int[] buffers = new int[NUM_BUFFERS];// Buffers hold sound data. There are two of them by default (front/back)
    private int[] source = new int[1];// Sources are points emitting sound.
    
    private int format;	// OpenAL data format
    private int rate;	// sample rate
    
    private URL url;

    private long sleepTime = 0;
    
    // Position, Velocity, Direction of the source sound.
    private float[] sourcePos = { 0.0f, 0.0f, 0.0f };
    private float[] sourceVel = { 0.0f, 0.0f, 0.0f };
    private float[] sourceDir = { 0.0f, 0.0f, 0.0f };
    
    /** Creates a new instance of OggStreamer */
    public OggStreamer(URL url) 
    {
	this.url = url;
        
    }
    
    
    //Open the Ogg/Vorbis stream and initialize OpenAL based on the stream properties
    public boolean open() 
    {
	oggDecoder = new OggDecoder(url);

        if (!oggDecoder.initialize()) 
        {
            System.err.println("Error initializing ogg stream...");
            return false;
            
        }
        
	int numChannels = oggDecoder.numChannels();
	int numBytesPerSample = 2;

        if (numChannels == 1)
	    format = AL.AL_FORMAT_MONO16;
	else
	    format = AL.AL_FORMAT_STEREO16;
        
	rate = oggDecoder.sampleRate();

	// A rough estimation of how much time in milliseconds we can sleep
	// before checking to see if the queued buffers have been played
	// (so that we dont peg the CPU by doing an active wait). We divide
	// by 10 at the end to be safe...
	// round it off to the nearest multiple of 10.
	sleepTime = (long)(1000.0 * BUFFER_SIZE /
			    numBytesPerSample / numChannels / rate / 10.0);
	sleepTime = (sleepTime + 10)/10 * 10;

	System.err.println("#Buffers: " + NUM_BUFFERS);
	System.err.println("Buffer size: " + BUFFER_SIZE);
	System.err.println("Format: 0x" + Integer.toString(format, 16));
	System.err.println("Sleep time: " + sleepTime);

	// TODO: I am not if this is the right way to fix the endian
	// problems I am having... but this seems to fix it on Linux
	oggDecoder.setSwap(true);

        AL.alGenBuffers(NUM_BUFFERS, IntBuffer.wrap(buffers));
        AL.alGenSources(1, IntBuffer.wrap(source));

	AL.alSourcefv(source[0], AL.AL_POSITION , FloatBuffer.wrap(sourcePos));
	AL.alSourcefv(source[0], AL.AL_VELOCITY , FloatBuffer.wrap(sourceVel));
	AL.alSourcefv(source[0], AL.AL_DIRECTION, FloatBuffer.wrap(sourceDir));
        
        AL.alSourcef(source[0], AL.AL_ROLLOFF_FACTOR,  0.0f    );
        AL.alSourcei(source[0], AL.AL_SOURCE_RELATIVE, AL.AL_TRUE);
        
        return true;
        
    }
    
    /**
     * OpenAL cleanup
     */
    public void release() 
    {
	AL.alSourceStop(source[0]);
	empty();

	for (int i = 0; i < NUM_BUFFERS; i++)
	    AL.alDeleteSources(i, IntBuffer.wrap(source));
        
    }

    /**
     * Play the Ogg stream
     */
    public boolean playback() 
    {
	if (playing())
	    return true;
        
	debugMsg("playback(): stream all buffers");
	for (int i = 0; i < NUM_BUFFERS; i++) 
        {
	    if (!stream(buffers[i]))
		return false;
            
	}
    
	debugMsg("playback(): queue all buffers & play source");
	AL.alSourceQueueBuffers(source[0], NUM_BUFFERS, IntBuffer.wrap(buffers));
	AL.alSourcePlay(source[0]);
    
        return true;
        
    }
    
    /**
     * Check if the source is playing
     */
    public boolean playing() 
    {
	int[] state = new int[1];
    
	AL.alGetSourcei(source[0], AL.AL_SOURCE_STATE, IntBuffer.wrap(state));
    
	return (state[0] == AL.AL_PLAYING);
        
    }
    
    /**
     * Update the stream if necessary
     */
    public boolean update() 
    {
	int[] processed = new int[1];
	boolean active = true;

	debugMsg("update()");
	AL.alGetSourcei(source[0], AL.AL_BUFFERS_PROCESSED, IntBuffer.wrap(processed));

	while (processed[0] > 0)
	{
	    int[] buffer = new int[1];
	    
	    AL.alSourceUnqueueBuffers(source[0], 1, IntBuffer.wrap(buffer));
	    debugMsg("update(): buffer unqueued => " + buffer[0]);

	    active = stream(buffer[0]);
	    debugMsg("update(): buffer queued => " + buffer[0]);
            
	    AL.alSourceQueueBuffers(source[0], 1, IntBuffer.wrap(buffer));

	    processed[0]--;
            
	}

	return active;
        
    }
    
    /**
     * Reloads a buffer (reads in the next chunk)
     */
    public boolean stream(int buffer) 
    {
	byte[] pcm = new byte[BUFFER_SIZE];
	int    size = 0;

	try 
        {
	    if ((size = oggDecoder.read(pcm)) <= 0)
		return false;
            
	} catch (Exception e) 
        {
	    e.printStackTrace();
	    return false;
            
	}

	totalBytes += size;
	debugMsg("stream(): buffer data => " + buffer + " totalBytes:" + totalBytes);

	ByteBuffer data = ByteBuffer.wrap(pcm, 0, size);
	AL.alBufferData(buffer, format, data, size, rate);
	check();
	
	return true;
        
    }

    /**
     * Empties the queue
     */
    protected void empty() 
    {
	int[] queued = new int[1];
	
	AL.alGetSourcei(source[0], AL.AL_BUFFERS_QUEUED, IntBuffer.wrap(queued));
	
	while (queued[0] > 0)
	{
	    int[] buffer = new int[1];
	
	    AL.alSourceUnqueueBuffers(source[0], 1, IntBuffer.wrap(buffer));
	    check();

	    queued[0]--;
            
	}

	oggDecoder = null;
        
    }

    /**
     * Check for OpenAL errors...
     */
    protected void check() 
    {
        if (AL.alGetError() != AL.AL_NO_ERROR)
            throw new ALException("OpenAL error raised...");
        
    }
    
    /**
     * The main loop to initialize and play the entire stream
     */
    public boolean playstream() 
    {
        if (!open())
            return false;
        
        oggDecoder.dump();
        
        if (!playback())
            return false;
        
        while (update()) 
        {
	    // We will try sleeping for sometime so that we dont
	    // peg the CPU...
	    try 
            {
		Thread.sleep(sleepTime);
                
	    } 
            catch (Exception e) {e.printStackTrace();}

            if (playing()) continue;
            
            if (!playback())
                return false;
            
        }
        
        return true;
        
    }
    
}