package d;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
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

		// textures
		final int tx=glGenTextures();
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D,tx);
		glPixelStorei(GL_UNPACK_ALIGNMENT,1);

		final int txwi=64;
		final int txhi=64;
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
		for(int i=0;i<n;i++){
			final byte d=(byte)(Math.random()*0x100);
//			final byte d=(byte)i;
			txbuf.put(d);
		}
		txbuf.flip();
		
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA8,txwi,txhi,0,GL_RGBA,GL_UNSIGNED_BYTE,txbuf);
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
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR_MIPMAP_LINEAR);
		if(glGetError()!=GL_NO_ERROR)throw new Error("opengl is in error state");
	}
	static long frameno;
	static private void loop()throws Throwable{
		// viewport
		glViewport(0,0,wi,hi);
		glClearColor(.4f,.6f,.9f,0);
		// loop
		long t0=System.currentTimeMillis();
		long t=t0;
		int frm=0;
		final mtx mtxproj=new mtx().ident();
		while(!Display.isCloseRequested()){
			frm++;
			frameno++;
			if(Keyboard.isKeyDown(Keyboard.KEY_W))bmpkeys|=1;else bmpkeys&=0xfffffffe;
			if(Keyboard.isKeyDown(Keyboard.KEY_A))bmpkeys|=2;else bmpkeys&=0xfffffffd;
				
			glClear(GL_COLOR_BUFFER_BIT);

			glUniformMatrix4(shader.umxproj,false,mtxproj.bf);
			
			for(final obj o:def.objs())
				o.render();
//			def.objs().iterator().next().render();
			
			final long t1=System.currentTimeMillis();
			final long dt0=t1-t;
//			if(dt0>16)
				System.out.println("frame #"+frameno+": "+dt0+" ms "+(dt0>16?"!":" "));
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