package d;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import javax.imageio.ImageIO;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
final public class app{
	public static void main(final String[]a)throws Throwable{load();loop();}
	static public String defclsnm="d.file.def";
	public interface def{
		void load()throws Throwable;
		Collection<vbo>vbos();
		Collection<obj>objs();
		void update()throws Throwable;
	}
	static private final int wi=512;
	static private final int hi=512;
	static public/*readonly*/int fps;
	static public final int nvbos=1024;
	static public final int nobjs=1024;
	static private def def;
	static public/*readonly*/int bmpkeys;
	static public/*readonly*/long dtms;
	static public/*readonly*/float dt;
//	public app()throws Throwable{load();loop();}
	static private void load()throws Throwable{
		def=(def)Class.forName(defclsnm).newInstance();
//		def.con(this);
		def.load();
		// display
		final PixelFormat pixelFormat=new PixelFormat();
		final ContextAttribs contextAtrributes=new ContextAttribs(3,2).withProfileCore(true).withForwardCompatible(true);
		Display.setDisplayMode(new DisplayMode(wi,hi));
		Display.create(pixelFormat,contextAtrributes);
		
//		Display.setDisplayMode(new DisplayMode(wi,hi));
//		Display.create();
//		Display.setResizable(false);

		if(glGetError()!=GL_NO_ERROR)throw new Error("opengl in error state");
		System.out.println("light weight java game layer");
		System.out.println("       version: "+Sys.getVersion());
		System.out.println("        opengl: "+glGetString(GL_VERSION));
		System.out.println("        64 bit: "+Sys.is64Bit());
		System.out.println("       adapter: "+Display.getAdapter());
		System.out.println("       version: "+Display.getVersion());
		System.out.println("GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS: "+glGetInteger(GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS));
		System.out.println("         GL_MAX_TEXTURE_IMAGE_UNITS: "+glGetInteger(GL_MAX_TEXTURE_IMAGE_UNITS));
		System.out.println(" GL_MAX_FRAGMENT_UNIFORM_COMPONENTS: "+glGetInteger(GL_MAX_FRAGMENT_UNIFORM_COMPONENTS));
		System.out.println("              GL_MAX_VERTEX_ATTRIBS: "+glGetInteger(GL_MAX_VERTEX_ATTRIBS));
		shader.load();

		// vbos
		for(final vbo o:def.vbos())
			o.load();

		txload("logo.jpg");
	}
	private static void txload(final String path)throws Throwable{
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
	}
	static long frmno;
	static private void loop()throws Throwable{
		// viewport
		glViewport(0,0,wi,hi);
		glClearColor(.4f,.6f,.9f,0);
		// loop
		long t0=System.currentTimeMillis();
		long t=t0;
		int frm=0;
		final mtx umxproj=new mtx().ident();
		while(!Display.isCloseRequested()){
			frm++;
			frmno++;
			if(Keyboard.isKeyDown(Keyboard.KEY_W))bmpkeys|=1;else bmpkeys&=0xfffffffe;
			if(Keyboard.isKeyDown(Keyboard.KEY_A))bmpkeys|=2;else bmpkeys&=0xfffffffd;
				
			glClear(GL_COLOR_BUFFER_BIT);

			umxproj.ident();
			umxproj.settranslate(new float[]{.5f,-.5f,0});
			glUniformMatrix4(shader.umxproj,false,umxproj.bf);
//			glUniform3f(shader.upos,-.5f,.5f,0);
			
			for(final obj o:def.objs())
				o.render();
//			def.objs().iterator().next().render();
			
			final long t1=System.currentTimeMillis();
			final long dt0=t1-t;
//			if(dt0>16)
				System.out.println("frame #"+frmno+": "+dt0+" ms "+(dt0>16?"!":" "));
			t=t1;
			dtms=t1-t0;
			dt=dtms/1000.f;
			if(dtms>1000){
				fps=(int)(frm*1000/dtms);
				t0=t1;
				frm=0;
				Display.setTitle("fps: "+fps+", obj: "+obj.count+" keys: "+bmpkeys);
			}
			
			def.update();
			
//			Display.sync(60);
			Display.update();
//			System.gc();
		}
		//? cleanupskippeddueto
//		Display.destroy();
	}
}