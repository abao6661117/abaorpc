package ecnu.abao;

import ecnu.abao.abaorpc.Peer;
import ecnu.abao.abaorpc.codec.Decoder;
import ecnu.abao.abaorpc.codec.Encoder;
import ecnu.abao.abaorpc.codec.JSONDecoder;
import ecnu.abao.abaorpc.codec.JSONEncoder;
import ecnu.abao.abaorpc.transport.HTTPTransportClient;
import ecnu.abao.abaorpc.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClass = HTTPTransportClient.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelect.class;
    private int connectCount = 1;
    private List<Peer> servers = Arrays.asList(new Peer("127.0.0.1", 3000));
}
