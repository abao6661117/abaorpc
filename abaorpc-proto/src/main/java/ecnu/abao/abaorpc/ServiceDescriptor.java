package ecnu.abao.abaorpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 表示服务
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypes;
    public static ServiceDescriptor from(Class clazz, Method method){
        ServiceDescriptor sdp = new ServiceDescriptor();
        sdp.setClazz(clazz.getName());
        sdp.setMethod(method.getName());
        sdp.setReturnType(method.getReturnType().getName());
        Class<?>[] mparaTypesClass = method.getParameterTypes();
        String[] mparaTypesString = new String[mparaTypesClass.length];
        for(int i = 0; i < mparaTypesClass.length; i++){
            mparaTypesString[i] = mparaTypesClass[i].getName();
        }
        sdp.setParameterTypes(mparaTypesString);
        return sdp;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return  true;
        if(obj == null || getClass() != obj.getClass())
            return  false;
        ServiceDescriptor that = (ServiceDescriptor) obj;
        return  this.toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return "clazz:" + clazz
                + "method:" + method
                + "returnType:" + returnType
                + "parameterType:" + Arrays.toString(parameterTypes);
    }
}
