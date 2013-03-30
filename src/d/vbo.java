package d;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_BGRA;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
public class vbo{
	private int vao;// vertex array object
	private int vbo;// vertex buffer object
	private int vboi;// indices buffer object
	private int nindices;
	private texture tx;
	final void load()throws Throwable{
		System.out.println(getClass().getName());
		// load buffers
//		FloatBuffer verticesBuffer = ByteBuffer.allocateDirect(4*vertices.length*Vertex.elementCount).asFloatBuffer();
		final int nvertices=nvertices();
		final int stride=9;//xyz,rgba,st
		final int sizeofnum=4;//sizeof(float)
		final int stridebytes=stride*sizeofnum;
		System.out.println("  "+nvertices+" vertices, "+stridebytes+" B/vertex");
		final FloatBuffer vb=BufferUtils.createFloatBuffer(nvertices*stride);
		vertices(vb);
		vb.flip();
		nindices=nindices();
		System.out.println("  "+nindices+" indices, 1 B/index");
		final ByteBuffer ib=BufferUtils.createByteBuffer(nindices);
		indices(ib);
		ib.flip();
		vao=glGenVertexArrays();
		glBindVertexArray(vao);
		vbo=glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glBufferData(GL_ARRAY_BUFFER,vb,GL_STATIC_DRAW);
		glVertexAttribPointer(0,3,GL_FLOAT,false,stridebytes,0);// positions
		glVertexAttribPointer(1,4,GL_FLOAT,false,stridebytes,3*sizeofnum);// colors, 16 bytes offset
		glVertexAttribPointer(2,2,GL_FLOAT,false,stridebytes,7*sizeofnum);// texture, 32 bytes offset
		glBindBuffer(GL_ARRAY_BUFFER,0);
		glBindVertexArray(0);
		vboi=glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboi);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,ib,GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		final String imgpath=imgpath();
		if(imgpath!=null){
			System.out.println("  texture "+imgpath);
			tx=loadtexture(imgpath,null,0,0);
		}else{
			final int[]txsize=imgsize();
			if(txsize!=null){
				final int wi=txsize[0],hi=txsize[1],bpp=txsize[2];
				final int n=wi*hi*bpp;
				System.out.println("  texture alloc "+wi+"x"+hi+"x"+bpp*8+" bpp");
				final ByteBuffer txdata=ByteBuffer.allocateDirect(n);
				System.out.println("  texture generate");
				imggen(txdata);
				txdata.flip();
				System.out.println("  texture load");
				tx=loadtexture(null,txdata,txsize[0],txsize[1]);
			}
		}
		System.out.println();
	}
	final void render()throws Throwable{
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		if(tx!=null){
			glBindTexture(GL_TEXTURE_2D,tx.id);
			glEnableVertexAttribArray(2);
		}
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboi);
		glUniform1i(shader.utx,0);
		glDrawElements(GL_TRIANGLES,nindices,GL_UNSIGNED_BYTE,0);
//		glDrawElements(GL_POINTS,nindices,GL_UNSIGNED_BYTE,0);
		//. groupedbymaterial,textureunit,drawelementsrange
	}
	

	protected int nvertices(){return 4;}
	protected void vertices(final FloatBuffer vb){
		final float w=1;
		//0
		vb.put(-w).put(w).put(0);//xyz
		vb.put(1).put(0).put(0).put(1);//rgba
		vb.put(0).put(0);//st
		//1
		vb.put(-w).put(-w).put(0);//xyz
		vb.put(0).put(1).put(0).put(1);//rgba
		vb.put(0).put(1);//st
		//2
		vb.put(w).put(-w).put(0);//xyz
		vb.put(0).put(0).put(1).put(1);//rgba
		vb.put(1).put(1);//st
		//3
		vb.put(w).put(w).put(0);//xyz
		vb.put(1).put(1).put(1).put(1);//rgba
		vb.put(1).put(0);//st
	}
	protected int nindices(){return 6;}
	protected void indices(final ByteBuffer ib){
		ib.put((byte)0).put((byte)1).put((byte)2);
//		ib.put((byte)1).put((byte)2).put((byte)3);
		ib.put((byte)2).put((byte)3).put((byte)0);
//		ib.put((byte)0).put((byte)1).put((byte)2);
//		ib.put((byte)2).put((byte)3).put((byte)0);
	}
	/** if texture in file path or null */
	protected String imgpath(){return null;}
	/** if texture is generated return width,height,bytesperpixel */
	protected int[]imgsize(){return null;}
	protected void imggen(final ByteBuffer bb){}
	private static img loadimg(final String path)throws Throwable{
		final BufferedImage img=ImageIO.read(new File(path));
		if(img==null)throw new Error("could not read file logo.jpg");
//		final BufferedImage img0=new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
//	    img0.getGraphics().drawImage(img,0,0,null);
//		img.getData().getDataBuffer();

		final int txwi=img.getWidth();
		final int txhi=img.getHeight();
		final int n=4*txwi*txhi;
		final ByteBuffer txbuf=ByteBuffer.allocateDirect(n);// 4 bytes/pixel
		for(int y=0;y<txhi;y++){
			for(int x=0;x<txwi;x++){
				final int argb=img.getRGB(x,y);
				final byte b=(byte)argb;
				final byte g=(byte)(argb>>8);
				final byte r=(byte)(argb>>16);
				final byte a=(byte)(argb>>24);
				if(r==0&&g==0&b==0){
					txbuf.put(b);
					txbuf.put(g);
					txbuf.put(r);
					txbuf.put((byte)0);
				}else{
					txbuf.put(b);
					txbuf.put(g);
					txbuf.put(r);
					txbuf.put(a);
				}
			}
		}		
		txbuf.flip();
		return new img(txwi,txhi,txbuf);
	}
	private static class img{
		final int w;
		final int h;
		final ByteBuffer rgba;
		img(final int w,final int h,final ByteBuffer rgba){this.w=w;this.h=h;this.rgba=rgba;}
	}
	/** either path or rgbabuf */
	private static texture loadtexture(final String path,final ByteBuffer rgbabuf,final int wi,final int hi)throws Throwable{
		// textures
		final int tx=glGenTextures();
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D,tx);
		glPixelStorei(GL_UNPACK_ALIGNMENT,1);
		final img img;
		final int w;
		final int h;
		final ByteBuffer txbuf;
		if(path!=null){
			img=loadimg(path);
			txbuf=img.rgba;
			w=img.w;
			h=img.h;
		}else{
			txbuf=rgbabuf;
			w=wi;
			h=hi;
		}
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA8,w,h,0,GL_BGRA,GL_UNSIGNED_BYTE,txbuf);
		if(glGetError()!=GL_NO_ERROR)throw new Error("opengl in error state");
//		final long t0=System.currentTimeMillis();
//		final int nn=1024*1024;
//		for(int i=0;i<nn;i++){
//			glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,txwi,txhi,0,GL_RGBA,GL_UNSIGNED_BYTE,txbuf);
//		}
//		final long dt=System.currentTimeMillis()-t0;
//		System.out.println(" loads "+nn/dt+" loads/ms");
//		if(glGetError()!=GL_NO_ERROR)throw new Error("could not load texture");
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
//		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR_MIPMAP_LINEAR);
//		glEnable(GL_BLEND);
//		glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
//		glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
		glBindTexture(GL_TEXTURE_2D,0);
		if(glGetError()!=GL_NO_ERROR)throw new Error("opengl is in error state");
		return new texture(tx,w,h);
	}
}