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
abstract public class vbo{
	private int vao;// vertex array object
	private int vbo;// vertex buffer object
	private int vboi;// indices buffer object
	private int nindices;
	private img tx;
	private static final class img{
		final int id;
		final int wi;
		final int hi;
		final String path;
		img(final int id,final int wi,final int hi,final String path){this.id=id;this.wi=wi;this.hi=hi;this.path=path;}
		public String toString(){return id+" "+wi+"x"+hi+" "+path;}
	}
	final void load()throws Throwable{
		final String imgpath=imgpath();
		if(imgpath!=null)
			tx=imgload(imgpath);
		// load buffers
//		FloatBuffer verticesBuffer = ByteBuffer.allocateDirect(4*vertices.length*Vertex.elementCount).asFloatBuffer();
		final int nvertices=nvertices();
		final int stride=10;//xyzw,rgba,st
		final int sizeofnum=4;//sizeof(float)
		final int stridebytes=stride*sizeofnum;
		final FloatBuffer vb=BufferUtils.createFloatBuffer(nvertices*stride);
	
		vertices(vb);

		vb.flip();
		
		nindices=nindices();
		final ByteBuffer ib=BufferUtils.createByteBuffer(nindices);
		indices(ib);

		ib.flip();
		
		vao=glGenVertexArrays();
		glBindVertexArray(vao);
		
		vbo=glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glBufferData(GL_ARRAY_BUFFER,vb,GL_STATIC_DRAW);
		glVertexAttribPointer(0,4,GL_FLOAT,false,stridebytes,0);// positions
		glVertexAttribPointer(1,4,GL_FLOAT,false,stridebytes,4*sizeofnum);// colors, 16 bytes offset
		glVertexAttribPointer(2,2,GL_FLOAT,false,stridebytes,8*sizeofnum);// texture, 32 bytes offset
		glBindBuffer(GL_ARRAY_BUFFER,0);
		glBindVertexArray(0);

		vboi=glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboi);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,ib,GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
	}
	final void render()throws Throwable{
		glBindTexture(GL_TEXTURE_2D,tx!=null?tx.id:0);
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboi);
		glUniform1i(shader.utx,0);
		glDrawElements(GL_TRIANGLES,nindices,GL_UNSIGNED_BYTE,0);
//		glDrawElements(GL_TRIANGLE_STRIP,nindices,GL_UNSIGNED_BYTE,0);
		
		//. groupedbymaterial,textureunit,drawelementsrange
	}
	
	
	abstract protected int nvertices();
	abstract protected void vertices(final FloatBuffer vb);
	abstract protected int nindices();
	abstract protected void indices(final ByteBuffer ib);
	abstract protected String imgpath();
	private static img imgload(final String path)throws Throwable{
			if(glGetError()!=GL_NO_ERROR)throw new Error("opengl in error state");
	
			// textures
			final int tx=glGenTextures();
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D,tx);
			glPixelStorei(GL_UNPACK_ALIGNMENT,1);
	
			final BufferedImage img=ImageIO.read(new File(path));
			if(img==null)throw new Error("could not read file logo.jpg");
	//		final BufferedImage img0=new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
	//	    img0.getGraphics().drawImage(img,0,0,null);
	//		img.getData().getDataBuffer();
	
			final int txwi=img.getWidth();
			final int txhi=img.getHeight();
			final int n=4*txwi*txhi;
			final ByteBuffer txbuf=ByteBuffer.allocateDirect(n);// 4 bytes/pixel
	//		for(int i=0;i<txhi;i++){
	//			for(int j=0;j<txwi;j++){
	////				final byte d=(byte)(Math.random()*0x100);
	//				final byte r=(byte)i;
	//				final byte g=(byte)i;
	//				final byte b=(byte)i;
	//				final byte a=(byte)0xff;
	//				txbuf.put(r);
	//				txbuf.put(g);
	//				txbuf.put(b);
	//				txbuf.put(a);
	//			}
	//		}
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
	//					txbuf.put((byte)0xff);
	//					txbuf.put((byte)0xff);
	//					txbuf.put((byte)0xff);
						txbuf.put((byte)0);
					}else{
						txbuf.put(b);
						txbuf.put(g);
						txbuf.put(r);
						txbuf.put(a);
					}
				}
			}
			
			
	//		for(int i=0;i<n;i++){
	//			final byte d=(byte)(Math.random()*0x100);
	////			final byte d=(byte)i;
	//			txbuf.put(d);
	//		}
			txbuf.flip();
			
			glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA8,txwi,txhi,0,GL_BGRA,GL_UNSIGNED_BYTE,txbuf);
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
			if(glGetError()!=GL_NO_ERROR)throw new Error("opengl is in error state");
			glBindTexture(GL_TEXTURE_2D,0);
			return new img(tx,txwi,txhi,path);
		}
}