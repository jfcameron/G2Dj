/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package G2Dj.Imp.Graphics;

import G2Dj.Resource.Graphics.Quad;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class ModelCollection extends GraphicsResourceCollection<Model>
{
    //*************
    // Data members
    //*************
    
    
    //
    //
    //
    /*@Override
    public void add(final Model aItem)
    {
        super.add(aItem);
        
    }*/
    
    @Override
    public WeakReference<Model> loadFromResource(String aAbsoluteResourcePath) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public WeakReference<Model> loadFromFile(String aRelativeFilePath) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }
    
    //*************
    // Constructors
    //*************
    public ModelCollection()
    {
        addClass(Quad.class);
        
    }
    
}
