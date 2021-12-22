import java.awt.*;  
import javax.swing.JFrame;  
import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileNotFoundException;

public class DisplayGraphics extends Canvas{  
      
    public void paint(Graphics g) {  
        g.drawString("Hello",40,40);  
        setBackground(Color.WHITE);  
        g.fillRect(130, 30,100, 80);  
        g.drawOval(30,130,50, 60);  
        setForeground(Color.RED);  
        g.fillOval(130,130,50, 60);  
        g.drawArc(30, 200, 40,50,90,60);  
        g.fillArc(30, 130, 40,50,180,40);       
    }  

    public static ArrayList<double[]> loadData(String filename) {
        ArrayList<double[]> pointsList = new ArrayList<double[]>();
        try {
            File myObj = new File(filename);
            Scanner fileReader = new Scanner(myObj);
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                // tokenize the data and save the first three doubles
                double[] pointsArray = new double[3];
                StringTokenizer st = new StringTokenizer(data);
                for(int i = 0; i < 3; i++) {
                    pointsArray[i] = Double.parseDouble(st.nextToken());
                }
                pointsList.add(pointsArray);
            }
            fileReader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        }
        return pointsList;
    }

    public static void main(String[] args) {  
        String fname = args[0];
        ArrayList<double[]> data = loadData(fname);
        // DisplayGraphics m = new DisplayGraphics();  
        // JFrame f = new JFrame();  
        // f.add(m);  
        // f.setSize(400,400);  
        // f.setVisible(true);  
    }  
  
}  