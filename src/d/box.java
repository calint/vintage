package d;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MAX_TEXTURE_SIZE;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL12.GL_MAX_ELEMENTS_VERTICES;
import static org.lwjgl.opengl.GL20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL20.GL_MAX_FRAGMENT_UNIFORM_COMPONENTS;
import static org.lwjgl.opengl.GL20.GL_MAX_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_ATTRIBS;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL20.GL_MAX_VERTEX_UNIFORM_COMPONENTS;
//import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
//import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
final public class box{
	static final class mtrs{
		public/*readonly*/static int niscol; 
		public/*readonly*/static int noncols;
		public/*readonly*/static int ngrids; 
		public/*readonly*/static int nobjcull; 
		public/*readonly*/static int ngridcull; 
		public/*readonly*/static int ngridrend; 
		public/*readonly*/static int nobjrend; 
		public/*readonly*/static long ms_gridupd;
		public/*readonly*/static long ms_render;
		public/*readonly*/static long ms_update;
		public/*readonly*/static long ms_coldet;
		public/*readonly*/static int nobjs;
		static void framereset(){niscol=noncols=ngrids=nobjcull=nobjrend=ngridcull=ngridrend=0;}
	}
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
	static final int nthreads=2;
	static final ExecutorService thdpool=Executors.newFixedThreadPool(nthreads);
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
		vbo.o.load();
		vboviewpyr.o.load();
		for(final vbo o:((app)app).vbos())
			o.load();
//		glShadeModel(GL_FLAT);

		
		glClearColor(.4f,.6f,.9f,0);
		glFrontFace(GL_CCW);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
//		glDepthFunc(GL_LEQUAL);
//		glDepthFunc(GL_GEQUAL);
//		glClearDepth(-1);
		
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
				long tt=tms-t0;
				if(tt==0)tt++;
				fps=(int)(frmi*1000/tt);
				t0=tms;
				frmi=0;
				Display.setTitle("fps:"+fps+" n objs:"+mtrs.nobjs+",gc:"+mtrs.ngridcull+",gr:"+mtrs.ngridrend+",noc:"+mtrs.nobjcull+",or:"+mtrs.nobjrend+", ms grd:"+mtrs.ms_gridupd+",rend:"+mtrs.ms_render+",upd:"+mtrs.ms_update+",fsx:"+mtrs.ms_coldet+",niscol:"+mtrs.niscol+",noncols:"+mtrs.noncols+",ngrids:"+(grid.ngrids+1)+",keys:"+keys);
//				grid.bench();
			}
//			Display.setTitle("fps:"+fps+",objs:"+mtrs.nobjs+",grdrfh:"+mtrs.ms_gridupd+",rend:"+mtrs.ms_render+",upd:"+mtrs.ms_update+",coldet:"+mtrs.ms_coldet+",niscol:"+mtrs.niscol+",noncols:"+mtrs.noncols+",ngrids:"+(grid.ngrids+1)+",keys:"+keys);
			mtrs.framereset();
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
			if(Keyboard.isKeyDown(Keyboard.KEY_Q))keys|=256;
			if(Keyboard.isKeyDown(Keyboard.KEY_E))keys|=512;
			
			if((keys&64)!=0)break;
				
				
			mxwv.ident();
			mxwv.setsclagltrans(p.n(1,wihiratio,1),app.agl.clone().neg(),app.pos.clone().neg());
			glUniformMatrix4(shader.umxwv,false,mxwv.bf);
//			if(Keyboard.isKeyDown(Keyboard.KEY_F2))
//				glUniform1i(shader.udopersp,1);
//			else
//				glUniform1i(shader.udopersp,0);
			if(Keyboard.isKeyDown(Keyboard.KEY_F2))glUniform1i(shader.udopersp,1);
			if(Keyboard.isKeyDown(Keyboard.KEY_F3))glUniform1i(shader.udopersp,0);
			if(Keyboard.isKeyDown(Keyboard.KEY_F4))glUniform1i(shader.urendzbuf,1);
			if(Keyboard.isKeyDown(Keyboard.KEY_F5))glUniform1i(shader.urendzbuf,0);

			// cullplanes
			final pn[]pns=new pn[5];
			// view (instead of mxwv^-1 cause orthonorm
			final p xinv=mxwv.axisxinv();
			final p yinv=mxwv.axisyinv();
			final p zinv=mxwv.axiszinv();
			//back
			final pn pnback=pn.frompointandnormal(app.pos.clone(),zinv);
			pns[0]=pnback;
			//viewpyr
			final p topright=p.n().add(xinv).add(yinv).sub(zinv);
			final p topleft=p.n().sub(xinv).add(yinv).sub(zinv);
			final p bottomleft=p.n().sub(xinv).sub(yinv).sub(zinv);
			final p bottomright=p.n().add(xinv).sub(yinv).sub(zinv);
			//top
			pns[1]=pn.from3points(app.pos.clone(),topright,topleft);
			//left
			pns[2]=pn.from3points(app.pos.clone(),topleft,bottomleft);
			//bottom
			pns[3]=pn.from3points(app.pos.clone(),bottomleft,bottomright);
			//right
			pns[4]=pn.from3points(app.pos.clone(),bottomright,topright);
			
			//far			
			grid.updaterender(pns);
			Display.update();
		}
		box.thdpool.shutdown();
		if(!box.thdpool.awaitTermination(1,TimeUnit.SECONDS)){
			System.out.println("... timedout");
		}
		//? cleanupskippeddueto
	}
	public static float rnd(){return random.nextFloat();}
	public static float rnd(final float min,final float max){return min+(max-min)*random.nextFloat();}
	public static float rnd(final double min,final double max){return (float)(min+(max-min)*random.nextDouble());}
	static public Iterator<obj>q(){return grid.objs.iterator();}
	static public Iterator<obj>q(final Class<? extends obj>cls){
		final LinkedList<obj>ls=new LinkedList<obj>();
		for(final Iterator<obj>i=q();i.hasNext();){
			final obj o=i.next();
			if(o.getClass()==cls)
				ls.add(o);				
		}
		return ls.iterator();
	}
	
	// todo
	public static Iterator<obj>q(final pn[]cullplanes){return null;}
	public static void a(final pn[]cullplanes,final apply code){}
	public interface apply{void on(final obj o)throws Throwable;}
}