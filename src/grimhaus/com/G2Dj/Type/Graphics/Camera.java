/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Graphics;
import grimhaus.com.G2Dj.Imp.Graphics.CameraClearMode;
import grimhaus.com.G2Dj.Imp.Graphics.CameraProjectionMode;
import grimhaus.com.G2Dj.Imp.Graphics.Color;
import grimhaus.com.G2Dj.Type.Math.IntVector2;
import grimhaus.com.G2Dj.Type.Math.Vector2;
import grimhaus.com.G2Dj.Type.Engine.Component;
import grimhaus.com.G2Dj.Type.Engine.GameObject;
import grimhaus.com.G2Dj.Type.Math.Mat4x4;
import grimhaus.com.G2Dj.Type.Math.Vector3;
import java.lang.ref.WeakReference;
import grimhaus.com.G2Dj.Imp.Graphics.GL;


/**
 *
 * @author Joe
 */
public class Camera extends Component
{
    //*************
    // Data members
    //*************
    private Color                m_ClearColor;		
    private CameraClearMode      m_ClearMode;
    private CameraProjectionMode m_ProjectionMode;
    private Vector2              m_ViewportPosition;
    private Vector2              m_ViewportSize;
    //private RenderTexture      m_RenderTexture;
    private float                m_FieldOfView;
    private float                m_NearClippingPlane;
    private float                m_FarClippingPlane;
    private float                m_Size;
    
    //buffers & pools
    private final Mat4x4 b_ProjectionMatrixBuffer = new Mat4x4();//reduce heap abuse in getProjectionMatrix()
    private final Mat4x4 b_ViewMatrixBuffer       = new Mat4x4();
    
    //**********
    // Accessors
    //**********
    public Color                getClearColor            (){return m_ClearColor;       }
    public CameraClearMode      getClearMode             (){return m_ClearMode;        }
    public CameraProjectionMode getProjectionMode        (){return m_ProjectionMode;   }
    public Vector2              getViewportScreenPosition(){return m_ViewportPosition; }
    public Vector2              getViewportScreenSize    (){return m_ViewportSize;     }
    //Math::Vector3 getWorldPointFromScreenPoint(const Math::Vector2 &aScreenPoint);
    //Math::Vector3 getWorldPointFromScreenPoint(const Math::Vector2 &aScreenPoint, const float &aWorldSpaceDistance);
    public float                getFieldOfView           (){return m_FieldOfView;      }
    public float                getNearClippingPlane     (){return m_NearClippingPlane;}
    public float                getFarClippingPlane      (){return m_FarClippingPlane; }
    public float                getSize                  (){return m_Size;             }
    
    public float getViewportAspectRatio(){return getViewportPixelSize().toVector2().aspectRatio();}
    
    public IntVector2 getViewportPixelPosition()
    {
        IntVector2 screenSize = Graphics.getScreenSize();
        
        return new IntVector2
        (
            (int)(m_ViewportPosition.x * screenSize.x),
            (int)(m_ViewportPosition.y * screenSize.y)
                
        );
        
    }
    
    public IntVector2 getViewportPixelSize()
    {
        IntVector2 screenSize = Graphics.getScreenSize();
        
        return new IntVector2
        (
            (int)(m_ViewportSize.x * screenSize.x),
            (int)(m_ViewportSize.y * screenSize.y)
                
        );
        
    }
    
    public Mat4x4 getProjectionMatrix()
    {
        //b_ProjectionMatrixBuffer.identityInPlace();//Mat4x4 p = Mat4x4.identity();
        
        switch(m_ProjectionMode)
        {
            case Perspective:  
                b_ProjectionMatrixBuffer.perspectiveInPlace(m_FieldOfView, getViewportAspectRatio(), m_NearClippingPlane, m_FarClippingPlane);//p = Mat4x4.perspective(m_FieldOfView, getViewportAspectRatio(), m_NearClippingPlane, m_FarClippingPlane); break;
            break;
                
            case Orthographic: 
                throw new java.lang.UnsupportedOperationException("Not supported yet.");
                //break;
            
        }
        
        return b_ProjectionMatrixBuffer;
        
    }
    
    public Mat4x4 getViewMatrix()
    {
        Vector3 cameraPosition = getTransform().get().getPosition().multiply(-1f);
        Vector3 cameraRotation = getTransform().get().getEulers().multiply(-1f);;
        Mat4x4 v = Mat4x4.identity();
        
        //R
        v.rotateX(cameraRotation.x);
        v.rotateY(cameraRotation.y);
        v.rotateZ(cameraRotation.z);
        //T
        v.translate(cameraPosition);
        
        return v;
        
    }
    
    public void setClearColor           (final Color                aClearColor       ){m_ClearColor        = aClearColor;       }
    public void setClearMode            (final CameraClearMode      aClearMode        ){m_ClearMode         = aClearMode;        }
    public void setProjectionMode       (final CameraProjectionMode aProjectionMode   ){m_ProjectionMode    = aProjectionMode;   }
    public void setViewportPixelPosition(final Vector2              aViewportPosition ){m_ViewportPosition  = aViewportPosition; }
    public void setViewportPixelSize    (final Vector2              aViewportSize     ){m_ViewportSize      = aViewportSize;     }
    public void setFieldOfView          (final float                aFieldOfView      ){m_FieldOfView       = aFieldOfView;      }
    public void setNearClippingPlane    (final float                aNearClippingPlane){m_NearClippingPlane = aNearClippingPlane;}
    public void setFarClippingPlane     (final float                aFarClippingPlane ){m_FarClippingPlane  = aFarClippingPlane; }
    public void setSize                 (final float                aSize             ){m_Size              = aSize;             }
      
    public void draw() //void draw(void) override;
    {   
        //Update viewport
        IntVector2 pixPos = getViewportPixelPosition(), pixSize = getViewportPixelSize();
        GL.glViewport(pixPos.x,pixPos.y,pixSize.x,pixSize.y);
        GL.glScissor (pixPos.x,pixPos.y,pixSize.x,pixSize.y);        
        
        switch(m_ClearMode)
        {
            case Color:
            {
                GL.glClearColor(m_ClearColor.r,m_ClearColor.g,m_ClearColor.b,m_ClearColor.a);
                GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
            
            } break;
            
            case DepthOnly:
            {
                GL.glClear(GL.GL_DEPTH_BUFFER_BIT);
                
            } break;
            
        }
        
    }
    
    //void generateViewProjectionMatrix(mat4x4* aViewMatrix, mat4x4* aProjectionMatrix, mat4x4* aVPMatrix = 0);
    
    //*************
    // Constructors
    //*************
    public Camera()
    {
        m_ClearColor        = Color.CornflowerBlue();		
        m_ClearMode         = CameraClearMode.Color;
        m_ProjectionMode    = CameraProjectionMode.Perspective;
        m_ViewportPosition  = Vector2.Zero();
        m_ViewportSize      = new Vector2(1,1);
        m_FieldOfView       = 90f;
        m_NearClippingPlane = 0.001f;
        m_FarClippingPlane  = 20;
        m_Size              = 100;
        
        //GL init
        GL.glEnable(GL.GL_DEPTH_TEST);
        GL.glEnable(GL.GL_SCISSOR_TEST);
        
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

    @Override
    protected void OnComponentAdded(Component aComponent) {
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) {
    }    
    
}
