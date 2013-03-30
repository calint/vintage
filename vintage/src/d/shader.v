#version 150 core
uniform mat4 umxmw;//model world matrix
uniform mat4 umxwv;//world view matrix
in vec3 in_Position;
in vec4 in_Color;
in vec2 in_TextureCoord;
out vtxs{vec4 rgba;vec2 txcoord;}vtx;
void main(){
	vec4 pw=umxmw*vec4(in_Position,1);//to world coords
	vec4 pv=umxwv*pw;//to view coords
	gl_Position=pv;
	vtx.rgba=in_Color;
	vtx.txcoord=in_TextureCoord;
}