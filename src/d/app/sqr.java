package d.app;
import d.obj;
public class sqr extends obj{
	static final long serialVersionUID=1;
	{vbo(vbosqr.o);scl[0]=2;scl[1]=2;}
	public void update()throws Throwable{
		super.update();
		dpos((float)(Math.random()-.5f)*.01f,(float)(Math.random()-.5f)*.01f,0);
	}
}