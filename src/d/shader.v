#version 150 core
uniform mat4 umxmw;//model world matrix
uniform mat4 umxwv;//world view matrix
uniform bool udopersp=true;
in vec3 in_pos;
in vec4 in_rgba;
in vec2 in_txc;
out vx{vec4 rgba;vec2 txcoord;}vtx;
void main(){
	vtx.rgba=in_rgba;
	vtx.txcoord=in_txc;
	vec4 pw=umxmw*vec4(in_pos,1);//to world coords
	vec4 pv=umxwv*pw;//to view coords (clipspace)
	if(udopersp){
		pv.w=-pv.z;
	}
	gl_Position=pv;
}