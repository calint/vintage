package d;
import static org.lwjgl.opengl.GL20.*;
import d.box.mtrs;
public class obj{
	static final long serialVersionUID=1;
	//collision detection
	protected int colbits;
	public int colbitsmsk;
	//
	protected int bits;
	protected vbo vbo;
	protected long t0ms=box.tms;
	protected/*readonly*/final p pos=p.n();// x y z
	protected/*readonly*/final p dpos=p.n();// x y z
	protected/*readonly*/final p scl=p.n(1,1,1);
	protected/*readonly*/final p agl=p.n();
	protected/*readonly*/final p dagl=p.n();
	protected/*readonly*/final mtx mxmw=new mtx().ident();
	protected/*readonly*/final p posprv=p.n();// x y z
	
//	private final p prevpos=new p();
	//bounding volume
	protected float radius;public obj radius(final float r){this.radius=r;return this;}
	final public static boolean isincol(final obj o1,final obj o2){
		box.mtrs.niscol++;
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
		box.mtrs.nobjs++;
		grid.news.add(this);
	}
	final public void rm(){
		if((bits&1)!=0)
			return;
		bits|=1;
		grid.dels.add(this);
		box.mtrs.nobjs--;
	}
	final private void updmxmw(){
		mxmw.setsclagltrans(scl,agl,pos);
//		mxmwfrm=box.frm;
	}
	private long renderfrm;
	final void render(){
		if(renderfrm==box.frm)
			return;
		renderfrm=box.frm;
		mtrs.nobjrend++;
//		GL11.glPushMatrix();
		if(vbo!=null){
			updmxmw();
			glUniformMatrix4(shader.umxmw,true,mxmw.tobb());
			vbo.render();
		}
//		vbo.o.elemtype=2;
//		vbo.o.render();
//		vbo.o.elemtype=1;
//		GL11.glPopMatrix();
	}
	final public obj vbo(final vbo v){this.vbo=v;return this;}
	final public obj agl(final float x,final float y,final float z){agl.set(x,y,z);return this;}
	final public obj scl(final float x,final float y,final float z){scl.set(x,y,z);return this;}
	final public obj pos(final float x,final float y,final float z){pos.set(x,y,z);return this;}
	final public obj pos(final p p){pos.set(p);return this;}
	final public obj dpos(final float x,final float y,final float z){dpos.set(x,y,z);return this;}
	final public obj dagl(final float x,final float y,final float z){dagl.set(x,y,z);return this;}
	final public obj incdpos(final float x,final float y,final float z){dpos.x+=x;dpos.y+=y;dpos.z+=z;return this;}
	final public obj incdagl(final float x,final float y,final float z){dagl.x+=x;dagl.y+=y;dagl.z+=z;return this;}
	
	private long updfrm;
	final void upd()throws Throwable{
		if(updfrm==box.frm)
			return;
		updfrm=box.frm;
		update();
	}
	protected void update()throws Throwable{
//		pos[0]+=dpos[0]*box.dt;pos[1]+=dpos[1]*box.dt;pos[2]+=dpos[2]*box.dt;
		posprv.set(pos);
		pos.add(dpos,box.dt);
		agl.add(dagl,box.dt);
//		agl.x+=dagl.x*box.dt;agl.y+=dagl.y*box.dt;agl.z+=dagl.z*box.dt;
	}
	protected void oncol(final obj o)throws Throwable{}
	final void cullrend(final pn[]cullpns){
		for(int i=0;i<cullpns.length;i++){
			final pn pn=cullpns[i];
			final float d=pn.disttopoint(pos);
			if(d-radius>0){
				mtrs.nobjcull++;
				return;
			}
		}
		render();
	}
	
	//? network
	protected final byte[]keys=new byte[net.protocol_msg_len];
	final byte[]keys(){return keys;}
}
