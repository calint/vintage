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
	public/*readonly*/static long ms_allcoldet;
	//collision detection
	protected int colbits;
	public int colbitsmsk;
	static void allupdaterender()throws Throwable{
		final long t0=System.currentTimeMillis();
		grid.rem(obj.dels);
		obj.dels.clear();
		grid.put(news);
		obj.news.clear();
		final long t1=System.currentTimeMillis();
		grid.update();
		final long t2=System.currentTimeMillis();
		grid.render();
		final long t3=System.currentTimeMillis();
		grid.coldet();
		final long t4=System.currentTimeMillis();		
		ms_allnewsdels=t1-t0;
		ms_allupdate=t2-t1;
		ms_allrender=t3-t2;
		ms_allcoldet=t4-t3;
	}

//	private int bits;
	protected vbo vbo;
	protected long t0ms=box.tms;
	protected/*readonly*/final p pos=p.n();// x y z
	protected/*readonly*/final p dpos=p.n();// x y z
	protected/*readonly*/final p scl=p.n(1,1,1);
	protected/*readonly*/final p agl=p.n();
	protected/*readonly*/final p dagl=p.n();
	protected/*readonly*/final mtx mxmw=new mtx().ident();
	
//	private final p prevpos=new p();
	//bounding volume
	protected float radius;public obj radius(final float r){this.radius=r;return this;}
	final public static boolean isincol(final obj o1,final obj o2){
		mtrs.niscol++;
		final float dr=o1.radius+o2.radius;
		final float dr2=dr*dr;
		final p p1=o1.pos.clone();
		final p p2=o2.pos.clone();
		final p dp=p2.add(p1.neg());
		final float dist2=dp.dot(dp);
//		System.out.println("isincol "+dist2+" < "+dr2+"   dp:"+dp+"   p1:"+p1+"   p2:"+p2);
		if(dist2<dr2)
			return true;
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
	final public obj pos(final float x,final float y,final float z){pos.set(x,y,z);return this;}
//	final public obj pos(final p p){pos.set(p);return this;}
	final public obj dpos(final float x,final float y,final float z){dpos.set(x,y,z);return this;}
	final public obj dagl(final float x,final float y,final float z){dagl.set(x,y,z);return this;}
	final public obj incdpos(final float x,final float y,final float z){dpos.x+=x;dpos.y+=y;dpos.z+=z;return this;}
	final public obj incdagl(final float x,final float y,final float z){dagl.x+=x;dagl.y+=y;dagl.z+=z;return this;}
	protected void update()throws Throwable{
//		pos[0]+=dpos[0]*box.dt;pos[1]+=dpos[1]*box.dt;pos[2]+=dpos[2]*box.dt;
		pos.add(dpos,box.dt);
		agl.add(dagl,box.dt);
//		agl.x+=dagl.x*box.dt;agl.y+=dagl.y*box.dt;agl.z+=dagl.z*box.dt;
	}
	protected void oncol(final obj o)throws Throwable{}
}