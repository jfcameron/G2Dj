/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Graphics;
import G2Dj.Math.IntVector2;
import G2Dj.Math.Vector2;
import com.jogamp.opengl.GL;

/**
 *
 * @author Joe
 */
public class Camera 
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
    //public void setViewportPixelPosition(final IntVector2      aViewportPosition){m_ViewportPosition = aViewportPosition;}
    //public void setViewportPixelSize    (final IntVector2      aViewportSize    ){m_ViewportSize     = aViewportSize;    }
    
    public void draw(GL aGL) //void draw(void) override;
    {   
        //Update viewport
        IntVector2 pixPos = getViewportPixelPosition(), pixSize = getViewportPixelSize();
        //Debug.log(pixSize);
        aGL.glViewport(pixPos.x,pixPos.y,pixSize.x,pixSize.y);
        aGL.glScissor (pixPos.x,pixPos.y,pixSize.x,pixSize.y);
          
        aGL.glDepthMask(true);  
        
        aGL.glClearColor(m_ClearColor.r,m_ClearColor.g,m_ClearColor.b,m_ClearColor.a);
        aGL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
    }
    
    //void generateViewProjectionMatrix(glm::mat4x4* aViewMatrix, glm::mat4x4* aProjectionMatrix, glm::mat4x4* aVPMatrix = 0);
    
    //*************
    // Constructors
    //*************
    public Camera(){this(Vector2.Zero(), new Vector2(1,1));}
    public Camera(Vector2 aViewportScreenPosition, Vector2 aViewportScreenSize)
    {
        this
        (
            aViewportScreenPosition, 
            aViewportScreenSize, 
            Color.CornflowerBlue(), 
            CameraClearMode.Color
        
        );
        
    }
    public Camera(Vector2 aViewportScreenPosition, Vector2 aViewportScreenSize, Color aClearColor, CameraClearMode aClearMode)
    {
        m_ClearColor       = aClearColor;		
        m_ClearMode        = aClearMode;
        m_ViewportPosition = aViewportScreenPosition;
        m_ViewportSize     = aViewportScreenSize;
        
        //GL init
        GL gl = Graphics.getGL();
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_SCISSOR_TEST);
        
    }
    
}
