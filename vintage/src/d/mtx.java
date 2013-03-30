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
	public mtx setsclagltrans(final float[]s,final float[]a,final float[]p){
		final float sinz=(float)Math.sin(a[2]);
		final float cosz=(float)Math.cos(a[2]);
		final float sx=s[0];
		final float sy=s[1];
		final float sz=s[2];
		bf.rewind();
		bf.put(cosz*sx).put(-sinz*sx).put(   0).put(0);
		bf.put(sinz*sy).put( cosz*sy).put(   0).put(0);
		bf.put(      0).put(       0).put(  sz).put(0);
		bf.put(   p[0]).put(    p[1]).put(p[2]).put(1);
		bf.flip();
		return this;
	}
}