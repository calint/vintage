package d.app;
import d.box;
import d.obj;
public class blt extends obj{static final long serialVersionUID=1;
	{vbo(vboblt.o).radius(.01f).scl(.01f,.01f,0);}
	public static int lifetimems=2000;
	obj from;public blt from(final obj from){this.from=from;return this;}
	protected void update()throws Throwable{
		super.update();
		if(box.tms-t0ms>lifetimems)
			rm();
	}
}
