/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Type.Engine;

import G2Dj.Dev.SceneGraph;
import G2Dj.Imp.Graphics.GraphicsScene;
import java.util.ArrayList;

/**
 *
 * @author Joe
 */
public class Scene 
{
    //
    //
    //
    private final String m_Name;
    private final ArrayList<SceneGraph> m_SceneGraphs = new ArrayList<>();
    
    //
    //
    //
    public void update()
    {
        m_SceneGraphs.forEach((currentGraph)->{currentGraph.update();});
        
    }
    
    //
    //
    //
    //standard scene constructor
    public Scene(final String aName)
    {
        m_Name = aName;
        m_SceneGraphs.add(new GraphicsScene());
        
    }
    
}
