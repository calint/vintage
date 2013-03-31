package d;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
final class grid{
	static private List<obj>all=new LinkedList<obj>();
	static public void put(final obj o){all.add(o);}
	static public void put(final Collection<obj>col){all.addAll(col);}
	static public void rem(final obj o){all.remove(o);}
	static public void rem(final Collection<obj>col){all.removeAll(col);}
	static public void update()throws Throwable{for(final obj o:all)o.update();}
	static public void render()throws Throwable{for(final obj o:all)o.render();}
	static public void coldet()throws Throwable{}
}
