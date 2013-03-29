package d.app;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import d.box;
import d.vbo;
public class vbobackground extends vbo{
	static final public vbobackground o=new vbobackground(); 
	protected int nvertices(){return 4;}
	protected void vertices(final FloatBuffer vb){
		final float w=1;
		//0
		vb.put(-w).put(w).put(0).put(1);//xyzw
		vb.put(0).put(0).put(0).put(0);//rgba
		vb.put(0).put(0);//st
		//1
		vb.put(-w).put(-w).put(0).put(1);//xyzw
		vb.put(0).put(0).put(0).put(0);//rgba
		vb.put(0).put(1);//st
		//2
		vb.put(w).put(-w).put(0).put(1);//xyzw
		vb.put(0).put(0).put(0).put(0);//rgba
		vb.put(1).put(1);//st
		//3
		vb.put(w).put(w).put(0).put(1);//xyzw
		vb.put(0).put(0).put(0).put(0);//rgba
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
//	protected String imgpath(){return "DSC00008.jpg";}
	protected String imgpath(){return null;}
	private final int wi=4096,hi=4096;
	protected int[]imgsize(){return new int[]{wi,hi,4};}// 4096x4096 rgba8
	protected void imggen(final ByteBuffer rgba){
		for(int y=0;y<hi;y++){
			for(int x=0;x<wi;x++){
				final byte a=(byte)box.rnd(0,256);
				rgba.put(a);
				rgba.put(a);
				rgba.put(a);
				rgba.put(a);				
			}
			
		}
	}
}