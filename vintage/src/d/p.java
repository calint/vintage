package d;
import java.io.Serializable;
public final class p implements Serializable,Cloneable{
	static final long serialVersionUID=1;
	public static p vectorfrom2points(final p po,final p p1){return p.n(p1.x-po.x,p1.y-po.y,p1.z-po.z);}
	public static p crossprod(final p v1,final p v2){
		final float xx=v1.y*v2.z-v1.z*v2.y;
		final float yy=v1.z*v2.x-v1.x*v2.z;
		final float zz=v1.x*v2.y-v1.y*v2.x;
		return p.n(xx,yy,zz);
	}
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
	public p sub(final p p){x-=p.x;y-=p.y;z-=p.z;return this;}
	public p norm(){return norm(1.f);}
	public p norm(final float length){
		float q=(float)Math.sqrt(x*x+y*y+z*z);
		if(q==0){
			zero();
			return this;
		}
		final float t=length/q;
		x=t*x;y=t*y;z=t*z;
		return this;
	}
	public p zero(){x=y=z=0;return this;}
}
