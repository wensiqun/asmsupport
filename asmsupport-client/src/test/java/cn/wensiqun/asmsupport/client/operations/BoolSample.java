package cn.wensiqun.asmsupport.client.operations;

public class BoolSample {

    public static String excepted(boolean b1, boolean b2, boolean b3, boolean b4) {
        StringBuilder sb = new StringBuilder();
        sb.append(b1 && b2 && b3 && b4).append("|");
        sb.append(b1 && b2 && b3 || b4).append("|");
        sb.append(b1 && b2 || b3 && b4).append("|");
        sb.append(b1 && b2 || b3 || b4).append("|");
        sb.append(b1 || b2 && b3 && b4).append("|");
        sb.append(b1 || b2 && b3 || b4).append("|");
        sb.append(b1 || b2 || b3 && b4).append("|");
        sb.append(b1 || b2 || b3 || b4).append("|");

        
        
        sb.append((b1 && b2) && b3 && b4).append("|");
        sb.append(b1 && (b2 && b3) && b4).append("|");
        sb.append(b1 && b2 && (b3 && b4)).append("|");

        sb.append((b1 && b2) && b3 || b4).append("|");
        sb.append(b1 && (b2 && b3) || b4).append("|");
        sb.append(b1 && b2 && (b3 || b4)).append("|");

        sb.append((b1 && b2) || b3 && b4).append("|");
        sb.append(b1 && (b2 || b3) && b4).append("|");
        sb.append(b1 && b2 || (b3 && b4)).append("|");

        sb.append((b1 && b2) || b3 || b4).append("|");
        sb.append(b1 && (b2 || b3) || b4).append("|");
        sb.append(b1 && b2 || (b3 || b4)).append("|");

        sb.append((b1 || b2) && b3 && b4).append("|");
        sb.append(b1 || (b2 && b3) && b4).append("|");
        sb.append(b1 || b2 && (b3 && b4)).append("|");

        sb.append((b1 || b2) && b3 || b4).append("|");
        sb.append(b1 || (b2 && b3) || b4).append("|");
        sb.append(b1 || b2 && (b3 || b4)).append("|");

        sb.append((b1 || b2) || b3 && b4).append("|");
        sb.append(b1 || (b2 || b3) && b4).append("|");
        sb.append(b1 || b2 || (b3 && b4)).append("|");

        sb.append((b1 || b2) || b3 || b4).append("|");
        sb.append(b1 || (b2 || b3) || b4).append("|");
        sb.append(b1 || b2 || (b3 || b4)).append("|");

        
        
        sb.append((b1 && b2) && (b3 && b4)).append("|");
        sb.append((b1 && b2) && (b3 || b4)).append("|");
        sb.append((b1 && b2) || (b3 && b4)).append("|");
        sb.append((b1 && b2) || (b3 || b4)).append("|");
        sb.append((b1 || b2) && (b3 && b4)).append("|");
        sb.append((b1 || b2) && (b3 || b4)).append("|");
        sb.append((b1 || b2) || (b3 && b4)).append("|");
        sb.append((b1 || b2) || (b3 || b4)).append("|");

        sb.append(b1 && b2 || b3 | b4 && b2 & b3 ^ b1 && b1);

        return sb.toString();
    }

}
