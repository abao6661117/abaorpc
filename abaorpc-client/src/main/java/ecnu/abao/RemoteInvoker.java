package ecnu.abao;

import ecnu.abao.abaorpc.Request;
import ecnu.abao.abaorpc.Response;
import ecnu.abao.abaorpc.ServiceDescriptor;
import ecnu.abao.abaorpc.codec.Decoder;
import ecnu.abao.abaorpc.codec.Encoder;
import ecnu.abao.abaorpc.common.utils.ReflectionUtils;
import ecnu.abao.abaorpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
@Slf4j
public class RemoteInvoker implements InvocationHandler {
    private Encoder encoder;
    private Class clazz;
    private Decoder decoder;
    private TransportSelector selector;
    public RemoteInvoker(Class clazz, Encoder encoder, Decoder decoder, TransportSelector selector){
        this.encoder = encoder;
        this.clazz = clazz;
        this.decoder = decoder;
        this.selector = selector;
    };
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setParameters(args);
        request.setService(ServiceDescriptor.from(clazz, method));

        Response resp = invokeRemote(request);
        if(resp == null || resp.getCode() != 0){
            throw new IllegalStateException("fail to invoke remote:" + resp);
        }
        return resp.getData();
    }

    private Response invokeRemote(Request request) {
        Response resp = null;
        TransportClient client = null;
        try {
            client = selector.select();
            byte[] outBytes = encoder.encode(request);
            InputStream revice = client.write(new ByteArrayInputStream(outBytes));

            byte[] inBytes = IOUtils.readFully(revice, revice.available());
            resp = decoder.decode(inBytes, Response.class);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            resp = new Response();
            resp.setCode(1);
            resp.setMessage("RpcClient got erroe:" + e.getClass() + ":" + e.getMessage());
        }finally {
            if(client != null){
                selector.release(client);
            }
        }
        return resp;

    }
}
