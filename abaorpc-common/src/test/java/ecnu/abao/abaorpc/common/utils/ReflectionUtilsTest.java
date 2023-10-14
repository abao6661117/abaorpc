package ecnu.abao.abaorpc.common.utils;

import junit.framework.TestCase;

import java.lang.reflect.Method;

public class ReflectionUtilsTest extends TestCase {

    public void testNewInstance() {
        TestClass t = ReflectionUtils.newInstance(TestClass.class);
        String c = t.b();
        assertEquals("b", c);
    }

    public void testGetPublicMethods() {
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        Method method = methods[0];
        assertEquals(1, methods.length);
        String mname = method.getName();
        assertEquals("b", mname);
    }

    public void testInvoke() {
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        Method method = methods[0];
        TestClass t = new TestClass();
        Object x = ReflectionUtils.invoke(t, method);
        assertEquals("b", x);
    }
}