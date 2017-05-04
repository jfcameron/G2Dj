/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Audio;

import grimhaus.com.G2Dj.Debug;

import de.jarnbjo.ogg.EndOfOggStreamException;
import de.jarnbjo.ogg.CachedUrlStream;
import de.jarnbjo.ogg.LogicalOggStream;
import de.jarnbjo.vorbis.IdentificationHeader;
import de.jarnbjo.vorbis.VorbisStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
    private AudioInputStream ais;
    
    private boolean swap        = false;
    private boolean endOfStream = false;
    
    //**********
    // Accessors
    //**********
    public int getNumChannels(){return vStreamHdr.getChannels();}
    public int getSampleRate() {return vStreamHdr.getSampleRate();}
    
    public void setSwap(boolean swap){this.swap = swap;}
    
    //*****************
    // Public interface
    //*****************
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
	    
	    ais = new AudioInputStream(new VorbisInputStream(vStream), audioFormat, -1);
            
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