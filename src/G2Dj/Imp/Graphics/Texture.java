/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Debug;
import G2Dj.Graphics;
import com.jogamp.opengl.GL;
import com.sun.prism.impl.BufferUtil;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Joe
 */
public class Texture extends GraphicsObject 
{
    //
    // Data members
    //
    private int m_TextureHandle;
    
    //
    // Accessors
    //
    public int getHandle(){return m_TextureHandle;}
    
    //
    // Constructors
    //
    public Texture(/*final String aTextureFileName, final int aRepeatMode, final int aMagfilter*/)
    {
	m_TextureHandle = 0;
        m_Name = "SomeTexture";

        GL gl = Graphics.getGL();
        
        Debug.log("Texture::Texture(const std::string &aTextureFileName, GFXuint repeatmode, GFXuint magfilter)\n");
        Debug.log("Loading texture: "+m_Name);

        //load texture
        {
            //String filename = aTextureFileName;
            
            {
                //unsigned char* pngbuffer;
		//unsigned int   width, height;

		//if (lodepng_decode32_file( &pngbuffer, &width, &height, filename.c_str()) != 0)
		{
                    //std::assert(0);
				
		}

		//FLIP THE DATA: required because opengl uv space's v is upside down...
		{
                
                }
                
                BufferedImage img = null;
                try 
                {
                    img = ImageIO.read(new File("brick.png"));
                
                }
                catch (IOException ex) 
                {
                    Logger.getLogger(Texture.class.getName()).log(Level.SEVERE, null, ex);
                
                }
                
                Debug.log("Dimensons: {"+img.getWidth()+", "+img.getWidth()+"}");
                
                
                DataBufferByte dbb = (DataBufferByte)img.getRaster().getDataBuffer();
                byte[] data = dbb.getData();
                //ByteBuffer pngbuffer = ByteBuffer.wrap(data); //BufferUtil.//newByteBuffer(data.length);
                //pngbuffer.put(data);
                //pngbuffer.flip();
                
                //img.getRaster().
                
                ByteBuffer pngbuffer = ByteBuffer.wrap
                (
                        //((DataBufferByte)img.getRaster().getDataBuffer()).getData()
                data
                );
                
                Debug.log("Byte array: "+pngbuffer.array().length);
                
		//push texture data to video memory
		//IntBuffer texturehandle = IntBuffer.allocate(1);//.get(0);
		int[] texturehandle = new int[1];
                
		gl.glGenTextures( 1, texturehandle,0 );
                gl.glActiveTexture( GL.GL_TEXTURE0 );
		gl.glBindTexture( GL.GL_TEXTURE_2D, texturehandle[0]/*texturehandle.get(0)*/ );
		gl.glTexImage2D( GL.GL_TEXTURE_2D, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, pngbuffer );
		
		//gl.glTexParameteri( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, repeatmode);
		//glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, repeatmode);
		    
		//glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magfilter );
		//glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR );

		//glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		    
		//add_Texture(filename,texturehandle);
		//m_Name = filename;
		m_TextureHandle = texturehandle[0]/*.get(0)*/;
                
            }
    
        }
    
    }
    
    //Texture(const unsigned char aByteArray[], const unsigned int &aByteArrayLength, GFXuint repeatmode = 0, GFXuint magfilter = 0);
    //Texture(std::string &aName, const GFXuint &aTextureHandle);
    //Texture();
    
}
