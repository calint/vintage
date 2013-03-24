package d;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
public class app{
	public static void main(final String[]a)throws Throwable{new app();}	
	private final int wi=512;
	private final int hi=512;
	private int fps;
	private final vbo vbo=new vbo();
	public app()throws Throwable{
		final PixelFormat pixelFormat=new PixelFormat();
		final ContextAttribs contextAtrributes=new ContextAttribs(3,2)
				.withForwardCompatible(true)
				.withProfileCore(true);
		Display.setDisplayMode(new DisplayMode(wi,hi));
		Display.create(pixelFormat,contextAtrributes);

		vbo.load();
		
		glViewport(0,0,wi,hi);
		glClearColor(.4f,.6f,.9f,0);
		long t0=System.currentTimeMillis();
		int frm=0;
		while(!Display.isCloseRequested()){
			frm++;
			glClear(GL_COLOR_BUFFER_BIT);
			
			vbo.render();
						
			final long t1=System.currentTimeMillis();
			final long dt=t1-t0;
			if(dt>1000){
				fps=(int)(frm*1000/dt);
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
}