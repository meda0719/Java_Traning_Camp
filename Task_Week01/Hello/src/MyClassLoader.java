import java.io.*;

public class MyClassLoader extends ClassLoader{
    String path = System.getProperty("user.dir")+"/out/production/Hello/";
    String fileType = ".xlass";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = loadClassData(name);
        return defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name){
        byte[] data = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try{
            System.out.println(path);
            in = new FileInputStream(new File(path + name + fileType));
            out = new ByteArrayOutputStream();
            int cnt = 0;
            while (-1 != (cnt = in.read())){
                out.write(255 - cnt);
            }
            data = out.toByteArray();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                in.close();
                out.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return data;
    }
}
