/*
 *  Main.java
 *  ECS 163 Final
 *  Alan Tai and Benjamin Roye
 *
 */

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVReadProc;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //TODO: splash screen/main menu need to look into lwjgl capabilities for this
        GameWorld gw = new GameWorld();

        gw.loadMap();
        gw.loadWorld();
        gw.start();
    }
}