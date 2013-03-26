package d;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import java.lang.reflect.Field;
import b.a;
public class obj extends a{
	static final long serialVersionUID=1;
	public static int count;
	protected vbo vbo;
	protected float[]pos=new float[3];// x y z
	protected mtx mxmw=new mtx().setident();
	public obj(){count++;}
	final public void render()throws Throwable{
//		GL11.glPushMatrix();
//		pos[0]=.25f;
//		pos[1]=.5f;
		mxmw.settranslate(pos);
		glUniformMatrix4(app.umxmw,false,mxmw.bf);
		vbo.render();
		for(final Field f:getClass().getFields()){
			if(obj.class.isAssignableFrom(f.getType())){
				final obj o=(obj)f.get(this);
				if(o!=null)
					o.render();
			}
		}
//		GL11.glPopMatrix();
	}
	public void setpos(final float x,final float y,final float z){
		pos[0]=x;pos[1]=y;pos[2]=z;
	}
}
