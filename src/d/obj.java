package d;
import static org.lwjgl.opengl.GL20.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import b.a;
public class obj extends a{
	static final long serialVersionUID=1;
	public static int count;
	protected vbo vbo;public void vbo(final vbo v){vbo=v;}
	protected float[]pos=new float[3];// x y z
	protected float[]dpos=new float[3];// x y z
	protected float[]scl=new float[]{1,1,1};
	private mtx mxmw=new mtx().ident();
	static Collection<obj>all=new ArrayList<obj>();
	static Collection<obj>removed=new ArrayList<obj>();
	static Collection<obj>news=new ArrayList<obj>();
	public obj(){count++;obj.news.add(this);}
	private int bits;
	public void rm(){
		count--;
		bits|=1;
		obj.removed.add(this);
	}
	private boolean isdeleted(){return (bits&1)!=0;}
	final void render()throws Throwable{
//		GL11.glPushMatrix();
//		pos[0]=.25f;
//		pos[1]=.5f;
		if(vbo!=null){
			mxmw.settranslate(pos);
			glUniformMatrix4(shader.umxmw,false,mxmw.bf);
			glUniform3f(shader.uscl,scl[0],scl[1],scl[2]);
			vbo.render();
		}
		for(final Field f:getClass().getFields()){
			if(obj.class.isAssignableFrom(f.getType())){
				final obj o=(obj)f.get(this);
				if(o!=null){
					if(!o.isdeleted())
						o.render();
				}
			}
		}
//		GL11.glPopMatrix();
	}
	public obj pos(final float x,final float y,final float z){pos[0]=x;pos[1]=y;pos[2]=z;return this;}
	public obj dpos(final float x,final float y,final float z){dpos[0]=x;dpos[1]=y;dpos[2]=z;return this;}
	protected void update()throws Throwable{
		pos[0]+=dpos[0];pos[1]+=dpos[1];pos[2]+=dpos[2];
		for(final Field f:getClass().getFields()){
			if(obj.class.isAssignableFrom(f.getType())){
				final obj o=(obj)f.get(this);
				if(o!=null)
					if(!o.isdeleted())
						o.update();
			}
		}
	}
}
