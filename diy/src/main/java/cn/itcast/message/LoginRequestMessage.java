package cn.itcast.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/19 09:16
 * @company：CTTIC
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class LoginRequestMessage extends Message{
    private String username;
    private String password;
    private String nickname;

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }
}
