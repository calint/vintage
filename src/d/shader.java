package d;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

final public class shader{
	// uniform variable locations
	static int umxproj;
	static int umxmw;
	static int utx;
	static public void load()throws Throwable{
		int errorCheckValue=glGetError();
		final int p=glCreateProgram();
		final int vs=loadshader("vs",GL_VERTEX_SHADER);
		final int fs=loadshader("fs",GL_FRAGMENT_SHADER);
		glAttachShader(p,vs);
		glAttachShader(p,fs);
		glLinkProgram(p);
		shader.umxproj=glGetUniformLocation(p,"umxproj");
		if(shader.umxproj==-1)throw new Error("could not getuniformlocation umxproj");
		shader.umxmw=glGetUniformLocation(p,"umxmw");
		if(shader.umxmw==-1)throw new Error("could not getuniformlocation umxmw");
		shader.utx=glGetUniformLocation(p,"utx");
		if(shader.utx==-1)throw new Error("could not getuniformlocation utx");
		glBindAttribLocation(p,0,"in_Position");
		glBindAttribLocation(p,1,"in_Color");
		glBindAttribLocation(p,2,"in_TextureCoord");
		glValidateProgram(p);
		errorCheckValue=glGetError();
		if (errorCheckValue!=GL_NO_ERROR)
			throw new Error("could not load program: "+errorCheckValue);
		glUseProgram(p);
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
