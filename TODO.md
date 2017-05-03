===NOW==========
JOAL support
JSON file loading support
Tilebased level editor

Sounds
 - ALut.alutLoadWAVFile cannot handle wavs with meta data. Move away from using alut

Physics2D
 - Raycasting
 - 2d point testing vs colliders, vs triggers: return Collider world.checkPoint(Vec2 aPoint, type). where type {collider,trigger,any}
===INBOX==============
Physics2D
 - Rigidbody.Raycast
 - World.Raycast
  - TileCollider2D   
    - add support for trigger tiles, add collider name option
    - optimize collision mesh gen. specifically: remove gen of faces that are inside a shape. these are a burden on the sim, contribute nothing and can cause collision problems at high enough velocity collisions.
    - Add support for 1 directional colliders
    - offset the whole grid by half xy
Tilegrid TestScene
 - PlayerController
    - bug: spamming jump while facing left lets you reach max speed near instantly
    - landing recovery anim (sideradical)
    - super fast run (hands back)
    - add raycast check to collision check
 - Create a stompable enemy
    - slime
    - bouncy up edge
    - state: {wander, idle, dead}
 - CharacterController
    - parameterize hitbox & detection boxes OR refactor these to playercontroller if this cannot be done meaningfully

Physics2D
 - SimpleCollider exists for FixtureDefinition therefore create ComplexCollider for FixtureDefinition[] -> abstract gridcollider & composite collider commonalities to this.
 - rigidbody.lockPosition(Vector2 aPosition, Vector2 aLocalOffset) -> allow hinges etc. in worldspace
 - rigidbody.lockPosition(Rigidbody aRigidbody, Vector2 aPosition, Vector2 aLocalOffset) -> local to aRigidbody
 - Rigidbody & colliders
 - colliders should require a rigidbody
 - rigidbody should be a unique component (create an attribute for this) VERY IMPORTANT: just ran into a bug around the lack of this!
 - colliders should have either names or optional "userdata" (type: object) for special behaviour in collision events
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
 /*- Mesh
    - allow different primitve modes (line, dot)
    - allow custom shapes from vert arrays, allow use of dynamic memory for faster vbo rewrites (currently hardcoded to static)
        - Mesh:Component and DynamicMesh:Component. 
        - Add a load from verts method in gfx. loadModel(String aPath), loadModel(Vector2[] aVertexData) for STATIC mesh
        - Add a loadFromVerts to DynamicMesh for DYNAMIC mesh*/ UNCERTAIN ABOUT THESE SUGGESTIONS. a new class type: DynamicMesh may be more appropriate.
 - TextMesh
   - allow support for only specified language regions, custom fonts.
     - strategy choice: divide & conquer BMLP or fileformat .FONT:{png,{utf8codepoint...}}
 - Add standardized uniforms: uv offsets, total time and delta time uniforms 

Audio
 - add an OpenAL dependency to the project, get an initial test working on desktop and android. Create AL wrapper object.
 


networking
 - should there be networking components or should networking implementation be entirely up to the end user?
  - There should be a wrapper if platform differences exist here between mobile & desktop.

==================BACKBURNER=====================
legal
 - licence requiring attribution & title card for ? time (2 sec?)
 - titlecard with attributions to box2d, glm, jogl et. all

ECS
 - Component : Think about the future of:
    void OnAddedToGameObject(WeakReference<GameObject> aGameObject)
    void OnRemovedFromGameObject()
    void OnComponentAdded(Component aComponent) 
    void OnComponentRemoved(Component aComponent)

Math
 - Mat4x4
    - cleaning. code is very messy
    - ortho: depth planes are placed incorrectly

Graphics
 - TextMesh
   - Split BML Plane texture into [16^2] array of textures.
        - allows opt in and out of character support (looking at you 漢字)
        - allows variable font resolutions across language domains

Mobile
 - Make the android engine present an identical public interface as the desktop one.


Tile renderer
 - 2D. Tesselated plane. Make heavy use of shaders?
 - 3D. Vector3[] associated to tile ids.

Midi Synthesizer
 - jfugue + Gervill
 - bundle with retro soundfonts

Input
 - Improve mobile Touch API
 - think about recording input states for a variable number of frames (instead of just current and last)
 - InputConfig
    - improve the way gamepads are associated to bindings

Collections/Resources
    -prevent duplicate names being entered
    -prevent duplicate resources from being allocated (fail silently or throw?)
    -create a scene destruction test to see what havok is done to the heap
    
Bugs
   - arrow keys dont seem to fire on getKey and getKeyDown calls
    
Cleaning
    - replace excessive requirement of new vec[]{new vec...} using varags
    - clean up all public interfaces
    - style
    - imports
    - unused data members
    - add meaningful ToString overrides for all types.
    - prevent duplicates in: all resource collections, gameobjectComponents
    - Pool shortlived and frequently used objects (Mat4x4 & Vec2/3 looking at you).