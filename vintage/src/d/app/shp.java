package d.app;
import d.box;
import d.obj;
public class shp extends obj{
	{vbo(vboshp.o);colbitsmsk=2;colbits=1+2+4;}
	public static float dagl=(float)Math.PI;
	public static float dpos=.5f;
	public static int firerate=0;
	private long lastfire;
	protected void update()throws Throwable{
		super.update();
		dpos(0,0,0);
		dagl(0,0,0);
		
		if((box.keys&1)!=0){//w
			incdpos(0,dpos,0);
//			incdagl(0,0,dagl);
		}
		if((box.keys&2)!=0){//a
			incdpos(-dpos,0,0);
		}
		if((box.keys&4)!=0){//s
			incdpos(0,-dpos,0);
//			incdagl(0,0,-dagl);
		}
		if((box.keys&8)!=0){//d
			incdpos(dpos,0,0);
		}
		if((box.keys&16)!=0){//j
			if((box.tms-lastfire)>firerate){
				lastfire=box.tms;
				new blt().from(this).pos(pos.x,pos.y,pos.z).dpos((float)Math.cos(agl.z-Math.PI/2),(float)-Math.sin(agl.z-Math.PI/2),0).agl(agl.x,agl.y,agl.z);
			}
		}
		
		// coldet
//		for(final Iterator<obj>i=box.q(tumbloid.class);i.hasNext();){
//			final obj o=i.next();
//			if(!obj.isincol(this,o))
//				continue;
//			oncol(o);
//		}
//		for(final Iterator<obj>i=box.q(blt.class);i.hasNext();){
//			final obj o=i.next();
//			if(!obj.isincol(this,o))
//				continue;
//			oncol(o);
//		}
	}
	protected void oncol(final obj o)throws Throwable{
		System.out.println("oncol:"+o.getClass().getName());
		if(o instanceof tumbloid){
			radius+=.01f;
			scl(radius,radius,radius);
			o.rm();
		}else if(o instanceof blt){
			if(((blt)o).from==this)
				return;
			radius-=.01f;
			scl(radius,radius,radius);
			o.rm();
		}
		if(o.colbitsmsk==1){
			
		}
	}
}
