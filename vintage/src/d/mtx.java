package d;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
final public class mtx{
	public final FloatBuffer fb=BufferUtils.createFloatBuffer(16);
	public void ident(){
		fb.rewind();
		fb.put(1).put(0).put(0).put(0);
		fb.put(0).put(1).put(0).put(0);
		fb.put(0).put(0).put(1).put(0);
		fb.put(0).put(0).put(0).put(1);
		fb.flip();
	}
}