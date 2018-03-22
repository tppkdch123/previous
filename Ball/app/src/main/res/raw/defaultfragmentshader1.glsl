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
 vec3 getDiffuse(vec3 Normal,vec3 LightDirect,vec3 LightColor){
     return LightColor*max(0.0,dot(normalize(-LightDirect),normalize(Normal)));
  }
  vec3 getSpecular(vec3 LightDirect,vec3 LightColor,vec3 Normal,vec3 FragPosition,vec3 cameraPosition,float shininess){
    vec3 viewDirect=normalize(cameraPosition-FragPosition);
    vec3 halfWayDirect=normalize(viewDirect+normalize(LightDirect));
    float f= pow(max(0.0,dot(halfWayDirect,normalize(Normal))),shininess);
    return f*LightColor;
  }
  float shadowNum(sampler2D shadowMap,vec3 LightCamera,vec3 Normal,vec3 direct){
   float currentDepth=LightCamera.z;
   if(currentDepth>1.0||currentDepth<0.0)return 0.0;
    if(LightCamera.x>1.0||LightCamera.y>1.0||LightCamera.x<0.0||LightCamera.y<0.0)
     return 0.0;
   float depth=texture(shadowMap,LightCamera.xy).r;
   float bias=max(0.005,0.095*(1.0-dot(normalize(Normal),-normalize(direct))));
   float shadow = 0.0;
   ivec2 texel=textureSize(shadowMap, 0);
   vec2 texelSize=vec2(1.0/float(texel.x),1.0/float(texel.y));
   for(int x = -1; x <= 1; ++x)
   {
       for(int y = -1; y <= 1; ++y)
       {
           float pcfDepth = texture(shadowMap, LightCamera.xy + vec2(x, y) * texelSize).r;
           shadow += currentDepth - bias> pcfDepth ? 1.0 : 0.0;
       }
   }
   shadow /= 9.0;
   return shadow;
  }
 bool isShadow(sampler2D shadowMap,vec3 LightCamera,vec3 Normal,vec3 direct){
  float currentDepth=LightCamera.z;
  if(currentDepth>1.0||currentDepth<0.0)return false;
  if(LightCamera.x>1.0||LightCamera.y>1.0||LightCamera.x<0.0||LightCamera.y<0.0)
  return false;
float depth=texture(shadowMap,LightCamera.xy).r;
//float bias=max(0.005,0.05*(1.0-dot(normalize(Normal),-normalize(direct))));
float bias=0.03;
if(currentDepth-depth>bias)
return true;
else return false;
  }

 // gl_FragColor=texture(sky,TexCoord);
 void main() {
  vec3 Color=vec3(texture(sky,vec2(1.0-TexCoord.x,TexCoord.y)));
  vec3 ambientColor=0.2*color*Color;
  vec3 diffuseColor=getDiffuse(Normal,direct,color)*Color;
  vec3 specColor=getSpecular(-direct,color,Normal,Position,cameraPosition,shininess)*Color;
  if(isShadow(shadowMap,LightCamera,Normal,direct))
  gl_FragColor=vec4(ambientColor,1);
  else
gl_FragColor=vec4(ambientColor+diffuseColor+specColor,1);
   //gl_FragColor=vec4(ambientColor+(diffuseColor+specColor)*(1.0-shadowNum(shadowMap,LightCamera,Normal,direct)),1);
   }