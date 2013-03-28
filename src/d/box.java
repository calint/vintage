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
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import java.util.Random;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
final public class box{
	public static void main(final String[]a)throws Throwable{load();loop();}
	public static String appcls="d.app.scene";
	private static final Random random=new Random(0);
	public interface app{
		vbo[]vbos()throws Throwable;
	}
	private static app app;
	private static final int width=1024;
	private static final int height=512;
	private static int wi=width;
	private static int hi=height;
	static long frmno;
	public/*readonly*/static int fps;
	public/*readonly*/static int keys;
	public/*readonly*/static long dtms;
	public/*readonly*/static float dt;
	public/*readonly*/static long tms;
	//	public app()throws Throwable{load();loop();}
	static private void load()throws Throwable{
		app=(app)Class.forName(appcls).newInstance();
		// display
		final PixelFormat pixelFormat=new PixelFormat();
		final ContextAttribs contextAtrributes=new ContextAttribs(3,2).withProfileCore(true).withForwardCompatible(true);
		Display.setDisplayMode(new DisplayMode(width,height));
		Display.create(pixelFormat,contextAtrributes);

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
	}
	static private void loop()throws Throwable{
		// loop
		long t0=System.currentTimeMillis();
		long t=t0;
		int frm=0;
		final mtx umxproj=new mtx().ident();
		while(!Display.isCloseRequested()){
			frm++;
			frmno++;
			tms=System.currentTimeMillis();
//			final long t1=tms;
//			final long dt0=t1-t;
//			if(dt0>16)
//				System.out.println("frame #"+frmno+": "+dt0+" ms "+(dt0>16?"!":" "));
			dtms=tms-t;
			dt=(float)(dtms/1000.);
			t=tms;
			dt=dtms/1000.f;
			if(tms-t0>1000){
				fps=(int)(frm*1000/(tms-t0));
				t0=tms;
				frm=0;
				Display.setTitle("fps: "+fps+", obj: "+obj.count+" keys: "+keys);
			}
			// viewport
//			System.out.println("scr: "+Display.getWidth()+" x "+Display.getHeight());
			wi=Display.getWidth();
			hi=Display.getHeight();
			final float wihiratio=(float)wi/hi;
//			glViewport(0,0,wi,hi);
			glClearColor(.4f,.6f,.9f,0);
			glClear(GL_COLOR_BUFFER_BIT);

			if(Keyboard.isKeyDown(Keyboard.KEY_W))keys|=1;else keys&=~1;
			if(Keyboard.isKeyDown(Keyboard.KEY_A))keys|=2;else keys&=~2;
			if(Keyboard.isKeyDown(Keyboard.KEY_S))keys|=4;else keys&=~4;
			if(Keyboard.isKeyDown(Keyboard.KEY_D))keys|=8;else keys&=~8;
			if(Keyboard.isKeyDown(Keyboard.KEY_J))keys|=16;else keys&=~16;
			if(Keyboard.isKeyDown(Keyboard.KEY_K))keys|=32;else keys&=~32;
				
			umxproj.ident();
			umxproj.settranslate(new float[]{0,0,0});
			glUniformMatrix4(shader.umxproj,false,umxproj.bf);
			glUniform3f(shader.upos,0,0,0);
			glUniform2f(shader.us,1,wihiratio);
			obj.allupdaterender();
			Display.update();
		}
		//? cleanupskippeddueto
	}
	public static float rnd(){return random.nextFloat();}
	public static float rnd(final float min,final float max){return min+(max-min)*random.nextFloat();}
}