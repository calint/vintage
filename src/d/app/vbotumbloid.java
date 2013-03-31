package d.app;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import d.vbo;
public class vbotumbloid extends vbo{
	static final public vbotumbloid o=new vbotumbloid();
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
	protected String imgpath(){return "KL_USSR_KP1810BM86.jpg";}
}