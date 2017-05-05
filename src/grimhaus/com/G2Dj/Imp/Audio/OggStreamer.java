/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Audio;

import grimhaus.com.G2Dj.Imp.Audio.OggDecoder;
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
    //**********
    // Constants
    //**********
    private static final int BUFFER_SIZE = 4096*16;// The size of a chunk from the stream that we want to read for each update.    
    private static final int NUM_BUFFERS = 2;// The number of buffers used in the audio pipeline
    
    //*************
    // Data members
    //*************
    private final OggDecoder     m_OggDecoder;
    private final PlaybackThread m_PlaybackThread = new PlaybackThread();
    
    //Sound data
    private IntBuffer buffers = IntBuffer.allocate(NUM_BUFFERS);// Buffers hold sound data. There are two of them by default (front/back)
    
    private final int format; // OpenAL data format
    private final int rate; // sample rate
    private long sleepTime = 0;
    private int totalBytes = 0;
    
    //AudioSource data
    private float[] sourcePos = { 0.0f, 0.0f, 0.0f };
    private float[] sourceVel = { 0.0f, 0.0f, 0.0f };
    private float[] sourceDir = { 0.0f, 0.0f, 0.0f };
    private IntBuffer source = IntBuffer.allocate(1);
    
    //Buffers
    IntBuffer b_IntBuffer = IntBuffer.allocate(1);
    
    //*****************
    // Public interface
    //*****************
    public void play() 
    {
        m_PlaybackThread.start(); 
        
    }
    
    //***************
    // Implementation
    //***************
    private void release() 
    {
	AL.alSourceStop(source.get(0));
	empty();

	for (int i = 0; i < NUM_BUFFERS; i++)
	    AL.alDeleteSources(i, source);
        
    }
    
    private boolean playback() 
    {
	if (playing())
	    return true;
        
	for (int i = 0; i < NUM_BUFFERS; i++) 
        {
	    if (!stream(buffers.get(i)))
		return false;
            
	}
    
	AL.alSourceQueueBuffers(source.get(0), NUM_BUFFERS, buffers);
	AL.alSourcePlay(source.get(0));
    
        return true;
        
    }
    
    //Check if the source is playing
    private boolean playing() 
    {
	AL.alGetSourcei(source.get(0), AL.AL_SOURCE_STATE, b_IntBuffer);
	return (b_IntBuffer.get(0) == AL.AL_PLAYING);
        
    }
    
    //Update the stream if necessary
    private boolean update() 
    {
	boolean active = true;
        
	AL.alGetSourcei(source.get(0), AL.AL_BUFFERS_PROCESSED, b_IntBuffer);

	while (b_IntBuffer.get(0) > 0)
	{
	    int[] buffer = new int[1];
	    
	    AL.alSourceUnqueueBuffers(source.get(0), 1, IntBuffer.wrap(buffer));
	    
	    active = stream(buffer[0]);
	    
	    AL.alSourceQueueBuffers(source.get(0), 1, IntBuffer.wrap(buffer));

	    b_IntBuffer.array()[0]--;
            
	}

	return active;
        
    }
    
    //Reloads a buffer (reads in the next chunk)
    private boolean stream(int buffer) 
    {
	byte[] pcm = new byte[BUFFER_SIZE];
	int    size = 0;
        
	try 
        {
	    if ((size = m_OggDecoder.read(pcm)) <= 0)
		return false;
            
	} 
        catch (Exception e) 
        {
	    e.printStackTrace();
	    return false;
            
	}

	totalBytes += size;
	
	ByteBuffer data = ByteBuffer.wrap(pcm, 0, size);
	AL.alBufferData(buffer, format, data, size, rate);
	
	return true;
        
    }
    
    //Empties the queue
    private void empty() 
    {
	AL.alGetSourcei(source.get(0), AL.AL_BUFFERS_QUEUED, b_IntBuffer);
	
	while (b_IntBuffer.get(0) > 0)
	{
	    int[] buffer = new int[1];
            
	    AL.alSourceUnqueueBuffers(source.get(0), 1, IntBuffer.wrap(buffer));
	    b_IntBuffer.array()[0]--;
            
	}
        
    }
        
    //************
    // Constructor
    //************
    public OggStreamer(URL aURL) 
    {
        m_OggDecoder = new OggDecoder(aURL);
        
	int numChannels = m_OggDecoder.getNumChannels();
	int numBytesPerSample = 2;

        if (numChannels == 1)
	    format = AL.AL_FORMAT_MONO16;
	else
	    format = AL.AL_FORMAT_STEREO16;
        
	rate = m_OggDecoder.getSampleRate();

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
    
    //**************************
    // Private class definitions
    //**************************
    private class PlaybackThread extends Thread
    {
        private volatile boolean exit = false;
        private volatile boolean canStart = true;
        
        public void kill(){exit = true;}
            
        @Override public void start()
        {
            if (canStart)
                super.start();
            
            canStart = false;
            
        }
        
        @Override public void run() 
        {
            playback();  
            
            while (update())
            {
                try {Thread.sleep(sleepTime);} 
                catch (InterruptedException ex) {Logger.getLogger(OggStreamer.class.getName()).log(Level.SEVERE, null, ex);}
            
            }
            
            canStart = true;
            m_OggDecoder.reset();
            
        }
        
    }
    
}