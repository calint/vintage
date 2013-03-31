package d;
import static org.lwjgl.opengl.GL20.*;
import java.util.ArrayList;
import java.util.Collection;
public class obj{
	static final long serialVersionUID=1;
	public/*readonly*/static int count;
//	private static Collection<obj>all=new ArrayList<obj>();
	private static Collection<obj>dels=new ArrayList<obj>();
	private static Collection<obj>news=new ArrayList<obj>();
	public/*readonly*/static long ms_allnewsdels;
	public/*readonly*/static long ms_allupdate;
	public/*readonly*/static long ms_allrender;
	static void allupdaterender()throws Throwable{
		final long t0=System.currentTimeMillis();
//		obj.all.removeAll(obj.dels);
		
		grid.rem(obj.dels);
		obj.dels.clear();
		
//		obj.all.addAll(obj.news);
		
		grid.put(news);
		obj.news.clear();
		final long t1=System.currentTimeMillis();
		
		grid.update();
//		for(final obj o:obj.all)o.update();
		final long t2=System.currentTimeMillis();
		
		grid.render();
//		for(final obj o:obj.all)o.render();
		final long t3=System.currentTimeMillis();
		ms_allnewsdels=t1-t0;
		ms_allupdate=t2-t1;
		ms_allrender=t3-t2;
	}

//	private int bits;
	protected vbo vbo;
	protected long t0ms=box.tms;
	protected float[]pos=new float[3];// x y z
	protected float[]dpos=new float[3];// x y z
	protected final p scl=new p(1,1,1);
	protected final p agl=new p();
	protected final p dagl=new p();
	private mtx mxmw=new mtx().ident();
	
	//bounding volume
	float r;
	static boolean isincol(final obj o1,final obj o2){
		return false;
	}
//	polyh polyh;
//	final static class polyh{
//		final static class vtx{
//			p position;
////			p normal;
////			p texturecoord;
////			p color;
//		}
//		final static class cpol{//convex polygon
//			int[]indices;
//			p normal;
//		}
//		vtx[]vtxs;
//		cpol[]polycs;
//	}
	
	
//	private long mxmwfrm;
//	private long mxmwfrmoch;//angle or position change at frame
	
	public obj(){
		count++;
		obj.news.add(this);
	}
	final public void rm(){
		count--;
		obj.dels.add(this);
	}
	final private void updmxmw(){
		mxmw.setsclagltrans(scl,agl,pos);
//		mxmwfrm=box.frm;
	}
	final void render()throws Throwable{
//		GL11.glPushMatrix();
		if(vbo!=null){
			updmxmw();
			glUniformMatrix4(shader.umxmw,false,mxmw.bf);
			vbo.render();
		}
//		GL11.glPopMatrix();
	}
	final public obj vbo(final vbo v){this.vbo=v;return this;}
	final public obj agl(final float x,final float y,final float z){agl.set(x,y,z);return this;}
	final public obj scl(final float x,final float y,final float z){scl.set(x,y,z);return this;}
	final public obj pos(final float x,final float y,final float z){pos[0]=x;pos[1]=y;pos[2]=z;return this;}
	final public obj dpos(final float x,final float y,final float z){dpos[0]=x;dpos[1]=y;dpos[2]=z;return this;}
	final public obj dagl(final float x,final float y,final float z){dagl.set(x,y,z);return this;}
	final public obj incdpos(final float x,final float y,final float z){dpos[0]+=x;dpos[1]+=y;dpos[2]+=z;return this;}
	final public obj incdagl(final float x,final float y,final float z){dagl.x+=x;dagl.y+=y;dagl.z+=z;return this;}
	protected void update()throws Throwable{
		pos[0]+=dpos[0]*box.dt;pos[1]+=dpos[1]*box.dt;pos[2]+=dpos[2]*box.dt;
		agl.add(dagl,box.dt);
//		agl.x+=dagl.x*box.dt;agl.y+=dagl.y*box.dt;agl.z+=dagl.z*box.dt;
	}
}
