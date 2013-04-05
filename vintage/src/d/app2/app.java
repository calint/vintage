package d.app2;
import d.box;
import d.obj;
import d.vbo;
public class app extends obj implements d.box.app{
	static final long serialVersionUID=1;
	//d.box.app
	int nplayers=2;
	obj[]netobjs=new obj[nplayers];
	public obj[]netobjs()throws Throwable{return netobjs;}
	public vbo[]vbos(){return new vbo[]{
			vboplr.o,
			vboblt.o,
			vbosqr.o,
//			vbobackground.o,
			vboshp.o,
			vbotumbloid.o,
			vbocrclexy.o
	};}{
//	vbo(vboviewpyr.o);//.radius(1).scl(1,1,1);
	radius(4);
	//.scl(4,4,4);
	pos(0,0,.99f);
//	dpos(0,0,-.01f);
//	dagl(0,0,-.1f);
	netobjs[0]=new shp().radius(.1f).scl(.1f,.1f,.1f).pos(.5f,0,.2f);
	netobjs[1]=new shp().radius(.1f).scl(.1f,.1f,.1f).pos(-.5f,0,.2f);

	
	final float r=.02f,s=r,stp=.25f,lmt=4;
	for(float u=-lmt;u<lmt;u+=stp)
		for(float v=-lmt;v<lmt;v+=stp)
			new circlexy().radius(r).scl(s,s,s).pos(u,v,0).dagl(0,0,u);
	
	
	final float sprd=.1f;
	for(int i=0;i<16;i++)
		new plr().radius(.02f).scl(.02f,.02f,.02f).pos(box.rnd(-sprd,sprd),box.rnd(-sprd,sprd),0);

//		
//		new background().pos(0,0,.1f);

		final int nstructs=128;
		for(int i=0;i<nstructs;i++){
			final float h=box.rnd(0,.2f);
			final float w=box.rnd(.05f,.1f);
			new sqr().radius(w).scl(w,h,1).pos(box.rnd(-radius,radius),h,0);
		}
	}
	
	private long t0;
	public static int rainratems=100;
//	public static int rainratems=100;
	public static int nrainitems=40;
	protected void update() throws Throwable{
		super.update();
//		if(pos.z>0)
//			dpos(0,0,-.01f);
//		if(pos.z<-1)
//			dpos(0,0,.01f);
		if(box.tms-t0>rainratems){
			t0=box.tms;
			for(int i=0;i<nrainitems;i++)
				new blt().pos(box.rnd(-radius,radius),1,0).agl(0,0,-(float)Math.PI/2).dpos(0,-1,0);
			if(box.rnd()<.05)
				new tumbloid().radius(.05f).scl(.05f,.05f,0).pos(box.rnd(-1,1),1,0).dpos(0,-.5f,0).dagl(0,0,box.rnd(0,3));
		}
	}
}
