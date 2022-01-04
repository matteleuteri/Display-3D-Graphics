import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataLoader { 
    private double[][] coordinates;
    // construct a dataloader and set coordinates to scaled point values from specified file
    public DataLoader(String fname) {  
        double[][] data = DataLoader.loadData(fname);
        double[] bounds = DataLoader.getBounds(data);      
        DataLoader.shiftOffset(data, bounds);
        int s = DataLoader.determineScale(bounds, 200);  
        coordinates = new double[data.length][3];
        for(int i = 0; i < data.length; i++) 
            for(int j = 0; j < 3; j++)
                coordinates[i][j] = data[i][j] * s;
    } 
    // shift all points to be centered around 0,0,0
    private static void shiftOffset(double[][] data, double[] bounds) {
        double[] shifts = new double[3];
        for(int i = 0; i < bounds.length; i += 2) {
            double toSubtract = (bounds[i + 1] + bounds[i]) / 2;
            shifts[(i + 1) / 2] = toSubtract;
        }
        for(int j = 0; j < data.length; j++) {
            for(int k = 0; k < shifts.length; k++) {
                data[j][k] -= shifts[k];
            }
        }
    }
    // get points and normals from a .pts file gprovided as cmd line agument
    private static double[][] loadData(String filename) {
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
    // return the smallest and largest value of each coordinate to determine how to scale the data
    private static double[] getBounds(double[][] unscaledData) {
        // order of bounds is xMin, xMax, yMin, yMax, zMin, zMax
        double[] bounds = { Double.MAX_VALUE,-Double.MAX_VALUE,Double.MAX_VALUE,-Double.MAX_VALUE,Double.MAX_VALUE,-Double.MAX_VALUE };
        for(int i = 0; i < unscaledData.length; i++) {
            if(unscaledData[i][0] < bounds[0])
                bounds[0] = unscaledData[i][0];
            else if(unscaledData[i][0] > bounds[1])
                bounds[1] = unscaledData[i][0];
            if(unscaledData[i][1] < bounds[2])
                bounds[2] = unscaledData[i][1];
            else if(unscaledData[i][1] > bounds[3])
                bounds[3] = unscaledData[i][1];
            if(unscaledData[i][2] < bounds[4])
                bounds[4] = unscaledData[i][2];
            else if(unscaledData[i][2] > bounds[5])
                bounds[5] = unscaledData[i][2];
        }
        return bounds;
    }
    // Scale the points so they fit inside a given window size
    private static int determineScale(double[] bounds, int wSize) {
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
    // return the coordinates so they can be painted in another class
    public double[][] getCoords() {
        return coordinates;
    } 
}  