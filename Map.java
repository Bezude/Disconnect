import org.lwjgl.opengl.GL11;

/*
 *  Math.java
 *  ECS 163 Final
 *  Alan Tai and Benjamin Roye
 *
 */

public class Map {

	public Map(String string) {
		// TODO Auto-generated constructor stub
		//string = map text file
	}

	public void draw() {
		
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3d(1.0, 0.0, 0.0);
		
		for (int x = -5; x <= 5; x++) {

			GL11.glVertex3d((double)x, 0d, -5d);
			GL11.glVertex3d((double)x, 0d, 5d);

		}
		
		for (int z = -5; z <= 5; z++) {
			
			GL11.glVertex3d(5d, 0d, (double)z);
			GL11.glVertex3d(-5d, 0d, (double)z);

		}
		
		GL11.glEnd();
	}
	
}