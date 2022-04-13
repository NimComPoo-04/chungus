package chungus;

import java.net.Socket;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

public class Main
{
	Router router;

	Main()
	{
		this.router = new Router();
	}

	public static void main(String args[])
	{
		Main m = new Main();

		m.router.route_register("/", "./public/index.html");

		System.out.println("Starting Server with backlog 1...");
		Listner l = new Listner(8080, 1, (Socket s) -> { m.con_handler(s); });

		System.out.println("Listning on port 8080...");
		l.run();
	}

	void con_handler(Socket s)
	{
		try
		{
			// TODO: Find a better way to do this
			// FIXME: if the request gets longer than 1024 bytes then java just simply goes 'eh idk whatever'
			byte buff[] = new byte[1024];

			String request = "";
			s.getInputStream().read(buff);
			request += new String(buff);

			String response = "";

			try
			{
				RequestParser r = new RequestParser(request);	
				r.parse();
				System.out.println(r.route);
				response = this.router.read_file_from_route(r.route);
			}
			catch(MalformedRequestException e)
			{
				System.out.println(e.getMessage());
			}


			//response = "HTTP/1.1 200 OK\r\nConnection: close\r\nContent-Type: text/html; charset=utf-8\r\n\r\n" + response;
			//System.out.println(response);
			s.getOutputStream().write(response.getBytes());

			s.close();
		}
		catch(IOException err)
		{
			System.out.println(err.getMessage());
		}
	}
}
