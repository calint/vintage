package d.app;
import d.box;
import d.obj;
import d.vbo;
import static d.box.*;
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
	};}
	{
		new background().pos(0,0,.5f);
		new circlexy().radius(.2f).scl(.2f,.2f,.2f).pos(0,.5f,0).dagl(0,0,1).dpos(-.01f,0,0);
	
		new shp().radius(.1f).scl(.1f,.1f,0).pos(0,-.5f,0);
		
		final float sprd=.1f;
		for(int i=0;i<8;i++)
			new plr().radius(.02f).scl(.02f,.02f,.02f).pos(rnd(-sprd,sprd),rnd(-sprd,sprd),0);

		for(int i=0;i<512;i++){
			final float h=rnd(0,.2f);
			final float w=rnd(.05f,.1f);
			new sqr().pos(rnd(-1,1),h,0).scl(w,h,1);
		}
//		
//		new plr().pos(-.5f,0,0);
		
//		new sqr().pos(.75f,.4f,0).scl(.1f,.4f,1).agl(0,0,0);
//		new sqr().pos(.75f,.2f,0).scl(.1f,.2f,1).agl(0,0,0);
//		new tri().pos(-1,0,0);
//		new blt().dpos(.1f,0,0);
		
//		dpos(0,.05f,0);
		dagl(0,0,.01f);
	}
	public static int rainratems=100;
	private long t0;
	protected void update() throws Throwable{
		super.update();
		if((box.keys&128)!=0)
			new blt().pos(1,box.rnd(-1,1),0).dpos(-1,0,0);
			
		if(box.tms-t0>rainratems){
			t0=box.tms;
			new blt().pos(1,box.rnd(-1,1),0).dpos(-1,0,0);
//			new tumbloid().scl(.05f,.05f,0).pos(0,1,0).dpos(0,-.5f,0).dagl(0,0,1);
			if(box.rnd()<.05)
				new tumbloid().radius(.05f).scl(.05f,.05f,0).pos(box.rnd(-1,1),1,0).dpos(0,-.5f,0).dagl(0,0,box.rnd(0,3));
		}
	}
}
