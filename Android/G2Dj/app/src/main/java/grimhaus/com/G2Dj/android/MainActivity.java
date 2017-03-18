package grimhaus.com.G2Dj.Android;

//.if ANDROID
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import grimhaus.com.G2Dj.Debug;

/*
* Android app entry point
*
* */
public class MainActivity extends Activity
{
    private final int FPS = 40;

    static public GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Debug.log("G2Dj Android Begins!");

        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                /*Engine.mainLoop();*/
                //TestEngine.update();
                loop();
                //TODO: add loop logic here

            }

        }, 0, 1000 / FPS);

        //throw new java.lang.UnsupportedOperationException("Not supported yet.");

    }

    private void loop()
    {
        //TestEngine.update();
        mGLView.requestRender();

    }

}
//.endif
