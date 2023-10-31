import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Task4 {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java MinMoves <file_path>");
            return;
        }

        String filePath = args[0];

        try {
            // Чтение чисел из файла
            int[] nums = readNumbersFromFile(filePath);

            // Вычисление минимального количества ходов
            int minMoves = calculateMinMoves(nums);

            System.out.println("Минимальное количество ходов: " + minMoves);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] readNumbersFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            count++;
        }
        reader.close();

        int[] nums = new int[count];

        reader = new BufferedReader(new FileReader(filePath));
        int index = 0;
        while ((line = reader.readLine()) != null) {
            nums[index] = Integer.parseInt(line);
            index++;
        }
        reader.close();

        return nums;
    }

    private static int calculateMinMoves(int[] nums) {
        int sum = 0;
        int min = Integer.MAX_VALUE;

        for (int num : nums) {
            sum += num;
            min = Math.min(min, num);
        }

        return sum - nums.length * min;
    }
}