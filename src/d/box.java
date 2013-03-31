package d;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
//import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;
import java.text.SimpleDateFormat;
import java.util.Random;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
final public class box{
	public static void main(final String[]a)throws Throwable{load();loop();}
	public interface app{vbo[]vbos()throws Throwable;}
	private static obj app;
	public static String appcls="d.app.app";//application object
	private static final Random random=new Random(0);
	private static int wi=512+256,hi=512+256;
	public/*readonly*/static long frm;//frame number
	public/*readonly*/static int fps;//frames per second
	public/*readonly*/static int keys;//keys bits
	public/*readonly*/static long tms;//time in millis
	public/*readonly*/static long dtms;//last frame time in millis
	public/*readonly*/static float dt;//dtms in seconds
	static private void load()throws Throwable{
		final long t0=System.currentTimeMillis();
		app=(obj)Class.forName(appcls).newInstance();
		// display
		final PixelFormat pixelFormat=new PixelFormat();
		final ContextAttribs contextAtrributes=new ContextAttribs(3,2).withProfileCore(true).withForwardCompatible(true);
		Display.setDisplayMode(new DisplayMode(wi,hi));
		Display.create(pixelFormat,contextAtrributes);
		banner();
		System.out.println();
		shader.load();
		for(final vbo o:((app)app).vbos())
			o.load();
		if(glGetError()!=GL_NO_ERROR)throw new Error();
		final long dt=System.currentTimeMillis()-t0;
		System.out.println();
		System.out.println(box.class.getName()+" load, "+dt+" millis");
	}
	private static void banner(){
		System.out.println("light weight java game layer");
		System.out.println("       version: "+Sys.getVersion());
		System.out.println("   application: "+app.getClass().getName());
		System.out.println("        opengl: "+glGetString(GL_VERSION));
		System.out.println("        64 bit: "+Sys.is64Bit());
		System.out.println("       adapter: "+Display.getAdapter());
		System.out.println("       version: "+Display.getVersion());
		System.out.println();
		System.out.println("        GL_SHADING_LANGUAGE_VERSION: "+glGetString(GL_SHADING_LANGUAGE_VERSION));
		System.out.println("                GL_MAX_TEXTURE_SIZE: "+glGetInteger(GL_MAX_TEXTURE_SIZE));
//		System.out.println("             GL_MAX_3D_TEXTURE_SIZE: "+glGetInteger(GL_MAX_3D_TEXTURE_SIZE));
		System.out.println("         GL_MAX_TEXTURE_IMAGE_UNITS: "+glGetInteger(GL_MAX_TEXTURE_IMAGE_UNITS));
		System.out.println("GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS: "+glGetInteger(GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS));
		System.out.println("  GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS: "+glGetInteger(GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS));
		System.out.println("GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS: "+glGetInteger(GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS));
		System.out.println(" GL_MAX_FRAGMENT_UNIFORM_COMPONENTS: "+glGetInteger(GL_MAX_FRAGMENT_UNIFORM_COMPONENTS));
		System.out.println("              GL_MAX_VERTEX_ATTRIBS: "+glGetInteger(GL_MAX_VERTEX_ATTRIBS));
//		System.out.println("              GL_MAX_TEXTURE_COORDS: "+glGetInteger(GL_MAX_TEXTURE_COORDS));
		System.out.println("   GL_MAX_VERTEX_UNIFORM_COMPONENTS: "+glGetInteger(GL_MAX_VERTEX_UNIFORM_COMPONENTS));
		System.out.println("           GL_MAX_ELEMENTS_VERTICES: "+glGetInteger(GL_MAX_ELEMENTS_VERTICES));
		
	}
	static private void loop()throws Throwable{
		// loop
		long t0=System.currentTimeMillis();
		final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(box.class.getName()+" loop, "+sdf.format(t0));
		long t=t0;
		int frmi=0;
		final mtx mxwv=new mtx().ident();
		glClearColor(.4f,.6f,.9f,0);
		glFrontFace(GL_CCW);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
//		glPolygonMode(GL_FRONT_AND_BACK,GL_POINT);
		while(!Display.isCloseRequested()){
			frm++;
			frmi++;
			tms=System.currentTimeMillis();
			dtms=tms-t;
			dt=(float)(dtms/1000.);
//			final long dt0=tms-t;
//			if(dt0>16)
//				System.out.println("frame #"+frmno+": "+dt0+" ms "+(dt0>16?"!":" "));
			t=tms;
			dt=dtms/1000.f;
			if(tms-t0>1000){
				fps=(int)(frmi*1000/(tms-t0));
				t0=tms;
				frmi=0;
				Display.setTitle("fps:"+fps+",objs:"+obj.count+",upd:"+obj.ms_allupdate+",rend:"+obj.ms_allrender+",keys:"+keys);
			}
			// viewport
//			System.out.println("scr: "+Display.getWidth()+" x "+Display.getHeight());
			wi=Display.getWidth();
			hi=Display.getHeight();
			final float wihiratio=(float)wi/hi;
//			glViewport(0,0,wi,hi);
			glClear(GL_COLOR_BUFFER_BIT+GL_DEPTH_BUFFER_BIT);

			keys=0;
			if(Keyboard.isKeyDown(Keyboard.KEY_W))keys|=1;
			if(Keyboard.isKeyDown(Keyboard.KEY_A))keys|=2;
			if(Keyboard.isKeyDown(Keyboard.KEY_S))keys|=4;
			if(Keyboard.isKeyDown(Keyboard.KEY_D))keys|=8;
			if(Keyboard.isKeyDown(Keyboard.KEY_J))keys|=16;
			if(Keyboard.isKeyDown(Keyboard.KEY_K))keys|=32;
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))keys|=64;
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))keys|=128;
			
			if((keys&64)!=0)break;
				
			mxwv.ident();
			final obj cam=app;
//			glUniform2f(shader.us,1,1);
			mxwv.setsclagltrans(new p(1,wihiratio,1),new p(-cam.agl.x,-cam.agl.y,-cam.agl.z),new float[]{-cam.pos[0],-cam.pos[1],-cam.pos[2]});
			glUniformMatrix4(shader.umxwv,false,mxwv.bf);
//			glUniform3f(shader.upos,0,0,0);
			obj.allupdaterender();
			Display.update();
		}
		//? cleanupskippeddueto
	}
	public static float rnd(){return random.nextFloat();}
	public static float rnd(final float min,final float max){return min+(max-min)*random.nextFloat();}
}