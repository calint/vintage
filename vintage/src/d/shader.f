#version 150 core
uniform sampler2D utx;
uniform bool urendzbuf;
in vec4 rgba;
in vec2 txcoord;
out vec4 out_Color;
const vec4 transp=vec4(0,0,0,0);
void main(){
	vec4 txrgba=texture(utx,txcoord);
	if(txrgba.rgba==transp)discard;//transparent pixel 
	float c=gl_FragCoord.z;
	if(!urendzbuf)
		out_Color=txrgba*rgba;
	else
		out_Color=vec4(c,c,c,1);
}