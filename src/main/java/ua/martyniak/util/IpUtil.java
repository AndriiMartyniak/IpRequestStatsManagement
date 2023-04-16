package ua.martyniak.util;

import java.util.regex.Pattern;

public class IpUtil {
    public int getIntFromString(String ip) {
        String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(ip).matches()){
            throw new RuntimeException("Not valid ip address");
        }
        String[] ipByDot = ip.split("\\.");
        int result = 0;
        for (String strFragment : ipByDot) {
            int fragment = Integer.parseInt(strFragment);
            result = (result << 8) | fragment;
        }
        return result;
    }

    public String getStringFromInteger(int ip) {
        StringBuilder sb = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            int fragment = (ip >> (8 * i)) & 0xFF;
            sb.append(fragment);
            if (i > 0) {
                sb.append(".");
            }
        }
        return sb.toString();
    }
}
