package ecnu.abao.abaorpc.server;

import ecnu.abao.abaorpc.Request;
import ecnu.abao.abaorpc.common.utils.ReflectionUtils;

public class ServiceInvoker {
    public Object invoke(ServiceInstance service, Request req){
        return ReflectionUtils.invoke(service.getTarget(),
                service.getMethod(),
                req.getParameters());
    }
}
