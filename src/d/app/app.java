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
		dagl(0,0,.01f);
		
		new shp().radius(.1f).scl(.1f,.1f,0).pos(0,-.5f,0);
//		new background().pos(0,0,.7f);
		new circlexy().radius(.2f).scl(.2f,.2f,.2f).pos(0,.5f,0).dagl(0,0,1).dpos(-.01f,0,0);
		new circlexy().radius(.1f).scl(.1f,.1f,.1f).pos(-.5f,.5f,0).dagl(0,0,1).dpos(.02f,0,0);
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
