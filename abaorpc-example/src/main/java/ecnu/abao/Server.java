package ecnu.abao;

import ecnu.abao.abaorpc.server.RpcServer;
import ecnu.abao.abaorpc.server.RpcServerConfig;

public class Server {
    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.register(CalService.class, new CalServiceImpl());
        server.start();
    }
}
