package ecnu.abao.abaorpc.server;

import ecnu.abao.abaorpc.Request;
import ecnu.abao.abaorpc.Response;
import ecnu.abao.abaorpc.codec.Decoder;
import ecnu.abao.abaorpc.codec.Encoder;
import ecnu.abao.abaorpc.common.utils.ReflectionUtils;
import ecnu.abao.abaorpc.transport.RequestHandler;
import ecnu.abao.abaorpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.servlet.Invoker;

import java.awt.image.RescaleOp;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class RpcServer {
    private RpcServerConfig rpcConfig ;
    private ServiceManager serviceManager;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceInvoker serviceInvoker;

    public RpcServer() {
        this(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig rpcConfig){
        this.rpcConfig = rpcConfig;
        this.net = ReflectionUtils.newInstance(rpcConfig.getTansportClass());
        net.init(rpcConfig.getPort(), this.handler);
        this.encoder = ReflectionUtils.newInstance(rpcConfig.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(rpcConfig.getDecoderClass());
        this.serviceInvoker = new ServiceInvoker();
        this.serviceManager = new ServiceManager();
    }
    public <T> void register(Class<T> interfaceClass, T bean){
        serviceManager.register(interfaceClass, bean);
    }
    public void start(){
        this.net.start();
    }
    public void stop(){
        this.net.stop();
    }
    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream recive, OutputStream toResp) {
            Response resp = new Response();
            try {
                byte[] bytes = IOUtils.readFully(recive, recive.available());
                Request req = decoder.decode(bytes, Request.class);
                log.info("get request: {}", req);
                ServiceInstance sis = serviceManager.lookup(req);
                Object data = serviceInvoker.invoke(sis, req);
                resp.setData(data);
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                resp.setCode(1);
                resp.setMessage("Rpc got error:" + e.getClass().getName() + ":" + e.getMessage());
            }finally {
                try {
                    byte[] bytes = encoder.encode(resp);
                    toResp.write(bytes);
                    log.info("respone client");
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
    };
}
