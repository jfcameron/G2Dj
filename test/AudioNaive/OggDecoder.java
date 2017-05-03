/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package AudioNaive;

import de.jarnbjo.ogg.CachedUrlStream;
import de.jarnbjo.ogg.EndOfOggStreamException;
import de.jarnbjo.ogg.LogicalOggStream;
import de.jarnbjo.vorbis.IdentificationHeader;
import de.jarnbjo.vorbis.VorbisStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class OggDecoder 
{    
    private static int BLOCK_SIZE = 4096*64;

    private VorbisStream vStream;
    private LogicalOggStream loStream;
    private javax.sound.sampled.AudioInputStream ais;
    private IdentificationHeader vStreamHdr;

    private AudioFormat audioFormat;

    private URL url;
    private boolean swap = false;
    private boolean endOfStream = false;

    public OggDecoder(URL url) 
    {
	this.url = url;
        
    }

    public boolean initialize() 
    {
	try 
        {
	    CachedUrlStream os = new CachedUrlStream(url);

	    loStream = (LogicalOggStream)os.getLogicalStreams().iterator().next();
	    vStream = new VorbisStream(loStream);
	    vStreamHdr = vStream.getIdentificationHeader();

	    audioFormat = new AudioFormat(
				(float)vStreamHdr.getSampleRate(),
				16,
				vStreamHdr.getChannels(),
				true, true);
	    
	    ais = new AudioInputStream(
			new VorbisInputStream(vStream), audioFormat, -1);

	} 
        catch (Exception e) 
        {
	    e.printStackTrace();
	    return false;
            
	}

	return true;
        
    }

    public int numChannels() 
    {
	return vStreamHdr.getChannels();
        
    }

    public int sampleRate() 
    {
	return vStreamHdr.getSampleRate();
        
    }

    public void setSwap(boolean swap) 
    {
	this.swap = swap;
        
    }

    /**
     * Swaps bytes.
     * @throws ArrayOutOfBoundsException if len is not a multiple of 2.
     */
    public static void swapBytes(byte[] b) 
    {
	swapBytes(b, 0, b.length);
        
    }

    public static void swapBytes(byte[] b, int off, int len) 
    {
	byte tempByte;
	for (int i = off; i < (off+len); i+=2) 
        {
	    tempByte = b[i];
	    b[i] = b[i+1];
	    b[i+1] = tempByte;
            
	}
        
    }

    // play using JavaSound
    public void play() 
    {
	if (!initialize())
	    return;

	dump();

	try 
        {
	    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
	    SourceDataLine sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
	    
	    sourceDataLine.open(audioFormat);
	    sourceDataLine.start();
	    
	    byte[] buffer = new byte[BLOCK_SIZE];
	    int bytesRead;
	    
	    while (true) {
		if ((bytesRead = read(buffer)) > 0)
		    sourceDataLine.write(buffer, 0, bytesRead);

		if (bytesRead < buffer.length)
		    break;
	    }
	    
	    sourceDataLine.drain();
	    sourceDataLine.close();
            
	}
        catch(Exception e) {e.printStackTrace();}
        
    }
    
    // play using JavaSound
    public void toraw(String fileName) 
    {
	if (!initialize())
	    return;

	setSwap(true);
	dump();

	try 
        {
	    byte[] buffer = new byte[BLOCK_SIZE];
	    int bytesRead;
	    
	    FileOutputStream fos = new FileOutputStream(fileName);

	    while (true) {
		if ((bytesRead = read(buffer)) > 0)
		    fos.write(buffer, 0, bytesRead);

		if (bytesRead < buffer.length)
		    break;
	    }

	    fos.close();
            
	} 
        catch(Exception e) {e.printStackTrace();}
        
    }
    
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

    public void dump() 
    {
	System.err.println("#Channels: " + vStreamHdr.getChannels());
	System.err.println("Sample rate: " + vStreamHdr.getSampleRate());
	System.err.println("Bitrate: nominal="
				 + vStreamHdr.getNominalBitrate() +
			", max=" + vStreamHdr.getMaximumBitrate() +
			", min=" + vStreamHdr.getMinimumBitrate());
        
    }

    public static class VorbisInputStream extends InputStream 
    {
        
        private VorbisStream source;
        
        public VorbisInputStream(VorbisStream source) 
        {
            this.source = source;
            
        }
        
        public int read() throws IOException 
        {
            return 0;
            
        }
        
        public int read(byte[] buffer) throws IOException 
        {
            return read(buffer, 0, buffer.length);
            
        }
        
        public int read(byte[] buffer, int offset, int length) throws IOException 
        {
            try 
            {
                return source.readPcm(buffer, offset, length);
                
            } 
            catch(EndOfOggStreamException e) {return -1;}
            
        }
        
    }
    
    public static void main(String args[]) 
    {
	URL url;
	int i = 0;
	String rawname = null;

	try 
        {
	    if (args.length == 0) 
            {
		url = OggDecoder.class.getClassLoader().getResource("demos/data/broken_glass.ogg");
		(new OggDecoder(url)).play();
                
	    }

	    for (; i < args.length; i++) 
            {
		if (args[i].equals("-r")) 
                {
		    rawname = args[++i];
		    continue;
		}

		System.err.println("Playing: " + args[i]);

		url = ((new File(args[i])).exists()) ?
                                new URL("file:" + args[i]) : new URL(args[i]);

		if (rawname != null)	(new OggDecoder(url)).toraw(rawname);
		else			(new OggDecoder(url)).play();
                
	    }
            
	} 
        catch (Exception e) {e.printStackTrace();}
        
    }
    
}