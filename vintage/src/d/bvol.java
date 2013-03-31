package d;
final class bvol{
	float r;
	p s;
	polyh ph;
	
	final static class polyh{
		final static class vtx{
			p position;
//			p normal;
//			p texturecoord;
//			p color;
		}
		final static class polyc{
			int[]indices;
			p normal;
		}
		vtx[]vtxs;
		polyc[]polycs;
	}
}
