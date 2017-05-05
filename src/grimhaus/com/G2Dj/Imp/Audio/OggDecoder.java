/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Audio;

import java.util.ArrayList;

import grimhaus.com.G2Dj.Debug;

import de.jarnbjo.ogg.EndOfOggStreamException;
import de.jarnbjo.ogg.CachedUrlStream;
import de.jarnbjo.ogg.LogicalOggStream;
import de.jarnbjo.vorbis.IdentificationHeader;
import de.jarnbjo.vorbis.VorbisStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//will be a desktop/android implementation split
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class OggDecoder 
{    
    //*************
    // Data members
    //*************
    //private VorbisStream         vStream;
    //private LogicalOggStream     loStream;
    private IdentificationHeader vStreamHdr;
    
    private AudioFormat      audioFormat;
    private ReusableAudioStream ais;
    
    private boolean swap        = true;
    private boolean endOfStream = false;
    
    //**********
    // Accessors
    //**********
    public int getNumChannels(){return vStreamHdr.getChannels();}
    public int getSampleRate() {return vStreamHdr.getSampleRate();}
    
    public void setSwap(boolean swap){this.swap = swap;}
    
    public void reset()
    {
        ais.reset();
        
    }
    
    private byte[] addBuffers(final byte[] aBuffer1, final byte[] aBuffer2)
    {
        byte[] temp = new byte[aBuffer1.length+aBuffer2.length];
        
        for(int i=0;i<aBuffer1.length;i++)
            temp[i]=aBuffer1[i];
        
        for(int i=0;i<aBuffer2.length;i++)
            temp[i+aBuffer1.length]=aBuffer2[i];
        
        return temp;
        
    }
    
    //*****************
    // Public interface
    //*****************
    public byte[] DUMPTEST()
    {
        Debug.log("DUMPTESTDUMPTESTDUMPTESTDUMPTESTDUMPTESTDUMPTESTDUMPTEST");
        
        byte[] rValue = new byte[0]; 
        {
            byte[] buffer = new byte[1024*1];
            
            //todo: work
            for(;;)
            {
            try 
            {
                if (read(buffer) <= 0)
                    break;
                
            } 
            catch (IOException ex) {Logger.getLogger(OggDecoder.class.getName()).log(Level.SEVERE, null, ex);}
            
            rValue = addBuffers(rValue,buffer);
            }
            
        }
        
        return rValue;
        /*
        byte[] asdf = new byte[1024*64];
        
        try 
        {
            read(asdf);
        
        } 
        catch (IOException ex) {Logger.getLogger(OggDecoder.class.getName()).log(Level.SEVERE, null, ex);}
        
        return asdf;*/
        /*
        byte[] pcm = new byte[4096*16];
	int    size = 0;
        
	try
        {
	    if ((size = read(pcm)) <= 0)
            {}
            
	}
        catch (Exception e)
        {
	    e.printStackTrace();
            
	}*/

        
        
    }
    
    
    //read data into buffer, return # of bytes read
    public int read(byte[] buffer) throws IOException 
    {
	if (endOfStream)
	    return -1;

	int bytesRead = 0, cnt = 0;

	while (bytesRead < buffer.length) 
        {
	    if ((cnt = ais.read(buffer, bytesRead, buffer.length-bytesRead)) <= 0) 
            {
		endOfStream = true;
		break;
                
	    }
            
	    bytesRead += cnt;
            
	}

	if (swap)
	    swapBytes(buffer, 0, bytesRead);

	return bytesRead;
        
    }
    
    //***************
    // Implementation
    //***************    
    private void swapBytes(byte[] b, int off, int len) 
    {
	byte tempByte;
        
	for (int i = off; i < (off+len); i+=2) 
        {
	    tempByte = b[i];
	    b[i] = b[i+1];
	    b[i+1] = tempByte;
            
	}
        
    }

    //************
    // Constructor
    //************
    public OggDecoder(URL url) 
    {
        try 
        {
	    CachedUrlStream os = new CachedUrlStream(url);

	    LogicalOggStream loStream = (LogicalOggStream)os.getLogicalStreams().iterator().next();
	    VorbisStream vStream = new VorbisStream(loStream);
            
	    vStreamHdr = vStream.getIdentificationHeader();

	    audioFormat = new AudioFormat
            (
                (float)vStreamHdr.getSampleRate(),
		16,
		vStreamHdr.getChannels(),
		true, 
                true
            
            );
	    
	    ais = new ReusableAudioStream(new VorbisInputStream(vStream), audioFormat, -1);
            ais.mark(Integer.MAX_VALUE);
            //setSwap(true);
            
	} 
        catch (Exception e) 
        {
	    e.printStackTrace();
            Debug.log("Error initializing ogg stream...");
            
	}
        
    }
    
    //**************************
    // Private class definitions
    //**************************
    private static class VorbisInputStream extends InputStream 
    {
        private final VorbisStream source;
        
        @Override public int read() throws IOException {return 0;}
        @Override public int read(byte[] buffer) throws IOException {return read(buffer, 0, buffer.length);}
        
        @Override public int read(byte[] buffer, int offset, int length) throws IOException 
        {
            try {return source.readPcm(buffer, offset, length);} 
            catch(EndOfOggStreamException e) {return -1;}
            
        }
        
        public VorbisInputStream(VorbisStream aSource)
        {
            source = aSource;
        
        }
        
    }
    
    private class ReusableAudioStream extends AudioInputStream
    {
        public ReusableAudioStream(InputStream stream, AudioFormat format, long length){super(stream, format, length);}
        
        @Override
        public void reset()
        {
            try 
            {
                super.reset();
                //framePos = 0;
            } 
            catch (IOException ex) {Logger.getLogger(OggDecoder.class.getName()).log(Level.SEVERE, null, ex);}
            
        }
        
        
    }
    
    //**********************
    // String representation
    //**********************
    @Override public String toString()
    {
        return new StringBuilder()
	.append("#Channels: ")      .append(vStreamHdr.getChannels      ())
	.append("Sample rate: ")    .append(vStreamHdr.getSampleRate    ())
	.append("Bitrate: nominal=").append(vStreamHdr.getNominalBitrate())
        .append(", max=")           .append(vStreamHdr.getMaximumBitrate())
        .append(", min=")           .append(vStreamHdr.getMinimumBitrate())
        .toString();
        
    }
    
}