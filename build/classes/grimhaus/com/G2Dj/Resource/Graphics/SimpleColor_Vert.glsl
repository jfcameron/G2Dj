//***************
// Vertex Formats
//***************
//VertIn
attribute vec3 a_Position;

//*********
// Uniforms
//*********
uniform mat4 _MVP;
uniform float _PointSize;

//**************
// Vertex shader
//**************
void main()
{
    if (_PointSize == 0.0)
    {    
        gl_PointSize = 5.0;
    
    }
    else
    {
        gl_PointSize = _PointSize;
    
    }
    
    //Calculate position
     vec4 position = vec4(a_Position,1.0);
    {       
        position = _MVP * position;

    }
    
    gl_Position = position;
   
}