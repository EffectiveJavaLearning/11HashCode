/**
 * 跟电话簿配合使用的电话号码
 *
 * @author LightDance
 */
public class PhoneNumber {

    private String number;

    public String get() {
        return number;
    }

    public void set(String number) {
        this.number = number;
    }

    public PhoneNumber(String number) {
        this.number = number;
    }
}
