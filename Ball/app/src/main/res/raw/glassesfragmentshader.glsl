#version 310 es
precision lowp float;
 in vec3 Position;
  in vec3 Normal;
  in vec2 TexCoord;
  in vec3 LightCamera;
  uniform sampler2D sky;
  uniform sampler2D shadowMap;
  uniform float shininess;
  layout(std140,binding=0) uniform Light{
   vec3 direct;
   vec3 color;
   vec3 cameraPosition;
   float ambient;
  };
  out vec4 gl_FragColor;
float computeFogFactor(vec3 cameraPosition,vec3 Position){
float fogDistance=length(cameraPosition-Position);
const float end=120.0f;
const float start=5.0;
float tmpFactor=smoothstep(start,end,fogDistance);
return tmpFactor;
}
  void main(){
  vec3 normalizeDirect=normalize(-direct);
  vec3 normalizeNormal=normalize(Normal);
  float diffuseNumber=dot(normalizeDirect,normalizeNormal);
    float currentDepth=LightCamera.z;
    float ourShadow=(currentDepth>1.0||currentDepth<0.0||LightCamera.x>1.0||LightCamera.y>1.0||LightCamera.x<0.0||LightCamera.y<0.0)?0.0:1.0;
    float depth=texture(shadowMap,LightCamera.xy).r;
    //float bias=max(0.005,0.095*(1.0-diffuseNumber));
    float bias=0.04f;
    float a=currentDepth-depth>bias?1.0:0.0;
    a*=ourShadow;
  vec3 Color=texture(sky,1.0-TexCoord).xyz;
  vec3 ambientColor=0.2f*Color*color;
  vec3 viewDirect=normalize(cameraPosition-Position);
  vec3 halfWayDirect=normalize(viewDirect+normalizeDirect);
  vec3 diffuseColor=Color*color*max(0.0,diffuseNumber);
  vec3 specColor=pow(max(0.0,dot(halfWayDirect,normalizeNormal)),shininess)*color*Color;
  vec4 resultColor=vec4(ambientColor+(1.0-a)*(specColor+diffuseColor),1);
  float Fog=computeFogFactor(cameraPosition,Position);
  vec4 FogColor=vec4(0.9,0.9,0.9,1.0);
  if(Fog!=1.0){
  gl_FragColor=Fog*FogColor+(1.0-Fog)*resultColor;
  }
  else gl_FragColor=FogColor;
  }
