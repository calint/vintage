package d.app;
import d.obj;
public class sqr extends obj{{vbo(vbosqr.o);colbitsmsk=1;}
	public static float dpos=.01f;
	protected void update()throws Throwable{
		super.update();
		dpos((float)(Math.random()-.5f)*dpos,0,0);
	}
}
