package Utils;

import Enums.PrecedenceEnum;
import Enums.ProtocolEnum;
import Enums.TOSEnum;

public class ParseIpUtils {
    //1. 返回版本号
    public String parseVersion(String s) {
        if (s.equals("4")) {
            return "IPV4";
        } else
            return "IPV6";
    }

    //2. 解析头长度
    public String parseHeadLength(String s) {
        int i = Integer.parseInt(s);
        return (i * 4) + "bytes";
    }

    //3. 服务类型 --- ---- -
    public String parseTOS(String s) {
        //优先级

        int i = Integer.parseInt(s, 16) >> 5;
        PrecedenceEnum precedence;
        switch (i) {
            case 7:
                precedence = PrecedenceEnum.NetworkControl;
                break;
            case 6:
                precedence = PrecedenceEnum.InternetworkControl;
                break;
            case 5:
                precedence = PrecedenceEnum.Critic;
                break;
            case 4:
                precedence = PrecedenceEnum.FlashOverride;
                break;
            case 3:
                precedence = PrecedenceEnum.Flash;
                break;
            case 2:
                precedence = PrecedenceEnum.Immediate;
                break;
            case 1:
                precedence = PrecedenceEnum.Priority;
                break;
            default:
                precedence = PrecedenceEnum.Routine;
                break;
        }
        //TOS
        int i1 = Integer.parseInt(s, 16) >> 1 & 0x0f;
        TOSEnum tos;
        switch (i1) {
            case 0:
                tos = TOSEnum.normal_service;
                break;
            case 1:
                tos = TOSEnum.minimize_delay;
                break;
            case 2:
                tos = TOSEnum.maximize_throughput;
                break;
            case 4:
                tos = TOSEnum.maximize_reliability;
                break;
            default:
                tos = TOSEnum.minimize_monetary_cost;
                break;
        }
        return precedence + " , " + tos;
    }

    public String parseTotalLength(String s) {
        return Integer.parseInt(s, 16) + "";
    }

    //标识
    public String parseId(String s) {
        return Integer.parseInt(s, 16) + "";
    }

    //生成标志和偏移值
    public String parseFlag(String s) {
        String[] s1 = s.split(" ");
        int flag = Integer.parseInt(s1[0], 16) >> 5;
        int DF = 0;
        int MF = 0;
        switch (flag) {
            case 0:
                break;
            case 1:
                MF = 1;
                break;
            case 2:
                DF = 1;
                break;
            default:
                DF = 1;
                MF = 1;
                break;
        }
        return "DF = " + DF + "  MF = " + MF;
    }

    public String parseOffset(String s) {
        String[] s1 = s.split(" ");
        String s2 = s1[0] + s1[1];
        int offset = Integer.parseInt(s2, 16) & 0x1f00;
        return offset + "";
    }

    //生存周期
    public String parseTTL(String s) {
        return Integer.parseInt(s, 16) + "";
    }

    //协议
    public String parseProtocol(String s) {
        int i = Integer.parseInt(s, 16);
        ProtocolEnum protocol;
        switch (i) {
            case 1:
                protocol = ProtocolEnum.ICMP;
                break;
            case 2:
                protocol = ProtocolEnum.IGMP;
                break;
            case 3:
                protocol = ProtocolEnum.GGP;
                break;
            case 5:
                protocol = ProtocolEnum.ST;
                break;
            case 6:
                protocol = ProtocolEnum.TCP;
                break;
            case 8:
                protocol = ProtocolEnum.EGP;
                break;
            case 9:
                protocol = ProtocolEnum.IGP;
                break;
            case 11:
                protocol = ProtocolEnum.NVP;
                break;
            default:
                protocol = ProtocolEnum.UDP;
                break;
        }
        return protocol + "";
    }

    //校验和
    public String parseCheckSum(String s) {
        return Integer.parseInt(s, 16) + "";
    }

    //源
    public String parseSource(String s) {
        return s;
    }

    //目的地
    public String parseDestination(String s) {
        return s;
    }
}
