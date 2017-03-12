//***************
// Vertex Formats
//***************
//VertIn
attribute vec3 a_Position;
attribute vec2 a_UV;

//FragIn
varying vec2 v_UV;
//varying vec3 v_Normal;

//*********
// Uniforms
//*********
//uniform  float _Time;
//uniform mat4 _ModelScaleMatrix;
//uniform mat4 _ModelRotationMatrix;
//uniform mat4 _Object2WorldMatrix;
//uniform mat4 _ViewMatrix;
//uniform mat4 _ProjectionMatrix;

//**************
// Vertex shader
//**************
void main()
{
    //Calculate position
     vec4 position = vec4(a_Position,1.0);
    {       
        //position = /*_MVP **/ position;
        
    }
    
    gl_Position = position;
    
    //Passthrough UV
    v_UV = a_UV;
   
}
