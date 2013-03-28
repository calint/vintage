package d;
import static org.lwjgl.opengl.GL20.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import b.a;
abstract public class obj extends a{
	static final long serialVersionUID=1;
	public/*readonly*/static int count;
	private static Collection<obj>all=new ArrayList<obj>();
	private static Collection<obj>dels=new ArrayList<obj>();
	private static Collection<obj>news=new ArrayList<obj>();
	static void allupdaterender()throws Throwable{
		obj.all.removeAll(obj.dels);
		obj.dels.clear();
		obj.all.addAll(obj.news);
		obj.news.clear();
		for(final obj o:obj.all)o.update();
		for(final obj o:obj.all)o.render();
	}

	private int bits;
	protected vbo vbo;
	protected float[]pos=new float[3];// x y z
	protected float[]dpos=new float[3];// x y z
	protected float[]scl=new float[]{1,1,1};
	protected float[]agl=new float[]{0,0,0};
	protected float[]dagl=new float[]{0,0,0};
	private mtx mxmw=new mtx().ident();
	
	public obj(){
		count++;
		obj.news.add(this);
	}
	final public void rm(){
		count--;
		bits|=1;
		obj.dels.add(this);
	}
	final private boolean isdeleted(){return (bits&1)!=0;}
	final private void updmxmw(){mxmw.setagltrans(agl,pos);}
	final void render()throws Throwable{
//		GL11.glPushMatrix();
		if(vbo!=null){
			updmxmw();
			glUniformMatrix4(shader.umxmw,false,mxmw.bf);
			glUniform3f(shader.uscl,scl[0],scl[1],scl[2]);
			vbo.render();
		}
//		for(final Field f:getClass().getFields()){
//			if(obj.class.isAssignableFrom(f.getType())){
//				final obj o=(obj)f.get(this);
//				if(o!=null)
//					o.render();
//			}
//		}
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
//		for(final Field f:getClass().getFields()){
//			if(obj.class.isAssignableFrom(f.getType())){
//				final obj o=(obj)f.get(this);
//				if(o!=null)
//					if(!o.isdeleted())
//						o.update();
//					else
//						f.set(this,null);
//			}
//		}
	}
}
