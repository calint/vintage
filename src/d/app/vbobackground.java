package d.app;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import d.box;
import d.vbo;
public class vbobackground extends vbo{
	static final public vbobackground o=new vbobackground(); 
	protected String imgpath(){return null;}
	private final int wi=4096,hi=4096;
	protected int[]imgsize(){return new int[]{wi,hi,4};}// 4096x4096 rgba8
	protected void imggen(final ByteBuffer rgba){
		for(int y=0;y<hi;y++){
			for(int x=0;x<wi;x++){
				final byte r=(byte)box.rnd(0,256);
				final byte g=(byte)box.rnd(0,256);
				final byte b=(byte)box.rnd(0,256);
				final byte a=(byte)box.rnd(0,256);
				rgba.put(r);
				rgba.put(g);
				rgba.put(b);
				rgba.put(a);				
			}
			
		}
	}
}