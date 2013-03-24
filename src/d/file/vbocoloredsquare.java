package d.file;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import d.vbo;
public class vbocoloredsquare extends vbo{
	static final public vbocoloredsquare o=new vbocoloredsquare(); 
	protected int nvertices(){return 4;}
	protected void vertices(final FloatBuffer vb){
		vb.put(-.5f).put( .5f).put(0).put(1);//xyzw
		vb.put(1).put(0).put(0).put(1);//rgba

		vb.put(-.5f).put(-.5f).put(0).put(1);//xyzw
		vb.put(0).put(1).put(0).put(1);//rgba

		vb.put( .5f).put(-.5f).put(0).put(1);//xyzw
		vb.put(0).put(0).put(1).put(1);//rgba

		vb.put( .5f).put( .5f).put(0).put(1);//xyzw
		vb.put(1).put(1).put(1).put(1);//rgba
	}
	protected int nindices(){return 6;}
	protected void indices(final ByteBuffer ib){
		ib.put((byte)0).put((byte)1).put((byte)2);//tri
		ib.put((byte)2).put((byte)3).put((byte)0);//tri
	}
}