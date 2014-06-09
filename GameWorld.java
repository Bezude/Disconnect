/*
 *  GameWorld.java
 *  ECS 163 Final
 *  Alan Tai and Benjamin Roye
 *
 */

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVReadProc;
import java.awt.Font;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class GameWorld {

    LinkedList<String[]> fdata = new LinkedList<String[]>();
    private boolean morefilteroptions = false;
    private int offset = 0;
    private int barpos = 2 + (15 * 3);

    boolean twoyear = true;
    boolean fouryear = true;
    boolean lessthantwoyear = true;
    boolean hasmedical = true;
    boolean publicschool = true;
    boolean privateschool = true;

    public static int instnm = 0;
    public static int address = 1;
    public static int city = 2;
    public static int stateabr = 3;
    public static int zipcode = 4;
    public static int telephone = 5;
    public static int opeflag = 6; //indicates the institution's degree of eligibility for Title IV aid.
    public static int webaddress = 7;
    public static int iclevel = 8; //indicates if an institution is 4 year, 2 year, or less than 2 year
    public static int control = 9; //indicates if institution is public, private not-for-profit, or private
    public static int hloffer = 10;
    /*
     "Highest level of offering (generated, based on response to IC
     survey)
     0 - Other
     1 - Postsecondary award, certificate or diploma of less than one academic year
     2 - Postsecondary award, certificate or diploma of at least one but less than two academic years
     3 - Associate's degree
     4 - Postsecondary award, certificate or diploma of at least two but less than four academic years
     5 - Bachelor's degree
     6 - Postbaccalaureate certificate
     7 - Master's degree
     8 - Post-master's certificate
     9 - Doctor's degree
     b - None of the above or no answer
     -2 - Not applicable, first-professional only
     -3 - Not Available"
     */
    public static int hbcu = 11; //indicates whether institution is a Historically Black College or not
    public static int hospital = 12; //indicates whether college has a hospital or not
    public static int medical = 13; //indicates whether institution has a medical degree (medicine, dentistry, osteopathic medicine, veterinary medicine)
    public static int locale = 14;
    /*
     "Locale codes identify the geographic status of a school on an urban continuum ranging from “large city” to “rural.”  They are based on a school’s physical address. The urban-centric locale codes introduced in this file are assigned through a methodology developed by the U.S. Census Bureau’s Population Division in 2005.  The urban-centric locale codes apply current geographic concepts to the original NCES locale codes used on IPEDS files through 2004. 

     11 = City: Large: Territory inside an urbanized area and inside a principal city with population of 250,000 or more. 

     12 = City: Midsize: Territory inside an urbanized area and inside a principal city with population less than 250,000 and greater than or equal to 100,000.

     13 = City: Small: Territory inside an urbanized area and inside a principal city with population less than 100,000.

     21 = Suburb: Large: Territory outside a principal city and inside an urbanized area with population of 250,000 or more.

     22 = Suburb: Midsize: Territory outside a principal city and inside an urbanized area with population less than 250,000 and greater than or equal to 100,000.

     23 = Suburb: Small: Territory outside a principal city and inside an urbanized area with population less than 100,000.

     31 = Town: Fringe: Territory inside an urban cluster that is less than or equal to 10 miles from an urbanized area.

     32 = Town: Distant: Territory inside an urban cluster that is more than 10 miles and less than or equal to 35 miles from an urbanized area.

     33 = Town: Remote: Territory inside an urban cluster that is more than 35 miles of an urbanized area.

     41 - Rural: Fringe: Census-defined rural territory that is less than or equal to 5 miles from an urbanized area, as well as rural territory that is less than or equal to 2.5 miles from an urban cluster. 

     42 = Rural: Distant: Census-defined rural territory that is more than 5 miles but less than or equal to 25 miles from an urbanized area, as well as rural territory that is more than 2.5 miles but less than or equal to 10 miles from an urban cluster. 

     43 = Rural: Remote: Census-defined rural territory that is more than 25 miles from an urbanized area and is also more than 10 miles from an urban cluster.
     */
    public static int openpublic = 15;
    public static int closedat = 16;
    public static int ialias = 17;
    public static int category = 18;
    /*
     "Institutional category was derived using the level of offerings reported on the Institutional Characteristics (IC) component and the number and level of awards that were reported on the Completions (C) component.

     Category descriptions:

     1) Degree-granting, graduate with no undergraduate degrees - These institutions offer a Master's degree, Doctor's degree  or a First-professional degree and do not offer a Bachelor's degree or an Associate's degree. 

     2) Degree-granting, primarily baccalaureate or above - These institutions offer a Bachelor's degree, Master's degree,Doctor's degree or a First-professional degree.  Also, the total number of degrees/certificates at or above the bachelor's level awarded divided by the total number of degrees/certificates awarded is greater than 50 percent. 

     3) Degree-granting, not primarily baccalaureate or above - These institutions offer a Bachelor's degree, Master's degree, Doctor's degree,or a First-professional degree.  Also, the total number of degrees/certificates at or above the bachelor's level awarded divided by the total number of degrees/certificates awarded must be less than or equal to 50 percent.

     4) Degree-granting, Associate's and certificates - Institutions offer an Associate's degree and may offer other postsecondary certificates, awards or diplomas of less than one academic year; at least one but less-than two academic years; at least two but less-than four academic years. This category also includes institutions that offer a postbaccalaureate certificate, Post-master's certificate or a First-professional certificate and the highest degree offered is an Associate's degree.

     5) Nondegree-granting, above the baccalaureate - Institutions do not offer Associate's, Bachelor's, Master's, Doctor's or First-professional degrees, but offer either Postbaccaulaureate, Post-master's or First-professional certificates. 

     6) Nondegree-granting, sub-baccalaureate - Institutions do not offer Associate's, Bachelor's , Master's, Doctor's, or First-professional degrees, or certificates above the baccalaureate level. They do offer postsecondary certificates, awards or diplomas of less than one academic year; at least one but less than two academic years; or at least two but less than four academic years.

     Technical details for Institutional Category (INSTCAT):

     Total degrees and certificates and total bachelor's degrees and all other degrees/certificates above the bachelor's degree awarded are derived using data from the Completions component.

     Total bachelor's degrees and all other degrees/certificates above the bachelor's degree is the sum of: 

     Bachelor's degrees (CTOTALT,AWLEVEL=5,MAJORNUM=1)
     Postbaccalaureate certificates (CTOTALT,AWLEVEL=6,MAJORNUM=1)
     Master's degrees (CTOTALT,AWLEVEL=7,MAJORNUM=1)
     Post-master's certificates (CTOTALT,AWLEVEL=8,MAJORNUM=1)
     Doctor's degrees (CTOTALT,AWLEVEL=9,MAJORNUM=1)
     First-professional degrees (CTOTALT,AWLEVEL=10,MAJORNUM=1)
     First-professional certificates (CTOTALT,AWLEVEL=11,MAJORNUM=1)

     Total degrees and certificates is the sum of Bachelor degrees and all other degrees/certificates above the bachelors degree as defined above added to the following:

     Associate's degrees (CTOTALT,AWLEVEL=3,MAJORNUM=1)
     Postsecondary certificates, awards or diplomas of at least two but less-than four academic years 
     (CTOTALT,AWLEVEL=4, MAJORNUM=1).
     Postsecondary certificates, awards or diplomas of at least one but less-than two academic years
     (CTOTALT,AWLEVEL=2,MAJORNUM=1); 
     Postsecondary certificates, awards or diplomas of less than one academic year 
     (CTOTALT,AWLEVEL=1,MAJORNUM=1) 

     Institutions are classified as Degree-granting (DEGGRANT=1) or Nondegree-granting(DEGGRANT=2) using level of offerings data reported on the Institutional Characteristics component. Degree-granting institutions offer an Associate's (LEVEL3=1),Bachelor's (LEVEL5=1, Master's (LEVEL7=1), Doctoral (LEVEL9=1), or a First-Professional (LEVEL10=1) degree. Any institution that offers only certificates are Nondegree-granting.

     Nondegree-granting institutions that offer a postbaccalaureate certificate (LEVEL6=1) or a post-master's certificate (LEVEL8=1) or a First-professional certificate (LEVEL11=1) are classified as Nondegree-granting, above the baccalaureate (INSTCAT=5).

     Nondegree-granting institutions that only offer certificates of less-than four academic years are classified as Nondegree-granting, sub-baccalaureate (INSTCAT=6).

     Degree-granting institutions whose highest degree granted are Associate's (HDEGOFFR=40) are classified as Degree-granting, Associate's and certificates (INSTCAT=4). (There are a few 4-year institutions that grant Postbaccalaureate or Post-master's or First-professional certificates in this category).

     Degree-granting institutions that do not grant a Bachelor's degree (LEVEL5=0) and do not grant an Associate's degree (LEVEL3=0) are classified as Degree-granting, graduate with no undergraduate degrees (INSTCAT=1).

     The remaining degree-granting institutions offer a bachelor's degree or an associate's degree, or both.
     For these institutions a percent of bachelor's degrees and all other degrees/certificates above the bachelor's degree of  total degrees and certificates is generated. If the percent is greater than 50, institutions are classified as Degree-granting, primarily baccalaureate or above (INSTCAT=2), If the percent is 50 or less, institutions are classified as Degree-granting, not primarily baccalaureate or above (INSTCAT=3).

     Inactive institutions (CYACTIVE in (2,3)) and administrative units (Sector=0) were coded as not applicable. All Non-Title IV institutions that did not respond to the  IC or Completions components were coded as not reported. 
     Degree-granting institutions whose completions data are reported with their parent institution were assigned the same code as the parent institution.
     New degree-granting institutions that report offering a bachelor's degree on the current institutional characteristics file for the upcoming academic year, and have not yet reported bachelor's degrees on the current completions that covers the previous academic year  June 1-July 30 were assigned as follows:

     If an institution reported a zero in any program (CIP code) for bachelor's degrees or above (indicating the level was offered) and did not report a zero for any program (CIP code) at any level below the bachelor's, the institution was classified as Degree-granting, primarily baccalaureate or above. If an institution reported a zero in any program (CIP code) at any level below the bachelor's degree and did not report a zero for any program (CIP code) for bachelor's degrees or above, the institution was classified as Degree-granting, not primarily baccalaureate or above.

     For institutions that reported zeros for bachelor's degrees of above and for levels below the bachelor's, the maximum number of programs by level was used to determine the primary classification."
     */
    public static int carnegie = 19;
    public static int size = 20;
    public static int countyname = 22;
    public static int longitude = 23;
    public static int latitude = 24;
    public static final CSV csv = CSV
            .separator(',') // delimiter of fields
            .create();       // new instance is immutable
    public static final String[][] universities = new String[7504][25];

    private TrueTypeFont font;
    private boolean antiAlias = false;
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
    
    float rotate;
    static float SWOOP_DUR;
    float targetX;
    float targetZ;
    float zoom;

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
        
        rotate = 0;
        targetX = 10;
        targetZ = 4;
        zoom = 0;

        camera = new Camera();
        camera.setPosition(targetX, 10f, targetZ);
        camera.setTarget(targetX, 0f, targetZ);
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
            if (frameDelta < FRAME_LENGTH_MINIMUM) { //If very little time has passed since the last update, yield the cpu
                timer.pushBack(frameDelta);
            	Thread.sleep(10);
            } else {
                pollMouse();
                pollKeyboard();
                updateMap();
                drawEntities();
                Display.update();
            }

        }

        Display.destroy();
    }

    private void checkbox(int xoffset, int ypos, boolean check) {
        int x1 = width - (290 + xoffset);
        int x2 = width - (280 + xoffset);
        int y1 = 5 + ypos;
        int y2 = 15 + ypos;
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor4d(1, 1, 1, 1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x1, y2);
        GL11.glVertex2d(x2, y1);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        if (check) {
            GL11.glBegin(GL11.GL_LINES);
            GL11.glColor4d(0, 0, 0, 1);
            GL11.glVertex2d(x1, y1);
            GL11.glVertex2d(x2, y2);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINES);
            GL11.glColor4d(0, 0, 0, 1);
            GL11.glVertex2d(x1, y2);
            GL11.glVertex2d(x2, y1);
            GL11.glEnd();
        }
    }

    // Method to initialize the display
    public void init() {

        //TODO: lighting, shading, fog, particles
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);
        }
        Font awtFont = new Font("Times New Roman", Font.BOLD, 14);
        font = new TrueTypeFont(awtFont, antiAlias);
        csv.read("Post-Secondary_Universe_Survey_2010_-_Directory_information.csv", new CSVReadProc() {
            public void procRow(int rowIndex, String... values) {
                for (int i = 0; i < 25; i++) {
                    universities[rowIndex][i] = values[i];
                }
                fdata.add(values);
            }
        });
        fdata.remove();
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
        map = new Map("comboMod.svg");
    }

    public void loadWorld() {
        //TODO: Read in the objects that make up the game world
    }

    public void drawEntities() {
        hero.draw();
    }

    public boolean toggle(boolean var) {
        try {
            TimeUnit.MILLISECONDS.sleep(150);
        } catch (InterruptedException e) {
            //Handle exception
            System.err.println("Caught error: " + e);
        }
        boolean temp = false;
        if (var) {
            temp = false;
        } else {
            temp = true;
        }
        return temp;
    }

    public void updateMap() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glTranslatef(hero.getPosition(0)+targetX, hero.getPosition(1)-zoom, hero.getPosition(2));
        GL11.glRotatef(rotate, 0, 0, 1);
        GL11.glTranslatef(-targetX, 0, 0);
        map.draw();
        GL11.glPopMatrix();

        setGraphicsMode("2D");
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        // draw UI
        //UI Box
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glBegin(GL11.GL_QUADS); //Right background for primary filter options
        GL11.glColor4d(1, 1, 1, 0.70);
        GL11.glVertex2d(width - 295, 5);
        GL11.glVertex2d(width - 5, 5);
        GL11.glVertex2d(width - 5, height - 5);
        GL11.glVertex2d(width - 295, height - 5);
        GL11.glEnd();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor4d(1, 1, 1, 0.70);
        GL11.glVertex2d(width - 455, 5);
        GL11.glVertex2d(width - 300, 5);
        GL11.glVertex2d(width - 300, 20);
        GL11.glVertex2d(width - 455, 20);
        GL11.glEnd();
        if (morefilteroptions) {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glColor4d(1, 1, 1, 0.70);
            GL11.glVertex2d(width - 465, 5);
            GL11.glVertex2d(width - 300, 5);
            GL11.glVertex2d(width - 300, height - 5);
            GL11.glVertex2d(width - 465, height - 5);
            GL11.glEnd();
            checkbox(170, 2 + (15 * 1), hasmedical);
            checkbox(170, 2 + (15 * 2), publicschool);
            checkbox(170, 2 + (15 * 3), privateschool);
        }
        checkbox(0, 2 + (15 * 0), lessthantwoyear);
        checkbox(0, 2 + (15 * 1), twoyear);
        checkbox(0, 2 + (15 * 2), fouryear);
        GL11.glBegin(GL11.GL_QUADS);
        if (barpos < 2 + (15 * 3)) {
            barpos = 2 + (15 * 3);
        }
        if (barpos > 580) {
            barpos = 580;
        }
        GL11.glColor4d(0.01, 0.01, 0.41, 0.80);
        GL11.glVertex2d(width - 22, barpos);
        GL11.glVertex2d(width - 8, barpos);
        GL11.glVertex2d(width - 8, barpos + 40);
        GL11.glVertex2d(width - 22, barpos + 40);
        GL11.glEnd();
        int mousex = Mouse.getX();
        int mousey = Mouse.getY();
        if (Mouse.isButtonDown(0)) {
            if (mousex > width - 22 && mousex < width - 8 && height - mousey < 600 && height - mousey > 50) {
                barpos = height - mousey - 10;
            } else if (mousex > width - 295 && mousex < width - 30) {
                if (height - mousey > 5 && height - mousey < 18) {
                    lessthantwoyear = toggle(lessthantwoyear);
                } else if (height - mousey > 20 && height - mousey < 33) {
                    twoyear = toggle(twoyear);
                } else if (height - mousey > 35 && height - mousey < 48) {
                    fouryear = toggle(fouryear);
                }
            } else if (mousex > width - 460 && mousex < width - 300 && height - mousey > 4 && height - mousey < 19) {
                morefilteroptions = toggle(morefilteroptions);
            } else if (mousex > width - 462 && mousex < width - 300 && morefilteroptions) {
                if (height - mousey > 18 && height - mousey < 33) {
                    hasmedical = toggle(hasmedical);
                } else if (height - mousey > 35 && height - mousey < 48) {
                    publicschool = toggle(publicschool);
                } else if (height - mousey > 50 && height - mousey < 63) {
                    privateschool = toggle(privateschool);
                }
            }
        }
        if (!twoyear) {
            for (int i = 0; i < fdata.size(); i++) {
                String[] temp = new String[25];
                temp = fdata.get(i);
                if (Integer.parseInt(temp[iclevel]) == 2) {
                    fdata.remove(i);
                }
            }
        }
        if (!fouryear) {
            for (int i = 0; i < fdata.size(); i++) {
                String[] temp = new String[25];
                temp = fdata.get(i);
                if (Integer.parseInt(temp[iclevel]) == 1) {
                    fdata.remove(i);
                }
            }
        }
        if (!lessthantwoyear) {
            for (int i = 0; i < fdata.size(); i++) {
                String[] temp = new String[25];
                temp = fdata.get(i);
                if (Integer.parseInt(temp[iclevel]) == 3) {
                    fdata.remove(i);
                }
            }
        }
        if (!privateschool) {
            for (int i = 0; i < fdata.size(); i++) {
                String[] temp = new String[25];
                temp = fdata.get(i);
                if (Integer.parseInt(temp[control]) != 1) {
                    fdata.remove(i);
                }
            }
        }
        if (!publicschool) {
            for (int i = 0; i < fdata.size(); i++) {
                String[] temp = new String[25];
                temp = fdata.get(i);
                if (Integer.parseInt(temp[control]) == 1) {
                    fdata.remove(i);
                }
            }
        }
        if (!hasmedical) {
            for (int i = 0; i < fdata.size(); i++) {
                String[] temp = new String[25];
                temp = fdata.get(i);
                if (Integer.parseInt(temp[medical]) > 1) {
                    fdata.remove(i);
                }
            }
        }
//        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        Color.white.bind();
        font.drawString(width - 270, 5, "Less-than-Two Year Colleges", Color.green);
        font.drawString(width - 270, 5 + 15, "Two Year Colleges", Color.green);
        font.drawString(width - 270, 5 + (15 * 2), "Four Year Colleges", Color.green);
        font.drawString(width - 445, 5, "More Filter Options", Color.black);
        if (morefilteroptions) {
            font.drawString(width - 445, 20, "Offers Medical Degrees", Color.green);
            font.drawString(width - 445, 5 + (15 * 2), "Public Colleges", Color.green);
            font.drawString(width - 445, 5 + (15 * 3), "Private Colleges", Color.green);
        }
        for (int i = 0; i < fdata.size(); i++) {
            if (5 + (15 * (i + 3)) - ((barpos - (2 + (15 * 3))) * 210) > 49) {
                String[] temp = new String[25];
                temp = fdata.get(i);
                font.drawString(width - 280, 5 + (15 * (i + 3)) - ((barpos - (2 + (15 * 3))) * 210), temp[0], Color.blue);
            }
        }
        GL11.glDisable(GL11.GL_BLEND);

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
            targetX = mouse[0];
            targetZ = mouse[2];
            //	hero.setTargetVect(mouse[0], mouse[1], mouse[2]);
        }
    }

    public void pollKeyboard() {

        //TODO: Implement jumping with the spacebar
        if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_A)) {
            hero.changePositionZ(hero.movementRate * diagonal);
            hero.changePositionX(hero.movementRate * diagonal);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
            hero.changePositionZ(hero.movementRate * diagonal);
            hero.changePositionX(-hero.movementRate * diagonal);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_A)) {
            hero.changePositionZ(-hero.movementRate * diagonal);
            hero.changePositionX(hero.movementRate * diagonal);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
            hero.changePositionZ(-hero.movementRate * diagonal);
            hero.changePositionX(-hero.movementRate * diagonal);
        } else {
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
        

        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            rotate += 1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            rotate -= 1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            zoom -= .1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
            zoom += .1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            zoom = 0;
            rotate = 0;
            targetX = 10;
            targetZ = 4;
        }
    }

    static public float[] getMousePosition(int mouseX, int mouseY) {

        //--------------------- Save the view matrices ------------------//
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
        //---------------------------------------------------------------//

        /*
         * The following logic:
         * gluUnProject takes the mouse coordinates, the three view matrices, and an empty
         * byte buffer to store the 3D world coordinate of the mouse click. By providing 0
         * for the mouse click's z value, the position calculated is on the near z clipping
         * plane. Likewise, providing a 1 for this value puts the 3D point on the far z
         * clipping plane. These 3D coordinates are stored in positionNear and positionFar.
         */
        GLU.gluUnProject((float) mouseX, (float) mouseY, 0, modelview, projection, viewport, positionNear);
        GLU.gluUnProject((float) mouseX, (float) mouseY, 1, modelview, projection, viewport, positionFar);

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
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_LIGHTING);

            GL11.glLoadIdentity();
            GL11.glOrtho(0, width, height, 0, -1, 1);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glShadeModel(GL11.GL_SMOOTH);
        } else if (string.equals("3D")) {
            GL11.glDepthMask(true);
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();

            GLU.gluPerspective(frustAngle, (float) width / (float) height, zNear, zFar);
            GLU.gluLookAt(
                    camera.getPosition(0), camera.getPosition(1), camera.getPosition(2),
                    camera.getTarget(0), camera.getTarget(1), camera.getTarget(2),
                    camera.getUp(0), camera.getUp(1), camera.getUp(2));
            GL11.glEnable(GL11.GL_DEPTH_TEST);

            GL11.glMatrixMode(GL11.GL_MODELVIEW);

        } else {
            try {
                throw new Exception("Invalid graphics mode. Mode must be 2D or 3D.");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
