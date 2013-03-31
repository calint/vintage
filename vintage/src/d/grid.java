package d;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
final class grid{
	final static List<obj>all=new LinkedList<obj>();
	static void put(final obj o){all.add(o);}
	static void put(final Collection<obj>col){all.addAll(col);}
	static void rem(final obj o){all.remove(o);}
	static void rem(final Collection<obj>col){all.removeAll(col);}
	static void update()throws Throwable{for(final obj o:all)o.update();}
	static void render()throws Throwable{for(final obj o:all)o.render();}
	static void coldet()throws Throwable{}
}
