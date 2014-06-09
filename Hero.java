/*
 *  Hero.java
 *  ECS 163 Final
 *  Alan Tai and Benjamin Roye
 *
 */
import org.lwjgl.opengl.GL11;
import org.jbox2d.common.Vec2;

public class Hero extends Character {
	
	double a = .05;
	double b = -.05;
	double aa = .1;
	double bb = -.1;
	
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
	
	public void drawPyramid(double lamda, double phi, boolean oscillate, long deltaTime) {
		double sec = (double)(deltaTime/10000000);
		float offset = (float)(Math.sin(sec));
		if(oscillate) {
			System.out.println(sec);
		}
		Vec2 v = Map.project(lamda, phi);
		//System.out.println("X: "+v.x+", Y: "+v.y);
		GL11.glBegin(GL11.GL_QUADS);	//Back
			GL11.glVertex3d(  v.x+a,  oscillate ? offset + a : a, v.y+0d );
			GL11.glVertex3d( v.x-a,  oscillate ? offset + a : a, v.y+0d );
			GL11.glVertex3d( v.x-a, oscillate ? offset + b : b, v.y+0d );
			GL11.glVertex3d(  v.x+a, oscillate ? offset + b : b, v.y+0d );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Top
			GL11.glVertex3d(  v.x+a,  oscillate ? offset + a : a,  v.y+0d );
			GL11.glVertex3d(  v.x+0d,     oscillate ? offset : 0d,     v.y-aa );
			GL11.glVertex3d( v.x-a,  oscillate ? offset + a : a,  v.y+0d );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Left
			GL11.glVertex3d( v.x-a,  oscillate ? offset + a : a, v.y+0d );
			GL11.glVertex3d(  v.x+0d,     oscillate ? offset : 0d,    v.y-aa );
			GL11.glVertex3d( v.x-a, oscillate ? offset + b : b, v.y+0d );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Bottom
			GL11.glVertex3d( v.x-a, oscillate ? offset + b : b, v.y+0d );
			GL11.glVertex3d(  v.x+0d,     oscillate ? offset : 0d,    v.y-aa );
			GL11.glVertex3d(  v.x+a, oscillate ? offset + b : b, v.y+0d );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES); // Right
			GL11.glVertex3d(  v.x+a, oscillate ? offset + b : b, v.y+0d );
			GL11.glVertex3d(  v.x+0d,     oscillate ? offset : 0d,    v.y-aa );
			GL11.glVertex3d(  v.x+a,  oscillate ? offset + a : a, v.y+0d );
		GL11.glEnd();
	}
	
public void drawCube(double lamda, double phi, boolean oscillate, long deltaTime) {
		double sec = (double)(deltaTime/10000000);
		float offset = (float)(Math.sin(sec));
		if(oscillate) {
			System.out.println(sec);
		}
		Vec2 v = Map.project(lamda, phi);
		//System.out.println("X: "+v.x+", Y: "+v.y);
		GL11.glBegin(GL11.GL_QUADS);	//Back
			GL11.glVertex3d(  v.x+a,  oscillate ? offset + a : a, v.y+b );
			GL11.glVertex3d( v.x-a,  oscillate ? offset + a : a, v.y+b );
			GL11.glVertex3d( v.x-a, oscillate ? offset + b : b, v.y+b );
			GL11.glVertex3d(  v.x+a, oscillate ? offset + b : b, v.y+b );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_QUADS);	//Front
			GL11.glVertex3d(  v.x+a,  oscillate ? offset + a : a, v.y+a );
			GL11.glVertex3d( v.x+a,  oscillate ? offset + b : b, v.y+a );
			GL11.glVertex3d( v.x-a, oscillate ? offset + b : b, v.y+a );
			GL11.glVertex3d(  v.x-a, oscillate ? offset + a : a, v.y+a );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_QUADS);	//Top
			GL11.glVertex3d(  v.x+a,  oscillate ? offset + a : a, v.y-a );
			GL11.glVertex3d( v.x+a,  oscillate ? offset + a : a, v.y+a );
			GL11.glVertex3d( v.x-a, oscillate ? offset + a : a, v.y+a );
			GL11.glVertex3d(  v.x-a, oscillate ? offset + a : a, v.y-a );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_QUADS);	//Bottom
			GL11.glVertex3d(  v.x+a,  oscillate ? offset + b : b, v.y-a );
			GL11.glVertex3d( v.x-a,  oscillate ? offset + b : b, v.y-a );
			GL11.glVertex3d( v.x-a, oscillate ? offset + b : b, v.y+a );
			GL11.glVertex3d(  v.x+a, oscillate ? offset + b : b, v.y+a );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_QUADS);	//Left
			GL11.glVertex3d(  v.x-a,  oscillate ? offset + a : a, v.y+a );
			GL11.glVertex3d( v.x-a,  oscillate ? offset + b : b, v.y+a );
			GL11.glVertex3d( v.x-a, oscillate ? offset + b : b, v.y-a );
			GL11.glVertex3d(  v.x-a, oscillate ? offset + a : a, v.y-a );
		GL11.glEnd();
		GL11.glBegin(GL11.GL_QUADS);	//Right
			GL11.glVertex3d(  v.x+a,  oscillate ? offset + b : b, v.y+a );
			GL11.glVertex3d( v.x+a,  oscillate ? offset + a : a, v.y+a );
			GL11.glVertex3d( v.x+a, oscillate ? offset + a : a, v.y-a );
			GL11.glVertex3d(  v.x+a, oscillate ? offset + b : b, v.y-a );
		GL11.glEnd();
	}
}