Graphics
 - TextMesh
    - Add text anchors {(left, right, center),(top, down, center)}

Physics
 - Collider hierarchy
   - m_DrawDebugLines in Collider behaves strangely in SimpleCollider.initialize()

Graphics
   - SpriteRenderer
   - component for drawing cellbased sprite sheets
   
???
 - TileRenderer2D   
   - depends on GraphicsSceneGraph, Physics2DSceneGraph

Physics2D
 - OnCollisionStay(Collision collisionInfo)
 - OnTriggerStay(Collider other)
 - World.Raycast
 - filters

android
 - maintain gl context when minimizing app
 - improve touch handler
 - Joystick handler
 - Keyboard handler
 - Mouse handler
 
Desktop
 - Mouse handler
 - Joystick handler
 
Graphics
 - Mesh
    - allow different primitve modes (line, dot)
    - allow custom shapes from vert arrays, allow use of dynamic memory for faster vbo rewrites (currently hardcoded to static)
        - Mesh:Component and DynamicMesh:Component. 
        - Add a load from verts method in gfx. loadModel(String aPath), loadModel(Vector2[] aVertexData) for STATIC mesh
        - Add a loadFromVerts to DynamicMesh for DYNAMIC mesh
 - TextMesh
   - naive unicode then smart unicode.
        
 - Add a solid color fallback shader (uniform specifies color)
 - Add standardized uniforms: uv offsets, total time and delta time uniforms 

Audio
 - add an OpenAL dependency to the project, get an initial test working on desktop and android. Create AL wrapper object.
 
Math
 - Mat4x4 
    - static Vec3 screenToWorldPoint(Vec2 screenPos, float dist, Mat4x4 cameraVP)
    - static Vec2 screenToXZPlanePoint(Vec2 screenPos, Mat4x4 cameraVP)

networking
 - should there be networking components or should networking implementation be entirely up to the end user?

==================BACKBURNER=====================


Graphics
 - TextMesh
   - Split BML Plane texture into [16^2] array of textures.
        - allows opt in and out of character support (looking at you 漢字)
        - allows variable font resolutions across language domains

Pool shortlived and frequently used objects (Mat4x4 & Vec2/3 looking at you).
Make the android engine present an identical public interface as the desktop one.

Collections/Resources
    -prevent duplicate names being entered
    -prevent duplicate resources from being allocated (fail silently or throw?)

Clean
    - style
    - imports
    - unused data members

Tile renderer
 - 2D. Tesselated plane. Make heavy use of shaders?
 - 3D. Vector3[] associated to tile ids.

Midi Synthesizer
 - jfugue + Gervill
 - bundle with retro soundfonts

prevent duplicates in: all resource collections, gameobjectComponents

implement more uniform collection
 - float2 uniforms

clean up all public interfaces

Improve mobile Touch API

think about recording input states for a variable number of frames (instead of just current and last)
add meaningful ToString overrides for all types.