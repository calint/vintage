package d.file;
import java.util.ArrayList;
import java.util.Collection;
import d.app;
import d.obj;
import d.vbo;
final public class def implements d.app.def{
	private final Collection<vbo>vbos=new ArrayList<vbo>(app.nvbos);
	private final Collection<obj>objs=new ArrayList<obj>(app.nobjs);
	private obj o;
	public void load() throws Throwable{
		vbos.add(vbosqr.o);
		vbos.add(vbotri.o);

		o=new objsqr();
		objs.add(o);
		
		final int n=1024;// 60+ fps
		for(int i=0;i<n;i++){
			final obj o=new objsqr();
			final float f=-1+(float)i/n;
			o.setpos(f,-f,0);
			objs.add(o);
		}
	}
	public Collection<obj>objs(){return objs;}
	public Collection<vbo>vbos(){return vbos;}
	public void update()throws Throwable{
		for(final obj o:objs)o.update();
		
		if((app.bmpkeys&1)!=0){//w
			o.dpos(.01f,0,0,app.dt);
		}
		if((app.bmpkeys&2)!=0){//a
			o.dpos(-.01f,0,0,app.dt);
		}
	}
}
