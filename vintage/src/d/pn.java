package d;
final class pn{
	public static pn frompointandnormal(final p p,final p n){return new pn(p,n);}
	public static pn from3points(final p p0,final p p1,final p p2){
		final p v1=d.p.vectorfrom2points(p0,p1);
		final p v2=d.p.vectorfrom2points(p0,p2);
		final p n=d.p.crossprod(v1,v2);
		n.norm();
		return new pn(p0,n);
	}
	public float disttopoint(final p po){
		final p v=d.p.vectorfrom2points(p,po);
		final float d=n.dot(v);
		return d;
	}
	public String toString(){return p+"||"+n;}
	final public p p;//origo
	final public p n;//normal
	pn(final p p,final p n){this.p=p;this.n=n;}
}
