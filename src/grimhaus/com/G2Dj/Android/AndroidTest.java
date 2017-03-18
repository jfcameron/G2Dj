package grimhaus.com.G2Dj.Android;

import java.lang.ref.WeakReference;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Engine;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Engine.Scene;
import grimhaus.com.G2Dj.Type.Graphics.Camera;

/**
 * Created by Joe on 3/18/2017.
 */

public class AndroidTest
{
    public static void doTest()
    {
        Debug.log("The Android test is beginning");

        WeakReference<Scene> mainScene = Engine.createScene("main");

        WeakReference<GameObject> playerCamera = mainScene.get().addGameObject();
        playerCamera.get().setName("Player Camera");

        playerCamera.get().addComponent(Camera.class);

    }

}
