import com.duling.service.demo;

import java.util.Iterator;
import java.util.ServiceLoader;

public class TestSPI {
    public static void main(String[] args) {
        ServiceLoader<demo> load = ServiceLoader.load(demo.class);
        Iterator<demo> iterator = load.iterator();
        //这个是去META-INF/services的文件
        while (iterator.hasNext()){
            demo next = iterator.next();
            next.say();
        }
    }
}
