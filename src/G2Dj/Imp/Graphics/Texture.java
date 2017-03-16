/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package G2Dj.Imp.Graphics;

import G2Dj.Debug;
import G2Dj.Graphics;
import G2Dj.Resources;
import G2Dj.Type.Resources.Image;
import com.jogamp.opengl.GL;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

/**
 *
 * @author Joe
 */
public class Texture extends GraphicsResource 
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
    public Texture(/*final String aName,*/ final Image aImage/*, final int aRepeatMode, final int aMagfilter*/)
    {
        m_Name = aImage.getName();
        
        GL gl = Graphics.getGL();
        
        Debug.log("Texture::Texture(const std::string &aTextureFileName, GFXuint repeatmode, GFXuint magfilter)\n");
        Debug.log("Loading texture: "+m_Name);

        //load texture      
        int width = aImage.getData().getWidth(),height = aImage.getData().getHeight();
            
        Debug.log("Dimensons: {"+width+", "+width+"}");

        int[] data = aImage.getData().getRGB(0, 0, width, height, (int[])null, 0, width);
            
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
            
        Debug.log("This is how long the intbuffer is: "    + pngbuffer.array().length );
        Debug.log("This is the size of a {RGBA} in bits: " + Integer.SIZE             );
            
        //push texture data to video memory
        IntBuffer texturehandle = IntBuffer.allocate(1);
                
        int textureFormat = GL.GL_RGBA;
            
        //Put the texture data in video memory
        gl.glGenTextures( 1, texturehandle );
        gl.glActiveTexture( GL.GL_TEXTURE0 );
        gl.glBindTexture( GL.GL_TEXTURE_2D, texturehandle.get(0) );
        gl.glTexImage2D( GL.GL_TEXTURE_2D, 0, textureFormat, width, height, 0, textureFormat, GL.GL_UNSIGNED_BYTE, pngbuffer );
            
        //Apply parameters
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST );
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST );
            
        m_TextureHandle = texturehandle.get(0);
        
        Debug.log("Handle is: "+m_TextureHandle);
        
        gl.glBindTexture( GL.GL_TEXTURE_2D,0);//clear the binding
        
    }
    
}
