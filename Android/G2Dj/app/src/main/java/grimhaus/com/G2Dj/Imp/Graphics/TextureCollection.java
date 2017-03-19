/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Graphics;

import grimhaus.com.G2Dj.Files;
import grimhaus.com.G2Dj.Resources;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class TextureCollection extends GraphicsResourceCollection<Texture>
{
    @Override
    public final WeakReference<Texture> loadFromResource(String aAbsoluteResourcePath) 
    {
        Texture newTexture = new Texture(Resources.loadImage(aAbsoluteResourcePath));
        
        addInstance(newTexture);
        
        return new WeakReference<>(newTexture);
    
    }

    @Override
    public final WeakReference<Texture> loadFromFile(String aRelativeFilePath) 
    {
        Texture newTexture = new Texture(Files.loadImage(aRelativeFilePath));
        
        addInstance(newTexture);
        
        return new WeakReference<>(newTexture);
        
    }
        
    //*************
    // Constructors
    //*************
    public TextureCollection()
    {
        //.if DESKTOP
        //|loadFromResource("/grimhaus/com/G2Dj/Resource/Graphics/default.png");
        //|loadFromResource("/grimhaus/com/G2Dj/Resource/Graphics/awesome.png");
        //.elseif ANDROID
        //loadFromResource("/grimhaus/com/G2Dj/Resource/Graphics/awesome.png");
        //.endif

    }
    
}
