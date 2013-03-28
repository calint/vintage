package d.app;
import d.obj;
public class sqr extends obj{
	static final long serialVersionUID=1;
	public static float dpos=.01f;
	{vbo(vbosqr.o).scl(2,2,0);}
	protected void update()throws Throwable{
		super.update();
		dpos((float)(Math.random()-.5f)*dpos,0,0);
	}
}
