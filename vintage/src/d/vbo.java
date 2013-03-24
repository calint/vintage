package d;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GLContext;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

public class vbo{
	public static int vbocreate(){
		if(GLContext.getCapabilities().GL_ARB_vertex_buffer_object){
			IntBuffer buffer=BufferUtils.createIntBuffer(1);
			ARBVertexBufferObject.glGenBuffersARB(buffer);
			return buffer.get(0);
		}
		return 0;
	}
	public static void vbovertexload(final int bufid,final FloatBuffer buf){
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,bufid);
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,buf,ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
	}
	public static void vboindicesload(final int bufid,final IntBuffer buf){
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB,bufid);
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB,buf,ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
	}
	public static void vbodraw(final int vertexbufid,final int indexbufid,final int indicescount){
		glEnableClientState(GL_VERTEX_ARRAY);
//		glEnableClientState(GL_NORMAL_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
//		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,vertexbufid);
		int stride=(3+4)*4;
		int offset=0*4;
		glVertexPointer(3,GL_FLOAT,stride,offset);
		offset=3*4; // 3 components is the initial offset from 0, then convert to bytes
		glColorPointer(4,GL_FLOAT,stride,offset);
		
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB,indexbufid);
//		glDrawRangeElements(GL_TRIANGLES,0,end,nelems,GL_UNSIGNED_INT,0);
		glDrawElements(GL_TRIANGLES,indicescount,GL_INT,0);
	}
	public static int[]vbomksample(){
		final int vid=vbocreate();
		if(vid==0)throw new Error();
		final FloatBuffer fb=ByteBuffer.allocateDirect(12*3*4).asFloatBuffer();//12 floats, 3 times, 4 bytes/float
		fb.put(0).put(0).put(0); //vertex
//		fb.put(0).put(0).put(0); //normal
		fb.put(1).put(1).put(1).put(1); //color
//		fb.put(0).put(0); //tex

		fb.put(100).put(0).put(0); //vertex
//		fb.put(0).put(0).put(0); //normal
		fb.put(1).put(1).put(1).put(1); //color
//		fb.put(0).put(0); //tex

		fb.put(100).put(100).put(0); //vertex
//		fb.put(0).put(0).put(0); //normal
		fb.put(1).put(1).put(1).put(1); //color
//		fb.put(0).put(0); //tex
		
		fb.flip();
		vbovertexload(vid,fb);

		final int iid=vbocreate();
		final IntBuffer ib=ByteBuffer.allocateDirect(3*4).asIntBuffer();
		ib.put(0).put(1).put(2);
		
		ib.flip();
		vboindicesload(iid,ib);
		return new int[]{vid,iid};
	}
}
