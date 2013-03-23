package d;
import java.lang.reflect.Field;
import b.a;
public class glob extends a{
	static final long serialVersionUID=1;
	public void toopengl()throws Throwable{
		for(final Field f:getClass().getFields()){
			if(glob.class.isAssignableFrom(f.getType())){
				final glob o=(glob)f.get(this);
				if(o!=null)
					o.toopengl();
			}
		}
	}
}
