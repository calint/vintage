package d.app2;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import d.vbo;
public class vbocrclexy extends vbo{
	static final public vbocrclexy o=new vbocrclexy(); 
	final static int circlepoints=12;
	protected int nvertices(){return circlepoints+1;}
	protected void vertices(final FloatBuffer vb){
		final double da=Math.PI*2/circlepoints;
		double a=0;
		elemtype=1;//triangle fan
		vb.put(0).put(0).put(0);//xyz
		vb.put(1).put(1).put(1).put(1);//rgba
		vb.put(0).put(0);//st
		for(int i=0;i<circlepoints;i++){
			final float x=(float)Math.cos(a);
			final float y=(float)Math.sin(a);
			final float z=0;
			final float s=x/2;
			final float t=-y/2;
			vb.put(x).put(y).put(z);//xyz
			vb.put(1).put(1).put(1).put(1);//rgba
			vb.put(s).put(t);//st
			a+=da;
		}
	}
	protected int nindices(){return circlepoints+2;}
	protected void indices(final ByteBuffer ib){
		ib.put((byte)0);
		for(int i=1;i<=circlepoints;i++)ib.put((byte)i);
		ib.put((byte)1);
	}
	protected String imgpath(){return "logo.jpg";}
}