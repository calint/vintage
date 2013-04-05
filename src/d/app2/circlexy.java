package d.app2;
import d.obj;
public class circlexy extends obj{{vbo(vbocrclexy.o);colbitsmsk=1;}

	protected void update()throws Throwable{
		super.update();
		if(pos.x>1)pos.x=-1;
		else if(pos.x<-1)pos.x=1;
		if(pos.y>1)pos.y=-1;
		else if(pos.y<-1)pos.y=1;
	}
}

