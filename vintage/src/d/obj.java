package d;
import static org.lwjgl.opengl.GL20.*;
import java.lang.reflect.Field;
import b.a;
public class obj extends a{
	static final long serialVersionUID=1;
	public static int count;
	private vbo vbo;public void vbo(final vbo v){vbo=v;}
	protected float[]pos=new float[3];// x y z
	protected float[]dpos=new float[3];// x y z
	protected float[]scl=new float[]{1,1,1};
	private mtx mxmw=new mtx().ident();
	public obj(){count++;}
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
				if(o!=null)
					o.render();
			}
		}
//		GL11.glPopMatrix();
	}
	public void setpos(final float x,final float y,final float z){pos[0]=x;pos[1]=y;pos[2]=z;}
	public void setdpos(final float x,final float y,final float z){dpos[0]=x;dpos[1]=y;dpos[2]=z;}
	protected void update()throws Throwable{
		pos[0]+=dpos[0];pos[1]+=dpos[1];pos[2]+=dpos[2];
		for(final Field f:getClass().getFields()){
			if(obj.class.isAssignableFrom(f.getType())){
				final obj o=(obj)f.get(this);
				if(o!=null)
					o.update();
			}
		}
	}
}
