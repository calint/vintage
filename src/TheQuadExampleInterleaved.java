import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.PixelFormat;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
class Vertex {
	// Vertex data
	private float[] xyzw = new float[] {0f, 0f, 0f, 1f};
	private float[] rgba = new float[] {1f, 1f, 1f, 1f};
	
	// The amount of elements that a vertex has
	public static final int elementCount = 8;	
	// The amount of bytes an element has
	public static final int elementBytes = 4;
	// The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
	public static final int sizeInBytes = elementBytes * elementCount;
	
	// Setters
	public void setXYZ(float x, float y, float z) {
		this.setXYZW(x, y, z, 1f);
	}
	
	public void setRGB(float r, float g, float b) {
		this.setRGBA(r, g, b, 1f);
	}
	
	public void setXYZW(float x, float y, float z, float w) {
		this.xyzw = new float[] {x, y, z, w};
	}
	
	public void setRGBA(float r, float g, float b, float a) {
		this.rgba = new float[] {r, g, b, 1f};
	}
	
	// Getters	
	public float[] getXYZW() {
		return new float[] {this.xyzw[0], this.xyzw[1], this.xyzw[2], this.xyzw[3]};
	}
	
	public float[] getRGBA() {
		return new float[] {this.rgba[0], this.rgba[1], this.rgba[2], this.rgba[3]};
	}
}
public class TheQuadExampleInterleaved {
	// Entry point for the application
	public static void main(String[] args) {
		new TheQuadExampleInterleaved();
	}
	
	// Setup variables
	private final String WINDOW_TITLE = "The Quad: Interleaved";
	private final int WIDTH = 320;
	private final int HEIGHT = 240;
	// Quad variables
	private int vaoId = 0;
	private int vboId = 0;
	private int vboiId = 0;
	private int indicesCount = 0;
	// Shader variables
	private int vsId = 0;
	private int fsId = 0;
	private int pId = 0;
	
	public TheQuadExampleInterleaved() {
		// Initialize OpenGL (Display)
		this.setupOpenGL();
		
		this.setupQuad();
		this.setupShaders();
		
		while (!Display.isCloseRequested()) {
			// Do a single loop (logic/render)
			this.loopCycle();
			
			// Force a maximum FPS of about 60
			Display.sync(60);
			// Let the CPU synchronize with the GPU if GPU is tagging behind
			Display.update();
		}
		
		// Destroy OpenGL (Display)
		this.destroyOpenGL();
	}

	public void setupOpenGL() {
		// Setup an OpenGL context with API version 3.2
		try {
			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
				.withForwardCompatible(true)
				.withProfileCore(true);
			
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle(WINDOW_TITLE);
			Display.create(pixelFormat, contextAtrributes);
//			Display.create(pixelFormat, (ContextAttribs)null);
			
			GL11.glViewport(0, 0, WIDTH, HEIGHT);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Setup an XNA like background color
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
		
		// Map the internal OpenGL coordinate system to the entire screen
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}
	
	public void setupQuad() {
		// We'll define our quad using 4 vertices of the custom 'Vertex' class
		Vertex v0 = new Vertex(); v0.setXYZ(-0.5f, 0.5f, 0f); v0.setRGB(1, 0, 0);
		Vertex v1 = new Vertex(); v1.setXYZ(-0.5f, -0.5f, 0f); v1.setRGB(0, 1, 0);
		Vertex v2 = new Vertex(); v2.setXYZ(0.5f, -0.5f, 0f); v2.setRGB(0, 0, 1);
		Vertex v3 = new Vertex(); v3.setXYZ(0.5f, 0.5f, 0f); v3.setRGB(1, 1, 1);
		
		Vertex[] vertices = new Vertex[] {v0, v1, v2, v3};
		// Put each 'Vertex' in one FloatBuffer
//		FloatBuffer verticesBuffer = ByteBuffer.allocateDirect(4*vertices.length*Vertex.elementCount).asFloatBuffer();
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length*Vertex.elementCount);
		for (int i=0;i<vertices.length;i++){
			verticesBuffer.put(vertices[i].getXYZW());
			verticesBuffer.put(vertices[i].getRGBA());
		}
		verticesBuffer.flip();
		
		// OpenGL expects to draw vertices in counter clockwise order by default
		final byte[]indices={0,1,2, 2,3,0};
		indicesCount=indices.length;
		final ByteBuffer indicesBuffer=ByteBuffer.allocateDirect(indicesCount);
//		BufferUtils.createByteBuffer(indicesCount);
		indicesBuffer.put(indices);
		indicesBuffer.flip();
		
		vaoId=glGenVertexArrays();
		glBindVertexArray(vaoId);
		
		vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,vboId);
		glBufferData(GL_ARRAY_BUFFER,verticesBuffer,GL_STATIC_DRAW);
		// positions
		glVertexAttribPointer(0,4,GL_FLOAT,false,Vertex.sizeInBytes,0);
		// colors
		glVertexAttribPointer(1,4,GL_FLOAT,false,Vertex.sizeInBytes,Vertex.elementBytes*4);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);

		// load
		vboiId=glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboiId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,indicesBuffer,GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
	}
	
	private void setupShaders() {
		int errorCheckValue=glGetError();
		vsId=loadShader("vertex.glsl",GL_VERTEX_SHADER);
		fsId=loadShader("fragment.glsl",GL_FRAGMENT_SHADER);
		pId=GL20.glCreateProgram();
		GL20.glAttachShader(pId,vsId);
		GL20.glAttachShader(pId,fsId);
		GL20.glLinkProgram(pId);
		GL20.glBindAttribLocation(pId,0,"in_Position");
		GL20.glBindAttribLocation(pId,1,"in_Color");
		GL20.glValidateProgram(pId);
		errorCheckValue=glGetError();
		if (errorCheckValue!=GL_NO_ERROR)
			throw new Error("ERROR - Could not create the shaders:"+errorCheckValue);
	}
	
	public void loopCycle() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		GL20.glUseProgram(pId);
		
		// Bind to the VAO that has all the information about the vertices
		GL30.glBindVertexArray(vaoId);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		// Bind to the index VBO that has all the information about the order of the vertices
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
		
//		GL11.glColor3b((byte)0,(byte)0,(byte)0);
		// Draw the vertices
		GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);
		
		// Put everything back to default (deselect)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL20.glUseProgram(0);
	}
	
	public void destroyOpenGL() {
		// Delete the shaders
		GL20.glUseProgram(0);
		GL20.glDetachShader(pId, vsId);
		GL20.glDetachShader(pId, fsId);
		
		GL20.glDeleteShader(vsId);
		GL20.glDeleteShader(fsId);
		GL20.glDeleteProgram(pId);
		
		// Select the VAO
		GL30.glBindVertexArray(vaoId);
		
		// Disable the VBO index from the VAO attributes list
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		
		// Delete the vertex VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboId);
		
		// Delete the index VBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboiId);
		
		// Delete the VAO
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vaoId);
		
		Display.destroy();
	}
	
	public int loadShader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		int shaderID = 0;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);

		if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}
		
		return shaderID;
	}
}