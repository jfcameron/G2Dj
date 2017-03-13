/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Debug;
import G2Dj.Graphics;
import G2Dj.Resources;
import com.jogamp.opengl.GL;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private final int m_TextureHandle;
    
    //
    // Accessors
    //
    public int getHandle(){return m_TextureHandle;}
    
    //
    // Constructors
    //
    public Texture(final String aTextureFileName/*, final int aRepeatMode, final int aMagfilter*/)
    {
	//m_TextureHandle = 0;
        m_Name = "SomeTexture";

        GL gl = Graphics.getGL();
        
        Debug.log("Texture::Texture(const std::string &aTextureFileName, GFXuint repeatmode, GFXuint magfilter)\n");
        Debug.log("Loading texture: "+m_Name);

        //load texture
        {
                
            BufferedImage img = null;// new BufferedImage(w, h, BufferedImage.YTPE_4BYTE_ABGR);
            /*try 
            {
                img = ImageIO.read(new File("awesome.png"));/////////////////THIS LOADS FROM FILE AND IT OWRKS
                
            }
            catch (IOException ex) 
            {
                Logger.getLogger(Texture.class.getName()).log(Level.SEVERE, null, ex);
                
            }*/
            
            img = Resources.readFileToImage("/G2Dj/Resource/Graphics/awesome.png"); ///////////THIS LOADS FROM PACKAGE AND WORKS
            
            Debug.log("Dimensons: {"+img.getWidth()+", "+img.getWidth()+"}");

            int[] data = img.getRGB(0, 0, img.getWidth(), img.getHeight(), (int[])null, 0, img.getWidth());
            
            //Change from BGRA to RGBA
            for(int i = 0, pixel,r,g,b,a; i < data.length; i++)
            {             
                pixel = data[i];
                a = (pixel >> 24) & 0xff;
                r = (pixel >> 16) & 0xff;
                g = (pixel >> 8) & 0xff;
                b = (pixel >> 0) & 0xff;
                pixel = (b<<16) | (g<<8) | (r<<0) | (a<<24);
                data[i] = pixel;
                             
            }
            
            IntBuffer pngbuffer = IntBuffer.wrap(data);
            
            Debug.log("This is how long the intbuffer is: "+pngbuffer.array().length);
            Debug.log("This is the size of a {RGBA} in bits: "+Integer.SIZE);
            
            //push texture data to video memory
            IntBuffer texturehandle = IntBuffer.allocate(1);//.get(0);
            //int[] texturehandle = new int[1];
                
            int textureFormat = GL.GL_RGBA;
            
            //Put the texture data in video memory
            gl.glGenTextures( 1, texturehandle );
            gl.glActiveTexture( GL.GL_TEXTURE0 );
            gl.glBindTexture( GL.GL_TEXTURE_2D, texturehandle.get(0) );
            gl.glTexImage2D( GL.GL_TEXTURE_2D, 0, textureFormat, img.getWidth(), img.getHeight(), 0, textureFormat, GL.GL_UNSIGNED_BYTE, pngbuffer );
            
            //Apply parameters
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST );
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST );
            
            m_TextureHandle = texturehandle.get(0);
                
        }
    
    }
    
    //Texture(const unsigned char aByteArray[], const unsigned int &aByteArrayLength, GFXuint repeatmode = 0, GFXuint magfilter = 0);
    //Texture(std::string &aName, const GFXuint &aTextureHandle);
    //Texture();
    
}
