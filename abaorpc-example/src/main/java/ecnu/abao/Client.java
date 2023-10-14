package ecnu.abao;

public class Client {
    public static void main(String[] args) {
        RpcClient rpcClient = new RpcClient();
        CalService proxy = rpcClient.getProxy(CalService.class);
        int r1 = proxy.add(1, 2);
        int r2 = proxy.minus(10, 8);
        System.out.println(r1);
        System.out.println(r2);


    }
}
