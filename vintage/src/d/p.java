package d;
public final class p{
	public float x,y,z;
	public p(){}
	public p(final float x,final float y,final float z){set(x,y,z);}
	public p set(final float x,final float y,final float z){this.x=x;this.y=y;this.z=z;return this;}
	public p set(final p p){x=p.x;y=p.y;z=p.z;return this;}
	public p add(final p p,final float dt){x+=p.x*dt;y+=p.y*dt;z+=p.z*dt;return this;}
	public String toString(){return x+","+y+","+z;}
}
