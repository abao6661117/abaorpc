package ecnu.abao.abaorpc.transport;

import ecnu.abao.abaorpc.Peer;

import java.io.InputStream;

/**
 * 1、创建链接
 * 2、发送数据，并且等待相应
 * 3、关闭连接
 */
public interface TransportClient {
    void connect(Peer peer);
    InputStream write(InputStream data);
    void close();

}
