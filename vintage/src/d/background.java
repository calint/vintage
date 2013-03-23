package d;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
public class background extends glob{
	static final long serialVersionUID=1;
	public float[]rgb;
	public void toopengl()throws Throwable{
		final int dspwi=512,dsphi=512;
		glBegin(GL_QUADS);
		glColor3f(rgb[0],rgb[1],rgb[2]);
		glVertex2f(0,0);
		glVertex2f(dspwi>>1,0);
		glVertex2f(dspwi>>1,dsphi);
		glVertex2f(0,dsphi);
		glEnd();
		super.toopengl();
	}
}
