precision mediump float;
//*************************
// FragmentIn vertex format
//*************************
varying  vec2 v_UV;
//varying  vec3 v_Normal ;

//*********
// Uniforms
//*********
//uniform  vec4 _Color;
uniform sampler2D _Texture;

//**********
// Constants
//**********
const float c_AlphaCutOff = 1.0;

//*********************************
// Fragment operations forward decs
//*********************************
vec4 calculateTexelColor(const vec4 aFrag);
vec4 alphaCutOff(const vec4 aFrag);

//*****************
// Fragment shader
//****************
void main()
{
     vec4 rvalue = vec4(0);
    {
        rvalue = calculateTexelColor(rvalue);
        //rvalue = alphaCutOff(rvalue);

        //rvalue = vec4(v_UV.x,v_UV.y,0.0,1.0);

    }

    gl_FragColor = rvalue;

}
//*******************************
//Fragment operations definitions
//*******************************
vec4 calculateTexelColor(const vec4 aFrag)
{
    vec4 rvalue = aFrag;
    {
        rvalue = texture2D(_Texture, v_UV);
        //rvalue = vec4(1.0,0.0,0.0,1.0);
        
    }
    
    return rvalue;      
    
}

vec4 alphaCutOff(const vec4 aFrag)
{
    vec4 rvalue = aFrag;
    {
        if (rvalue[3] < c_AlphaCutOff)
            discard;
                    
    }
    
    return rvalue;
    
}