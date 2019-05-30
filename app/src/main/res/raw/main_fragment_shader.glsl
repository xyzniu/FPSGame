precision mediump float;

uniform sampler2D u_TextureUnit;
uniform vec3 u_LightColor;
uniform vec3 u_LightPosition;
uniform vec3 u_ViewPosition;

varying vec2 v_TextureCoordinates;
varying vec3 v_Normal;
varying vec3 v_Position;

void main() {
    // ambient
    float ambientStrength = 0.1;
    vec3 ambient = ambientStrength * u_LightColor;

    // diffuse
    vec3 lightDirection = normalize(v_LightPosition - v_Position);
    float diff = max(dot(v_Normal, lightDirection), 0.0);
    vec3 diffuse = diff * u_LightColor;

    // specular
    float specularStrength = 0.5;
    vec3 viewDir = normalize(u_ViewPosition - v_Position);
    vec3 reflectDir = reflect(-lightDirection, v_Normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
    vec3 specular = specularStrength * spec * u_LightColor;

    vec3 objectColor = vec3(texture2D(u_TextureUnit, v_TextureCoordinates));
    vec3 result = (ambient + diffuse + specular) * objectColor;

    gl_FragColor = vec4(result, 1.0);
}