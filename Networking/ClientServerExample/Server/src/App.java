import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
	/**
	 * The port number to accept socket connections on.
	 * 25565 is the default Minecraft server port :)
	 */
	private static final int PORT_NUM = 25565;

	public static void main(String[] args) throws Exception {
		// Initialize a server to receive incoming client socket connections.
		// The "try" syntax here allows us to initialize the server without needing to
		// call the "close" method later. The server is automatically closed when we
		// reach the end of the "try" block.
		try (ServerSocket serverSocket = initializeServerSocket(PORT_NUM)) {
			// Begin a loop to accept new incoming client connections.
			while (true) {
				// Accept the incoming client socket connection.
				// Again, the "try" syntax allows us to initialize the socket without needing to
				// manually close it later.
				try (Socket clientSocket = acceptClientConnection(serverSocket)) {
					// Read a message from the client.
					String clientMessage = readClientMessage(clientSocket);

					// Display the client message in the terminal.
					printlnf("Client sent the following message: '%s'", clientMessage);

					// Send a response to the client.
					sendClientResponse(clientSocket, "Your message has been received. Thank you.");
				}
			}
		}
	}

	/**
	 * Initialize a server socket that listens for incoming connections on the given
	 * port.
	 *
	 * @param portNum The port number to use when listening for incoming
	 *                connections.
	 * @return A ServerSocket that is ready to listen on the given port.
	 * @throws IOException
	 */
	private static ServerSocket initializeServerSocket(int portNum) throws IOException {
		printlnf("Opening server socket on port %d...", portNum);

		// Create a ServerSocket object. This represents the socket that our server uses
		// to send and retrieve information.
		ServerSocket serverSocket = new ServerSocket(PORT_NUM);

		printlnf("Server socket opened.");

		return serverSocket;
	}

	/**
	 * Use the given ServerSocket to wait for an incoming client socket connection.
	 *
	 * @param serverSocket The ServerSocket to listen on.
	 * @return A Socket that represents the connected client.
	 * @throws IOException
	 */
	private static Socket acceptClientConnection(ServerSocket serverSocket) throws IOException {
		printlnf("Waiting for an incoming client connection...");

		// Wait for, and then accept, a new socket connection from a client. When we
		// reach this line of code, program execution will pause (or "block") until we
		// accept an incoming socket connection from a client.
		Socket socket = serverSocket.accept();

		printlnf("Received a client connection.");

		return socket;
	}

	/**
	 * Read a message from the client that is connected via the given Socket.
	 *
	 * @param clientSocket The Socket that the client is connected on.
	 * @return A message sent by the client.
	 * @throws IOException
	 */
	private static String readClientMessage(Socket clientSocket) throws IOException {
		printlnf("Waiting for client to send a message...");

		// Get the socket's InputStream, which is an object representing a stream of
		// data that the client sends to us.
		InputStream input = clientSocket.getInputStream();

		// Create a BufferedReader as a wrapper around the InputStream. This just
		// provides a more convenient way to retrieve data sent by the client.
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));

		// Get a line of data sent to us by the client.
		String line = reader.readLine();

		return line;
	}

	/**
	 * Send a message to the client that is connected via the given Socket.
	 *
	 * @param clientSocket The Socket that the client is connected on.
	 * @param message      The message to send.
	 * @throws IOException
	 */
	private static void sendClientResponse(Socket clientSocket, String message) throws IOException {
		printlnf("Sending a response to the client...");

		// Get the socket's OutputStream, which is an object representing a stream of
		// data that we send to the client.
		OutputStream output = clientSocket.getOutputStream();

		// Create a PrintWriter as a wrapper around the OutputStream. This just provides
		// a more convenient way to send data to the client.
		PrintWriter writer = new PrintWriter(output, true);

		// Write a message to the client.
		writer.println(message);

		printlnf("Response sent to the client.");
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
