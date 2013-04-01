package d.app;
import d.box;
import d.obj;
public class plr extends obj{{vbo(vboplr.o).radius(1).scl(1,1,1);colbitsmsk=2;}
	private long lastfire;
	private double a=0;
	public static float dagl=(float)Math.PI*2;
	public static float dpos=.5f;
	public static int firerate=100;
	protected void update()throws Throwable{
		super.update();
				
		final float d=.05f+(float)(.05*Math.sin(a));
		scl(d,d,0);
		radius(d);
		a+=(Math.PI/4*box.dt);
		
		dpos(0,0,0);
		dagl(0,0,0);
		if((box.keys&1)!=0){//w
			incdpos(0,dpos,0);
		}
		if((box.keys&256)!=0){//q
			incdagl(0,0,-dagl);
		}
		if((box.keys&512)!=0){//e
			incdagl(0,0,dagl);
		}
		if((box.keys&2)!=0){//a
			incdpos(-dpos,0,0);
		}
		if((box.keys&4)!=0){//s
			incdpos(0,-dpos,0);
		}
		if((box.keys&8)!=0){//d
			incdpos(dpos,0,0);
		}
		if((box.keys&16)!=0){//j
			if((box.tms-lastfire)>firerate){
				lastfire=box.tms;
				new blt().pos(pos.x,pos.y,pos.z).dpos((float)Math.cos(agl.z-Math.PI/2),(float)-Math.sin(agl.z-Math.PI/2),0).agl(agl.x,agl.y,agl.z);
			}
		}
		if((box.keys&32)!=0){//k
			if(vbo==vboblt.o){
				vbo=vboplr.o;
			}else{
				vbo=vboblt.o;
			}
			vbo=vboblt.o;
//			vbo(vbosqr.o);
		}
//		System.out.println(this+" "+pos[0]+","+pos[1]);
	}
}
