package d;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL20.*;
import java.io.IOException;
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
		void vbos(final Collection<vbo>vbos)throws Throwable;
		void objs(final Collection<obj>objs)throws Throwable;
		void update()throws Throwable;
	}
	private static final int wi=512;
	private static final int hi=512;
//	private static final int nvbos=1024;
//	private static final int nobjs=1024;
	private static def def;
	public/*readonly*/static int fps;
	public/*readonly*/static int bmpkeys;
	public/*readonly*/static long dtms;
	public/*readonly*/static float dt;
//	public app()throws Throwable{load();loop();}
	static private void load()throws Throwable{
		def=(def)Class.forName(defclsnm).newInstance();
//		def.con(this);
//		def.load();
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

		def.vbos(vbos);
		for(final vbo o:vbos)
			o.load();
		def.objs(objs);
	}
	private static final Collection<vbo>vbos=new ArrayList<vbo>();
	private static final Collection<obj>objs=new ArrayList<obj>();

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
			for(final obj o:objs)
				o.update();
			for(final obj o:objs)
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