package safar.machine_learning.text_classification_api.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class Data<T> {
    public void serialize(T object){

    }
    public T deserialize(String filePath){
        T object = null;
        try {
            ClassLoader c1 = Thread.currentThread().getContextClassLoader();
            InputStream s1 = c1.getResourceAsStream(filePath);

            ObjectInputStream objectInputStream = new ObjectInputStream(s1);
            object = (T) objectInputStream.readObject();
            objectInputStream.close();
            s1.close();
        } catch (IOException | ClassNotFoundException e) {
        }

        return object;
    }
}
