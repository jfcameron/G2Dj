/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Graphics;

import grimhaus.com.G2Dj.Debug;
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
import grimhaus.com.G2Dj.Imp.Graphics.GraphicsComponent;
import grimhaus.com.G2Dj.Type.Math.Vector4;


/**
 *
 * @author Joe
 */
public class Camera extends GraphicsComponent
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
    //private float                m_Size;
    private Vector2 m_OrthoSize;
    
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
    //public float                getSize                  (){return m_Size;             }
    
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
                b_ProjectionMatrixBuffer.perspectiveInPlace(m_FieldOfView, getViewportAspectRatio(), m_NearClippingPlane, m_FarClippingPlane);
            break;
                
            case Orthographic: 
                b_ProjectionMatrixBuffer.orthographicInPlace(m_OrthoSize,m_NearClippingPlane,m_FarClippingPlane,getViewportAspectRatio());
            break;
            
        }
        
        return b_ProjectionMatrixBuffer;
        
    }
    
    public Mat4x4 getViewMatrix()
    {
        Vector3 cameraPosition = getTransform().get().getPosition().multiply(-1f);
        Vector3 cameraRotation = getTransform().get().getEulers().multiply(-1f);
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
    //public void setSize                 (final float                aSize             ){m_Size              = aSize;             }
    public void setOrthoSize(final float aWidth, final float aHeight){m_OrthoSize.x = aWidth; m_OrthoSize.y = aHeight;}
      
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
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /*
    template <typename T, typename U, precision P>
	GLM_FUNC_QUALIFIER tvec3<T, P> unProject
	(
		tvec3<T, P> const & win,
		tmat4x4<T, P> const & model,
		tmat4x4<T, P> const & proj,
		tvec4<U, P> const & viewport
	)
	{
		tmat4x4<T, P> Inverse = inverse(proj * model);

		tvec4<T, P> tmp = tvec4<T, P>(win, T(1));
		tmp.x = (tmp.x - T(viewport[0])) / T(viewport[2]);
		tmp.y = (tmp.y - T(viewport[1])) / T(viewport[3]);
		tmp = tmp * T(2) - T(1);

		tvec4<T, P> obj = Inverse * tmp;
		obj /= obj.w;

		return tvec3<T, P>(obj);
	}
    */
    
/*
    mat4 projectionMatrix = GetPerspectiveMatrix();
    mat4 projectionMatrixInverse = glm::inverse(projectionMatrix);
    mat4 viewMatrix = GetViewMatrix();
    mat4 viewMatrixInverse = glm::inverse(viewMatrix);


    vec2 screenCoord = MathLib::vec2(x, y);
    vec2 normalizedCoord2D = MathLib::vec2(2.0 * screenCoord.x / viewportSize.x - 1.0, 2.0 * screenCoord.y / viewportSize.y - 1.0);
    vec4 worldCoordNear;
    vec4 worldCoordFar;
    
    {
        vec4 normalizedCoordNear = vec4(normalizedCoord2D.x, normalizedCoord2D.y, -1.0, 1.0);
        vec4 clipCoordNear = normalizedCoordNear;
        vec4 eyeCoordNear = projectionMatrixInverse * clipCoordNear;
        worldCoordNear = viewMatrixInverse * eyeCoordNear;
        worldCoordNear /= worldCoordNear.w;
    }
    {
        vec4 normalizedCoordFar = vec4(normalizedCoord2D.x, normalizedCoord2D.y, 1.0, 1.0);
        vec4 clipCoordFar = normalizedCoordFar;
        vec4 eyeCoordFar = projectionMatrixInverse * clipCoordFar;
        worldCoordFar = viewMatrixInverse * eyeCoordFar;
        worldCoordFar /= worldCoordFar.w;
    }   
*/
    
    private Vector3 TEMPunproject(final Vector3 screenPos, final Mat4x4 aViewMatrix, final Mat4x4 aProjectionMatrix, final Vector4 aViewport)
    {
        Mat4x4 projectionMatrix = aProjectionMatrix;
        Mat4x4 projectionMatrixInverse = Mat4x4.Inversion(projectionMatrix);
        Mat4x4 viewMatrix = aViewMatrix;
        Mat4x4 viewMatrixInverse = Mat4x4.Inversion(aViewMatrix);
        
        Vector2 viewportSize = new Vector2(m_ViewportSize.x,m_ViewportSize.y);
        
        Debug.log("Viewport: "+m_ViewportSize);
        
        Vector2 screenCoord = new Vector2(screenPos.x, screenPos.y);
        Vector2 normalizedCoord2D = new Vector2(2.0f * screenCoord.x / viewportSize.x - 1.0f, 2.0f * screenCoord.y / viewportSize.y - 1.0f);
        Vector4 worldCoordNear;
        Vector4 worldCoordFar;
        
        {
            Vector4 normalizedCoordNear = new Vector4(normalizedCoord2D.x, normalizedCoord2D.y, -1.0f, 1.0f);
            Vector4 clipCoordNear = new Vector4(normalizedCoordNear);
            Vector4 eyeCoordNear = projectionMatrixInverse.mul(clipCoordNear);//projectionMatrixInverse * clipCoordNear;
            worldCoordNear = viewMatrixInverse.mul(eyeCoordNear);
            worldCoordNear.multiply(1.0f/worldCoordNear.w);
            
        }
        {
            Vector4 normalizedCoordFar = new Vector4(normalizedCoord2D.x, normalizedCoord2D.y, 1.0f, 1.0f);
            Vector4 clipCoordFar = normalizedCoordFar;
            Vector4 eyeCoordFar = projectionMatrixInverse.mul(clipCoordFar);
            worldCoordFar = viewMatrixInverse.mul(eyeCoordFar);
            worldCoordFar.multiply(1.0f/worldCoordFar.w);
        
        }
        
        Debug.log("unprojected coord: "+worldCoordNear);
        
        return new Vector3
        (
                worldCoordNear.x,
                worldCoordNear.y,
                worldCoordNear.z
                
        );
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public Vector3 getWorldPointFromScreenPoint(final IntVector2 aScreenPoint, final float aWorldSpaceDistance)
    {
        Debug.log("Screen point: "+aScreenPoint,"Viewport size: "+m_ViewportSize);
        //**********************1 ./
        //----
    	//marshal data
    	//----
    	//vp dimensions
        IntVector2 screenSize = Graphics.getScreenSize();
    	float viewportSizeX = m_ViewportSize.x * screenSize.x;
    	float viewportSizeY = m_ViewportSize.y * screenSize.y;
    	Vector4 viewport = new Vector4(0.0f, 0.0f, viewportSizeX, viewportSizeY);/*glm::vec4 viewport = glm::vec4(0.0f, 0.0f, screenW, screenH);*/
        //v&p mats
    	Mat4x4 view       = new Mat4x4(getViewMatrix());/*glm::mat4 tmpView;// (1.0f);*/
    	Mat4x4 projection = new Mat4x4(getProjectionMatrix());/*glm::mat4 tmpProj;// = glm::perspective(90.0f, screenW / screenH, 0.1f, 1000.0f);*/
    	
        //**********************2 ./
    	float zFar  = m_FarClippingPlane;/*float zFar        = m_FarClippingDistance;*/
    	float zNear = m_NearClippingPlane;/*float zNear       = m_NearClippingDistance;*/
    	float linearDepth = aWorldSpaceDistance;/*float linearDepth = aWorldSpaceDistance;*/
    	float nonLinearDepth = (zFar + zNear - 2.0f * zNear * zFar / linearDepth) / (zFar - zNear);/*float nonLinearDepth = (zFar + zNear - 2.0 * zNear * zFar / linearDepth) / (zFar - zNear);*/
    	nonLinearDepth = (nonLinearDepth + 1.0f) / 2.0f;/*nonLinearDepth = (nonLinearDepth + 1.0) / 2.0;*/
        
    	//Debug::log("wsd:", aWorldSpaceDistance,"Lineardepth:",linearDepth, "\n");
        
        //**********************3 ./
    	Vector3 screenPos = new Vector3
    	(
    		aScreenPoint.x * m_ViewportSize.x, //denormalizing to fit glm expectation
    		m_ViewportSize.y + (-1.f*aScreenPoint.y * m_ViewportSize.y), //flipping input screen pos y and denormalizing to match glm expectation
    		//(2 * m_NearClippingDistance) / (m_FarClippingDistance + m_NearClippingDistance - 1.0f * (m_FarClippingDistance - m_NearClippingDistance)) //linearizing depth
    		nonLinearDepth
    	);
        
        //**********************4
        //calc wpos
    	return TEMPunproject(screenPos, view, projection, viewport);/*glm::vec3 worldPos = glm::unProject(screenPos, tmpView, tmpProj, viewport);*/
        
    }
    /*Math::Vector3 RenderCamera::getWorldPointFromScreenPoint(const Math::Vector2 &aScreenPoint, const float &aWorldSpaceDistance)
    {
        //**********************1
    	//----
    	//marshal data
    	//----
    	//vp dimensions
    	float screenW = m_ViewportSize.x;
    	float screenH = m_ViewportSize.y;
    	glm::vec4 viewport = glm::vec4(0.0f, 0.0f, screenW, screenH);
    	//v&p mats
    	glm::mat4 tmpView;// (1.0f);
    	glm::mat4 tmpProj;// = glm::perspective(90.0f, screenW / screenH, 0.1f, 1000.0f);
    	generateViewProjectionMatrix(&tmpView, &tmpProj);
    	//screen pos
        
        //**********************2
    	float zFar        = m_FarClippingDistance;
    	float zNear       = m_NearClippingDistance;
    	float linearDepth = aWorldSpaceDistance;
    	float nonLinearDepth = (zFar + zNear - 2.0 * zNear * zFar / linearDepth) / (zFar - zNear);
    	nonLinearDepth = (nonLinearDepth + 1.0) / 2.0;
        
    	//Debug::log("wsd:", aWorldSpaceDistance,"Lineardepth:",linearDepth, "\n");
        
        //**********************3
    	glm::vec3 screenPos = glm::vec3
    	(
    		aScreenPoint.x * m_ViewportSize.x, //denormalizing to fit glm expectation
    		m_ViewportSize.y + (-1.f*aScreenPoint.y * m_ViewportSize.y), //flipping input screen pos y and denormalizing to match glm expectation
    		//(2 * m_NearClippingDistance) / (m_FarClippingDistance + m_NearClippingDistance - 1.0f * (m_FarClippingDistance - m_NearClippingDistance)) //linearizing depth
    		nonLinearDepth
    	);
                
    	//calc wpos
    	glm::vec3 worldPos = glm::unProject(screenPos, tmpView, tmpProj, viewport);
        
    	return Math::Vector3
    	(
    		worldPos.x,
    		worldPos.y,
    		worldPos.z
                        
    	);
                
    }*/
    
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
        //m_Size              = 100;
        m_OrthoSize = new Vector2(40,40);
        
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
    public void fixedUpdate() {}

    @Override
    protected void OnRemovedFromGameObject(){}

    @Override
    protected void OnComponentAdded(Component aComponent) {
    }

    @Override
    protected void OnComponentRemoved(Component aComponent) {
    }    

    @Override
    protected void initialize() {
    }

    @Override
    protected void OnAddedToGameObject(WeakReference<GameObject> aGameObject) {
    }
    
}
