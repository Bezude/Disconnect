/*
 *  GameWorld.java
 *  ECS 163 Final
 *  Alan Tai and Benjamin Roye
 *
 */
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.*;

public class GameWorld {
	
	private int width = 1400;
	private int height = 768;
	private float diagonal = .70710678f;
	
	private float zNear = 0.1f;
	private float zFar = 1000f;
	private float frustAngle = 60f;
	
	private Camera camera;
	private Hero hero;
	private Map map;
	float mouse[];
	
	private Timer timer;
	private long frameDelta;
	private long FRAME_LENGTH_MINIMUM = 10000000;
	
	//--------------------- Memory setup ----------------------------//
	private static IntBuffer viewport;
	private static FloatBuffer modelview;
	private static FloatBuffer projection;
	
	private static FloatBuffer positionNear;
	private static FloatBuffer positionFar;
	

	static float pos[];
	static float r[];
	//---------------------------------------------------------------//
	
	// Method to begin setup
	public void start() throws InterruptedException {
		
		viewport = BufferUtils.createIntBuffer(16);
		modelview = BufferUtils.createFloatBuffer(16);
		projection = BufferUtils.createFloatBuffer(16);
		
		positionNear = BufferUtils.createFloatBuffer(3);
		positionFar = BufferUtils.createFloatBuffer(3);
		

		pos = new float[3];
		r = new float[3];
		
		camera = new Camera();
		camera.setPosition(8f, 10f, 5f);
		camera.setTarget(8f, 0f, 5f);
		camera.setUp(0f, 0f, -1f);
		
		mouse = new float[3];
		
		hero = new Hero();
		hero.setPositionArray(0f, 0f, 0f);
		
		init();
		timer = new Timer(); //The Timer constructor establishes an origin time.
		
		/* isCloseRequested comes in the form of the X on a window being pushed,
		   a CTRL-C keystroke, etc. Continue updating until this happens. update()
		   swaps the buffers.
		 */
		
		while (!Display.isCloseRequested()) {
			
			frameDelta = timer.getNanoDelta(); //This value will get passed to all the updates that are time dependent.
			if(frameDelta < FRAME_LENGTH_MINIMUM) { //If very little time has passed since the last update, yield the cpu
				Thread.sleep(10);
			}else{
				pollMouse();
				pollKeyboard();
				updateMap();
				drawEntities();
				Display.update();
			}
		
		}
		
		Display.destroy();
	}
	
	// Method to initialize the display
	public void init() {
	
		//TODO: lighting, shading, fog, particles
		
		try {
			Display.setDisplayMode( new DisplayMode( width, height ) );
			Display.create();
			
		} catch (LWJGLException e) {
			
			e.printStackTrace();
			System.exit(1);
		}
		
		setGraphicsMode("3D");
		/*
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
		GLU.gluPerspective(frustAngle, (float)width/(float)height, zNear, zFar);
		GLU.gluLookAt(
				camera.getPosition(0),	camera.getPosition(1), 	camera.getPosition(2),
				camera.getTarget(0), 	camera.getTarget(1), 	camera.getTarget(2),
				camera.getUp(0), 		camera.getUp(1), 		camera.getUp(2));
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();// */
	}
	
	
	public void loadMap() {	
		// /Users/benroye/Documents/workspace/Disconnect
		map = new Map("usMapMod.svg");	
	}
	
	
	public void loadWorld() {
		//TODO: Read in the objects that make up the game world
	}	
	

	public void drawEntities() {
		hero.draw();
	}
	
	
	public void updateMap() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glPushMatrix();
			GL11.glLoadIdentity();
			GL11.glTranslatef(hero.getPosition(0), hero.getPosition(1), hero.getPosition(2));
			map.draw();
		GL11.glPopMatrix();
		
		setGraphicsMode("2D");
		// draw UI
		// example
		GL11.glBegin(GL11.GL_LINES);
		
		for (int x = -5; x <= 5; x++) {

			GL11.glVertex2d((double)x*100, -500d);
			GL11.glVertex2d((double)x*100, 500d);

		}
		
		for (int z = -5; z <= 5; z++) {
			
			GL11.glVertex2d(500d, (double)z*100);
			GL11.glVertex2d(-500d, (double)z*100);

		}
		GL11.glEnd();
		// end example
		setGraphicsMode("3D");
	}
	
	
	public void pollMouse() {
		//TODO: Implement camera angle changes with mouse wheel scroll
		
		//Updated regardless of mouse click
		mouse = getMousePosition(Mouse.getX(), Mouse.getY());
		hero.setTargetVect(mouse[0], mouse[1], mouse[2]);
		
		if (Mouse.isButtonDown(0)) { 		// Left click
			mouse = getMousePosition(Mouse.getX(), Mouse.getY());
			System.out.println(mouse[0] + ", " + mouse[1] + ", " + mouse[2]);
		//	hero.setTargetVect(mouse[0], mouse[1], mouse[2]);
		}
	}
	

	public void pollKeyboard() {
		
		//TODO: Implement jumping with the spacebar
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_A)) {
			hero.changePositionZ(hero.movementRate * diagonal);
			hero.changePositionX(hero.movementRate * diagonal);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
			hero.changePositionZ(hero.movementRate * diagonal);
			hero.changePositionX(-hero.movementRate * diagonal);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_A)) {
			hero.changePositionZ(-hero.movementRate * diagonal);
			hero.changePositionX(hero.movementRate * diagonal);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
			hero.changePositionZ(-hero.movementRate * diagonal);
			hero.changePositionX(-hero.movementRate * diagonal);
		}
		else {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				hero.changePositionZ(hero.movementRate);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				hero.changePositionX(hero.movementRate);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				hero.changePositionZ(-hero.movementRate);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				hero.changePositionX(-hero.movementRate);
			}
		}
	}
	

	static public float[] getMousePosition(int mouseX, int mouseY) {

		
		//--------------------- Save the view matrices ------------------//
		GL11.glGetFloat( GL11.GL_MODELVIEW_MATRIX, modelview );
		GL11.glGetFloat( GL11.GL_PROJECTION_MATRIX, projection );
		GL11.glGetInteger( GL11.GL_VIEWPORT, viewport );
		//---------------------------------------------------------------//
		
		/*
		 * The following logic:
		 * gluUnProject takes the mouse coordinates, the three view matrices, and an empty
		 * byte buffer to store the 3D world coordinate of the mouse click. By providing 0
		 * for the mouse click's z value, the position calculated is on the near z clipping
		 * plane. Likewise, providing a 1 for this value puts the 3D point on the far z
		 * clipping plane. These 3D coordinates are stored in positionNear and positionFar.
		 */
		GLU.gluUnProject((float)mouseX, (float)mouseY, 0, modelview, projection, viewport, positionNear);
		GLU.gluUnProject((float)mouseX, (float)mouseY, 1, modelview, projection, viewport, positionFar);
		
		/*
		 * The following logic:
		 * We now effectively have the beginning and end points of the ray that is produced
		 * by a mouse click. The direction vector is determined like so:
		 * 				r = <P2x, P2y, P2z> - <P1x, P1y, P1z>
		 * This vector is used in the parametric form for the equation of a line:
		 * 				x = P1x + mrx
		 * 				y = P1y + mry
		 * 				z = P1z + mrz
		 * We know that the "ground level" is the plane in which y = 0. Therefore if we fix
		 * this value, we can solve for m (m = (y - P1y) / ry). The newly acquired m value
		 * allows us to easily solve for the x and y positions on the y = 0 plane.
		 */

		float m;
		float fixedYPlane = 0;
		
		r[0] = positionFar.get(0) - positionNear.get(0);
		r[1] = positionFar.get(1) - positionNear.get(1);
		r[2] = positionFar.get(2) - positionNear.get(2);

		m = (fixedYPlane - positionNear.get(1)) / r[1];
		
		pos[0] = positionNear.get(0) + (m * r[0]);
		pos[1] = fixedYPlane;
		pos[2] = positionNear.get(2) + (m * r[2]);

		return pos;
	}
	
	private void setGraphicsMode(String string) {
		if (string.equals("2D")) {
			GL11.glDepthMask(false);
			GL11.glDisable(GL11.GL_DEPTH_TEST);								
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_TEXTURE_2D);	
			
			GL11.glLoadIdentity();
			GL11.glOrtho(0, width, height, 0, -1, 1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glShadeModel(GL11.GL_SMOOTH);
		}
		
		else if (string.equals("3D")) {
			GL11.glDepthMask(true);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			
			GLU.gluPerspective(frustAngle, (float)width/(float)height, zNear, zFar);
			GLU.gluLookAt(
					camera.getPosition(0),	camera.getPosition(1), 	camera.getPosition(2),
					camera.getTarget(0), 	camera.getTarget(1), 	camera.getTarget(2),
					camera.getUp(0), 		camera.getUp(1), 		camera.getUp(2));
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			
		}
		else
			try {
				throw new Exception("Invalid graphics mode. Mode must be 2D or 3D.");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
	}
}