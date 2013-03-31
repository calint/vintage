package d.app;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import d.box;
import d.obj;
public class background extends obj{
	static final long serialVersionUID=1;
	protected int nvertices(){return 4;}
	protected void vertices(final FloatBuffer vb){
		final float s=1;
		vb.put(s).put(s).put(0);//xyzw
		vb.put(1).put(1).put(1).put(1);//rgba
		vb.put(1).put(1);//st

		vb.put(-s).put(s).put(0);//xyzw
		vb.put(1).put(1).put(1).put(1);//rgba
		vb.put(0).put(1);//st

		vb.put(-s).put(-s).put(0);//xyzw
		vb.put(0).put(0).put(0).put(0);//rgba
		vb.put(0).put(0);//st

		vb.put(s).put(-s).put(0);//xyzw
		vb.put(0).put(0).put(0).put(0);//rgba
		vb.put(1).put(0);//st

	}
	protected int nindices(){return 6;}
	protected void indices(final ByteBuffer ib){
		ib.put((byte)0).put((byte)1).put((byte)2);
		ib.put((byte)2).put((byte)3).put((byte)0);
	}

	
	public static float dpos=.01f;
	{vbo(vbobackground.o).scl(1,1,0);}
	private double a;
	protected void update()throws Throwable{
		super.update();
		dpos(.01f,0,0);
		final float d=(float)Math.sin(a)*64.f;
		scl.set(d,d,0);
		a+=(Math.PI/64*box.dt);
	}
}
