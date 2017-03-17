package grimhaus.com.g2dj;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Joe on 3/16/2017.
 */

public class MyGLRenderer implements GLSurfaceView.Renderer
{
    private Test m_Test = new Test();

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config)
    {
        // Set the background frame color
        GLES20.glClearColor
        (
                0.3921568627450980392156862745098f,
                0.58431372549019607843137254901961f,
                0.92941176470588235294117647058824f,
                1.0f

        );
        Log.d("d","Hello");
    }

    public void onDrawFrame(GL10 unused)
    {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        m_Test.update();

        Log.d("E","Hello");

    }

    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);


    }

}