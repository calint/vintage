package d;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
final class shader{
	// uniform variable locations
	static int umxproj;// projection matrix
	static int umxmw;// model-world matrix
	static int utx;// texture
	static int upos;// camera position
	static int uscl;//xyz current object scale
	static int us;//xy projection scale x,y
	static public void load()throws Throwable{
		int errorCheckValue=glGetError();
		final int p=glCreateProgram();
		final int vs=loadshader("vs",GL_VERTEX_SHADER);
		final int fs=loadshader("fs",GL_FRAGMENT_SHADER);
		glAttachShader(p,vs);
		glAttachShader(p,fs);
		glLinkProgram(p);
		umxproj=glGetUniformLocation(p,"umxproj");
		if(umxproj==-1)throw new Error("could not getuniformlocation umxproj");
		umxmw=glGetUniformLocation(p,"umxmw");
		if(umxmw==-1)throw new Error("could not getuniformlocation umxmw");
		utx=glGetUniformLocation(p,"utx");
		if(utx==-1)throw new Error("could not getuniformlocation utx");
		upos=glGetUniformLocation(p,"upos");
		if(upos==-1)throw new Error("could not getuniformlocation upos");
		uscl=glGetUniformLocation(p,"uscl");
		if(uscl==-1)throw new Error("could not getuniformlocation uscl");
		us=glGetUniformLocation(p,"us");
		if(us==-1)throw new Error("could not getuniformlocation us");
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
