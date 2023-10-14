package ecnu.abao.abaorpc.server;

import ecnu.abao.abaorpc.Request;
import ecnu.abao.abaorpc.ServiceDescriptor;
import ecnu.abao.abaorpc.common.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ServiceManager {
    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager(){
        this.services = new ConcurrentHashMap<>();
    }

    public <T> void register(Class<T> interfaceClass, T bean){
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for(Method m : methods){
            ServiceInstance sis = new ServiceInstance(bean, m);
            ServiceDescriptor sdp = ServiceDescriptor.from(interfaceClass, m);
            services.put(sdp, sis);
            log.info("service register: {},{}",sdp.getClazz(),sdp.getMethod());
        }
    }
    public ServiceInstance lookup(Request request){
        ServiceDescriptor sdp = request.getService();
        return services.get(sdp);
    }
}
