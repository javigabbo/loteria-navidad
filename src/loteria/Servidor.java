package loteria;

import java.io.*;
import java.net.*;

public class Servidor {

	static int numeroPremiado = 12345;
	static int numeroRecibido = 1;

	public static void main(String[] args) {
		try {
			System.out.println("Se crea el socket Servidor");
			ServerSocket serverSocket = new ServerSocket();
			System.out.println("Realiza el bind");
			InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
			serverSocket.bind(addr);
			System.out.println("Aceptando conexiones...");
			Socket newSocket = serverSocket.accept();
			System.out.println("Conexión recibida");

			while (numeroRecibido != 0) {
				System.out.println("Recibiendo números...");
				byte[] mensaje = new byte[100];
				InputStream is = newSocket.getInputStream();
				is.read(mensaje);
				String msjRecibido = new String(mensaje);
				numeroRecibido = Integer.parseInt(msjRecibido.trim());
				System.out.println("Numero recibido = " + numeroRecibido);

				if (numeroRecibido == numeroPremiado) {
					OutputStream os = newSocket.getOutputStream();
					String resultado = "¡Premio Gordo!\n";
					os.write(resultado.getBytes());
				} else{
					OutputStream os = newSocket.getOutputStream();
					String resultado = "Mala suerte.\n";
					os.write(resultado.getBytes());
				}

			}

			System.out.println("Cerrando el nuevo socket");
			newSocket.close();
			System.out.println("Cerrando el socket servidor");
			serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
