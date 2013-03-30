#version 150 core
layout(triangles)in;
layout(triangle_strip)out; 
layout(max_vertices=3)out;
in vx{vec4 rgba;vec2 txcoord;}vtx[];
out vec2 txcoord;
out vec4 rgba;
void main(){
	int i;
	const int n=gl_in.length();
	for(i=0;i<n;i++){
		gl_Position=gl_in[i].gl_Position;
		txcoord=vtx[i].txcoord;
		rgba=vtx[i].rgba;
//		rgba=vec4(0,0,0,0);
		EmitVertex();
	}
	EndPrimitive();
}