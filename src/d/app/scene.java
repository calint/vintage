package d.app;
import d.obj;
import d.vbo;
public class scene extends obj implements d.box.app{
	static final long serialVersionUID=1;
	public tri tri;
	public sqr sqr;
	public plr plr;
	public vbo[]vbos()throws Throwable{
		return new vbo[]{
				vbosqr.o,
				vbotri.o
		};
	}
}
