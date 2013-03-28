package d.app;
import d.box;
import d.obj;
import d.vbo;
public class scene extends obj implements d.box.app{
	static final long serialVersionUID=1;
	public tri tri;
	public sqr sqr;
	public plr plr;
	public blt blt;
	{
		plr.pos(-.5f,0,0);
		sqr.pos(.75f,0,0);
		tri.pos(-1,0,0);
	}
	private long t0;
	protected void update() throws Throwable{
		super.update();
		if(box.tms-t0>1000){
			t0=box.tms;
			new blt().pos(-1,box.rnd(),0);
		}
	}
	
	//d.box.app
	public vbo[]vbos()throws Throwable{return new vbo[]{vboplr.o,vboblt.o};}
}
