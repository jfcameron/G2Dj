precision mediump float;
//*************************
// FragmentIn vertex format
//*************************
varying vec2 v_UV;

//*********
// Uniforms
//*********
uniform sampler2D _Texture;

uniform vec2 _UVScale;
uniform vec2 _UVOffset;

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
        rvalue = alphaCutOff(rvalue);

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
        vec2 uvScale = _UVScale;

        if (_UVScale.x == 0.0 && _UVScale.y == 0.0)
            uvScale = vec2(1.0);

        vec2 uvOffset = _UVOffset;

        vec2 uv = (v_UV*uvScale) + uvOffset;

        rvalue = texture2D(_Texture, uv);
                
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