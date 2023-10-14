package ecnu.abao;

import ecnu.abao.abaorpc.Peer;
import ecnu.abao.abaorpc.transport.TransportClient;

import java.util.List;

public interface TransportSelector {
    void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz);

    TransportClient select();

    void release(TransportClient client);
    void close();
}
