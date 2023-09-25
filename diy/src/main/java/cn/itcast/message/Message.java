package cn.itcast.message;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/19 09:11
 * @company：CTTIC
 */
@Data
public abstract class Message implements Serializable {
    public static Class<?> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    /*请求序号*/
    private int sequenceId;
    private int messageType;

    public abstract int getMessageType();

    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 0;

    private static final Map<Integer, Class<?>> messageClasses = new HashMap<>();
}
