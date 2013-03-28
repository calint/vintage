package d.app;
import d.box;
import d.obj;
public class plr extends obj{static final long serialVersionUID=1;
	{vbo=vboplr.o;}
	private long lastfire;
	private double a;
	public void update()throws Throwable{
		super.update();
		
		scl[0]=scl[1]=(float)Math.sin(a);
		a+=Math.PI/400*box.dt;
		
		dpos(0,0,0);
		if((box.keys&1)!=0){//w
			incdpos(0,.001f,0);
		}
		if((box.keys&2)!=0){//a
			incdpos(-.001f,0,0);
		}
		if((box.keys&4)!=0){//s
			incdpos(0,-.001f,0);
		}
		if((box.keys&8)!=0){//d
			incdpos(.001f,0,0);
		}
		if((box.keys&16)!=0){//j
			if((box.tms-lastfire)>100){
				lastfire=box.tms;
				new blt().pos(pos[0],pos[1],pos[2]);
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
