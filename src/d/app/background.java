package d.app;
import d.box;
import d.obj;
public class background extends obj{
	static final long serialVersionUID=1;
	public static float dpos=.01f;
	{vbo(vbobackground.o).scl(1,1,0);}
	private double a;
	protected void update()throws Throwable{
		super.update();
		dpos(.01f,0,0);
		scl[0]=scl[1]=(float)Math.sin(a)*64.f;
		a+=(Math.PI/64*box.dt);
	}
}
