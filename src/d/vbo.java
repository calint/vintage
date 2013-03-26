package d;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
abstract public class vbo{
	private int vao;// vertex array object
	private int vbo;// vertex buffer object
	private int vboi;// indices buffer object
	private int nindices;
	final public void load()throws Throwable{
		// load buffers
//		FloatBuffer verticesBuffer = ByteBuffer.allocateDirect(4*vertices.length*Vertex.elementCount).asFloatBuffer();
		final int nvertices=nvertices();
		final int stride=10;//xyzw,rgba,st
		final int sizeofnum=4;//sizeof(float)
		final int stridebytes=stride*sizeofnum;
		final FloatBuffer vb=BufferUtils.createFloatBuffer(nvertices*stride);
	
		vertices(vb);

		vb.flip();
		
		nindices=nindices();
		final ByteBuffer ib=BufferUtils.createByteBuffer(nindices);
		indices(ib);

		ib.flip();
		
		vao=glGenVertexArrays();
		glBindVertexArray(vao);
		
		vbo=glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glBufferData(GL_ARRAY_BUFFER,vb,GL_STATIC_DRAW);
		glVertexAttribPointer(0,4,GL_FLOAT,false,stridebytes,0);// positions
		glVertexAttribPointer(1,4,GL_FLOAT,false,stridebytes,4*sizeofnum);// colors, 16 bytes offset
		glVertexAttribPointer(2,2,GL_FLOAT,false,stridebytes,8*sizeofnum);// texture, 32 bytes offset
		glBindBuffer(GL_ARRAY_BUFFER,0);
		glBindVertexArray(0);

		vboi=glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboi);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,ib,GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
	}
	final public void render()throws Throwable{
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboi);
		glDrawElements(GL_TRIANGLES,nindices,GL_UNSIGNED_BYTE,0);
//			glDrawElements(GL_POINTS,nindices,GL_UNSIGNED_BYTE,0);
		
		//. groupedbymaterial,textureunit,drawelementsrange
	}
	
	
	abstract protected int nvertices();
	abstract protected void vertices(final FloatBuffer vb);
	abstract protected int nindices();
	abstract protected void indices(final ByteBuffer ib);
}