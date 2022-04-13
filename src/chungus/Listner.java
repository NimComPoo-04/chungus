package chungus;

import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;

import java.io.IOException;

class Listner implements Runnable
{
	ServerSocket sock;		// socket to listen on
	boolean do_listen;		// should the server still listen
	Connection con_callback;	// connection callback

	Listner(int port, int backlog, Connection con_callback)
	{
		try
		{
			this.sock = new ServerSocket(port, backlog);
			this.con_callback = con_callback;
			this.do_listen = true;
		}
		catch(IOException err)
		{
			System.out.println(err.getMessage());
		}
	}

	@Override
	public void run()
	{
		try
		{
			while(this.do_listen)
			{
				Socket s = sock.accept();
				con_callback.con(s);
			}
		}
		catch(IOException err)
		{
			System.out.println(err.getMessage());
		}
	}
}

interface Connection
{
	void con(Socket s);
}
