package grimhaus.com.G2Dj.Android;

//.if ANDROID
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import grimhaus.com.G2Dj.Debug;
import grimhaus.com.G2Dj.Type.Math.IntVector2;

/*
* Android app entry point
*
* */
public class MainActivity extends Activity
{
    private final int FPS = 40;

    private static MainActivity s_MainActivity;

    static public GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Debug.log("G2Dj Android Begins!");

        s_MainActivity = this;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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

    public static IntVector2 getScreenSize()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        s_MainActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        return new IntVector2(displayMetrics.widthPixels,displayMetrics.heightPixels);

    }

    public static InputStream loadAsset(final String aAssetPath)
    {
        InputStream data = null;

        try
        {
            data = s_MainActivity.getAssets().open(aAssetPath);//"book/contents.json"
        }
        catch (IOException e) {Debug.log("Not found!: "+aAssetPath);e.printStackTrace();}

        return data;

    }

}
//.endif
