package d.file;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import d.vbo;
public class vbosqr extends vbo{
	static final public vbosqr o=new vbosqr(); 
	protected int nvertices(){return 4;}
	protected void vertices(final FloatBuffer vb){
		final float w=.5f;
		//0
		vb.put(-w).put(w).put(0).put(1);//xyzw
		vb.put(1).put(0).put(0).put(1);//rgba
		vb.put(0).put(0);//st
		//1
		vb.put(-w).put(-w).put(0).put(1);//xyzw
		vb.put(0).put(1).put(0).put(1);//rgba
		vb.put(0).put(1);//st
		//2
		vb.put(w).put(-w).put(0).put(1);//xyzw
		vb.put(0).put(0).put(1).put(1);//rgba
		vb.put(1).put(1);//st
		//3
		vb.put(w).put(w).put(0).put(1);//xyzw
		vb.put(1).put(1).put(1).put(1);//rgba
		vb.put(1).put(0);//st //? 
	}
	protected int nindices(){return 6;}
	protected void indices(final ByteBuffer ib){
		ib.put((byte)0).put((byte)1).put((byte)2);
//		ib.put((byte)1).put((byte)2).put((byte)3);
		ib.put((byte)2).put((byte)3).put((byte)0);
//		ib.put((byte)0).put((byte)1).put((byte)2);
//		ib.put((byte)2).put((byte)3).put((byte)0);
	}
}