#version 310 es
precision lowp float;
layout(location=0) in vec3 position;
uniform mat4 VPMatrix;
out vec3 Position;
void main() {
Position=position;
 gl_Position=VPMatrix*vec4(position,1);
}
