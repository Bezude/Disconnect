package svg.parser;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;



public class ExtractSVGPaths {
	
	/*public static void main(String [] args) {
		ExtractSVGPaths.extract("/Users/bensmiley45/Documents/workspace/SVGParser/src/res/icy1.svg");
	}*/
	
	/*
	 * Path is an absolute path i.e. /Users/..../workspace/file.svg
	 */
	public static ArrayList<ArrayList<Vec2>> extract (String filePath) {
		/*
    	Camera camera = new Camera (new Vec2(), new Vec2(100, 60), new Vec2(), new Vec2(800, 480), new Vec2(-20, -12), new Vec2(120, 72));
    	
    	CameraController cc = new CameraController(camera);
    	
		GraphicsManager gm = new GraphicsManager(camera);
				
		// Visualise Geometry
		VisualiseGeometry visualiseGeometry = new VisualiseGeometry(gm);

		Display2D display = new Display2D(camera);
	   	DefaultWindow window = new DefaultWindow((int)camera.screenWidth(),(int)camera.screenHeight(), display);	

	   	display.addDrawable(gm);
	   	display.addUpdateListener(cc);
		*/
		ParseSVG l = new ParseSVG (filePath);
		ArrayList<ArrayList<Vec2>> states = new ArrayList<ArrayList<Vec2>>();

		for(int i = 0; i < l.paths.size(); i++) {
	    	Spline sl1 = l.paths.get(i);
	    	
	    	// Refine the spline to an appropriate level of accuracy
//	    	System.out.print("From: "+ sl1.getVertices().size());
//			sl1.refine(0.1f);
//			System.out.println(" To: "+ sl1.getVertices().size());
			ArrayList<Vec2> vecs = new ArrayList<Vec2>();
			
			/*
			 * Convert from Vertex to Vec2
			 */
			ArrayList<Vertex> al = sl1.getVertices();
			//al.remove(0);
			/*
	 		Vertex [] verts = new Vertex [al.size()];
			for(int i=0; i<al.size(); i++) {
				verts [i] = al.get(i);
			}
			*/
			
			for(Vertex v: al) {
				vecs.add(v.toVec2());
			}
			
			states.add(vecs);
		}
		//visualiseGeometry.lineString(verts);
		
		return states;
	}

}
