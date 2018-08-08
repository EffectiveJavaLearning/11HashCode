import java.lang.reflect.Array;

/**
 * 较为理想的hashCode重写方式及一般步骤，这里不妨将所存的数据搞多一些，以方便说明问题。
 *
 * 1.随便搞个非零的常数，作为hashCode的乘数，这里用了23，放在hashCode()方法里面
 *
 * 2.对equals方法所设计需要比较的每一个参数f，按如下方式计算其局部哈希值c：
 *      a.对boolean, byte, char, short, int, long, float, double这些基本数据类型，使用
 *      Type.hashCode(f),其中Type是对应基本类型的装箱类型
 *      b.对引用类型并且其equals也使用递归比较字段的，那么也递归调用其hashCode()以计算c;
 *      如果要实现更复杂的比较，那么就为它设计一个“范式”，然后在范式中调用hashCode.
 *      另外，若该字段为null则c = 0（其实也可以是其他常数，但一般都用0）
 *      c.对于数组字段，将其中所有有效字段单独计算局部哈希值然后求和;如果没有有效字段，
 *      则使用常量，但这时最好就不要使用0了。另外，如果数组中全部元素都有效，考虑使用
 *      {@link Array#hashCode()}
 *
 * 3.result = 31 * result + c，并将这个值返回
 *
 *      *选择31这个数字是因为它是奇素数，素数可以有效减少哈希值相同的情景，而奇数可以防止因位移而丢失信息
 *      (偶数为2的倍数，*2等价于向左移位)
 *      **result有一个非0的初始值，这样第二步中的0默认值就不会对它产生影响导致冲突增加
 *      ***有时需要基于参数顺序调整计算哈希值的参数，例如当计算String类型hashCode时，"add"与"dda"
 *      有不同返回值更有利于减少冲突。
 *      ****另外，数字31有个比较好的特性，就是可以通过位移和减法完成乘法操作(31 * i = i << 5 -i)，
 *      这种优化很多虚拟机可以自动完成
 *
 * 将它们运用在hashCode中得到这样的代码：{@link #hashCode()}.它是hashCode近乎完美的一个实现，
 * 媲美Java类库中的其他hashCode实现。但是这并不是最先进的，如果的确需要减少hashCode值的冲突，
 * 可以考虑使用Guava的com.google.common.hash.Hashing
 *
 *
 * @author LightDance
 */
public class BestPhoneNumber {
    /**
     * 性别
     */
    private boolean sex;
    /**
     * 亲密度
     */
    private float intimacy;
    /**
     * 电话号码
     */
    private String number;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BestPhoneNumber)) {
            return false;
        }
        BestPhoneNumber phoneNumber = (BestPhoneNumber) obj;

        return phoneNumber.number.equals(this.number) &&
                phoneNumber.sex == this.sex &&
                phoneNumber.intimacy == this.intimacy;
    }


    @Override
    public int hashCode() {
        int result = 23;
        result = 31 * result + number.hashCode();
        result = 31 * result + Float.hashCode(intimacy);
        result = 31 * result + Boolean.hashCode(sex);
        return result;
    }
}
