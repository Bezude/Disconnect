import org.lwjgl.opengl.GL11;
import org.apache.batik.parser.PathParser;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.parser.ParseException;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import svg.parser.ExtractSVGPaths;

/*
 *  Math.java
 *  ECS 163 Final
 *  Alan Tai and Benjamin Roye
 *
 */

public class Map {
	
	private ArrayList<ArrayList<Vec2>> states;
	private ArrayList<ArrayList<Vec2>> latLines;

	public Map(String string) {
		
		states = ExtractSVGPaths.extract(string);
		latLines = new ArrayList<ArrayList<Vec2>>();

		for(int i = 0; i < states.size(); i++) {
			for(int j = 0; j < states.get(i).size(); j++) {
				Vec2 v = states.get(i).get(j);
				v.x /= 118f;
				v.y /= 118f;
				v.y += .05f;
			}
		}
		// lat 25 to 50; long -65 to -125
		for(int j = 0; j <= 5; j++) {
			double j0 = (double)(25 + 5*j);
			latLines.add(new ArrayList<Vec2>());
			for(double i = -65; i >= -125; i--) {
				latLines.get(j).add(project(i,j0));
			}
		}
		ArrayList<Vec2> lat49 = new ArrayList<Vec2>();
		lat49.add(project(-125,49));
		lat49.add(project(-65,49));
		latLines.add(lat49);
		
		// 32.5344 N, 117.1228 W Cali-Mexico corner
		// 48.9881 N, 122.7436 W WA-Canada corner
		// 47.3765 N, 68.3253 W ME-Canada corner
		// 25.1067 N, 80.4300 W Key Largo Florida
	}

	public void draw() {
		
		for(int j = 0; j < states.size(); j++) {
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glColor3d(1.0, 1.0, 1.0);
			for(int i = 0; i < states.get(j).size(); i++) {
				Vec2 v = states.get(j).get(i);
				//System.out.println("x: "+v.x+" | y: "+v.y);
				GL11.glVertex3d((double)v.x, 0d, (double)v.y);
			}
			GL11.glEnd();
		}
		/*
		for(int j = 0; j < latLines.size(); j++) {
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glColor3d(1.0, 0.0, 0.0);
			for(int i = 0; i < latLines.get(j).size(); i++) {
				Vec2 v = latLines.get(j).get(i);
				//System.out.println("x: "+v.x+" | y: "+v.y);
				GL11.glVertex3d((double)v.x, 0d, (double)v.y);
			}
			GL11.glEnd();
		} // */
		/*
		GL11.glBegin(GL11.GL_LINES);
		
		for (int x = -5; x <= 5; x++) {

			GL11.glVertex3d((double)x, 0d, -5d);
			GL11.glVertex3d((double)x, 0d, 5d);

		}
		
		for (int z = -5; z <= 5; z++) {
			
			GL11.glVertex3d(5d, 0d, (double)z);
			GL11.glVertex3d(-5d, 0d, (double)z);

		}
		
		GL11.glEnd();// */
	}
	
	public static Vec2 project(double lamda, double phi) {
		Vec2 projected = new Vec2();
		// map -125 to -65 : 0 to 16
		projected.x = (float)(((lamda+125)/60)*16);
		// map 25 to 50 : 8 to 0
		projected.y = (float)(8-(((phi-25)/25)*8));
		
//		double phi0 = Math.toRadians(25);  // 32.7150� N, 117.1625� W San Diego
//		double phi1 = Math.toRadians(25);
//		double phi2 = Math.toRadians(50);
//		double lamda0 = Math.toRadians(-125);
//		
//		double n = (Math.sin(phi1) + Math.sin(phi2));
//		double theta = n*(lamda - lamda0);
//		double C = Math.cos(phi1)*Math.cos(phi1) + 2*n*Math.sin(phi1);
//		double rho = Math.sqrt(C - 2*n*Math.sin(phi))/n;
//		double rho0 = Math.sqrt(C - 2*n*Math.sin(phi0))/n;
//		
//		projected.x = (float)(rho*Math.sin(theta));
//		projected.y = (float)(rho0 - rho*Math.cos(theta));
		
		return projected;
	}
	
}