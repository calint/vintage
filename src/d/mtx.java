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
	public mtx setsclagltrans(final p s,final p a,final float[]p){
		final float sinz=(float)Math.sin(a.z);
		final float cosz=(float)Math.cos(a.z);
		final float sx=s.x;
		final float sy=s.y;
		final float sz=s.z;
		bf.rewind();
		bf.put(cosz*sx).put(-sinz*sx).put(   0).put(0);
		bf.put(sinz*sy).put( cosz*sy).put(   0).put(0);
		bf.put(      0).put(       0).put(  sz).put(0);
		bf.put(   p[0]).put(    p[1]).put(p[2]).put(1);
		bf.flip();
		return this;
	}
}