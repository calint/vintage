package d;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
//final class vertex{
//	private float[]xyzw=new float[]{0,0,0,1};
//	private float[]rgba=new float[]{1,1,1,1};
//	public static final int elementCount=8;	
//	public static final int elementBytes=4;
//	public static final int sizeInBytes=elementBytes*elementCount;
//	public vertex xyz(final float x,final float y,final float z){return xyzw(x,y,z,1);}
//	public vertex rgb(final float r,final float g,final float b){return rgba(r,g,b,1);}
//	public vertex xyzw(final float x,final float y,final float z,final float w){
//		xyzw[0]=x;xyzw[1]=y;xyzw[2]=z;xyzw[3]=w;
//		return this;
//	}
//	public vertex rgba(final float r,final float g,final float b,final float a){
//		rgba[0]=r;rgba[1]=g;rgba[2]=b;rgba[3]=a;
//		return this;
//	}
//	public float[]xyzw(){return xyzw;}
//	public float[]rgba(){return rgba;}
//}
public class vboexample{
	public static void main(final String[]a)throws Throwable{new vboexample();}
	
	private final String title="vbo";
	private final int wi=512;
	private final int hi=512;
	private int vao;// vertex array object
	private int vbo;// vertex buffer object
	private int vboi;// indices buffer object
	private int nindices;
	private FloatBuffer vb;//vertex buffer
	private ByteBuffer ib;//indices buffer
	private int fps;
	
	public vboexample()throws Throwable{
		final PixelFormat pixelFormat=new PixelFormat();
		final ContextAttribs contextAtrributes=new ContextAttribs(3,2)
				.withForwardCompatible(true)
				.withProfileCore(true);
		Display.setDisplayMode(new DisplayMode(wi,hi));
		Display.setTitle(title);
		Display.create(pixelFormat,contextAtrributes);

		glViewport(0,0,wi,hi);
		glClearColor(.4f,.6f,.9f,0);
		
		// program
		int errorCheckValue=glGetError();
		final int p=glCreateProgram();
		final int vs=loadshader("vertex.glsl",GL_VERTEX_SHADER);
		final int fs=loadshader("fragment.glsl",GL_FRAGMENT_SHADER);
		glAttachShader(p,vs);
		glAttachShader(p,fs);
		glLinkProgram(p);
		glBindAttribLocation(p,0,"in_Position");
		glBindAttribLocation(p,1,"in_Color");
		glValidateProgram(p);
		errorCheckValue=glGetError();
		if (errorCheckValue!=GL_NO_ERROR)
			throw new Error("could not load program: "+errorCheckValue);
		
		// load buffers
//		FloatBuffer verticesBuffer = ByteBuffer.allocateDirect(4*vertices.length*Vertex.elementCount).asFloatBuffer();
		final int nvertices=4;
		final int stride=8;
		final int sizeofnum=4;
		final int stridebytes=stride*sizeofnum;
		vb=BufferUtils.createFloatBuffer(nvertices*stride);
	
		vb.put(-.5f).put( .5f).put(0).put(1);//xyzw
		vb.put(1).put(0).put(0).put(1);//rgba

		vb.put(-.5f).put(-.5f).put(0).put(1);//xyzw
		vb.put(0).put(1).put(0).put(1);//rgba

		vb.put( .5f).put(-.5f).put(0).put(1);//xyzw
		vb.put(0).put(0).put(1).put(1);//rgba

		vb.put( .5f).put( .5f).put(0).put(1);//xyzw
		vb.put(1).put(1).put(1).put(1);//rgba

		vb.flip();
		
		nindices=6;
		ib=BufferUtils.createByteBuffer(nindices);
		ib.put((byte)0).put((byte)1).put((byte)2);//tri
		ib.put((byte)2).put((byte)3).put((byte)0);//tri

		ib.flip();
		
		vao=glGenVertexArrays();
		glBindVertexArray(vao);
		
		vbo=glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glBufferData(GL_ARRAY_BUFFER,vb,GL_STATIC_DRAW);
		glVertexAttribPointer(0,4,GL_FLOAT,false,stridebytes,0);// positions
		glVertexAttribPointer(1,4,GL_FLOAT,false,stridebytes,4*sizeofnum);// colors, 16 bytes offset
		glBindBuffer(GL_ARRAY_BUFFER,0);
		glBindVertexArray(0);

		// load
		vboi=glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboi);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,ib,GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		
		// loop
		long t0=System.currentTimeMillis();
		int frm=0;
		while(!Display.isCloseRequested()){
			frm++;
			glClear(GL_COLOR_BUFFER_BIT);
			glUseProgram(p);
			glBindVertexArray(vao);
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboi);
			glDrawElements(GL_TRIANGLES,nindices,GL_UNSIGNED_BYTE,0);
			
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glBindVertexArray(0);
			glUseProgram(0);
			
			final long t1=System.currentTimeMillis();
			final long dt=t1-t0;
			if(dt>1000){
				fps=(int)(frm*1000/dt);
				t0=t1;
				frm=0;
				System.out.println("fps: "+fps);
			}
			
			Display.sync(1000);
			Display.update();
		}
		
		// cleanup
		glUseProgram(0);
		glDetachShader(p,vs);
		glDetachShader(p,fs);
		glDeleteShader(vs);
		glDeleteShader(fs);
		glDeleteProgram(p);
		
		glBindVertexArray(vao);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER,0);
		glDeleteBuffers(vbo);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		glDeleteBuffers(vboi);
		
		glBindVertexArray(0);
		glDeleteVertexArrays(vao);
		
		Display.destroy();
	}

	public int loadshader(final String filename,final int type)throws Throwable{
		final StringBuilder src=new StringBuilder();
		final BufferedReader r=new BufferedReader(new FileReader(filename));
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