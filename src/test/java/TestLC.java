import org.junit.Test;

import java.util.*;

/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/18 12:30
 * @company：CTTIC
 */
public class TestLC {

    @Test
    public void testMain(){
        System.out.println(hammingWeight(00000000000000000000000000001011));
    }

    public int hammingWeight(int n) {
        int ret = 0;
        for (int i = 0; i < 32; i++) {
            int i1 = 1 << i;
            int flag = n & i1;
            if (flag != 0) {
                ret++;
            }
        }
        return ret;
    }
}
