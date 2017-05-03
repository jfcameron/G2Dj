/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package AudioNaive;

import grimhaus.com.G2Dj.Debug;

import grimhaus.com.G2Dj.Imp.Audio.AL;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OggStreamer 
{    
    private static void debugMsg(String str) 
    {
	if (debug) System.err.println(str);
        
    }
    
    ///////////////////////////////////////////////////////////////////////////////////
    private static boolean debug = false;
    
    private final OggDecoder     m_OggDecoder;
    private final PlaybackThread m_PlaybackThread = new PlaybackThread();
    
    //Sound data
    private IntBuffer buffers = IntBuffer.allocate(NUM_BUFFERS);// Buffers hold sound data. There are two of them by default (front/back)
    private static int totalBytes = 0;
    private static int BUFFER_SIZE = 4096*16;// The size of a chunk from the stream that we want to read for each update.    
    private static int NUM_BUFFERS = 2;// The number of buffers used in the audio pipeline
    private int format;	// OpenAL data format
    private int rate;	// sample rate
    private long sleepTime = 0;
    
    //AudioSource data
    private float[] sourcePos = { 0.0f, 0.0f, 0.0f };
    private float[] sourceVel = { 0.0f, 0.0f, 0.0f };
    private float[] sourceDir = { 0.0f, 0.0f, 0.0f };
    private IntBuffer source = IntBuffer.allocate(1);
    
    /**
     * OpenAL cleanup
     */
    public void release() 
    {
	AL.alSourceStop(source.get(0));
	empty();

	for (int i = 0; i < NUM_BUFFERS; i++)
	    AL.alDeleteSources(i, source);
        
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
	    if (!stream(buffers.get(i)))
		return false;
            
	}
    
	debugMsg("playback(): queue all buffers & play source");
	AL.alSourceQueueBuffers(source.get(0), NUM_BUFFERS, buffers);
	AL.alSourcePlay(source.get(0));
    
        return true;
        
    }
    
    /**
     * Check if the source is playing
     */
    IntBuffer b_IntBuffer = IntBuffer.allocate(1);
    
    public boolean playing() 
    {
	AL.alGetSourcei(source.get(0), AL.AL_SOURCE_STATE, b_IntBuffer);
        
	return (b_IntBuffer.get(0) == AL.AL_PLAYING);
        
    }
    
    /**
     * Update the stream if necessary
     */
    public boolean update() 
    {
	boolean active = true;

	debugMsg("update()");
	AL.alGetSourcei(source.get(0), AL.AL_BUFFERS_PROCESSED, b_IntBuffer);

	while (b_IntBuffer.get(0) > 0)
	{
	    int[] buffer = new int[1];
	    
	    AL.alSourceUnqueueBuffers(source.get(0), 1, IntBuffer.wrap(buffer));
	    debugMsg("update(): buffer unqueued => " + buffer[0]);

	    active = stream(buffer[0]);
	    debugMsg("update(): buffer queued => " + buffer[0]);
            
	    AL.alSourceQueueBuffers(source.get(0), 1, IntBuffer.wrap(buffer));

	    b_IntBuffer.array()[0]--;
            
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
	    if ((size = m_OggDecoder.read(pcm)) <= 0)
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
	//check();
	
	return true;
        
    }

    /**
     * Empties the queue
     */
    protected void empty() 
    {
	AL.alGetSourcei(source.get(0), AL.AL_BUFFERS_QUEUED, b_IntBuffer);
	
	while (b_IntBuffer.get(0) > 0)
	{
	    int[] buffer = new int[1];
            
	    AL.alSourceUnqueueBuffers(source.get(0), 1, IntBuffer.wrap(buffer));
	    b_IntBuffer.array()[0]--;
            
	}
        
    }
    
    /**
     * The main loop to initialize and play the entire stream
     */
    public void playstream() 
    {
        m_PlaybackThread.start();
        
    }
    
    private class PlaybackThread extends Thread
    {
        private volatile boolean exit = false;
        public void kill(){exit = true;}
            
        @Override public void run() 
        {
            playback();     
            while (update())
            {
                try {Thread.sleep(sleepTime);} 
                catch (InterruptedException ex) {Logger.getLogger(OggStreamer.class.getName()).log(Level.SEVERE, null, ex);}
            
            }
            
        }
        
    }
    
    //************
    // Constructor
    //************
    public OggStreamer(URL url) 
    {
        m_OggDecoder = new OggDecoder(url);
        
        if (!m_OggDecoder.initialize()) 
            Debug.log("Error initializing ogg stream...");

	int numChannels = m_OggDecoder.numChannels();
	int numBytesPerSample = 2;

        if (numChannels == 1)
	    format = AL.AL_FORMAT_MONO16;
	else
	    format = AL.AL_FORMAT_STEREO16;
        
	rate = m_OggDecoder.sampleRate();

	sleepTime = (long)(1000.0 * BUFFER_SIZE / numBytesPerSample / numChannels / rate / 10.0);
	sleepTime = (sleepTime + 10)/10 * 10;
        
	System.err.println("#Buffers: " + NUM_BUFFERS);
	System.err.println("Buffer size: " + BUFFER_SIZE);
	System.err.println("Format: 0x" + Integer.toString(format, 16));
	System.err.println("Sleep time: " + sleepTime);

	m_OggDecoder.setSwap(true);

        AL.alGenBuffers(NUM_BUFFERS, buffers);
        AL.alGenSources(1, source);

	AL.alSourcefv(source.get(0), AL.AL_POSITION , FloatBuffer.wrap(sourcePos));
	AL.alSourcefv(source.get(0), AL.AL_VELOCITY , FloatBuffer.wrap(sourceVel));
	AL.alSourcefv(source.get(0), AL.AL_DIRECTION, FloatBuffer.wrap(sourceDir));
        
        AL.alSourcef(source.get(0), AL.AL_ROLLOFF_FACTOR,  0.0f    );
        AL.alSourcei(source.get(0), AL.AL_SOURCE_RELATIVE, AL.AL_TRUE);
        
    }
    
}