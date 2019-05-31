uniform mat4 u_Model;
uniform mat4 u_IT_Model;
uniform mat4 u_ModelViewProjection;

attribute vec3 a_Position;
attribute vec3 a_Normal;
attribute vec2 a_TextureCoordinates;

varying vec2 v_TextureCoordinates;
varying vec3 v_Normal;
varying vec3 v_Position;


void main() {
    v_TextureCoordinates = a_TextureCoordinates;
    v_Normal = mat3(u_IT_Model)* a_Normal;
    v_Position = vec3(u_Model * vec4(a_Position, 1.0));

    gl_Position = u_ModelViewProjection * vec4(a_Position, 1.0);
}

