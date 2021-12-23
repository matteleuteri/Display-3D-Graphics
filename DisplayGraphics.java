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


public class DisplayGraphics extends Canvas {  
    @Override
    public void paint(Graphics g) {    
        setBackground(Color.GRAY);   
        setForeground(Color.RED);  
        
        //g.fillOval(130,130,50, 60);  
        //g.drawArc(30, 200, 40,50,90,60);  
        //g.fillArc(30, 130, 40,50,180,40);       
    }  

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

    /* 
    *  This method scales the points so they fit inside a window, 
    *  and are resized when the widow size changes
    *
    */
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
            if(unscaledData[i][1] > xMax)
                xMax = unscaledData[i][1];
            if(unscaledData[i][2] < yMin)
                yMin = unscaledData[i][2];
            if(unscaledData[i][3] > yMax)
                yMax = unscaledData[i][3];
            if(unscaledData[i][4] < zMin)
                zMin = unscaledData[i][4];
            if(unscaledData[i][5] > zMax)
                zMax = unscaledData[i][5];
        }
        double[] bounds = { xMin, xMax, yMin, yMax, zMin, zMax };
        return bounds;
    }

    /*
    *  TODO: determine bounds by difference betwen min and max value, instead
    *  of by relative to window size argument. 
    */
    public static int determineScale(double[] bounds, int wSize) {
        int scale = 1;
        while(Math.abs(bounds[0]) < wSize && bounds[1] < wSize && Math.abs(bounds[2]) < wSize && bounds[3] < wSize && Math.abs(bounds[4]) < wSize && bounds[5] < wSize) {
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
        for(double i: bounds)
            System.out.println(i);

        int s = determineScale(bounds, 100);
        System.out.println(s);
        // now scale data by s
  
        for(int i = 0; i < data.length; i++) 
            for(int j = 0; j < 3; j++)
                data[i][j] *= s;

        // for(int i = 0; i < data.length; i++) 
        //     for(int j = 0; j < 2; j++)
        //         System.out.println(data[i][j]);

        // double[] bounds2 = getBounds(data);
        // for(int i = 0; i < bounds2.length; i++) 
        //     System.out.println(bounds2[i]);

        // DisplayGraphics m = new DisplayGraphics();  
        // JFrame f = new JFrame();  
        // f.add(m);  
        // f.setSize(400,400);  
        // f.setVisible(true);  
    }  
  
}  