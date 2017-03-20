package grimhaus.com.G2Dj;

//.if ANDROID
//|import android.app.Activity;
//|import android.content.Context;
//|import android.content.pm.ActivityInfo;
//|import android.opengl.GLES20;
//|import android.opengl.GLSurfaceView;
//|import android.os.Bundle;
//|import android.util.DisplayMetrics;
//|import android.view.MotionEvent;
//|
//|import java.io.IOException;
//|import java.io.InputStream;
//|import java.nio.IntBuffer;
//|import java.util.Timer;
//|import java.util.TimerTask;
//|
//|import javax.microedition.khronos.opengles.GL10;
//|
//|import Adhoc.Main;
//|import grimhaus.com.G2Dj.Imp.Graphics.Color;
//|import grimhaus.com.G2Dj.Type.Math.IntVector2;
//|
//|/*
//|* Android app entry point
//|*
//|* */
//|public class Mobile extends Activity
//|{
//|    private final int FPS = 60;
//|
//|    private static Mobile s_MainActivity;
//|
//|    static protected GLSurfaceView mGLView;
//|
//|    @Override
//|    public void onCreate(Bundle savedInstanceState)
//|    {
//|        super.onCreate(savedInstanceState);
//|
//|        Debug.log("G2Dj Android Begins!");
//|
//|        s_MainActivity = this;
//|
//|        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//|
//|        mGLView = new MyGLSurfaceView(this);
//|        setContentView(mGLView);
//|
//|        Timer timer = new Timer();
//|        timer.scheduleAtFixedRate(new TimerTask()
//|        {
//|            @Override
//|            public void run()
//|            {
//|                loop();
//|
//|            }
//|
//|        }, 0, 1000 / FPS);
//|
//|    }
//|
//|    private void loop() //TODO: Marked for refactoring
//|    {
//|        mGLView.requestRender();
//|
//|    }
//|
//|    //*******************
//|    // GRAPHICS INTERFACE
//|    //*******************
//|    protected static IntVector2 getScreenSize()
//|    {
//|        DisplayMetrics displayMetrics = new DisplayMetrics();
//|        s_MainActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//|
//|        return new IntVector2(displayMetrics.widthPixels,displayMetrics.heightPixels);
//|
//|    }
//|
//|    protected static InputStream loadAsset(final String aAssetPath)
//|    {
//|        InputStream data = null;
//|
//|        try{data = s_MainActivity.getAssets().open(aAssetPath);}
//|        catch (IOException e) {Debug.log("Not found!: "+aAssetPath);e.printStackTrace();}
//|
//|        return data;
//|
//|    }
//|
//|    class MyGLSurfaceView extends GLSurfaceView
//|    {
//|        protected GLSurfaceView s_Instance;
//|
//|        private final MyGLRenderer mRenderer;
//|
//|
//|        @Override
//|        public boolean onTouchEvent(MotionEvent e)
//|        {
//|            Input.S_TouchHandler.update(e);
//|            return true;
//|
//|        }
//|
//|        public MyGLSurfaceView(Context context)
//|        {
//|            super(context);
//|
//|            // Create an OpenGL ES 2.0 context
//|            setEGLContextClientVersion(2);
//|
//|            mRenderer = new MyGLRenderer();
//|
//|            // Set the Renderer for drawing on the GLSurfaceView
//|            setRenderer(mRenderer);
//|            setRenderMode(RENDERMODE_WHEN_DIRTY); //must be manually updated
//|
//|            s_Instance = this;
//|
//|        }
//|
//|        public class MyGLRenderer implements GLSurfaceView.Renderer
//|        {
//|            @Override
//|            public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config)
//|            {
//|                Engine.init();
//|                Adhoc.Main.main(null);
//|
//|            }
//|
//|            @Override
//|            public void onDrawFrame(GL10 unused)
//|            {
//|                Engine.update();
//|                Engine.draw();
//|
//|            }
//|
//|            @Override
//|            public void onSurfaceChanged(GL10 unused, int width, int height){}
//|
//|        }
//|
//|    }
//|
//|}
//.endif
