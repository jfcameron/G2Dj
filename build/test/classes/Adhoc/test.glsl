//VertIn
attribute vec3 a_Position;
attribute vec2 a_UV;
    
//Uniforms
uniform mat4 _MVP;
    
void main ()                        
{
    gl_Position = _MVP * vec4(a_Position,1.0);  
    
}

============================================================

void main()                            
{                                      
    gl_FragColor = vec4(1,0.2,0.8,1);
    
}