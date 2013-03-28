package d.app;
import d.obj;
public class sqr extends obj{
	static final long serialVersionUID=1;
	{vbo(vbosqr.o);}
	public void update()throws Throwable{
		super.update();
		setdpos((float)(Math.random()-.5f)*.01f,(float)(Math.random()-.5f)*.01f,0);
	}
}
