/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Imp.Graphics;

import G2Dj.Math.Vector2;
import G2Dj.Type.Graphics.Color;

/**
 *
 * @author Joe
 */
public class Camera 
{
    //************
    //Data members
    //************
    private Color           m_ClearColor;
    private Vector2         m_ViewportPosition;		
    private boolean         m_ClearIsEnabled;
    private CameraClearMode m_ClearMode;
    
    //
    //
    //
    
    //public:
    public Color getClearColor(){return m_ClearColor;}//Color getClearColor    (void) { return m_ClearColor;           }
    //std::weak_ptr<RenderTexture> getRenderTexture (void) { return m_RenderTexture;        }
			
    //void setClearColor      (const Color                  &aClearColor      ) { m_ClearColor       = aClearColor       ; }
    //void setRenderTexture   (std::weak_ptr<RenderTexture> aRenderTexture    ) { m_RenderTexture    = aRenderTexture    ; }
    //void setViewportPosition(const Math::Vector2          &aViewportPosition) { m_ViewportPosition = aViewportPosition ; }
    //void setViewportSize    (const Math::Vector2          &aViewportSize    ) { m_ViewportSize     = aViewportSize     ; }
    //void setClearMode(const bool &aEnabled, const ClearMode::ClearModeCode &aClearMode);

    //Math::Vector3 getWorldPointFromScreenPoint(const Math::Vector2 &aScreenPoint);
    //Math::Vector3 getWorldPointFromScreenPoint(const Math::Vector2 &aScreenPoint, const float &aWorldSpaceDistance);

    //void draw(void) override;

    //RenderCamera();

    //void generateViewProjectionMatrix(glm::mat4x4* aViewMatrix, glm::mat4x4* aProjectionMatrix, glm::mat4x4* aVPMatrix = 0);
    
    //*************
    // Constructors
    //*************
    public Camera()
    {
        
        
    }
    
}
