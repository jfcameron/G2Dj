/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Graphics;
import G2Dj.Math.IntVector2;
import G2Dj.Math.Vector2;
import G2Dj.Type.Graphics.Color;
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
    private IntVector2      m_ViewportPosition;
    private IntVector2      m_ViewportSize;
    //private RenderTexture m_RenderTexture;
    
    //**********
    // Accessors
    //**********
    public Color           getClearColor      (){return m_ClearColor;      }
    public CameraClearMode getClearMode       (){return m_ClearMode;       }
    public IntVector2      getViewportPixelPosition(){return m_ViewportPosition;}
    public IntVector2      getViewportPixelSize    (){return m_ViewportSize;    }
    //Math::Vector3 getWorldPointFromScreenPoint(const Math::Vector2 &aScreenPoint);
    //Math::Vector3 getWorldPointFromScreenPoint(const Math::Vector2 &aScreenPoint, const float &aWorldSpaceDistance);
    
    public Vector2 getViewportScreenPosition()
    {
        return new Vector2
        (
            m_ViewportPosition.x * Graphics.getWindow().getWidth(),
            m_ViewportPosition.y * Graphics.getWindow().getHeight()
                
        );
        
    }
    
    public Vector2 getViewportScreenSize()
    {
        return new Vector2
        (
            m_ViewportSize.x * Graphics.getWindow().getWidth(),
            m_ViewportSize.y * Graphics.getWindow().getHeight()
                
        );
        
    }
    
    public void setClearColor           (final Color           aClearColor      ){m_ClearColor       = aClearColor;      }
    public void setClearMode            (final CameraClearMode aClearMode       ){m_ClearMode        = aClearMode;       }
    public void setViewportPixelPosition(final IntVector2      aViewportPosition){m_ViewportPosition = aViewportPosition;}
    public void setViewportPixelSize    (final IntVector2      aViewportSize    ){m_ViewportSize     = aViewportSize;    }
    
    public void draw(GL aGL) //void draw(void) override;
    {
        //This should be moved to an init
        aGL.glEnable(GL.GL_DEPTH_TEST);
        aGL.glEnable(GL.GL_SCISSOR_TEST);
                                
        aGL.glViewport(m_ViewportPosition.x,m_ViewportPosition.y,m_ViewportSize.x,m_ViewportSize.y);
        aGL.glScissor (m_ViewportPosition.x,m_ViewportPosition.y,m_ViewportSize.x,m_ViewportSize.y);
          
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
        m_ClearColor       = aClearColor      ;		
        m_ClearMode        = aClearMode       ;
        
        m_ViewportPosition = new IntVector2((int)(aViewportScreenPosition.x*Graphics.getWindow().getWidth()),(int)(aViewportScreenPosition.y*Graphics.getWindow().getHeight()));
        m_ViewportSize     = new IntVector2((int)(aViewportScreenSize.x    *Graphics.getWindow().getWidth()),(int)(aViewportScreenSize.y    *Graphics.getWindow().getHeight()));
        
    }
    
}
