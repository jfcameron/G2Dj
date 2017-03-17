package grimhaus.com.G2Dj.android;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Joe on 3/16/2017.
 */

public class MyGLRenderer implements GLSurfaceView.Renderer
{
    static public GLES20 gl;

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
        Log.d("d","onSurfaceCreated");

        //gl = GLES20.

    }

    public void onDrawFrame(GL10 unused)
    {
        //GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //Log.d("d","onDrawFrame");
        TestEngine.draw();



    }

    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);


    }

    /**
     * Created by Joe on 3/16/2017.
     */
    static class MyGLSurfaceView extends GLSurfaceView
    {

        private final MyGLRenderer mRenderer;

        public MyGLSurfaceView(Context context)
        {
            super(context);



            // Create an OpenGL ES 2.0 context
            setEGLContextClientVersion(2);

            mRenderer = new MyGLRenderer();

            // Set the Renderer for drawing on the GLSurfaceView
            setRenderer(mRenderer);
            setRenderMode(RENDERMODE_WHEN_DIRTY); //must be manually updated


        }

    }
}