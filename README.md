# G2Dj
![alt tag](http://jfcameron.github.io/Images/GD2j_Sprites/Big.png "")
![alt tag](http://jfcameron.github.io/Images/G2Dj_Android/Big.png "")



## Description:
Crossplatform game engine in Java & OpenGLES.
Designed for 2D, low-fi graphics, but is capable of perspective rendering for "2.5D" projects. Uses the [JOGL](http://jogamp.org/) project (version 2.3) to access OpenGL.
Makes use of [PreprocessorWizard.pl](http://www.rtbaileyphd.com/preprocessorwizard/) to split implementation between desktop and mobile code.

## Setup:
Include JOGL as lib.
Run PreprocessorWizard.pl with either define ANDROID or DESKTOP and build the G2Dj project.
Include G2Dj as library to your Java project.
Call Engine.init();

### Creating an example scene:
```java
public static void main(String[] args) 
{
    Engine.init();
    
    //Load a texture
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
        
        //aGameObject.get().removeComponent(Mesh.class);
        
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
    
    Engine.update();
    
}
```

## Libraries used:
* [JOGL](http://jogamp.org/)
* [GLM](https://github.com/java-graphics/glm)
* [jbox2D](https://github.com/jbox2d/jbox2d)