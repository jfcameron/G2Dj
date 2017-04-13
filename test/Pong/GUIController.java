/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package Pong;

import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Imp.Engine.SceneState;
import grimhaus.com.G2Dj.Imp.Input.KeyCode;
import grimhaus.com.G2Dj.Input;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joseph Cameron
 */
public class GUIController extends Component
{
    private WeakReference<Scene> m_GameScene = null;

    @Override
    protected void initialize() 
    {
        m_GameScene = Engine.getScene(Constants.MainSceneName);
        
    }

    @Override
    protected void update() 
    {
        if (Input.getKeyDown(KeyCode.Escape))
            if (m_GameScene.get().getSceneState() != SceneState.Paused)
                m_GameScene.get().setSceneState(SceneState.Paused);
            else
                m_GameScene.get().setSceneState(SceneState.Active);
        
        
    }

    @Override
    protected void fixedUpdate() 
    {
        
        
    }

    //
    //
    //
    @Override protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    @Override protected void OnRemovedFromGameObject() {}
    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}
    
}
