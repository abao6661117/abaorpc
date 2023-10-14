package ecnu.abao.abaorpc.server;

import ecnu.abao.abaorpc.codec.Decoder;
import ecnu.abao.abaorpc.codec.Encoder;
import ecnu.abao.abaorpc.codec.JSONDecoder;
import ecnu.abao.abaorpc.codec.JSONEncoder;
import ecnu.abao.abaorpc.transport.HTTPTransportServer;
import ecnu.abao.abaorpc.transport.TransportServer;
import lombok.Data;

@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> tansportClass = HTTPTransportServer.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private int port = 3000;
}
