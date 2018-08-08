import java.util.HashMap;
import java.util.Map;

/**
 * 电话簿，用于说明为什么重写equals一定要同时重写hashCode
 *
 * 我们希望只要把相同的电话号码当作参数传进去，就可以得到相同的联系人姓名，但是实际上，
 * 由于没有重写equals方法，两个new出来的PhoneNumber是两个不同的对象，
 * 于是.get方法当然查不到对应的联系人姓名
 *
 * @author LightDance
 */
public class PhoneBookTest {
    private static Map<PhoneNumber , String> phoneBook= new HashMap<>();

    public static void main(String[] args) {
        phoneBook.put(new PhoneNumber("773,617,3499") , "lightDance");

        String targetName = phoneBook.get(new PhoneNumber("773,617,3499"));

        System.out.println(targetName);
    }
}
