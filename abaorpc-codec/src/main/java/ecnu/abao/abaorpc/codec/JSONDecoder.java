package ecnu.abao.abaorpc.codec;

import com.alibaba.fastjson.JSON;
import ecnu.abao.abaorpc.codec.Decoder;

public class JSONDecoder implements Decoder {
    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
