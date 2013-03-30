package d;
import static org.lwjgl.opengl.GL20.*;
import java.util.ArrayList;
import java.util.Collection;
public class obj{
	static final long serialVersionUID=1;
	public/*readonly*/static int count;
	private static Collection<obj>all=new ArrayList<obj>();
	private static Collection<obj>dels=new ArrayList<obj>();
	private static Collection<obj>news=new ArrayList<obj>();
	public/*readonly*/static long ms_allupdate;
	public/*readonly*/static long ms_allrender;
	static void allupdaterender()throws Throwable{
		final long t0=System.currentTimeMillis();
		obj.all.removeAll(obj.dels);
		obj.dels.clear();
		obj.all.addAll(obj.news);
		obj.news.clear();
		for(final obj o:obj.all)o.update();
		final long t1=System.currentTimeMillis();
		for(final obj o:obj.all)o.render();
		final long t2=System.currentTimeMillis();
		ms_allupdate=t1-t0;
		ms_allrender=t2-t1;
	}

//	private int bits;
	protected vbo vbo;
	protected long t0ms=box.tms;
	protected float[]pos=new float[3];// x y z
	protected float[]dpos=new float[3];// x y z
	protected float[]scl=new float[]{1,1,1};
	protected float[]agl=new float[]{0,0,0};
	protected float[]dagl=new float[]{0,0,0};
	private mtx mxmw=new mtx().ident();
//	private long mxmwfrm;
	
	public obj(){
		count++;
		obj.news.add(this);
	}
	final public void rm(){
		count--;
		obj.dels.add(this);
	}
	final private void updmxmw(){
		mxmw.setagltrans(agl,pos);
//		mxmwfrm=box.frm;
	}
	final void render()throws Throwable{
//		GL11.glPushMatrix();
		if(vbo!=null){
			updmxmw();
			glUniformMatrix4(shader.umxmw,false,mxmw.bf);
			glUniform3f(shader.uscl,scl[0],scl[1],scl[2]);
			vbo.render();
		}
//		GL11.glPopMatrix();
	}
	final public obj vbo(final vbo v){this.vbo=v;return this;}
	final public obj agl(final float x,final float y,final float z){agl[0]=x;agl[1]=y;agl[2]=z;return this;}
	final public obj scl(final float x,final float y,final float z){scl[0]=x;scl[1]=y;scl[2]=z;return this;}
	final public obj pos(final float x,final float y,final float z){pos[0]=x;pos[1]=y;pos[2]=z;return this;}
	final public obj dpos(final float x,final float y,final float z){dpos[0]=x;dpos[1]=y;dpos[2]=z;return this;}
	final public obj dagl(final float x,final float y,final float z){dagl[0]=x;dagl[1]=y;dagl[2]=z;return this;}
	final public obj incdpos(final float x,final float y,final float z){dpos[0]+=x;dpos[1]+=y;dpos[2]+=z;return this;}
	final public obj incdagl(final float x,final float y,final float z){dagl[0]+=x;dagl[1]+=y;dagl[2]+=z;return this;}
	protected void update()throws Throwable{
		pos[0]+=dpos[0]*box.dt;pos[1]+=dpos[1]*box.dt;pos[2]+=dpos[2]*box.dt;
		agl[0]+=dagl[0]*box.dt;agl[1]+=dagl[1]*box.dt;agl[2]+=dagl[2]*box.dt;
	}
}
