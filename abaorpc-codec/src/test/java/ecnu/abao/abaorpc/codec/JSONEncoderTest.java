package ecnu.abao.abaorpc.codec;

import junit.framework.TestCase;

public class JSONEncoderTest extends TestCase {

    public void testEncode() {
        Encoder encoder = new JSONEncoder();
        TestBean bean = new TestBean();
        bean.setAge(18);
        bean.setName("abao");
        byte[] bytes = encoder.encode(bean);
        assertNotNull(bytes);
    }
}