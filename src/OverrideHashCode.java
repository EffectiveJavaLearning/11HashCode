import java.util.Objects;

/**
 * 必须在每个重写equals()的类中重写{@link Object#hashCode()} ，否则将违反hashCode()的通用规约，
 * 然后在比如{@link java.util.HashMap},{@link java.util.HashSet}这种类里面使用时，
 * 出现各种奇怪的异常。hashCode的使用规约大致如下：
 *
 *  1.不改变在equals()中比较所涉及到的相关参数前提下，对某一对象多次执行hashCode，
 *  它必须返回相同值，而该应用本次执行结束后，下一次执行跟本次执行时的返回值是否相同就没啥要求了。
 *  2.如果x.equals(y) == true，那么x和y的hashCode()返回值也应相同。
 *  3.如果x.equals(y) == false，x和y的hashCode()返回值不要求绝对不同。但程序员应清楚，
 *  存在不同对象有相同hashCode的情况。可以考虑改用更好的映射函数尽量减少这种情况，以提供更好性能。
 *
 * 最关键的是第二条。根据equals方法，先后创建的两个实例在逻辑上相等是有可能的，但对于hashCode方法来讲，
 * 两者只是两个不同的对象，于是返回值也会不同。比如这个例子{@link PhoneBookTest}。而且，
 * 就算两对象哈希返回值恰好一样也不会将两实体弄混，因为HashMap在存储的时候，会同时存储key的哈希值和key的实例，
 * 而根据key去取对应value时{@link java.util.HashMap#get(Object)}，也会在算出hashCode值之后，
 * 在具有相同hashCode的元素链表中逐一对比，看key是否相同(x.equals(y) || x == y),
 * 只有这些条件全部满足才会确认是该元素。
 *
 * 要想修正这个问题，应该重写hashCode()方法的逻辑。首先如果想让其满足预期要求，那么有很简单很暴力的方式，
 * 比如这个{@link BadPhoneNumber}，直接返回个常数24出来。这样的确能令相同对象拥有相同hashCode，但如此一来，
 * HashMap中所有的元素就全部被放到了同一根链表里面，复杂度平了个方。
 *
 * 优秀的hashCode方法倾向于为不同的对象提供尽可能不同的hashCode值，而且各个相同hashCode值相同的链表中，
 * 元素个数也尽可能相同。不过这只是理想状态。下面给出一种较为理想的修改方式：{@link BestPhoneNumber}
 *
 * 重写hashCode后，需编码验证相等的实例是否有相同hashCode值（除非使用了AutoValue这样的工具，能保证无误），
 * 如果不能则找出问题并改正它。
 *
 * 其实，还有一种更偷懒的方式创建hashCode，即使用{@link java.util.Objects#hash(Object...)},
 * 但可惜由于用到了varargs新特性，以及基本类型的自动装箱和拆箱，速度较慢，只建议在对性能不作要求的条件下使用。
 * 比如{@link #hashCode()}
 *
 * 对于不可变且hashCode计算成本高的类，考虑在对象中缓存hashCode，仅仅当参数发生改变时才重新计算。
 * 如果能确认hashCode会作为key用在HashMap等类中，那么最好在实例创建的时候就把hashCode计算出来，否则就使用延迟加载。
 *
 * 不可以为了加快hashCode运行的速度就随随便便把什么参数(equals中涉及的)排除在计算范围之外，否则很可能影响
 * HashMap的性能。举个例子，Java1.2中的String.hashCode()仅仅取前16个字符进行hash计算，于是它遇到了URL...
 *
 * 不要对返回值做些什么规定，这样会降低其灵活性而且不利于以后的扩展和修改，虽然Java类库中有不少类已经这么干了，
 * 但是小朋友们千万不要模仿他们哦
 *
 * @author LightDance
 */
public class OverrideHashCode {
    int arg1;
    double arg2;
    short arg3;

    public OverrideHashCode(int arg1, double arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(arg1 , arg2);
    }
/*  延迟加载的hashCode代码
    private int hashCode;

    @Override
    public int hashCode() {
        int result = hashCode;
        if(result == 0){
            result = Integer.hashCode(arg1);
            result = 31 * result + Double.hashCode(arg2);
            result = 31 * result + Short.hashCode(arg3);
            hashCode = result;
        }
        return result;
    }
*/



}
