package d;
final class texture{
	final int id;
	final int wi;
	final int hi;
//	final String path;
//	texture(final int id,final int wi,final int hi,final String path){this.id=id;this.wi=wi;this.hi=hi;this.path=path;}
	texture(final int id,final int wi,final int hi){this.id=id;this.wi=wi;this.hi=hi;}
//	public String toString(){return "texture "+id+" size "+wi+"x"+hi+" from "+path;}
	public String toString(){return "texture "+id+" size "+wi+"x"+hi+"x32 bpp";}
}