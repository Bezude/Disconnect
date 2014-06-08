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

	public Map(String string) {
		
		states = ExtractSVGPaths.extract(string);

		for(int i = 0; i < states.size(); i++) {
			for(int j = 0; j < states.get(i).size(); j++) {
				Vec2 v = states.get(i).get(j);
				v.x /= 60f;
				v.y /= 60f;
				//System.out.println("x: "+v.x+" | y: "+v.y);
			}
		}
//		final LinkedList points = new LinkedList();
//        PointsParser pp = new PointsParser();
//        PointsHandler ph = new DefaultPointsHandler() {
//            public void point(float x, float y) throws ParseException {
//                Point2D p = new Point2D.Float(x, y);
//                points.add(p);
//            }
//        };
//        pp.setPointsHandler(ph);
//        pp.parse(s);
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
	
}