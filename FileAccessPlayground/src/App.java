import java.io.FileWriter;
import java.util.Scanner;

public class App {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		FileWriter writer = new FileWriter("data.txt", true);

		System.out.println("What is your name?");
		String name = scanner.nextLine();

		// writer.write("Hello, " + name);
		writer.append("Hello, " + name + "\n");

		writer.close();
		scanner.close();
	}
}
