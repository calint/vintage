package d;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
final class mtx{
	public final FloatBuffer bf=BufferUtils.createFloatBuffer(16);
	public mtx ident(){
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
	public mtx setagltrans(final float[]a,final float[]p){
		final float sinz=(float)Math.sin(a[2]);
		final float cosz=(float)Math.cos(a[2]);
		bf.rewind();
		bf.put(cosz).put(-sinz).put(0).put(0);
		bf.put(sinz).put(cosz).put(0).put(0);
		bf.put(0).put(0).put(1).put(0);
		bf.put(p[0]).put(p[1]).put(p[2]).put(1);
		bf.flip();
		return this;
	}
}