package d;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL20.GL_MAX_FRAGMENT_UNIFORM_COMPONENTS;
import static org.lwjgl.opengl.GL20.GL_MAX_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_ATTRIBS;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
final public class box{
	public static void main(final String[]a)throws Throwable{load();loop();}
	static public String appcls="d.app.scene";
	public interface app{
		vbo[]vbos()throws Throwable;
	}
	private static final int wi=512;
	private static final int hi=512;
//	private static final int nvbos=1024;
//	private static final int nobjs=1024;
	private static app app;
//	private static obj scene;
	static long frmno;
	public/*readonly*/static int fps;
	public/*readonly*/static int keys;
	public/*readonly*/static long dtms;
	public/*readonly*/static float dt;
	public/*readonly*/static long tms;
	//	public app()throws Throwable{load();loop();}
	static private void load()throws Throwable{
		app=(app)Class.forName(appcls).newInstance();
//		scene=(obj)app;
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
		System.out.println("   application: "+app.getClass().getName());
		System.out.println("        opengl: "+glGetString(GL_VERSION));
		System.out.println("        64 bit: "+Sys.is64Bit());
		System.out.println("       adapter: "+Display.getAdapter());
		System.out.println("       version: "+Display.getVersion());
		System.out.println("GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS: "+glGetInteger(GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS));
		System.out.println("         GL_MAX_TEXTURE_IMAGE_UNITS: "+glGetInteger(GL_MAX_TEXTURE_IMAGE_UNITS));
		System.out.println(" GL_MAX_FRAGMENT_UNIFORM_COMPONENTS: "+glGetInteger(GL_MAX_FRAGMENT_UNIFORM_COMPONENTS));
		System.out.println("              GL_MAX_VERTEX_ATTRIBS: "+glGetInteger(GL_MAX_VERTEX_ATTRIBS));
		shader.load();

		for(final vbo o:app.vbos())
			o.load();
		
//		objs.add(scene);
	}
	static private void loop()throws Throwable{
		// loop
		long t0=System.currentTimeMillis();
		long t=t0;
		int frm=0;
		final mtx umxproj=new mtx().ident();
		while(!Display.isCloseRequested()){
			tms=System.currentTimeMillis();
			// viewport
			System.out.println("scr: "+Display.getWidth()+" x "+Display.getHeight());
			glViewport(0,0,Display.getWidth(),Display.getHeight());
			glClearColor(.4f,.6f,.9f,0);

			frm++;
			frmno++;
			if(Keyboard.isKeyDown(Keyboard.KEY_W))keys|=1;else keys&=~1;
			if(Keyboard.isKeyDown(Keyboard.KEY_A))keys|=2;else keys&=~2;
			if(Keyboard.isKeyDown(Keyboard.KEY_S))keys|=4;else keys&=~4;
			if(Keyboard.isKeyDown(Keyboard.KEY_D))keys|=8;else keys&=~8;
			if(Keyboard.isKeyDown(Keyboard.KEY_J))keys|=16;else keys&=~16;
			if(Keyboard.isKeyDown(Keyboard.KEY_K))keys|=32;else keys&=~32;
				
			glClear(GL_COLOR_BUFFER_BIT);

			umxproj.ident();
			umxproj.settranslate(new float[]{0,0,0});
			glUniformMatrix4(shader.umxproj,false,umxproj.bf);
//			glUniform3f(shader.upos,-.5f,.5f,0);
			
			obj.all.removeAll(obj.removed);
			obj.removed.clear();
			
			obj.all.addAll(obj.news);
			obj.news.clear();
			for(final obj o:obj.all)o.update();
			for(final obj o:obj.all)o.render();
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
				Display.setTitle("fps: "+fps+", obj: "+obj.count+" keys: "+keys);
			}
			
//			Display.sync(60);
			Display.update();
//			System.gc();
		}
		//? cleanupskippeddueto
//		Display.destroy();
	}
	public static float rnd(){return (float)Math.random();}
}