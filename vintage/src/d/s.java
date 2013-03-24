package d;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2f;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
public class s{
	public scene scene=new scene();
	public s() throws Throwable{
		final int dspwi=512,dsphi=512;
		Display.setDisplayMode(new DisplayMode(dspwi,dsphi));
		Display.create();
		Display.setResizable(false);
		final DisplayMode dm=Display.getDisplayMode();
		System.out.println("display "+dm.getWidth()+" x "+dm.getHeight()+" x "+dm.getBitsPerPixel()+" bpp @ "+dm.getFrequency()+" Hz");
		
		
		final int[]vboid=vbo.vbomksample();
		int frm=0;
		long t0=System.currentTimeMillis();
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();

			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0,dspwi,dsphi,0,1,-1);

			glColor4f(1,1,1,1);
//			glBegin(GL_TRIANGLES);
//		    glVertex3f(0,0,0);
//		    glVertex3f(100,100,0);
//		    glVertex3f(0,100,0);
//		    glEnd(); 
		
			vbo.vbodraw(vboid[0],vboid[1],3);
			
			scene.background.rgb=new float[]{0,.3f,0};
//			scene.toopengl();

//			glMatrixMode(GL_PROJECTION);
//			glLoadIdentity();
//			glOrtho(-dspwi>>1,dspwi>>1,dsphi,0,1,-1);
//
//			scene.background.rgb=new float[]{.3f,0,0};
//			scene.toopengl();

			Display.update();
			frm++;
			final long t1=System.currentTimeMillis();
			final long dt=t1-t0;
			if(dt>1000){
				System.out.print("fps "+(frm*1000/dt)+"\r");
				t0=t1;
				frm=0;
			}
		}
		Display.destroy();
	}
	public static void main(String[] a) throws Throwable{
		new s();
	}
}