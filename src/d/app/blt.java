package d.app;
import d.obj;
public class blt extends obj{static final long serialVersionUID=1;
	{vbo(vboblt.o).scl(.01f,.01f,0).dpos(.01f,0,0).dagl(0,0,3);}
	protected void update()throws Throwable{
		super.update();
		if(pos[0]>1)
			rm();
	}
}
