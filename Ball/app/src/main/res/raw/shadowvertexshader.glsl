#version 310 es
precision lowp float;
layout (location=0) in vec3 position;
layout (std140,binding=2) uniform myLightMatrix{
 mat4 LightMatrix;
};
uniform mat4 Model;
void main() {
 gl_Position=LightMatrix*Model*vec4(position,1);
}
