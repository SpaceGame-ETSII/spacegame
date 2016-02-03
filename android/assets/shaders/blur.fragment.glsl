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
varying vec2 v_blurTexCoords[14];

uniform float resolution;
uniform float radius;
uniform vec2 dir;
 
void main() {
    vec4 sum = vec4(0.0);
    vec2 tc = v_texCoord;
	float blur = radius/resolution;

	float hstep = dir.x;
	float vstep = dir.y;

	sum += texture2D(u_texture, vec2(tc.x - 4.0*blur*hstep, tc.y - 4.0*blur*vstep)) * 0.05;
    sum += texture2D(u_texture, vec2(tc.x - 3.0*blur*hstep, tc.y - 3.0*blur*vstep)) * 0.09;
	sum += texture2D(u_texture, vec2(tc.x - 2.0*blur*hstep, tc.y - 2.0*blur*vstep)) * 0.12;
    sum += texture2D(u_texture, vec2(tc.x - 1.0*blur*hstep, tc.y - 1.0*blur*vstep)) * 0.15;

    sum += texture2D(u_texture, vec2(tc.x, tc.y)) * 0.16;

    sum += texture2D(u_texture, vec2(tc.x + 1.0*blur*hstep, tc.y + 1.0*blur*vstep)) * 0.15;
	sum += texture2D(u_texture, vec2(tc.x + 2.0*blur*hstep, tc.y + 2.0*blur*vstep)) * 0.12;
	sum += texture2D(u_texture, vec2(tc.x + 3.0*blur*hstep, tc.y + 3.0*blur*vstep)) * 0.09;
	sum += texture2D(u_texture, vec2(tc.x + 4.0*blur*hstep, tc.y + 4.0*blur*vstep)) * 0.05;

    gl_FragColor = vec4(sum.rgb, sum.w);
}