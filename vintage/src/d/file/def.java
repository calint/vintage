package d.file;
import java.util.Collection;
import d.obj;
import d.vbo;
public class def implements d.app.def{
	public void addvbos(final Collection<vbo>col){
		col.add(vbosqr.o);
		col.add(vbotri.o);
	}
	public void addobjs(final Collection<obj>col){
		col.add(new objsqr());
		col.add(new objtri());
	}
}
