#version 310 es

layout (location=0) in vec3 position;
layout (location=1) in vec3 normal;
layout (location=2) in vec2 texCoord;
layout (location=4) in vec3 offset;
layout (std140,binding=2) uniform myLightMatrix{
 mat4 LightMatrix;
};
layout(std140,binding=1) uniform VP{
mat4 VPMatrix;
};
uniform mat4 Model;
out vec3 Position;
out vec3 LightCamera;
out vec3 Normal;
out vec2 TexCoord;
void main() {
float q=float(gl_InstanceID)/1000.0f;
float qe=0.4f+(float(gl_InstanceID)/120.0f);
TexCoord=texCoord;
Position=vec3(Model*vec4(position*qe+q+offset,1));
mat4 m=inverse(transpose(Model));
Normal=vec3(m*vec4(normal,1));
vec4 bbq=LightMatrix*Model*vec4(position*qe+q+offset,1);
LightCamera=bbq.xyz/bbq.w;
LightCamera=LightCamera*0.5f+0.5f;
gl_Position=VPMatrix*Model*vec4(position*qe+q+offset,1);
}
