package grimhaus.com.g2dj;

import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;


/*
* Android app entry point
*
* */
public class MainActivity extends Activity
{
    private final int FPS = 40;

    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run(){main();}

        }, 0, 1000 / FPS);


    }

    private void main()
    {


        GLES20.glClearColor
                (
                        1f,
                        0f,
                        0f,
                        1.0f

                );

    }

}
