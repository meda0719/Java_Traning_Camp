import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloTest {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        MyClassLoader loader = new MyClassLoader();
        Class<?> aClass = loader.findClass("Hello");
        Method m1 = aClass.getDeclaredMethod("hello");
        m1.invoke(aClass.newInstance());


    }


}
