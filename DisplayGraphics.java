import java.awt.*;  
import javax.swing.JFrame;  
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class DisplayGraphics { 
    // get points and normals from a .pts file gprovided as cmd line agument
    public static double[][] loadData(String filename) {
        int lines = 0;
        try {
            lines = (int)Files.lines(Paths.get(filename)).count();
        }
        catch(IOException e) {
            System.out.println("IO Exception.");
            e.printStackTrace();            
        }
        double[][] dataPoints = new double[lines][6];
        try {
            File myObj = new File(filename);
            Scanner fileReader = new Scanner(myObj);
            int lineNum = 0;
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                StringTokenizer st = new StringTokenizer(data);
                for(int i = 0; i < 6; i++) {
                    dataPoints[lineNum][i] = Double.parseDouble(st.nextToken());
                }
                lineNum++;
            }
            fileReader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        }
        return dataPoints;
    }
    // return the largets and smallest value of each coordinate to determine how to scale the data
    public static double[] getBounds(double[][] unscaledData) {
        double xMin = 0;
        double xMax = 0;
        double yMin = 0;
        double yMax = 0;
        double zMin = 0;
        double zMax = 0;
        for(int i = 0; i < unscaledData.length; i++) {
            if(unscaledData[i][0] < xMin)
                xMin = unscaledData[i][0];
            if(unscaledData[i][0] > xMax)
                xMax = unscaledData[i][0];
            if(unscaledData[i][1] < yMin)
                yMin = unscaledData[i][1];
            if(unscaledData[i][1] > yMax)
                yMax = unscaledData[i][1];
            if(unscaledData[i][2] < zMin)
                zMin = unscaledData[i][2];
            if(unscaledData[i][2] > zMax)
                zMax = unscaledData[i][2];
        }
        double[] bounds = { xMin, xMax, yMin, yMax, zMin, zMax };
        return bounds;
    }
    // Scale the points so they fit inside a given window size
    public static int determineScale(double[] bounds, int wSize) {
        int scale = 1;
        while(bounds[1] - bounds[0] < wSize && bounds[3] - bounds[2] < wSize && bounds[5] - bounds[4] < wSize) {
            for(int i = 0; i < bounds.length; i++) {
                bounds[i] /= scale;
                bounds[i] *= (scale + 1);
            }
            scale++;
        }
        return scale;
    }

    public static void main(String[] args) {  
        final String fname = args[0];
        double[][] data = loadData(fname);
        double[] bounds = getBounds(data);

        int s = determineScale(bounds, 200);
        System.out.println("scale: " + s);
  
        double[][] toProject = new double[data.length][3];
        for(int i = 0; i < data.length; i++) 
            for(int j = 0; j < 3; j++)
                toProject[i][j] = data[i][j] * s;

    }  
}  