import io.netty.util.NettyRuntime;
import org.junit.Test;
import java.util.*;
/**
 * @Description: TODO
 * @Auther: Zki Young
 * @Date: 2023/9/12 09:58
 * @company：CTTIC
 */
public class NettyTest {
    public void test(List list){
        System.out.println(NettyRuntime.availableProcessors());
    }

    @Test
    public void test0202(){
        int [] gem = new int[]{100,0,50,100};
        int [][] opertations = new int[][]{{0,2},{0,1},{3,0},{3,0}};
        System.out.println(test02(gem, opertations));
    }

    public int test02(int[] gem, int[][] operations){
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (int[] operation : operations) {
            int from = operation[0];
            int to = operation[1];
            int trade = gem[from]/2;
            gem[from] -= trade;
            gem[to] += trade;
        }
        for (int i : gem) {
            max = Math.max(i, max);
            min = Math.min(i, min);
        }
        return max - min;
    }
}
