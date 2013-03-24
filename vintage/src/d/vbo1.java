package d;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
public class vbo1 extends vbo{
	protected int nindices(){return 3;}
	protected void vertices(final FloatBuffer vb){
		vb.put(-.5f).put( .5f).put(0).put(1);//xyzw
		vb.put(1).put(1).put(1).put(1);//rgba

		vb.put(-.5f).put(-.5f).put(0).put(1);//xyzw
		vb.put(1).put(1).put(1).put(1);//rgba

		vb.put( .5f).put(-.5f).put(0).put(1);//xyzw
		vb.put(1).put(1).put(1).put(1);//rgba
	}
	protected void indices(final ByteBuffer ib){
		ib.put((byte)0).put((byte)1).put((byte)2);
	}
}