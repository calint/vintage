#version 150 core
uniform mat4 umxmw;//model world matrix
uniform mat4 umxwv;//world view matrix
in vec4 in_Position;
in vec4 in_Color;
in vec2 in_TextureCoord;
smooth out vec4 f_rgba;
smooth out vec2 f_txcoord;
void main(){
	vec4 pw=umxmw*in_Position;//to world coords
	vec4 pv=umxwv*pw;//to view coords
	gl_Position=pv;
	f_rgba=in_Color;
	f_txcoord=in_TextureCoord;
}