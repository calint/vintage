package d;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import d.box.mtrs;
final class mtx{
	// axis x,y,z,w
	//	0  4   8  12
	//	1  5   9  13
	//	2  6  10  14
	//	3  7  11  15
	float xx,yx,zx,ox;
	float xy,yy,zy,oy;
	float xz,yz,zz,oz;
	float xo,yo,zo,oo;

//	public final FloatBuffer bf=BufferUtils.createFloatBuffer(16);
	mtx ident(){
		xx=1;xy=0;xz=0;xo=0;
		yx=0;yy=1;yz=0;yo=0;
		zx=0;zy=0;zz=1;zo=0;
		ox=oy=oz=0; oo=1;
		return this;
	}

//	public mtx ident(){
//		bf.rewind();
//		bf.put(1).put(0).put(0).put(0);
//		bf.put(0).put(1).put(0).put(0);
//		bf.put(0).put(0).put(1).put(0);
//		bf.put(0).put(0).put(0).put(1);
//		bf.flip();
//		return this;
//	}
	public mtx setsclagltrans(final p s,final p a,final p p){
		final float sinz=(float)Math.sin(a.z);
		final float cosz=(float)Math.cos(a.z);
		final float sx=s.x;
		final float sy=s.y;
		final float sz=s.z;
		xx=cosz*sx; yx=-sinz*sx; zx=  0; ox=0;
		xy=sinz*sy; yy= cosz*sy; zy=  0; oy=0;
		xz=      0; yz=       0; zz= sz; oz=0;
		xo=    p.x; yo=     p.y; zo=p.z; oo=1;
		return this;
	}
	public p axisx(){return p.n(xx,xy,xz);}
	public p axisy(){return p.n(yx,yy,yz);}
	public p axisz(){return p.n(zx,zy,zz);}
	//asumming orthonorm
	public p axisxinv(){return p.n(xx,yx,zx);}
	public p axisyinv(){return p.n(xy,yy,zy);}
	public p axiszinv(){return p.n(xz,yz,zz);}
	
	mtx mw(final p p,final p a){//? Mszxyt
		ident();
		roty(a.y);
		rotx(a.x);
		rotz(a.z);
		ox=p.x;
		oy=p.y;
		oz=p.z;
		return this;
	}
	mtx trnsf(final p src,final p dst){
		mtrs.npointstransformed++;
		final float x=src.x;
		final float y=src.y;
		final float z=src.z;
		final float nx=x*xx+y*yx+z*zx+ox;
		final float ny=x*xy+y*yy+z*zy+oy;
		final float nz=x*xz+y*yz+z*zz+oz;
		dst.set(nx,ny,nz);
		return this;
	}
	mtx set(final float[]m){
		xx=m[ 0];yx=m[ 4];zx=m[ 8];ox=m[12];
		xy=m[ 1];yy=m[ 5];zy=m[ 9];oy=m[13];
		xz=m[ 2];yz=m[ 6];zz=m[10];oz=m[14];
		xo=m[ 3];yo=m[ 7];zo=m[11];oo=m[15];
		return this;
	}
	mtx mul(final mtx m){
		mtrs.nmmul++;
		final float nxx=m.xx*xx+m.yx*xy+m.zx*xz+m.ox*xo;
		final float nyx=m.xx*yx+m.yx*yy+m.zx*yz+m.ox*yo;
		final float nzx=m.xx*zx+m.yx*zy+m.zx*zz+m.ox*zo;
		final float nox=m.xx*ox+m.yx*oy+m.zx*oz+m.ox*oo;

		final float nxy=m.xy*xx+m.yy*xy+m.zy*xz+m.oy*xo;
		final float nyy=m.xy*yx+m.yy*yy+m.zy*yz+m.oy*yo;
		final float nzy=m.xy*zx+m.yy*zy+m.zy*zz+m.oy*zo;
		final float noy=m.xy*ox+m.yy*oy+m.zy*oz+m.oy*oo;

		final float nxz=m.xz*xx+m.yz*xy+m.zz*xz+m.oz*xo;
		final float nyz=m.xz*yx+m.yz*yy+m.zz*yz+m.oz*yo;
		final float nzz=m.xz*zx+m.yz*zy+m.zz*zz+m.oz*zo;
		final float noz=m.xz*ox+m.yz*oy+m.zz*oz+m.oz*oo;

		final float nxo=m.xo*xx+m.yo*xy+m.zo*xz+m.oo*xo;
		final float nyo=m.xo*yx+m.yo*yy+m.zo*yz+m.oo*yo;
		final float nzo=m.xo*zx+m.yo*zy+m.zo*zz+m.oo*zo;
		final float noo=m.xo*ox+m.yo*oy+m.zo*oz+m.oo*oo;

		xx=nxx;yx=nyx;zx=nzx;ox=nox;
		xy=nxy;yy=nyy;zy=nzy;oy=noy;
		xz=nxz;yz=nyz;zz=nzz;oz=noz;
		xo=nxo;yo=nyo;zo=nzo;oo=noo;

		return this;
	}
	mtx rotx(final float a){
		final float c=(float)Math.cos(a),s=(float)Math.sin(a);
		final float nyx=yx*c+zx*s,nyy=yy*c+zy*s,nyz=yz*c+zz*s,nyo=yo*c+zo*s;
		final float nzx=zx*c-yx*s,nzy=zy*c-yy*s,nzz=zz*c-yz*s,nzo=zo*c-yo*s;
		yx=nyx;yy=nyy;yz=nyz;yo=nyo;
		zx=nzx;zy=nzy;zz=nzz;zo=nzo;
		return this;
	}
	mtx roty(final float a){
		final float c=(float)Math.cos(a),s=(float)Math.sin(a);
		final float nxx=xx*c-zx*s,nxy=xy*c-zy*s,nxz=xz*c-zz*s,nxo=xo*c-zo*s;
		final float nzx=zx*c+xx*s,nzy=zy*c+xy*s,nzz=zz*c+xz*s,nzo=zo*c+xo*s;
		xx=nxx;xy=nxy;xz=nxz;xo=nxo;
		zx=nzx;zy=nzy;zz=nzz;zo=nzo;
		return this;
	}
	mtx rotz(final float a){
		final float c=(float)Math.cos(a),s=(float)Math.sin(a);
		final float nxx=xx*c+yx*s,nxy=xy*c+yy*s,nxz=xz*c+yz*s,nxo=xo*c+yo*s;
		final float nyx=yx*c-xx*s,nyy=yy*c-xy*s,nyz=yz*c-xz*s,nyo=yo*c-xo*s;
		xx=nxx;xy=nxy;xz=nxz;xo=nxo;
		yx=nyx;yy=nyy;yz=nyz;yo=nyo;
		return this;
	}
	
//	public void setperspective(final float fovy,final float aspect,final float znear,final float zfar){}

	public FloatBuffer tobb(){
		final FloatBuffer bf=BufferUtils.createFloatBuffer(16);
		bf.put(xx).put(xy).put(xz).put(xo);
		bf.put(yx).put(yy).put(yz).put(yo);
		bf.put(zx).put(zy).put(zz).put(zo);
		bf.put(ox).put(oy).put(oz).put(oo);
		bf.flip();
		return bf;
	}
}