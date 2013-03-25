package d;
import static org.lwjgl.opengl.GL11.*;
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
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
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
	
	// uniform variable locations
	private int umxproj;
	private int utx;
	
	public app()throws Throwable{load();loop();}
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

		if(glGetError()!=GL_NO_ERROR)throw new Error("opengl in error state");
		
		// program
		int errorCheckValue=glGetError();
		final int p=glCreateProgram();
		final int vs=loadshader("vs",GL_VERTEX_SHADER);
		final int fs=loadshader("fs",GL_FRAGMENT_SHADER);
		glAttachShader(p,vs);
		glAttachShader(p,fs);
		glLinkProgram(p);
		umxproj=glGetUniformLocation(p,"umxproj");
		utx=glGetUniformLocation(p,"utx");
		if(umxproj==-1)
			throw new Error("could not getuniformlocation umxproj");
		glBindAttribLocation(p,0,"in_Position");
		glBindAttribLocation(p,1,"in_Color");
		glBindAttribLocation(p,2,"in_TextureCoord");
		glValidateProgram(p);
		errorCheckValue=glGetError();
		if (errorCheckValue!=GL_NO_ERROR)
			throw new Error("could not load program: "+errorCheckValue);
		glUseProgram(p);

		if(glGetError()!=GL_NO_ERROR)throw new Error("opengl in error state");

		// vbos
		for(final vbo o:vbos)
			o.load();

		if(glGetError()!=GL_NO_ERROR)throw new Error("opengl in error state");

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
		
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGB,txwi,txhi,0,GL_RGBA,GL_UNSIGNED_BYTE,txbuf);
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR_MIPMAP_LINEAR);
		if(glGetError()!=GL_NO_ERROR)throw new Error("could not load texture");
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
			glUniformMatrix4(umxproj,false,mtxproj.fb);
			glUniform1i(utx,0);
			
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

		if(glGetShaderi(shdr,GL_COMPILE_STATUS)==GL_FALSE){
			throw new Error("could not compile "+filename+" due to: "+glGetShaderInfoLog(shdr,255));
		}

		return shdr;
	}
}