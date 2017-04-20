/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package SpriteSheetTest;


import grimhaus.com.G2Dj.Time;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Graphics.SpriteSheet;
import grimhaus.com.G2Dj.Type.Math.IntVector2;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class Slime extends Component
{
    private SpriteSheet m_SpriteSheet = null;
    private float m_Timer = 0;
    private int frameIndex = 0;
    
    private IntVector2[] m_FrameIndexSet = new IntVector2[]
    {
        new IntVector2(0,0),
        new IntVector2(1,0),
        new IntVector2(2,0),
        new IntVector2(3,0),
        new IntVector2(2,0),
        new IntVector2(1,0),
        
    }; 

    @Override
    protected void initialize() 
    {
        m_SpriteSheet = (SpriteSheet)getGameObject().get().addComponent(SpriteSheet.class);
        m_SpriteSheet.setSpriteSheet("Slime.png",16,16);
        m_SpriteSheet.setCurrentCell(0, 0);
        
    }

    @Override
    protected void update() 
    {
        if (m_Timer > 0.2f)
        {
            m_Timer = 0;
            
            frameIndex+=1;
            
            if (frameIndex >= m_FrameIndexSet.length)
                frameIndex=0;
            
            m_SpriteSheet.setCurrentCell(m_FrameIndexSet[frameIndex].x, m_FrameIndexSet[frameIndex].y);
            
        }
        
        m_Timer += (float)Time.getDeltaTime();
        
    }

    @Override
    protected void fixedUpdate() 
    {
        
        
    }

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {
    }

    @Override
    protected void OnRemovedFromGameObject() {
    }

    @Override
    protected void OnComponentAdded(Component aComponent) {
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) {
    }
    
}
