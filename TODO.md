Android touch detection to Input

Make the android engine present an identical public interface as the desktop one.

add meaningful ToString overrides for all types.
 
2D Physics
    -Integrate jBox2D (https://github.com/jbox2d/jbox2d) into project
    
improve desktop input implementation (pure java isnt cutting it)

Vertex data
    -allow custom shapes from vert arrays, allow use of dynamic memory for faster vbo rewrites (currently hardcoded to static)
    
allow different primitve modes (line, dot)

add openAL dep

add networking

native key/mouse on desktop path
gestures on android
controllers
        
reassess todos ()

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


AuthList in G2Dj.Memory
    - takes a class name, distributes weakreferences to its content

prevent duplicates in: all resource collections, gameobjectComponents

implement more uniform collection
 - float2 uniforms

clean up all public interfaces
