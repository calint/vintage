package d.file;
import java.util.Collection;
import d.app;
import d.obj;
import d.vbo;
final public class def implements d.app.def{
	private obj player;
	public void vbos(final Collection<vbo>vbos)throws Throwable{
		vbos.add(vbosqr.o);
		vbos.add(vbotri.o);
	}
	public void objs(final Collection<obj>objs)throws Throwable{
		player=new objsqr();
		objs.add(player);
		{final int n=1024>>2;// 60+ fps
		for(int i=0;i<n;i++){
			final obj o=new objsqr();
			final float f=-1+(float)i/n;
			o.setpos(f,-f,0);
			objs.add(o);
		}}
//		{final int n=1024>>4;// 60+ fps
//		for(int i=0;i<n;i++){
//			final obj o=new objtri();
//			final float f=-1+(float)i/n;
//			o.setpos(f,-f,0);
//			objs.add(o);
//		}}
	}
	public void update()throws Throwable{
		if((app.bmpkeys&1)!=0){//w
			player.vbo(vbosqr.o);
			player.dpos(.01f,0,0,app.dt);
		}
		if((app.bmpkeys&2)!=0){//a
			player.vbo(vbotri.o);
			player.dpos(-.01f,0,0,app.dt);
		}
	}
}
