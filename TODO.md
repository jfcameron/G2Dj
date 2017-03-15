Implement transform related code
 - vec3 pos, rot, scale.
 - who owns this data?
 - pack transform data into mat4x4 for transport to shaders
 - add standard mat uniforms (MVP, VP, M).
 - should a perspective projection be possible? if so how? Camera.projectionMode(aMode) where aMode is of type enum ProjectionMode{orthographic,perspective}? OR Camera is abstract w 2 implementers: Camera2D and Camera3D?

Implement GameObject.removeComponent. See LOG.MD for details

AuthList in G2Dj.Memory
    - takes a class name, distributes weakreferences to its content

prevent duplicates in: all resource collections, gameobjectComponents

graphics scene
dev scene? scene interface??

camera, mesh need positions
 - give them a common parent from which they inhert position, rotation, scale (vec3s?)
    - find or write mat4x4 for G2Dj.Math


implement more uniform collection types & refactor textureuniformcollection imp to abstract generic uniformCollection
 - float uniforms
 - float2 uniforms
 -???

clean up all public interfaces


Textures
 - convert texture to abstract
 - Either derive children: ResourceTexture and FileTexture OR have two constructors 1 for file loading 1 for package loading
    
abstract base scenegraph type (or should it be an interface?), system specific Scenegraph, make systems extendable.

Vertex data
    -allow custom shapes from vert arrays, allow use of dynamic memory for faster vbo rewrites (currently hardcoded to static)
    
2D Physics
    -Integrate jBox2D (https://github.com/jbox2d/jbox2d) into project
        
reassess todos (continue gl? work on math lib? add openAL dep?)

==================BACKBURNER=====================
Collections/Resources
    -prevent duplicate names being entered
    -prevent duplicate resources from being allocated (fail silently or throw?)

Clean
    - style
    - imports
    - unused data members

Input
    - test current implementation, decide whether or not to change it

Tile renderer
famicom style synth

add meaningful ToString overrides for all types.
