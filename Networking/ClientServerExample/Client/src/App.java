import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class App {
	/**
	 * The hostname of the server to connect to.
	 * Since the server is running on our local machine, we can use "localhost".
	 */
	private static final String HOSTNAME = "localhost";

	/**
	 * The port number of the server to connect to.
	 * 25565 is the default Minecraft port number :)
	 */
	private static final int PORT_NUM = 25565;

	public static void main(String[] args) throws Exception {
		// Initialize a client socket connected to a server.
		// The "try" syntax here allows us to initialize the client socket without
		// needing to call the "close" method later. The socket is automatically closed
		// when we reach the end of the "try" block.
		try (Socket socket = initializeClientSocket(HOSTNAME, PORT_NUM)) {
			// Send a message to the server.
			sendServerMessage(socket, "Hello, this is the Client!");

			// Receive the server response.
			String serverResponse = readServerMessage(socket);

			// Display the server response in the terminal.
			printlnf("Server sent the following message: '%s'", serverResponse);
		}

		printlnf("Client socket connection closed.");
	}

	/**
	 * Initialize a client socket connected to the given server.
	 *
	 * @param hostname The hostname of the server to connect to.
	 * @param portNum  The port number to connect to on the server.
	 * @return A Socket that is connected to the given server.
	 * @throws IOException
	 */
	private static Socket initializeClientSocket(String hostname, int portNum) throws IOException {
		printlnf("Opening a client socket connection to '%s:%d'", HOSTNAME, PORT_NUM);

		// Create a new socket that is connected to a server running on the given host
		// and port.
		Socket socket = new Socket(hostname, portNum);

		printlnf("Client socket opened.");

		return socket;
	}

	/**
	 * Send a message to the server that the given Socket is connected to.
	 *
	 * @param socket  A socket connected to the desired server.
	 * @param message The message to send.
	 * @throws IOException
	 */
	private static void sendServerMessage(Socket socket, String message) throws IOException {
		printlnf("Sending a message to the server...");

		// Get the socket's OutputStream, which is an object representing a stream of
		// data that we send to the server.
		OutputStream output = socket.getOutputStream();

		// Create a PrintWriter as a wrapper around the OutputStream. This just provides
		// a more convenient way to send data to the server.
		PrintWriter writer = new PrintWriter(output, true);

		// Write a message to the server.
		writer.println(message);

		printlnf("Message sent to the server.");
	}

	/**
	 * Read a message from the server that the given Socket is connected to.
	 *
	 * @param socket A socket connected to the desired server.
	 * @return A message sent by the server.
	 * @throws IOException
	 */
	private static String readServerMessage(Socket socket) throws IOException {
		printlnf("Waiting for the server to send a message...");

		// Get the socket's InputStream, which is an object representing a stream of
		// data that the server sends to us.
		InputStream input = socket.getInputStream();

		// Create a BufferedReader as a wrapper around the InputStream. This just
		// provides a more convenient way to retrieve data sent by the server.
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));

		// Get a line of data sent to us by the server.
		String line = reader.readLine();

		return line;
	}

	/**
	 * Helper method to print to the terminal slightly more conveniently.
	 *
	 * @param format    The line of text to print, with format arguments.
	 * @param arguments Arguments to the format string.
	 */
	private static void printlnf(String format, Object... arguments) {
		System.out.printf(format, arguments);
		System.out.println();
	}
}
