package d;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
final public class mtx{
	public final FloatBuffer bf=BufferUtils.createFloatBuffer(16);
	public mtx setident(){
		bf.rewind();
		bf.put(1).put(0).put(0).put(0);
		bf.put(0).put(1).put(0).put(0);
		bf.put(0).put(0).put(1).put(0);
		bf.put(0).put(0).put(0).put(1);
		bf.flip();
		return this;
	}
	public mtx settranslate(final float[]p){
		bf.rewind();
		bf.put(1).put(0).put(0).put(0);
		bf.put(0).put(1).put(0).put(0);
		bf.put(0).put(0).put(1).put(0);
		bf.put(p[0]).put(p[1]).put(p[2]).put(1);
		bf.flip();
		return this;
	}
}