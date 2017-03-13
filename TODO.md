finish texture loading implementation

do renderobject implemntation

implement standard uniforms
    -texture
    -time
    -mvp
    ...

Textures
    - texture loading
    - texture collecting
    - texture retrieval

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
abstract base scenegraph type (or should it be an interface?), system specific Scenegraph, make systems extendable.