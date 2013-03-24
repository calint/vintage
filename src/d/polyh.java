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
public class polyh{
	private int vao;// vertex array object
	private int vbo;// vertex buffer object
	private int vboi;// indices buffer object
	private int nindices;
	final public void load()throws Throwable{
		// load buffers
//		FloatBuffer verticesBuffer = ByteBuffer.allocateDirect(4*vertices.length*Vertex.elementCount).asFloatBuffer();
		final int nvertices=nvertices();
		final int stride=8;
		final int sizeofnum=4;
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
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboi);
		glDrawElements(GL_TRIANGLES,nindices,GL_UNSIGNED_BYTE,0);
//			glDrawElements(GL_POINTS,nindices,GL_UNSIGNED_BYTE,0);
	}
	
	
	protected int nvertices(){return 4;}
	protected void vertices(final FloatBuffer vb){
		vb.put(-.5f).put( .5f).put(0).put(1);//xyzw
		vb.put(1).put(0).put(0).put(1);//rgba

		vb.put(-.5f).put(-.5f).put(0).put(1);//xyzw
		vb.put(0).put(1).put(0).put(1);//rgba

		vb.put( .5f).put(-.5f).put(0).put(1);//xyzw
		vb.put(0).put(0).put(1).put(1);//rgba

		vb.put( .5f).put( .5f).put(0).put(1);//xyzw
		vb.put(1).put(1).put(1).put(1);//rgba
	}
	protected int nindices(){return 6;}
	protected void indices(final ByteBuffer ib){
		ib.put((byte)0).put((byte)1).put((byte)2);//tri
		ib.put((byte)2).put((byte)3).put((byte)0);//tri
	}
}