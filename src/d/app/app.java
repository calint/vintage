package d.app;
import static d.box.rnd;
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
			vbobackground.o,
			vboshp.o,
			vbotumbloid.o,
			vbocrclexy.o
	};}{		
		pos(0,0,1);
		dpos(0,0,-.01f);
		dagl(0,0,.01f);
		
		new background().pos(0,0,.1f);
		new shp().radius(.1f).scl(.1f,.1f,0).pos(0,-.5f,0);
		for(int i=0;i<8;i++){
			final float r=box.rnd(.05,.1);
			final float s=(float)Math.sqrt(r*r+r*r);
			new circlexy().radius(r).scl(s,s,s).pos(box.rnd(-.5,.5),box.rnd(0,.5),0).dagl(0,0,1).dpos(box.rnd(0,-.3),box.rnd(0,-.3),0);
		}
			//		new circlexy().radius(.1f).scl(.1f,.1f,.1f).pos(-.5f,.5f,0).dagl(0,0,1).dpos(.02f,0,0);
		final float sprd=.1f;
		for(int i=0;i<16;i++)
			new plr().radius(.02f).scl(.02f,.02f,.02f).pos(rnd(-sprd,sprd),rnd(-sprd,sprd),0);
//		final int nstructs=32;
//		for(int i=0;i<nstructs;i++){
//			final float h=rnd(0,.2f);
//			final float w=rnd(.05f,.1f);
//			new sqr().radius(w).scl(w,h,1).pos(rnd(-1,1),h,0);
//		}
	}
	
	private long t0;
	public static int rainratems=10;
	public static int nrainitems=20;
	protected void update() throws Throwable{
		super.update();
		if(box.tms-t0>rainratems){
			t0=box.tms;
			for(int i=0;i<nrainitems;i++)
				new blt().pos(1,box.rnd(-1,1),0).agl(0,0,-(float)Math.PI/2).dpos(-1,0,0);
			if(box.rnd()<.05)
				new tumbloid().radius(.05f).scl(.05f,.05f,0).pos(box.rnd(-1,1),1,0).dpos(0,-.5f,0).dagl(0,0,box.rnd(0,3));
		}
	}
}
