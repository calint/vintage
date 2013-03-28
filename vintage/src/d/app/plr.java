package d.app;
import d.box;
import d.obj;
public class plr extends obj{static final long serialVersionUID=1;
	{vbo(vboplr.o).scl(1,1,1);}
	private long lastfire;
	private double a=0;
	public static float dagl=(float)Math.PI;
	public static float dpos=.5f;
	public static int firerate=1;
	protected void update()throws Throwable{
		super.update();
				
		scl[0]=scl[1]=(float)Math.sin(a);
		a+=(Math.PI/4*box.dt);
		
		dpos(0,0,0);
		dagl(0,0,0);
		if((box.keys&1)!=0){//w
			incdpos(0,dpos,0);
			incdagl(0,0,dagl);
		}
		if((box.keys&2)!=0){//a
			incdpos(-dpos,0,0);
		}
		if((box.keys&4)!=0){//s
			incdpos(0,-dpos,0);
			incdagl(0,0,-dagl);
		}
		if((box.keys&8)!=0){//d
			incdpos(dpos,0,0);
		}
		if((box.keys&16)!=0){//j
			if((box.tms-lastfire)>firerate){
				lastfire=box.tms;
				new blt().pos(pos[0],pos[1],pos[2]).dpos((float)Math.cos(agl[2]+Math.PI/2),(float)-Math.sin(agl[2]+Math.PI/2),0).agl(agl[0],agl[1],agl[2]);
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
