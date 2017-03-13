/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

/**
 *
 * @author Joe
 */
public class TextureCollection extends GraphicsObjectCollection<Texture>
{
    //*************
    // Data members
    //*************
    
    
    //
    //
    //
    @Override
    public void add(final Texture aItem)
    {
        super.add(aItem);
        
    }
    
    
    //*************
    // Constructors
    //*************
    public TextureCollection()
    {
        add(new Texture());
        
    }
        
}
