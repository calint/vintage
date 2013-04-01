package d;
import java.io.Serializable;
public final class p implements Serializable,Cloneable{
	static final long serialVersionUID=1;
	public static p n(final float x,final float y,final float z){return new p(x,y,z);}
	public static p n(final float x,final float y){return new p(x,y,0);}
	public static p n(){return new p();}
	public float x,y,z;
	public p(){}
	public p(final float x,final float y,final float z){set(x,y,z);}
	public p set(final float x,final float y,final float z){this.x=x;this.y=y;this.z=z;return this;}
	public p set(final p p){x=p.x;y=p.y;z=p.z;return this;}
	public p add(final p p,final float dt){x+=p.x*dt;y+=p.y*dt;z+=p.z*dt;return this;}
	public p add(final p p){x+=p.x;y+=p.y;z+=p.z;return this;}
	public String toString(){return x+","+y+","+z;}
	public p clone(){return new p(x,y,z);}
	public p neg(){x=-x;y=-y;z=-z;return this;}
	public float dot(final p p){return x*p.x+y*p.y+z*p.z;}
	public p add(final float x,final float y,final float z){this.x+=x;this.y+=y;this.z+=z;return this;}
}
