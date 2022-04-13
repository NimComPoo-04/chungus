package chungus;

import java.util.Map;
import java.util.HashMap;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

class Router
{
	Map<String, String> routes;

	Router()
	{
		this.routes = new HashMap<String, String>();
	}

	void route_register(String route, String path) 
	{
		this.routes.put(route, path);
	}

	String read_file_from_route(String route)
	{
		String resp = "HTTP/1.1 %s OK\r\nConnection: close\r\nContent-Type: text/html; charset=utf-8\r\n\r\n";
		try
		{
			File f = new File(this.routes.get(route));

			if(!f.exists())
			{
				resp += "<h1> Oi Dude nothing to see here now GTFO </h1>";
				resp = String.format(resp, "404");
			}
			else
			{
				FileInputStream fis = new FileInputStream(f);
				Scanner sc = new Scanner(fis);

				while(sc.hasNextLine())
					resp += sc.nextLine()+"\n";

				sc.close();
				resp = String.format(resp, "200");
			}

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			resp += "<h1> Oi Dude nothing to see here now GTFO </h1>";
			resp = String.format(resp, "404");
		}

		return resp;
	}
}
