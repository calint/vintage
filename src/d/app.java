package d;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collection;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;
public class app{
	public static void main(final String[]a)throws Throwable{new app();}
	public static String defclsnm="d.file.def";
	public interface def{
		void addvbos(final Collection<vbo>col);
		void addobjs(final Collection<obj>col);
	}
	private final int wi=512;
	private final int hi=512;
	private int fps;
	public final int nvbos=128;
	public final int nobjs=128;
	private final Collection<vbo>vbos=new ArrayList<vbo>(nvbos);
	private final Collection<obj>objs=new ArrayList<obj>(nobjs);

	public app()throws Throwable{
		load();
		loop();		
	}
	private int umxproj;
	public void load()throws Throwable{
		final def def=(def)Class.forName(defclsnm).newInstance();
		def.addvbos(vbos);
		def.addobjs(objs);
		// display
		final PixelFormat pixelFormat=new PixelFormat();
		final ContextAttribs contextAtrributes=new ContextAttribs(3,2).withProfileCore(true);
		Display.setDisplayMode(new DisplayMode(wi,hi));
		Display.create(pixelFormat,contextAtrributes);
		
//		Display.setDisplayMode(new DisplayMode(wi,hi));
//		Display.create();
//		Display.setResizable(false);
		
		System.out.println(Display.getAdapter());
		System.out.println(Display.getVersion());
		System.out.println(Sys.getVersion());

		
		// program
		int errorCheckValue=glGetError();
		final int p=glCreateProgram();
		final int vs=loadshader("vs",GL_VERTEX_SHADER);
		final int fs=loadshader("fs",GL_FRAGMENT_SHADER);
		glAttachShader(p,vs);
		glAttachShader(p,fs);
		glLinkProgram(p);
		umxproj=GL20.glGetUniformLocation(p,"umxproj");
		if(umxproj==-1)
			throw new Error("could not link umxproj");
		glBindAttribLocation(p,0,"in_Position");
		glBindAttribLocation(p,1,"in_Color");
		glValidateProgram(p);
		errorCheckValue=glGetError();
		if (errorCheckValue!=GL_NO_ERROR)
			throw new Error("could not load program: "+errorCheckValue);
		glUseProgram(p);

		// vbos
		for(final vbo o:vbos)
			o.load();
		
	}
	final public static class mtx{
		public final FloatBuffer fb=BufferUtils.createFloatBuffer(16);
		public void ident(){
			fb.rewind();
			fb.put(1).put(0).put(0).put(0);
			fb.put(0).put(1).put(0).put(0);
			fb.put(0).put(0).put(1).put(0);
			fb.put(0).put(0).put(0).put(1);
			fb.flip();
		}
	}
	final public static class mtxstk{
		public mtxstk pushmul(final mtx m){return this;}
		public mtxstk pop(){return this;}
		public mtx get(){return null;}
	}
	public void loop()throws Throwable{
		// viewport
		glViewport(0,0,wi,hi);
		glClearColor(.4f,.6f,.9f,0);
		// loop
		long t0=System.currentTimeMillis();
		int frm=0;
		final mtx mtxproj=new mtx();
		float sx=1;
		while(!Display.isCloseRequested()){
			frm++;
			glClear(GL_COLOR_BUFFER_BIT);

			mtxproj.ident();
			
			mtxproj.fb.put(0,sx);
			GL20.glUniformMatrix4(umxproj,false,mtxproj.fb);
			
			for(final obj o:objs)
				o.render();
			
			final long t1=System.currentTimeMillis();
			final long dtms=t1-t0;
			if(dtms>1000){
				fps=(int)(frm*1000/dtms);
				t0=t1;
				frm=0;
				Display.setTitle("fps: "+fps);
			}
		
			Display.sync(1000);
			Display.update();
		}
		//? cleanupskippeddueto
		Display.destroy();
	}
	private static int loadshader(final String filename,final int type)throws Throwable{
		final StringBuilder src=new StringBuilder();
		final InputStream srcis=vbo.class.getResourceAsStream(filename);
		final BufferedReader r=new BufferedReader(new InputStreamReader(srcis));
		for(String line;(line=r.readLine())!=null;)
			src.append(line).append("\n");
		r.close();
		
		final int shdr=glCreateShader(type);
		glShaderSource(shdr,src);
		glCompileShader(shdr);

		if(glGetShaderi(shdr,GL_COMPILE_STATUS)==GL_FALSE)
			throw new Error("could not compile "+filename);

		return shdr;
	}
}