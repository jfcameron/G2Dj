package grimhaus.com.G2Dj.android;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by Joe on 3/17/2017.
 */
public class TestGameObject
{
    public void update()
    {
        Log.d("d","Test update");



    }

    public void draw()
    {
        Log.d("d","Test draw");

        GLES20.glClearColor
                (
                        1f,
                        1f,
                        0,
                        1.0f

                );

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);


    }

}
