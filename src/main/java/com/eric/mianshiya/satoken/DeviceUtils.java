package com.eric.mianshiya.satoken;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.eric.mianshiya.common.ErrorCode;
import com.eric.mianshiya.exception.ThrowUtils;

import javax.servlet.http.HttpServletRequest;

public class DeviceUtils {
    /**
     * 根据请求获取设备信息
     * @param request
     * @return
     */
    public static String getRequestDevice(HttpServletRequest request){
        String userAgentStr = request.getHeader(Header.USER_AGENT.toString());
        //使用Hutool解析UserAgent
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        ThrowUtils.throwIf(userAgent==null, ErrorCode.PARAMS_ERROR,"非法请求");
        //默认是PC
        String device="pc";
        //是否是小程序
        if(isMiniProgram(userAgentStr)){
            device="miniProgram";
        }else if(isPad(userAgentStr)){
            device="pad";
        }else if(userAgent.isMobile()){
            device="mobile";
        }
        return device;
    }

    private static boolean isMiniProgram(String userAgentStr){
        return StrUtil.containsIgnoreCase(userAgentStr,"MicroMessenger")
                &&StrUtil.containsIgnoreCase(userAgentStr,"MiniProgram");
    }

    private static boolean isPad(String userAgentStr){
        boolean isIpad = StrUtil.containsIgnoreCase(userAgentStr, "iPad");
        boolean isAndroidTablet = StrUtil.containsIgnoreCase(userAgentStr,"Android")
                && !StrUtil.containsIgnoreCase(userAgentStr,"Mobile");
        return isIpad||isAndroidTablet;
    }
}




