import Utils.ParseIpUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;


public class ReceiveIp{

    public static void main(String[] args) {
        System.out.println("接收线程启动了");
        while (true) {
            try {
                ServerSocket serverSocket;
                serverSocket = new ServerSocket(8080);
                Socket accept = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(accept.getInputStream());
                Object o = ois.readObject();
                System.out.println("成功解析一个数据包");
                Map<String, String> map = new ReceiveIp().parseIp((Properties) o);
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    System.out.println(entry.getKey()+" : "+entry.getValue());
                }
                System.out.println(" ");
                accept.close();
                serverSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    //解析properties
    Map<String,String> parseIp(Properties properties){
        ParseIpUtils parseIp = new ParseIpUtils();
        Map<String,String> map = new LinkedHashMap<>();
        //版本信息
        map.put("版本",parseIp.parseVersion((String) properties.get(1)));
        //首部长度
        map.put("首部长度",parseIp.parseHeadLength((String)properties.get(2)));
        //服务类型
        map.put("服务类型",parseIp.parseTOS((String)properties.get(3)));
        //数据长度
        map.put("数据长度",parseIp.parseTotalLength((String)properties.get(4)));
        //数据报标识
        map.put("数据报标识",parseIp.parseId((String)properties.get(5)));
        //标志和偏移值
        map.put("标志",parseIp.parseFlag((String)properties.get(6)));
        map.put("片位移",parseIp.parseOffset((String)properties.get(6)));
        //生存周期
        map.put("生存周期",parseIp.parseTTL((String)properties.get(7)));
        //协议
        map.put("协议",parseIp.parseProtocol((String)properties.get(8)));
        //校验和
        map.put("校验和",parseIp.parseCheckSum((String)properties.get(9)));
        //源
        map.put("源IP地址",parseIp.parseSource((String)properties.get(10)));
        //目的地
        map.put("目的IP地址",parseIp.parseDestination((String)properties.get(11)));

        return map;
    }
}
