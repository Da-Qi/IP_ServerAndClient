import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.Random;


public class IpPackageProduce extends Thread{

    @Override
    public void run() {
        Socket socket;
        try {
            socket = new Socket("127.0.0.1",8080);
            System.out.println("成功发送了一个ip数据包");
            Properties properties = new IpPackageProduce().IpCreate();
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(properties);
            oos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new IpPackageProduce().run();
        }
    }

    Properties IpCreate(){
        Properties properties = new Properties();
        Random random = new Random();
        //TOS
        int[] param = new int[]{0,1,2,4,8};
        //protocol
        int[] protocol = new int[]{1,2,3,5,6,8,9,11,17};
        //TTL
        int[] ttl = new int[]{1,32,64,128,256};
        //标志
        int[] sign = new int[]{0,1,2,3};
        //加入版本号和头长度
        properties.put(1,"4");
        properties.put(2,"5");
        //随机生成服务类型；0~7
        int type = ( random.nextInt(8));
        int service = (type*16+param[random.nextInt(param.length)])*2;
        properties.put(3,Integer.toHexString(service));
        //生成数据长度
        int totallength = 20+(random.nextInt(65535)-20+1);
        properties.put(4,Integer.toHexString(totallength));
        //生成数据报标识
        properties.put(5,Integer.toHexString(random.nextInt(65536)));
        //生成标志和偏移值
        properties.put(6,"40 00");
        //生成默认TTL
        properties.put(7,Integer.toHexString(ttl[random.nextInt(ttl.length)]));
        //生成协议
        properties.put(8,Integer.toHexString(protocol[random.nextInt(protocol.length)]));
        //生成校验和
        properties.put(9,Integer.toHexString(random.nextInt(65536)));
        //生成源和目的地
        properties.put(10,"192.168.0.169");
        properties.put(11,"127.0.0.1");
        return properties;
    }
}
