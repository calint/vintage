package d.file;
import d.obj;
public class objsqr extends obj{
	static final long serialVersionUID=1;
	{vbo=vbosqr.o;}
	public void update() throws Throwable{
		super.update();
		pos[0]+=(Math.random()-.5f)*.01f;
		pos[1]+=(Math.random()-.5f)*.01f;
	}
}
