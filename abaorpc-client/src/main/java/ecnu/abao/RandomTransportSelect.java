package ecnu.abao;

import ecnu.abao.abaorpc.Peer;
import ecnu.abao.abaorpc.common.utils.ReflectionUtils;
import ecnu.abao.abaorpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class RandomTransportSelect implements TransportSelector{
    private  List<TransportClient> clients;

    public RandomTransportSelect() {
        clients = new ArrayList<>();
    }

    @Override
    public synchronized void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        count = Math.max(count, 1);
        for(Peer peer : peers){
            for(int i = 0 ; i < count; i++){
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("connect server: {}", peer);
        }
    }

    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.get(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        clients.add(client);
    }

    @Override
    public synchronized void close() {
        for(TransportClient  client : clients  ){
            client.close();
        }
        clients.clear();
    }
}
