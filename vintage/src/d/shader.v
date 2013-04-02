#version 150 core
uniform mat4 umxmw;//model world matrix
uniform mat4 umxwv;//world view matrix
in vec3 in_pos;
in vec4 in_rgba;
in vec2 in_txc;
out vx{vec4 rgba;vec2 txcoord;}vtx;
void main(){
	vec4 pw=umxmw*vec4(in_pos,1);//to world coords
	vec4 pv=umxwv*pw;//to view coords
	vec3 ps=vec3(pv.x/pv.z,pv.y/pv.z,pv.z);
	gl_Position=vec4(ps,1);
	vtx.rgba=in_rgba;
	vtx.txcoord=in_txc;
}