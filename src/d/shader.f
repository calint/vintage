#version 150 core
uniform sampler2D utx;
in vec4 rgba;
in vec2 txcoord;
out vec4 out_Color;
const vec4 transp=vec4(0,0,0,0);
void main(){
//	if((gl_FragCoord.z/gl_FragCoord.w)>.5)discard;//? .5
	vec4 txrgba=texture(utx,txcoord);
	if(txrgba.rgba==transp)discard;//transparent pixel 
//	out_Color=vec4(gl_FragCoord.z,0,0,1);
	out_Color=txrgba*rgba;
}