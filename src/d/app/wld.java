package d.app;
import d.box;
import d.obj;
import d.vbo;
public class wld extends obj implements d.box.app{
	static final long serialVersionUID=1;
	{
		new plr().pos(-.5f,0,0);
		new sqr().pos(.75f,0,0).scl(.1f,.4f,1).agl(0,0,0);
		new sqr().pos(.75f,0,0).scl(.1f,.2f,1).agl(0,0,0);
		new tri().pos(-1,0,0);
		new blt().dpos(.1f,0,0);
		
//		dpos(0,.05f,0);
		dagl(0,0,.01f);
	}
	private static int rainratems=100;
	private long t0;
	protected void update() throws Throwable{
		super.update();
		if(box.tms-t0>rainratems){
			t0=box.tms;
			new blt().pos(1,box.rnd(-1,1),0).dpos(-1,0,0);
		}
	}
	
	//d.box.app
	public vbo[]vbos()throws Throwable{return new vbo[]{vboplr.o,vboblt.o,vbosqr.o};}
}
