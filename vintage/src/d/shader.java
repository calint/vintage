package d;
import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL12.*;
//import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
final class shader{
	// uniform variable locations
	static int umxwv;// world to view matrix
	static int umxmw;// model to world matrix
	static int utx;// texture sampler
	
	static public void load()throws Throwable{
		if(glGetError()!=GL_NO_ERROR)throw new Error("opengl in error state");
		final int p=glCreateProgram();
		loadshader(p,GL_GEOMETRY_SHADER,"shader.g");
		loadshader(p,GL_VERTEX_SHADER,"shader.v");
		loadshader(p,GL_FRAGMENT_SHADER,"shader.f");
		glLinkProgram(p);
		if(glGetProgrami(p,GL_LINK_STATUS)==GL_FALSE)throw new Error("could not link due to: "+glGetProgramInfoLog(p,255));
		if((umxwv=glGetUniformLocation(p,"umxwv"))==-1)throw new Error();
		if((umxmw=glGetUniformLocation(p,"umxmw"))==-1)throw new Error();
		if((utx=glGetUniformLocation(p,"utx"))==-1)throw new Error();
		glBindAttribLocation(p,0,"in_Position");
		glBindAttribLocation(p,1,"in_Color");
		glBindAttribLocation(p,2,"in_TextureCoord");
		glValidateProgram(p);
		glUseProgram(p);
		if(glGetError()!=GL_NO_ERROR)throw new Error("could not load shader program");
	}
	private static void loadshader(final int p,final int type,final String path)throws Throwable{
		final StringBuilder src=new StringBuilder();
		final InputStream srcis=vbo.class.getResourceAsStream(path);
		final BufferedReader r=new BufferedReader(new InputStreamReader(srcis));
		for(String line;(line=r.readLine())!=null;)
			src.append(line).append("\n");
		r.close();
		final int shdr=glCreateShader(type);
		glShaderSource(shdr,src);
		glCompileShader(shdr);
		if(glGetShaderi(shdr,GL_COMPILE_STATUS)==GL_FALSE)throw new Error("could not compile "+path+" due to: "+glGetShaderInfoLog(shdr,255));
		glAttachShader(p,shdr);
		if(glGetError()!=GL_NO_ERROR)throw new Error();
	}
}
