package d.app2;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import d.vbo;
public class vboshp extends vbo{
	static final public vboshp o=new vboshp();
	protected int nvertices(){return 3;}
	protected void vertices(final FloatBuffer vb){
		final float s=1;
		vb.put(0).put(s).put(0);//xyzw
		vb.put(0).put(0).put(0).put(0);//rgba
		vb.put(0).put(0);//st

		vb.put(-s).put(-s).put(0);//xyzw
		vb.put(0).put(0).put(0).put(0);//rgba
		vb.put(0).put(0);//st

		vb.put(s).put(-s).put(0);//xyzw
		vb.put(0).put(0).put(0).put(0);//rgba
		vb.put(0).put(0);//st
	}
	protected int nindices(){return 3;}
	protected void indices(final ByteBuffer ib){
		ib.put((byte)0).put((byte)1).put((byte)2);
	}
	protected String imgpath(){return null;}
}