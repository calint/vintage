package d.app;
import d.box;
import d.obj;
public class tumbloid extends obj{
	{vbo(vbotumbloid.o);}
	public static int lifetimems=10000;
	protected void update()throws Throwable{
		super.update();
		if(box.tms-t0ms>lifetimems)
			rm();
	}
}
