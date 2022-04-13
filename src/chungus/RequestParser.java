package chungus;

import java.util.Map;
import java.util.HashMap;

class RequestParser
{
	String request;

	String method;
	String http_version;
	String route;
	Map<String, String> req_opts;	// request Options
	
	RequestParser(String request)
	{
		this.request = request;
		this.req_opts = new HashMap<String, String>();
	}

	void parse() throws MalformedRequestException
	{
		String r[] = this.request.split("\r\n");

		// if the request size is less than 1 then it most defenitely is malformed
		if(r.length < 1) throw new MalformedRequestException(this.request);

		// the main http request for the route and the other important things are parsed first
		// this is since this part of the header is a bit different from the rest of the request
		String main[] = r[0].split(" ");

		// if the main part is not composed of the method, route and the version then it probably is malformed
		if(main.length != 3) throw new MalformedRequestException(this.request);

		this.method = main[0];
		this.route = main[1];
		this.http_version = main[2];

		// The request options are stored in a hashmap
		for(int i = 1; i < r.length; i++)
		{
			int g = r[i].indexOf(":");	// keys are separated by :
			if(g < 0) continue;		// somethings are always fking malformed ignore them
			this.req_opts.put(r[i].substring(0, g), r[i].substring(g+1));
		}
	}

	@Override
	public String toString()
	{
		return String.format("METHOD: %s\nROUTE: %s\nTYPE: %s\n%s", this.method, this.route, this.http_version, this.req_opts.toString());
	}
}

class MalformedRequestException extends Exception
{
	public MalformedRequestException(String str)
	{
		super(str);
	}
}
