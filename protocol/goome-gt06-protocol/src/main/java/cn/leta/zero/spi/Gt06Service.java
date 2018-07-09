package cn.leta.zero.spi;

import cn.leta.zero.Gt06Constants.Command;

import cn.leta.zero.annotation.SPI;
import cn.leta.zero.annotation.ZeroBean;
import cn.leta.zero.dto.*;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-14.
 * @author Xie Gengcai
 */
@ZeroBean
public class Gt06Service {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 登录
     * @param request
     * @return
     */
    @SPI(cmd = Command.LOGIN, description = "登录")
    public CommonResult login(LoginRequest request) {
        logger.info("IMEI:{}", request.getImei());
        return new CommonResult();
    }

    @SPI(cmd = Command.GPS_MSG, description = "GPS信息")
    public CommonResult gpsMsg(GpsRequest request){
        logger.info("gpsMsg={}",JSON.toJSONString(request));
        return new CommonResult();
    }

    @SPI(cmd = Command.LBS_MSG, description = "LBS信息")
    public CommonResult lbsMsg(LbsRequest request){
        logger.info("lbsMsg={}",JSON.toJSONString(request));
        return new CommonResult();
    }
    @SPI(cmd = Command.LBS_COMPLETE_MSG, description = "LBS完整信息")
    public CommonResult lbsMsg(LbsCompleteRequest request){
        logger.info("lbsCompleteRequest={}",JSON.toJSONString(request));
        return new CommonResult();
    }
    @SPI(cmd = Command.STATE_MSG, description = "状态信息")
    public CommonResult stateMsg(StateRequest request){
        logger.info("StateRequest={}",JSON.toJSONString(request));
        return new CommonResult();
    }
    @SPI(cmd = Command.GPS_LBS_MSG, description = "GPS、LBS合并信息")
    public CommonResult gpsLbsMsg(GpsLbsRequest request){
        logger.info("GpsLbsRequest={}",JSON.toJSONString(request));
        return new CommonResult();
    }
    @SPI(cmd = Command.GPS_LBS_STATE_MSG)
    public CommonResult gpsLbsStateMergeMsg(GpsLbaStateRequest request){
        logger.info("GpsLbaStateRequest={}",JSON.toJSONString(request));
        return new CommonResult();
    }
    @SPI(cmd = Command.LBS_PHONE_SEARCH_MSG, description = "LBS、电话号码查询信息")
    public CommonResult lbsPhoneNumberSearchMsg(LbsPhoneRquest request){
        logger.info("LbsPhoneRquest={}",JSON.toJSONString(request));
        return new CommonResult();
    }

    @SPI(cmd = Command.SATELLITE_SNR, description = "卫星信噪比")
    public CommonResult satelliteSNR(SatelliteSnrRequest request){
        logger.info("SatelliteSnrRequest={}",JSON.toJSONString(request));
        return new CommonResult();
    }


}
