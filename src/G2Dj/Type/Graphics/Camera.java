/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package G2Dj.Type.Graphics;

import G2Dj.Graphics;
import G2Dj.Imp.Graphics.CameraClearMode;
import G2Dj.Imp.Graphics.Color;
import G2Dj.Type.Math.IntVector2;
import G2Dj.Type.Math.Vector2;
import G2Dj.Type.Engine.Component;
import G2Dj.Type.Engine.GameObject;
import com.jogamp.opengl.GL;
import java.lang.ref.WeakReference;

/**
 *
 * @author Joe
 */
public class Camera extends Component
{
    //*************
    // Data members
    //*************
    private Color           m_ClearColor;		
    private CameraClearMode m_ClearMode;
    private Vector2         m_ViewportPosition;
    private Vector2         m_ViewportSize;
    //private RenderTexture m_RenderTexture;
    
    //**********
    // Accessors
    //**********
    public Color           getClearColor            (){return m_ClearColor;      }
    public CameraClearMode getClearMode             (){return m_ClearMode;       }
    public Vector2         getViewportScreenPosition(){return m_ViewportPosition;}
    public Vector2         getViewportScreenSize    (){return m_ViewportSize;    }
    //Math::Vector3 getWorldPointFromScreenPoint(const Math::Vector2 &aScreenPoint);
    //Math::Vector3 getWorldPointFromScreenPoint(const Math::Vector2 &aScreenPoint, const float &aWorldSpaceDistance);
    
    public float getViewportAspectRatio()
    {
        return getViewportPixelSize().toVector2().aspectRatio();
    
    }
    
    public IntVector2 getViewportPixelPosition()
    {
        return new IntVector2
        (
            (int)(m_ViewportPosition.x * Graphics.getWindow().getWidth ()),
            (int)(m_ViewportPosition.y * Graphics.getWindow().getHeight())
                
        );
        
    }
    
    public IntVector2 getViewportPixelSize()
    {
        return new IntVector2
        (
            (int)(m_ViewportSize.x * Graphics.getWindow().getWidth ()),
            (int)(m_ViewportSize.y * Graphics.getWindow().getHeight ())
                
        );
        
    }
    
    public void setClearColor           (final Color           aClearColor      ){m_ClearColor       = aClearColor;      }
    public void setClearMode            (final CameraClearMode aClearMode       ){m_ClearMode        = aClearMode;       }
    public void setViewportPixelPosition(final Vector2         aViewportPosition){m_ViewportPosition = aViewportPosition;}
    public void setViewportPixelSize    (final Vector2         aViewportSize    ){m_ViewportSize     = aViewportSize;    }
      
    public void draw() //void draw(void) override;
    {   
        GL gl = Graphics.getGL();
        
        //Update viewport
        IntVector2 pixPos = getViewportPixelPosition(), pixSize = getViewportPixelSize();
        //Debug.log(pixSize);
        gl.glViewport(pixPos.x,pixPos.y,pixSize.x,pixSize.y);
        gl.glScissor (pixPos.x,pixPos.y,pixSize.x,pixSize.y);
          
        gl.glDepthMask(true);  
        
        switch(m_ClearMode)
        {
            case Color:
            {
                gl.glClearColor(m_ClearColor.r,m_ClearColor.g,m_ClearColor.b,m_ClearColor.a);
                gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
            
            } break;
            
            case DepthOnly:
            {
                gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
                
            } break;
            
        }
        
    }
    
    //void generateViewProjectionMatrix(mat4x4* aViewMatrix, mat4x4* aProjectionMatrix, mat4x4* aVPMatrix = 0);
    
    //*************
    // Constructors
    //*************
    public Camera()
    {
        m_ClearColor       = Color.CornflowerBlue();		
        m_ClearMode        = CameraClearMode.Color;
        m_ViewportPosition = Vector2.Zero();
        m_ViewportSize     = new Vector2(1,1);
        
        //GL init
        GL gl = Graphics.getGL();
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_SCISSOR_TEST);
        
    }
    
    //
    // Component
    //
    @Override
    public void update() {}

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {}
    
    @Override
    protected void OnRemovedFromGameObject(){}

    
    
}
