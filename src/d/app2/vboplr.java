package d.app2;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import d.vbo;
public class vboplr extends vbo{
	static final public vboplr o=new vboplr(); 
	{elemtype=1;}
	protected String imgpath(){return "logo2.png";}
	protected int nvertices(){return 4;}
	protected void vertices(final FloatBuffer vb){
		final float w=1;
		//0
		vb.put(-w).put(w).put(0);//xyz
		vb.put(1).put(1).put(1).put(1);//rgba
		vb.put(0).put(0);//st
		//1
		vb.put(-w).put(-w).put(0);//xyz
		vb.put(1).put(1).put(1).put(1);//rgba
		vb.put(0).put(1);//st
		//2
		vb.put(w).put(-w).put(0);//xyz
		vb.put(0).put(0).put(1).put(1);//rgba
		vb.put(1).put(1);//st
		//3
		vb.put(w).put(w).put(0);//xyz
		vb.put(1).put(1).put(1).put(1);//rgba
		vb.put(1).put(0);//st
	}
	protected int nindices(){return 4;}
	protected void indices(final ByteBuffer ib){
		ib.put((byte)0).put((byte)1).put((byte)2).put((byte)3);
	}
}