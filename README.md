# G2Dj
![alt tag](http://jfcameron.github.io/Images/GD2j_Sprites/Big.png "")
![alt tag](http://jfcameron.github.io/Github/G2Dj/Pong.png "")
![alt tag](https://jfcameron.github.io/Images/G2Dj_Platformer/Big.png "")
![alt tag](http://jfcameron.github.io/Images/G2Dj_Android/Big.png "")


## Description:
![asdf](https://img.shields.io/badge/development%20status-suspended-yellow.svg)
![asdf](https://img.shields.io/badge/platforms-win32%20|%20osx%20|%20linux%20|%20android-lightgrey.svg)
![asdf](https://img.shields.io/badge/license-MIT-blue.svg)

Crossplatform game engine in Java & OpenGLES.
Designed for 2D, low-fi graphics, but is capable of 3D perspective rendering for "2.5D" projects.
Builds for Mac/Linux/Windows and Android.

Games are programmed against an Entity Component System, the components used by the game depend on a set of engine systems.
While systems can be added arbitarily to G2Dj, there are a few that are present out of the box:

### Graphics
* 3D Forward Renderer
* Resource managers for shaders, textures, models
* Supports OpenGLES 2.0 standard
* Basic 2D game shaders & programmable shader interface
* Unicode Text renderer supporting the full Basic Multilingual Plane 
* Tilegrid and spritesheet renderers


### Physics2D
* Rigidbodies, colliders and trigger areas
* Colliders can be built from rectangles, circles or complex shapes made from sets of vertexes or primitives
* When Rigidbodies collide with colliders or enter trigger areas, OnCollision and OnTigger callbacks on the Rigidbody's Gameobject are invoked
* Implementation based on [jbox2D](https://github.com/jbox2d/jbox2d)

### Audio
* 3D sound system based on OpenAL
* Supports wav, Ogg Vorbis, FLAC files

### Math
* 2D and 3D linear algebra
* 2-4d Vectors, Mat4x4

### Input
* Mouse
* Keyboard
* USB Gamepads
* Touchscreen handler

### Time, Files, Debug.
 
## Building the engine:
### Desktop
* Add the correct native libs for JOGL, JOAL and JInput to the project (Win32/64, Mac, Linux32/64).
* Run PreprocessorWizard.pl with #define DESKTOP
* Open the NetBeans project file and build G2Dj.

### Mobile
* Run PreprocessorWizard.pl with #define ANDROID
* Open the Android Studio project file and build G2Dj.

## Creating a game:
Games are written against an ECS. The ECS is comprised of 3 basic class types: Scene, GameObject, Component.
Scenes define a 3D space with a customizable set of capabilities (rendering, physics, etc.). A game can be made of any number of scenes and can be run concurrently.
GameObjects have a set of Components.
Components define behaviours for their parent GameObjects.
```java
public static void main(String[] args) 
{
    //Engine entry point
    Engine.init(new Game()
    {
        @Override public void init(){initMyGame();}
        
    });
    
}

public static void initMyGame() 
{
    //Load some textures
    Graphics.loadFromResource("/Resources/Cloud.png");
    Graphics.loadFromResource("/Resources/brick.png");
    
    //Create a scene
    WeakReference<Scene> mainScene = Engine.createScene("Main");
    
    //Create the player game object
    {
        WeakReference<GameObject> aGameObject = mainScene.get().addGameObject();
        aGameObject.get().setName("Player1");
    
        Mesh aMesh = (Mesh)aGameObject.get().addComponent(Mesh.class); //add a mesh
        aMesh.setTexture("_Texture", "awesome.png"); //give the mesh a texture
        
        PlayerController aPC = (PlayerController)aGameObject.get().addComponent(PlayerController.class);
        aPC.getTransform().get().setRotation(new Vector3(0,0,45));
        aPC.getTransform().get().setPosition(new Vector3(0,0,+0.5f));
    
    }
    
    //Create the camera
    {
        WeakReference<GameObject> theCamera = mainScene.get().addGameObject();
        theCamera.get().setName("Camera");
        theCamera.get().getTransform().get().setPosition(new Vector3(-1,0,2));
        theCamera.get().getTransform().get().setRotation(new Vector3(0,45,0));
        
        theCamera.get().addComponent(CameraController.class);
        theCamera.get().addComponent(Camera.class);
                
    }
    
}
```
### Creating a custom component
```java
//Require that this GameObject's scene have 2D physics capability
@RequireSceneGraphs({Physics2DSceneGraph.class}) 

//Require that this GameObject have a rigidbody and a collider
@RequireComponents({Camera.class,Rigidbody.class,CircleCollider.class}) 

public class PlayerController extends grimhaus.com.G2Dj.Type.Engine.Component
{
    private static final float s_Speed = 0.1f; 
    private final Vector3 inputBuffer = new Vector3();
    private final Vector3 rotationBuffer = new Vector3();
    private final Vector3 scaleBuffer = new Vector3();

    @Override
    protected void initialize() {}
    
    @Override
    protected void update() 
    {
        //Translation
        {
            inputBuffer.setInPlace(0);

            //Keyboard input
            if (Input.getKey(KeyCode.J))
                inputBuffer.x-=s_Speed;

            if (Input.getKey(KeyCode.L))
                inputBuffer.x+=s_Speed;

            if (Input.getKey(KeyCode.I))
                inputBuffer.z-=s_Speed;

            if (Input.getKey(KeyCode.K))
                inputBuffer.z+=s_Speed;

            getTransform().get().translate(inputBuffer);
            
        }
        
        //Rotation
        {
            rotationBuffer.setInPlace(0);

            //Keyboard input
            if (Input.getKey(KeyCode.U))
                rotationBuffer.y +=0.1f;

            if (Input.getKey(KeyCode.O))
                rotationBuffer.y -=0.1f;
            
            getTransform().get().rotate(rotationBuffer);
            
        }
        
        //Scale
        {
            scaleBuffer.setInPlace(0);
            
            if (Input.getKey(KeyCode.R))
                scaleBuffer.addInPlace(0.01f);
            
            if (Input.getKey(KeyCode.F))
                scaleBuffer.addInPlace(-0.01f);
            
            getTransform().get().scale(scaleBuffer);
            
        }
        
    }

    @Override protected void OnComponentAdded(Component aComponent) {}
    @Override protected void OnComponentRemoved(Component aComponent) {}

}
```

## Libraries used:
* [JOGL](http://jogamp.org/) - OpenGL support on Desktop builds
* [JOAL](http://jogamp.org/) - OpenAL
* [jGLM](https://github.com/java-graphics/glm) - Reference material for Math.Mat4x4
* [jbox2D](https://github.com/jbox2d/jbox2d) - Implementation dependency for Physics2D
* [JInput](https://github.com/jinput/jinput) - Gamepad support on Desktop builds
* [J-Ogg](http://j-ogg.de/) - Ogg Vorbis and FLAC decoder

## Tools used:
* The desktop branch is setup for [Netbeans](https://netbeans.org/)
* The mobile branch is setup for [Android Studio](https://developer.android.com/studio/index.html?gclid=Cj0KEQjww7zHBRCToPSj_c_WjZIBEiQAj8il5EmbhevnYcRaWG-OkJNcStUiwWozWMhyd1vzX7e8ZK8aAub18P8HAQ)
* [PreprocessorWizard.pl](http://www.rtbaileyphd.com/preprocessorwizard/) is used as a code preprocessor to comment out desktop and mobile specific implementation
