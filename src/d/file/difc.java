package d.file;

import java.util.Collection;
import d.obj;
import d.vbo;

public class difc implements d.app.def{
	public void addvbos(final Collection<vbo>col){
		col.add(vbocoloredsquare.o);
		col.add(vbowhitetriangle.o);
	}
	public void addobjs(final Collection<obj>col){
		col.add(new background());
	}
}
