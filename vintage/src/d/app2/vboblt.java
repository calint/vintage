package d.app2;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import d.vbo;
public class vboblt extends vbo{
	static final public vboblt o=new vboblt();
	protected int nvertices(){return 3;}
	protected void vertices(final FloatBuffer vb){
		final float w=1;
		vb.put(0).put(w).put(0);//xyzw
		vb.put(1).put(0).put(0).put(1);//rgba
		vb.put(0).put(0);//st

		vb.put(-w).put(-w).put(0);//xyzw
		vb.put(1).put(1).put(1).put(1);//rgba
		vb.put(0).put(1);//st

		vb.put(w).put(-w).put(0);//xyzw
		vb.put(1).put(1).put(1).put(1);//rgba
		vb.put(1).put(1);//st
	}
	protected int nindices(){return 3;}
	protected void indices(final ByteBuffer ib){
		ib.put((byte)0).put((byte)1).put((byte)2);
	}
	protected String imgpath(){return null;}
}