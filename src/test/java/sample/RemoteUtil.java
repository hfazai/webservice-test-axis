package sample;

import java.io.IOException;
import java.net.ServerSocket;

public class RemoteUtil
{

    public static int findFreePort()
    {
        try
        {
            ServerSocket server = new ServerSocket(0);
            int port = server.getLocalPort();
            server.close();
            return port;
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}