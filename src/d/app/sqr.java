package d.app;
import d.obj;
public class sqr extends obj{
	static final long serialVersionUID=1;
	public static float dpos=.5f;
	{vbo(vbosqr.o).scl(2,2,0);}
	public void update()throws Throwable{
		super.update();
		dpos((float)(Math.random()-.5f)*dpos,(float)(Math.random()-.5f)*dpos,0);
	}
}
