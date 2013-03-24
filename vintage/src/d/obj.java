package d;
import java.lang.reflect.Field;
import b.a;
public class obj extends a{
	static final long serialVersionUID=1;
	protected vbo vbo;
	final public void render()throws Throwable{
		vbo.render();
		for(final Field f:getClass().getFields()){
			if(obj.class.isAssignableFrom(f.getType())){
				final obj o=(obj)f.get(this);
				if(o!=null)
					o.render();
			}
		}
	}
}
