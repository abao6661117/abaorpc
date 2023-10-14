package ecnu.abao.abaorpc.codec;

import com.alibaba.fastjson.JSON;

public class JSONEncoder implements Encoder{

    @Override
    public byte[] encode(Object T) {
        return JSON.toJSONBytes(T);
    }
}
