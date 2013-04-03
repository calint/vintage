package d;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
public class vboviewpyr extends vbo{
	static final public vboviewpyr o=new vboviewpyr(); 
//	protected String imgpath(){return "logo.jpg";}
	protected int nvertices(){return 5;}
	protected void vertices(final FloatBuffer vb){
		final float w=1;
		//0
		vb.put(0).put(0).put(0);//xyz
		vb.put(1).put(1).put(1).put(1);//rgba
		vb.put(0).put(0);//st
		//1
		vb.put(-w).put(w).put(-1);//xyz topleft
		vb.put(1).put(0).put(0).put(0);//rgba
		vb.put(-w).put(w);//st
		//2
		vb.put(-w).put(-w).put(-1);//xyz bottomleft
		vb.put(0).put(1).put(0).put(0);//rgba
		vb.put(-w).put(-w);//st
		//3
		vb.put(w).put(-w).put(-1);//xyz bottomrright
		vb.put(0).put(0).put(1).put(0);//rgba
		vb.put(w).put(-w);//st
		//4
		vb.put(w).put(w).put(-1);//xyz topright
		vb.put(0).put(0).put(0).put(0);//rgba
		vb.put(w).put(w);//st
	}
	{elemtype=1;}
	protected int nindices(){return 6;}
	protected void indices(final ByteBuffer ib){
		ib.put((byte)0).put((byte)1).put((byte)2).put((byte)3).put((byte)4).put((byte)1);
	}
}