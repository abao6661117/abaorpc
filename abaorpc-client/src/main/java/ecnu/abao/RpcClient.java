package ecnu.abao;

import ecnu.abao.abaorpc.codec.Decoder;
import ecnu.abao.abaorpc.codec.Encoder;
import ecnu.abao.abaorpc.common.utils.ReflectionUtils;
import lombok.Data;

import java.lang.reflect.Proxy;

public class RpcClient {
    private RpcClientConfig config;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RpcClient() {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig config) {
        this.config = config;
        this.decoder = ReflectionUtils.newInstance(this.config.getDecoderClass());
        this.encoder = ReflectionUtils.newInstance(this.config.getEncoderClass());
        this.selector = ReflectionUtils.newInstance(this.config.getSelectorClass());

        this.selector.init(this.config.getServers(),this.config.getConnectCount(), this.config.getTransportClass());
    }

    public <T> T getProxy(Class<T> clazz){
        return (T)Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{clazz},
                new RemoteInvoker(clazz, encoder, decoder, selector));
    }
}
