#version 310 es
precision lowp float;
layout (location=0) in vec3 position;
layout (location=1) in vec3 normal;
layout (location=2) in vec2 texCoord;
layout(std140,binding=1) uniform VP{
mat4 VPMatrix;
};
layout (std140,binding=2) uniform myLightMatrix{
 mat4 LightMatrix;
};
uniform mat4 Model;
out vec3 Position;
out vec3 LightCamera;
out vec3 Normal;
out vec2 TexCoord;

void main() {
TexCoord=texCoord;
Position=vec3(Model*vec4(position,1));
mat4 m=inverse(transpose(Model));
Normal=vec3(m*vec4(normal,1));
vec4 bbq=LightMatrix*Model*vec4(position,1);
LightCamera=bbq.xyz/bbq.w;
LightCamera=LightCamera*0.5f+0.5f;
gl_Position=VPMatrix*Model*vec4(position,1);
}