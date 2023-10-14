package ecnu.abao.abaorpc.server;

import ecnu.abao.abaorpc.Request;
import ecnu.abao.abaorpc.ServiceDescriptor;
import ecnu.abao.abaorpc.common.utils.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ServiceManagerTest {
    ServiceManager sm;
    @Before
    public void init(){
        sm = new ServiceManager();
        TestClass ts = new TestClass();
        sm.register(TestInterface.class, ts);
    }
    @Test
    public void register() {
        TestClass ts = new TestClass();
        sm.register(TestInterface.class, ts);
    }

    @Test
    public void lookup() {
        Method method = ReflectionUtils.getPublicMethods(TestInterface.class)[0];
        ServiceDescriptor sdp = ServiceDescriptor.from(TestInterface.class, method);
        Request req = new Request();
        req.setService(sdp);
        ServiceInstance sis = sm.lookup(req);
        assertNotNull(sis);
    }
}