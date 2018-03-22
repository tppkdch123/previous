#version 310 es
precision lowp float;
in vec2 TexCoord;
uniform sampler2D canvas;
uniform float exposure;
out vec4 gl_FragColor;
vec3 yingshe(vec3 color){
 return color/(color+vec3(1.0));
 }
void main() {
vec3 color=texture(canvas,TexCoord).rgb;
//vec3 mapped=vec3(1.0)-exp(-color*exposure);
//mapped=pow(mapped,vec3(0.45));
//vec3 result=pow(color,vec3(0.45));
//color=pow(color,vec3(0.45));
gl_FragColor=vec4(color,1);
}
