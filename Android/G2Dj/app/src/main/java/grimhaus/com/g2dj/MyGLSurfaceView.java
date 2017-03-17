package grimhaus.com.g2dj;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Joe on 3/16/2017.
 */
class MyGLSurfaceView extends GLSurfaceView
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

    }
}