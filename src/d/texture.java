package d;
final class texture{
	final int id;
	final int wi;
	final int hi;
	final String path;
	texture(final int id,final int wi,final int hi,final String path){this.id=id;this.wi=wi;this.hi=hi;this.path=path;}
	public String toString(){return id+" "+wi+"x"+hi+" "+path;}
}