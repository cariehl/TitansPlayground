import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
	public static void main(String[] args) throws Exception {
		// Prompt the user for their name, and write it to a file.
		writeUserNameToFile();

		// Open the file and display the contents.
		displayFileContents();
	}

	private static void writeUserNameToFile() throws IOException {
		Scanner scanner = new Scanner(System.in);
		FileWriter writer = new FileWriter("data.txt", true);

		System.out.println("What is your name?");
		String name = scanner.nextLine();

		writer.append("Hello, " + name + "\n");

		writer.close();
		scanner.close();
	}

	private static void displayFileContents() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("data.txt"));

		System.out.println("The contents of data.txt are:");
		String s;
		while ((s = reader.readLine()) != null) {
			System.out.println(s);
		}

		reader.close();
	}
}
