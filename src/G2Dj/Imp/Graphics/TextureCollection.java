/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Files;
import G2Dj.Resources;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class TextureCollection extends GraphicsResourceCollection<Texture>
{
    //*************
    // Data members
    //*************
    
    
    //
    //
    //
    @Override
    public WeakReference<Texture> loadFromResource(String aAbsoluteResourcePath) 
    {
        Texture newTexture = new Texture(Resources.loadImage(aAbsoluteResourcePath));
        
        addInstance(newTexture);
        
        return new WeakReference<>(newTexture);
    
    }

    @Override
    public WeakReference<Texture> loadFromFile(String aRelativeFilePath) 
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
        loadFromResource("/G2Dj/Resource/Graphics/default.png");
        loadFromResource("/G2Dj/Resource/Graphics/awesome.png");
        
    }
    
}
