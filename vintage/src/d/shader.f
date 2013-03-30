#version 150 core
uniform sampler2D utx;
in vec4 rgba;
in vec2 txcoord;
out vec4 out_Color;
const vec4 transp=vec4(0,0,0,0);
void main(){
	vec4 txrgba=texture(utx,txcoord);
	if(txrgba.rgba==transp)discard;//transparent pixel 
	out_Color=(rgba+txrgba)/2;
}