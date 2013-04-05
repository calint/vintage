package d;
import static org.lwjgl.opengl.GL11.*;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		public/*readonly*/static int npointstransformed;
		public/*readonly*/static int nmmul;
		static void framereset(){niscol=noncols=ngrids=nobjcull=nobjrend=ngridcull=ngridrend=npointstransformed=nmmul=0;}
	}
	public static void main(final String[]a)throws Throwable{
		if(a.length>0)
			nplayers=Integer.parseInt(a[0]);
		load();
		loop();
	}
	public interface app{vbo[]vbos()throws Throwable;obj[]netobjs()throws Throwable;}
	private static obj app;
	public static String appcls="d.app2.app";//application object
	private static final Random random=new Random(0);
	private static int wi=512+256,hi=512+256;//,scrdst=wi;
	public/*readonly*/static long frm;//frame number
	public/*readonly*/static int fps;//frames per second
//	public/*readonly*/static int keys;//keys bits
	public/*readonly*/static long tms;//time in millis
	public/*readonly*/static long dtms;//last frame time in millis
	public/*readonly*/static float dt;//dtms in seconds
	public/*readonly*/static float netdt=.016f;//fixed dt in multiplayer	
	public static String net_server_port="9999";
	public static boolean gc_before_stats;
	static final int nthreads=2;
	static final ExecutorService thdpool=Executors.newFixedThreadPool(nthreads);
	
	public static boolean cullplanes=true;
	
	
	private static Socket sock;
	private static OutputStream os;
	private static InputStream is;
	private static final int protocol_msg_len=32;
	private static String playerid;
	private final static byte[]keys=new byte[protocol_msg_len];
	public static int nplayers=1;
	private static obj[]plrs;
	private static int playerix;
//	static final int net_packetsize_bytes=32;
	
	static private void mkplayerid(){
		final String chars="01234567890abcdef";
		final StringBuilder sb=new StringBuilder(net.protocol_msg_len);
		for(int n=0;n<net.protocol_msg_len;n++){
			int hx=(int)(Math.random()*16);
			sb.append(chars.charAt(hx));
		}
		playerid=sb.toString();
	}
	static void pl(final String s){System.out.println(s);}
	static private void load()throws Throwable{
		final long t0=System.currentTimeMillis();
		app=(obj)Class.forName(appcls).newInstance();
		grid.o.s=app.radius;
		plrs=((app)app).netobjs();
		final String host="localhost";
		if(nplayers>1){
			pl(nplayers+" players game");
			pl("connecting to "+host);
			sock=new Socket(host,Integer.parseInt(box.net_server_port));
			os=sock.getOutputStream();
			is=sock.getInputStream();
			mkplayerid();
			pl("connected, waiting for other players");
			os.write(playerid.getBytes());
			os.flush();
			for(int n=0;n<plrs.length;n++){
				final int c=is.read(plrs[n].keys());
				if(c==-1)
					throw new IOException("connection to server lost");
			}
			playerix=-1;
			for(int n=0;n<plrs.length;n++){
				final String pid=new String(plrs[n].keys());
				if(pid.equals(playerid)){
					playerix=n;
					break;
				}
			}
			for(int k=0;k<plrs.length;k++){
				final byte[]keys=plrs[k].keys();
				for(int n=0;n<keys.length;n++)
					keys[n]=0;
			}
		}

		
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
		System.out.println("        radius: "+app.radius);
		System.out.println("         scale: "+app.scl);
		System.out.println("          grid: radius="+grid.o.s+" levels="+grid.subgridlevels+" splitthresh="+grid.splitthresh);
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
//		Display.sync(60);
//		Display.setVSyncEnabled(true);
//		glPolygonMode(GL_FRONT_AND_BACK,GL_POINT);
		while(!Display.isCloseRequested()){
			final long t0n=System.nanoTime();
			frm++;
			frmi++;
			tms=System.currentTimeMillis();
			dtms=tms-t;
			t=tms;
			dt=(float)(dtms/1000.);
			dt=dtms/1000.f;
//			if(dt>netdt)
//				System.out.println(sdf.format(tms)+"  "+dt+"   "+(dt>netdt?"!":" "));
			if(nplayers>1){
				final float slp=netdt-dt;
				final long slpms=(long)(1000*slp);
				System.out.println("sleep "+slpms);
				if(slpms>1)
					Thread.sleep(slpms);
				dt=netdt;
			}
			if(tms-t0>1000){
				long tt=tms-t0;
				if(tt==0)tt++;
				fps=(int)(frmi*1000/tt);
				t0=tms;
				frmi=0;
				Display.setTitle("fps:"+fps+" n objs:"+mtrs.nobjs+",gc:"+mtrs.ngridcull+",gr:"+mtrs.ngridrend+",noc:"+mtrs.nobjcull+",or:"+mtrs.nobjrend+", ms grd:"+mtrs.ms_gridupd+",rend:"+mtrs.ms_render+",upd:"+mtrs.ms_update+",fsx:"+mtrs.ms_coldet+",niscol:"+mtrs.niscol+",noncols:"+mtrs.noncols+",ngrids:"+(grid.ngrids+1));
			}
			mtrs.framereset();
			
			System.arraycopy(keys,0,plrs[playerix].keys(),0,keys.length);
			if(nplayers>1)try{
				os.write(plrs[playerix].keys());
			}catch(IOException e){throw new Error(e);}
							
			// cullplanes
			final pn[]pns=new pn[5];
			// view (instead of mxwv^-1 cause orthonorm
			final p xinv=mxwv.axisxinv().scl(1.5f);
			final p yinv=mxwv.axisyinv().scl(1.5f);
			final p zinv=mxwv.axiszinv();
//			final p xinv=mxwv.axisxinv().scl(wi*.8f);
//			final p yinv=mxwv.axisyinv().scl(hi*.8f);
//			final p zinv=mxwv.axiszinv().scl(scrdst);
			//back
			final pn pnback=pn.frompointandnormal(app.pos.clone(),zinv);
			pns[0]=pnback;
			//viewpyr
			final p topright=p.n().add(xinv).add(yinv).sub(zinv);
			final p topleft=p.n().sub(xinv).add(yinv).sub(zinv);
			final p bottomleft=p.n().sub(xinv).sub(yinv).sub(zinv);
			final p bottomright=p.n().add(xinv).sub(yinv).sub(zinv);
			pns[1]=pn.from3points(app.pos.clone(),topright,topleft);//top
			pns[2]=pn.from3points(app.pos.clone(),topleft,bottomleft);//left
			pns[3]=pn.from3points(app.pos.clone(),bottomleft,bottomright);//bottom
			pns[4]=pn.from3points(app.pos.clone(),bottomright,topright);//right
			//far			
			//
			wi=Display.getWidth();
			hi=Display.getHeight();
//			System.out.println(wi+"x"+hi);
			final float wihiratio=(float)wi/hi;
			glViewport(0,0,wi,hi);
			glClear(GL_COLOR_BUFFER_BIT+GL_DEPTH_BUFFER_BIT);
			mxwv.ident();
			mxwv.setsclagltrans(p.n(1,wihiratio,1),app.agl.clone().neg(),app.pos.clone().neg());
			glUniformMatrix4(shader.umxwv,true,mxwv.tobb());
			
			if(Keyboard.isKeyDown(Keyboard.KEY_F2))glUniform1i(shader.udopersp,1);
			if(Keyboard.isKeyDown(Keyboard.KEY_F3))glUniform1i(shader.udopersp,0);
			if(Keyboard.isKeyDown(Keyboard.KEY_F4))glUniform1i(shader.urendzbuf,1);
			if(Keyboard.isKeyDown(Keyboard.KEY_F5))glUniform1i(shader.urendzbuf,0);
			if(Keyboard.isKeyDown(Keyboard.KEY_F6))cullplanes=false;
			if(Keyboard.isKeyDown(Keyboard.KEY_F7))cullplanes=true;
			
			if(cullplanes)
				grid.updaterender(pns);
			else
				grid.updaterender(new pn[0]);
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))break;

			
			if(nplayers>1){
				for(int n=0;n<plrs.length;n++){
					try{
						final int c=is.read(plrs[n].keys());
						if(c!=box.protocol_msg_len)
							throw new IOException("expected "+box.protocol_msg_len+" B. received:"+c);
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}else
				System.arraycopy(keys,0,plrs[0].keys(),0,keys.length);
			
//			final obj plr=plrs[playerix];
			for(int i=0;i<keys.length;i++)
				keys[i]=0;
			if(Keyboard.isKeyDown(Keyboard.KEY_W))keys[0]|=1;
			if(Keyboard.isKeyDown(Keyboard.KEY_A))keys[1]|=1;
			if(Keyboard.isKeyDown(Keyboard.KEY_S))keys[2]|=1;
			if(Keyboard.isKeyDown(Keyboard.KEY_D))keys[3]|=1;
			if(Keyboard.isKeyDown(Keyboard.KEY_J))keys[4]|=1;
			if(Keyboard.isKeyDown(Keyboard.KEY_K))keys[5]|=1;
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))keys[6]|=1;
			if(Keyboard.isKeyDown(Keyboard.KEY_Q))keys[7]|=1;
			if(Keyboard.isKeyDown(Keyboard.KEY_E))keys[8]|=1;			

			final long t1n=System.nanoTime();			
			Display.update();
//			Display.swapBuffers();
			final long t2n=System.nanoTime();
//			if(t2-t1>10000000)
//			System.out.println(sdf.format(new Date(t1n/1000000))+"     Display.update:"+(t2n-t1n)/1000+" us  swapbuffers:"+Display.ns_swapbuffers+"  rest:"+(t1n-t0n)/1000+" us");
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
	public static Iterator<obj>q(final vol vol){return null;}
	public static void a(final vol vol,final apply code){}
	public interface apply{void on(final obj o)throws Throwable;}
}