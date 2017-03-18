package grimhaus.com.G2Dj.Android;

//.if ANDROID
import android.content.Context;
import android.opengl.GLSurfaceView;

import grimhaus.com.G2Dj.Engine;

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
        setRenderMode(RENDERMODE_WHEN_DIRTY); //must be manually updated


    }

}

//.endif
