package d.app;
import java.util.Collection;
import d.box;
import d.obj;
import d.vbo;
final public class app implements d.box.app{
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
		if((box.keys&1)!=0){//w
			player.dpos(0,.01f,0,box.dt);
		}
		if((box.keys&2)!=0){//a
			player.dpos(-.01f,0,0,box.dt);
		}
		if((box.keys&4)!=0){//s
			player.dpos(0,-.01f,0,box.dt);
		}
		if((box.keys&8)!=0){//d
			player.dpos(.01f,0,0,box.dt);
		}
		if((box.keys&16)!=0){//j
			player.vbo(vbotri.o);
		}
		if((box.keys&32)!=0){//k
			player.vbo(vbosqr.o);
		}
	}
}
