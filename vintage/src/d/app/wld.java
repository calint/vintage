package d.app;
import d.box;
import d.obj;
import d.vbo;
import static d.box.*;
public class wld extends obj implements d.box.app{
	static final long serialVersionUID=1;
	//d.box.app
	public vbo[]vbos(){return new vbo[]{vboplr.o,vboblt.o,vbosqr.o,vbobackground.o};}
	{
		new background().pos(0,0,.5f);
		
		final float sprd=.2f;
		for(int i=0;i<8;i++)
			new plr().pos(rnd(-sprd,sprd),rnd(0,sprd),-1).scl(.02f,.02f,.02f);

		for(int i=0;i<512;i++){
			final float h=rnd(0,.2f);
			final float w=rnd(.05f,.1f);
			new sqr().pos(rnd(-1,1),h,0).scl(w,h,1);
		}
		
		new plr().pos(-.5f,0,0);
		new sqr().pos(.75f,.4f,0).scl(.1f,.4f,1).agl(0,0,0);
		new sqr().pos(.75f,.2f,0).scl(.1f,.2f,1).agl(0,0,0);
		new tri().pos(-1,0,0);
		new blt().dpos(.1f,0,0);
		
//		dpos(0,.05f,0);
		dagl(0,0,.01f);
	}
	public static int rainratems=10;
	private long t0;
	protected void update() throws Throwable{
		super.update();
		if((box.keys&128)!=0)
			new blt().pos(1,box.rnd(-1,1),0).dpos(-1,0,0);
			
		if(box.tms-t0>rainratems){
			t0=box.tms;
			new blt().pos(1,box.rnd(-1,1),0).dpos(-1,0,0);
		}
	}
}
