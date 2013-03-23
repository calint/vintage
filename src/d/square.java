package d;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
public class square extends glob{
	static final long serialVersionUID=1;
	public void toopengl()throws Throwable{
		final int dspwi=512,dsphi=512;
		glBegin(GL_QUADS);
		glColor3f(0,0,0);
		glVertex2f((dspwi>>2)+0,(dsphi>>1)+0);
		glVertex2f((dspwi>>2)+5,(dsphi>>1)+0);
		glVertex2f((dspwi>>2)+5,(dsphi>>1)+5);
		glVertex2f((dspwi>>2)+0,(dsphi>>1)+5);
		glEnd();
		super.toopengl();
	}
}
