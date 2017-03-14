/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Dev;

import G2Dj.Type.Engine.Scene;
import java.util.ArrayList;

/**
 *
 * @author Joe
 */
public abstract class SceneGraph 
{
    final Scene m_Scene;
    
    public abstract void update();
    
    protected SceneGraph(final Scene aScene){m_Scene = aScene;}
    
}
