package d;
import java.lang.reflect.Field;
import org.lwjgl.opengl.GL11;
import b.a;
public class obj extends a{
	static final long serialVersionUID=1;
	protected vbo vbo;
	protected float[]pos=new float[3];// x y z
	final public void render()throws Throwable{
//		GL11.glPushMatrix();
//		GL11.glTranslatef(pos[0],pos[1],pos[2]);
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
}
