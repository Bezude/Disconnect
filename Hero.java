/*
 *  Hero.java
 *  ECS 163 Final
 *  Alan Tai and Benjamin Roye
 *
 */
import org.lwjgl.opengl.GL11;
import org.jbox2d.common.Vec2;

public class Hero extends Character {
	
	public Hero() {
		super();
		//TODO Link attributes, skills, inventory, animations, textures
	}

	@Override
	public void draw() {
		
		/*
		calculateRotation();
		
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glColor3d(0d, 1d, 0d);
		GL11.glRotatef(getRotation(1), 0, 1, 0);
		
		GL11.glBegin(GL11.GL_QUADS);	//Back
			GL11.glVertex3d(  0.25d,  0.25d, 0d );
			GL11.glVertex3d( -0.25d,  0.25d, 0d );
			GL11.glVertex3d( -0.25d, -0.25d, 0d );
			GL11.glVertex3d(  0.25d, -0.25d, 0d );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Top
			GL11.glVertex3d(  0.25d,  0.25d,  0d );
			GL11.glVertex3d(  0d,     0d,     -.5d );
			GL11.glVertex3d( -0.25d,  0.25d,  0d );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Left
			GL11.glVertex3d( -0.25d,  0.25d, 0d );
			GL11.glVertex3d(  0d,     0d,    -.5d );
			GL11.glVertex3d( -0.25d, -0.25d, 0d );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Bottom
			GL11.glVertex3d( -0.25d, -0.25d, 0d );
			GL11.glVertex3d(  0d,     0d,    -.5d );
			GL11.glVertex3d(  0.25d, -0.25d, 0d );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Right
			GL11.glVertex3d(  0.25d, -0.25d, 0d );
			GL11.glVertex3d(  0d,     0d,    -.5d );
			GL11.glVertex3d(  0.25d,  0.25d, 0d );
		GL11.glEnd();
		GL11.glPopMatrix();	// */	
	}
	
	public void drawPyramid(double lamda, double phi) {
		double a = .05;
		double b = -.05;
		double aa = .1;
		double bb = -.1;
		Vec2 v = Map.project(lamda, phi);
		System.out.println("X: "+v.x+", Y: "+v.y);
		GL11.glBegin(GL11.GL_QUADS);	//Back
			GL11.glVertex3d(  v.x+a,  a, v.y+0d );
			GL11.glVertex3d( v.x-a,  a, v.y+0d );
			GL11.glVertex3d( v.x-a, b, v.y+0d );
			GL11.glVertex3d(  v.x+a, b, v.y+0d );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Top
			GL11.glVertex3d(  v.x+a,  a,  v.y+0d );
			GL11.glVertex3d(  v.x+0d,     0d,     v.y-aa );
			GL11.glVertex3d( v.x-a,  a,  v.y+0d );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Left
			GL11.glVertex3d( v.x-a,  a, v.y+0d );
			GL11.glVertex3d(  v.x+0d,     0d,    v.y-aa );
			GL11.glVertex3d( v.x-a, b, v.y+0d );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Bottom
			GL11.glVertex3d( v.x-a, b, v.y+0d );
			GL11.glVertex3d(  v.x+0d,     0d,    v.y-aa );
			GL11.glVertex3d(  v.x+a, b, v.y+0d );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Right
			GL11.glVertex3d(  v.x+a, b, v.y+0d );
			GL11.glVertex3d(  v.x+0d,     0d,    v.y-aa );
			GL11.glVertex3d(  v.x+a,  a, v.y+0d );
		GL11.glEnd();
	}
}