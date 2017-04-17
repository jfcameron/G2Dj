/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package SpriteSheetTest;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Imp.Engine.RequireComponents;
import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.SpriteSheet;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
@RequireComponents({SpriteSheet.class})
public class Blocky extends Component
{
    private SpriteSheet m_SpriteSheet = null;
    private float m_Timer = 0;
    
    @Override protected void initialize() 
    {
        m_SpriteSheet = (SpriteSheet)getGameObject().get().getComponent(SpriteSheet.class);
        m_SpriteSheet.setTexture("_Texture", "Blocky.png");
        m_SpriteSheet.setCellSizeByPixel(16,17);
        m_SpriteSheet.setCurrentCell(1, 0);
    
    }
    
    @Override protected void update() 
    {
        if ((int)m_Timer % 2 == 0)
            m_SpriteSheet.setCurrentCell(0, 0);
        else
            m_SpriteSheet.setCurrentCell(1, 0);
        
        m_Timer += 5*Time.getDeltaTime();
            
    }
    
    @Override protected void fixedUpdate() {}
    
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}
