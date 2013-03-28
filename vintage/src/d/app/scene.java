package d.app;
import d.obj;
import d.vbo;
public class scene extends obj implements d.box.app{
	static final long serialVersionUID=1;
	public tri tri;
	public sqr sqr;
	public plr plr;
	{
		plr.setpos(0,0,0);
		sqr.setpos(0,0,0);
		tri.setpos(-1,0,0);
	}
	
	//d.box.app
	public vbo[]vbos()throws Throwable{return new vbo[]{vbosqr.o,vbotri.o};}
}
