#version 150 core
uniform mat4 umxmw;//model world matrix
uniform mat4 umxwv;//world view matrix
//uniform vec2 us=vec2(1,1);//view port adjust
in vec4 in_Position;
in vec4 in_Color;
in vec2 in_TextureCoord;
smooth out vec4 in_rgba;
smooth out vec2 in_txcoord;
void main(){
	vec4 pw=umxmw*in_Position;//to world coords
	vec4 pv=umxwv*pw;//to view coords
	//vec4 ps=pv;
	//ps.x*=us.x;//viewport adjust
	//ps.y*=us.y;//
	//gl_Position=ps;
	gl_Position=pv;
	in_rgba=in_Color;
	in_txcoord=in_TextureCoord;
}