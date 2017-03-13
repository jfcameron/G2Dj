/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    @Override
    public void add(final Model aItem)
    {
        super.add(aItem);
        
    }
    
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
        add(new Quad());
        
    }
    
}
