package d.app;
import d.box;
import d.obj;
public class plr extends obj{static final long serialVersionUID=1;
	{vbo(vbosqr.o);}
	public void update()throws Throwable{
		super.update();
		setdpos(0,0,0);
		if((box.keys&1)!=0){//w
			setdpos(0,.01f,0);
		}
		if((box.keys&2)!=0){//a
			setdpos(-.01f,0,0);
		}
		if((box.keys&4)!=0){//s
			setdpos(0,-.01f,0);
		}
		if((box.keys&8)!=0){//d
			setdpos(.01f,0,0);
		}
		if((box.keys&16)!=0){//j
			vbo(vbotri.o);
		}
		if((box.keys&32)!=0){//k
			vbo(vbosqr.o);
		}
	}
}
