package d.app;
import d.box;
import d.obj;
import d.vbo;
public class scene extends obj implements d.box.app{
	static final long serialVersionUID=1;
	public tri tri;
	public sqr sqr;
	public sqr sqr2;
	public plr plr;
	public blt blt;
	{
		plr.pos(-.5f,0,0);
		sqr.pos(.75f,0,0).scl(.1f,.4f,1).agl(0,0,0);
		sqr2.pos(.75f,0,0).scl(.1f,.2f,1).agl(0,0,0);
		tri.pos(-1,0,0).scl(1,1,1);
	}
	private long t0;
	private static int rainratems=100;
	protected void update() throws Throwable{
		super.update();
		if(box.tms-t0>rainratems){
			t0=box.tms;
			new blt().pos(1,box.rnd(),0).dpos(-1,0,0);
		}
	}
	
	//d.box.app
	public vbo[]vbos()throws Throwable{return new vbo[]{vboplr.o,vboblt.o,vbosqr.o};}
}
