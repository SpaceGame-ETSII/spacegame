/* BlurFragmentShader.glsl */
#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
 
uniform sampler2D u_texture;

varying LOWP vec4 v_color;
varying vec2 v_texCoord;

 
void main() {

    vec4 textureColor = texture2D(u_texture, v_texCoord);
/*
    if(textureColor.r < 0.2){
        textureColor.r = 0;
        textureColor.a = 0;
    }
    if(textureColor.g < 0.2){
        textureColor.g = 0;
        textureColor.a = 0;
    }
    if(textureColor.b < 0.0){
        textureColor.b = 0;
        textureColor.a = 0;
    }
*/
    if(textureColor.r > 0.5 || textureColor.b > 0.5 || textureColor.g > 0.5){
        textureColor.r = 1.0;
        textureColor.g = 0.0;
        textureColor.b = 0.0;
    }
    gl_FragColor = textureColor;
}