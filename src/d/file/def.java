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
		obj o;
//		o=new objsqr();
//		o.setpos(0,0,0);
//		col.add(o);
//		
//		o=new objsqr();
//		o.setpos(.5f,.5f,0);
//		col.add(o);
//		
//		o=new objsqr();
//		o.setpos(-.5f,.5f,0);
//		col.add(o);
		
//		final int n=256+128;
//		final int n=512;
		final int n=256;
		for(int i=0;i<n;i++){
			o=new objsqr();
			final float f=-1+(float)i/n;
			o.setpos(f,-f,0);
			col.add(o);
		}

//		col.add(new objtri());
	}
}
