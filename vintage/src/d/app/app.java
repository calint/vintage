package d.app;
import d.box;
import d.obj;
import d.vbo;
public class app extends obj implements d.box.app{
	static final long serialVersionUID=1;
	//d.box.app
	public vbo[]vbos(){return new vbo[]{
			vboplr.o,
			vboblt.o,
			vbosqr.o,
//			vbobackground.o,
			vboshp.o,
			vbotumbloid.o,
			vbocrclexy.o
	};}{
//	vbo(vboviewpyr.o);
	pos(0,0,1);
//	dpos(0,0,-.01f);
	dagl(0,0,-.1f);
	new shp().radius(.1f).scl(.1f,.1f,.1f).pos(0,0,.1f);
	new shp().radius(.1f).scl(.1f,.1f,.1f).pos(0,0,.2f);
	new shp().radius(.1f).scl(.1f,.1f,.1f).pos(0,0,.3f);
	
	final float sprd=.1f;
	for(int i=0;i<16;i++)
		new plr().radius(.02f).scl(.02f,.02f,.02f).pos(box.rnd(-sprd,sprd),box.rnd(-sprd,sprd),0);

//	new circlexy().radius(.1f).scl(.1f,.1f,.1f).pos(-.5f,0,0).dagl(0,0,2).dpos(0,0,.05f);
	
//	for(float u=-1;u<1;u+=.08f)
//		for(float v=-1;v<1;v+=.08f)
//			new circlexy().radius(.1f).scl(.1f,.1f,.1f).dpos(0,.1f,0).pos(u,v,u+v).dagl(0,0,u);
	final float s=.9f;
	new circlexy().radius(.1f).scl(.1f,.1f,.1f).pos(-s,-s,0).dagl(0,0,2);
	new circlexy().radius(.1f).scl(.1f,.1f,.1f).pos(-s, s,0).dagl(0,0,2);
	new circlexy().radius(.1f).scl(.1f,.1f,.1f).pos( s, s,0).dagl(0,0,2);
	new circlexy().radius(.1f).scl(.1f,.1f,.1f).pos( s,-s,0).dagl(0,0,2);
	new circlexy().radius(.1f).scl(.1f,.1f,.1f).pos(0,0,.75f).dagl(0,0,2).dpos(0,0,.01f);
	new circlexy().radius(.1f).scl(.1f,.1f,.1f).pos(0,0,1.2f).dagl(0,0,2).dpos(0,0,0);
	new circlexy().radius(.1f).scl(.1f,.1f,.1f).pos(0,0,.8f).dagl(0,0,2).dpos(0,0,.02f);
	new circlexy().radius(.1f).scl(.1f,.1f,.1f).pos(0,0,.5f).dagl(0,0,2).dpos(0,0,.02f);

	
//	new circlexy().radius(.1f).scl(.1f,.1f,.1f).dpos(0,.1f,0).pos(.1f,.1f,.75f).dagl(0,0,2);
//	new circlexy().radius(.1f).scl(.1f,.1f,.1f).dpos(0,.1f,0).pos(.2f,.2f,.50f).dagl(0,0,3);
//	new circlexy().radius(.1f).scl(.1f,.1f,.1f).dpos(0,.1f,0).pos(.3f,.3f,.25f).dagl(0,0,4);
//	new circlexy().radius(.1f).scl(.1f,.1f,.1f).dpos(0,.1f,0).pos(.4f,.4f,.10f).dagl(0,0,5);
//	new circlexy().radius(.1f).scl(.1f,.1f,.1f).dpos(0,.1f,0).pos(.5f,.5f,.01f).dagl(0,0,6);
//	new circlexy().radius(.1f).scl(.1f,.1f,.1f).dpos(0,.1f,0).pos(-.1f,0,-.5f).dagl(0,0,7);
//	new circlexy().radius(.1f).scl(.1f,.1f,.1f).dpos(0,.1f,0).pos(.6f,.6f,   0);
//		
//		new background().pos(0,0,.1f);
//		new shp().radius(.1f).scl(.1f,.1f,0).pos(0,-.5f,0);
//		for(int i=0;i<8;i++){
//			final float r=box.rnd(.05,.1);
//			final float s=(float)Math.sqrt(r*r+r*r);
//			new circlexy().radius(r).scl(s,s,s).pos(box.rnd(-.5,.5),box.rnd(0,.5),0).dagl(0,0,1).dpos(box.rnd(0,-.3),box.rnd(0,-.3),0);
//		}
		final int nstructs=128;
		for(int i=0;i<nstructs;i++){
			final float h=box.rnd(0,.2f);
			final float w=box.rnd(.05f,.1f);
			new sqr().radius(w).scl(w,h,1).pos(box.rnd(-1,1),h,0);
		}
	}
	
	private long t0;
	public static int rainratems=10;
	public static int nrainitems=20;
	protected void update() throws Throwable{
		super.update();
		if(pos.z>0)
			dpos(0,0,-.01f);
		if(pos.z<-1)
			dpos(0,0,.01f);
		if(box.tms-t0>rainratems){
			t0=box.tms;
			for(int i=0;i<nrainitems;i++)
				new blt().pos(1,box.rnd(-1,1),0).agl(0,0,-(float)Math.PI/2).dpos(-1,0,0);
			if(box.rnd()<.05)
				new tumbloid().radius(.05f).scl(.05f,.05f,0).pos(box.rnd(-1,1),1,0).dpos(0,-.5f,0).dagl(0,0,box.rnd(0,3));
		}
	}
}
