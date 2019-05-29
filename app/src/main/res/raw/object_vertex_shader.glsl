uniform mat4 u_MVMatrix;
uniform mat4 u_IT_MVMatrix;
uniform mat4 u_MVPMatrix;

uniform vec3 u_VectorToLight;
uniform vec4 u_PointLightPosition;
uniform vec3 u_PointLightColor;

attribute vec3 a_Position;
attribute vec3 a_Normal;
attribute vec2 a_TextureCoordinates;

varying vec2 v_TextureCoordinates;
varying vec3 v_Color;

vec3 materialColor;
vec4 eyeSpacePosition;
vec3 eyeSpaceNormal;

vec3 getAmbientLighting();
vec3 getDirectionalLighting();
vec3 getPointLighting();


void main() {
    v_TextureCoordinates = a_TextureCoordinates;

    eyeSpacePosition = u_MVMatrix * vec4(a_Position, 1.0);
    eyeSpaceNormal = normalize(vec3(u_IT_MVMatrix * vec4(a_Normal, 0.0)));

    materialColor = vec3(1.0, 1.0, 1.0);
    v_Color = getAmbientLighting();
    //v_Color+=getDirectionalLighting();
    v_Color+=getPointLighting();

    gl_Position = u_MVPMatrix * vec4(a_Position, 1.0);
}

vec3 getAmbientLighting(){
    return materialColor*0.1;
}
vec3 getDirectionalLighting(){
    return materialColor*0.3*max(dot(eyeSpaceNormal, u_VectorToLight), 0.0);
}
vec3 getPointLighting(){
    vec3 toPointLight = vec3(u_PointLightPosition) - vec3(eyeSpacePosition);
    float distance = length(toPointLight);
    toPointLight = normalize(toPointLight);

    float cosine = max(dot(eyeSpaceNormal, toPointLight), 0.0);
    return vec3(materialColor*u_PointLightColor * 4.0 * cosine )/(distance*distance);
}
