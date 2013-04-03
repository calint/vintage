package d;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
final class mtx{
	// axis x,y,z,w
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
	public mtx setsclagltrans(final p s,final p a,final p p){
		final float sinz=(float)Math.sin(a.z);
		final float cosz=(float)Math.cos(a.z);
		final float sx=s.x;
		final float sy=s.y;
		final float sz=s.z;
		bf.rewind();
		bf.put(cosz*sx).put(-sinz*sx).put(   0).put(0);
		bf.put(sinz*sy).put( cosz*sy).put(   0).put(0);
		bf.put(      0).put(       0).put(  sz).put(0);
		bf.put(    p.x).put(     p.y).put( p.z).put(1);
		bf.flip();
		return this;
	}
	public p axisz(){return p.n(bf.get(8),bf.get(9),bf.get(10));}
//	public p axisx(){return p.n(bf.get(0),bf.get(1),bf.get(2));}
	//asumming orthonorm
	public p axisxinv(){return p.n(bf.get(0),bf.get(4),bf.get(8));}
	public p axisyinv(){return p.n(bf.get(1),bf.get(5),bf.get(9));}
	public p axiszinv(){return p.n(bf.get(2),bf.get(6),bf.get(10));}
}