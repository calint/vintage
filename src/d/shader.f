#version 150 core
uniform sampler2D utx;
smooth in vec4 f_rgba;
smooth in vec2 f_txcoord;
out vec4 out_Color;
void main(){
	const vec4 transp=vec4(0,0,0,0);
	vec4 txrgba=texture(utx,f_txcoord);
	if(txrgba.rgba==transp)discard;//transparent pixel 
	out_Color=(f_rgba+txrgba)/2;
}