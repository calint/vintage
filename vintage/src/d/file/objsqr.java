package d.file;
import d.app;
import d.obj;
public class objsqr extends obj{
	static final long serialVersionUID=1;
	{vbo(vbosqr.o);}
	public void update() throws Throwable{
		super.update();
		dpos((float)(Math.random()-.5f)*.01f,(float)(Math.random()-.5f)*.01f,0,app.dt);
	}
}
