import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Task2 {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java CirclePosition <circle_file> <points_file>");
            return;
        }

        String circleFile = args[0];
        String pointsFile = args[1];

        try {
            double centerX, centerY, radius;
            BufferedReader circleReader = new BufferedReader(new FileReader(circleFile));
            centerX = Double.parseDouble(circleReader.readLine());
            centerY = Double.parseDouble(circleReader.readLine());
            radius = Double.parseDouble(circleReader.readLine());
            circleReader.close();

            BufferedReader pointsReader = new BufferedReader(new FileReader(pointsFile));
            String point;
            while ((point = pointsReader.readLine()) != null) {
                double pointX, pointY;
                String[] coordinates = point.split(" ");
                pointX = Double.parseDouble(coordinates[0]);
                pointY = Double.parseDouble(coordinates[1]);
                String position = determinePointPosition(centerX, centerY, radius, pointX, pointY);
                System.out.println(position);
            }
            pointsReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String determinePointPosition(double centerX, double centerY, double radius, double pointX, double pointY) {
        double distance = calculateDistance(centerX, centerY, pointX, pointY);
        if (distance < radius) {
            return "Inside";
        } else if (distance > radius) {
            return "Outside";
        } else {
            return "On the circumference";
        }
    }

    private static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }
}