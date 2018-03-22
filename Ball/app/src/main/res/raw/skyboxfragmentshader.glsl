#version 310 es
precision lowp float;
in vec3 Position;
uniform samplerCube skyBox;
out vec4 gl_FragColor;
void main() {
gl_FragColor=texture(skyBox,Position)*0.4f+vec4(0.9f,0.9f,0.9f,1)*0.6f;
}
