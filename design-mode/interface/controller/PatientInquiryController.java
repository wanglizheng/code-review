package com.inspur.health.inquiry.controller;

import com.inspur.health.inquiry.constant.InquiryOrderStatusEnum;
import com.inspur.health.inquiry.constant.InquiryTopicEnum;
import com.inspur.health.inquiry.constant.ResponseCodeEnum;
import com.inspur.health.inquiry.domain.DoctorSatisticInfo;
import com.inspur.health.inquiry.domain.InquiryDiagnosisRecord;
import com.inspur.health.inquiry.domain.author.UserInfo;
import com.inspur.health.inquiry.domain.req.InquiryOrderQueryDto;
import com.inspur.health.inquiry.domain.InquiryOrderRecord;
import com.inspur.health.inquiry.server.*;
import com.inspur.healthcare.common.response.RestItemResult;
import com.inspur.healthcare.common.response.ResultType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//1、注入一个MAP：Map<String, InquiryStatusManager> inquiryStatusManagerMap;
//2、通过注入的Map动态获取： InquiryStatusManager manager = inquiryStatusManagerMap.get(InquiryOrderStatusEnum.REGISTER.getManager());
    InquiryStatusManager manager = inquiryStatusManagerMap.get("cancelled")


@Api(tags = {"患者在线问询"})
@RestController
@RequestMapping(value = "/patient/api")
public class PatientInquiryController {
    Logger log = LoggerFactory.getLogger(PatientInquiryController.class);

    @Autowired
    InquiryFlowServer inquiryFlowServer;

    @Autowired
    DoctorSatisticServer doctorSatisticServer;

    @Autowired
    Map<String, InquiryStatusManager> inquiryStatusManagerMap;

    @Autowired
    JPushServer jPushServer;

    @ApiOperation(value = "患者挂号问诊new", httpMethod = "POST")
    @PostMapping("/v1/register/{topic}")
    @AuthCheck
    public RestItemResult<InquiryOrderRecord> registerv2(@PathVariable String topic,
                                                         @RequestBody InquiryOrderRecord inquiryOrder,
                                                         HttpServletRequest request,
                                                         UserInfo userInfo) {
        RestItemResult<InquiryOrderRecord> response = new RestItemResult<>();

        String appid = request.getHeader("APPID");
        String token = request.getHeader("token");
        String receiver;
        if ("TNA_APP".equals(appid)) {
            receiver = "user";
        } else if ("HET_APP".equals(appid)) {
            receiver = "neimeng";
        } else {
            receiver = "user";
        }

        InquiryStatusManager manager = inquiryStatusManagerMap.get(InquiryOrderStatusEnum.REGISTER.getManager());

        if (!manager.haveInquiry(userInfo.getUserId(), inquiryOrder.getDoctorId(), 205)) {
            response.setCode(ResponseCodeEnum.Permit_Info.getCode());
            response.setMessage(ResponseCodeEnum.Permit_Info.getMsg());
            response.setResult(ResultType.failure);
            return response;
        }

        try {
            if (inquiryOrder == null || topic == null) {
                log.warn("##PatientInquiryController.registerv2## inquiryOrder:" + inquiryOrder + " topic:" + topic);
                return response;
            }
            inquiryOrder.setTopic(InquiryTopicEnum.getEnumBySpec(topic).getTopic());
            log.info("##PatientInquiryController.registerv2##" + inquiryOrder.toString());
            //manager = inquiryStatusManagerMap.get(InquiryOrderStatusEnum.REGISTER.getManager());
            if (manager != null) {
                return manager.execute(inquiryOrder, userInfo, receiver, token);
            }
        } catch (Exception e) {
            log.error("##PatientInquiryController.registerv2## ERROR");
            e.printStackTrace();
            response.setCode(ResponseCodeEnum.Service_Error.getCode());
            response.setMessage(ResponseCodeEnum.Service_Error.getMsg());
            response.setResult(ResultType.failure);
        }
        return response;
    }

    @ApiOperation(value = "患者挂号问诊old", httpMethod = "POST")
    @PostMapping("/v2/register/{topic}")
    @AuthCheck
    public RestItemResult<InquiryOrderRecord> register(@PathVariable String topic, @RequestBody InquiryOrderRecord inquiryOrder) {
        RestItemResult<InquiryOrderRecord> response = new RestItemResult<>();
        if (!inquiryFlowServer.checkRegsiterParam(inquiryOrder)) {
            response.setCode(ResponseCodeEnum.Parameter_Error.getCode());
            response.setMessage(ResponseCodeEnum.Parameter_Error.getMsg());
            response.setResult(ResultType.failure);
            return response; //
        }
        try {
            inquiryOrder.setTopic(InquiryTopicEnum.getEnumBySpec(topic).getTopic());
            InquiryOrderRecord record = inquiryFlowServer.registerInquiry(inquiryOrder);
            log.info("#PatientInquiryController.register#患者挂号问诊结果: " + record.toString());
            if (record.getId() != null) {
                doctorSatisticServer.increaseDoctorRegisterInquiryNum(inquiryOrder.getDoctorId(), inquiryOrder.getDocName());
                response.setCode(ResponseCodeEnum.SUCESS.getCode());
                response.setResult(ResultType.success);
                response.setMessage(ResponseCodeEnum.SUCESS.getMsg());
                response.setItem(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCodeEnum.Service_Error.getCode());
            response.setMessage(ResponseCodeEnum.Service_Error.getMsg());
            response.setResult(ResultType.failure);
        }
        return response;
    }

    @ApiOperation(value = "结束本次问询new")
    @GetMapping("/v1/close")
    @AuthCheck
    public RestItemResult closeByPatientv2(@RequestParam(value = "id") Long id,
                                           @RequestParam(value = "patId") String patientId,
                                           @RequestParam(value = "type", defaultValue = "1") Integer type,
                                           HttpServletRequest httpServletRequest,
                                           UserInfo userInfo) {
        InquiryStatusManager manager = null;

        String appid = httpServletRequest.getHeader("APPID");
        String token = httpServletRequest.getHeader("token");
        log.info("from request header, your appip: " + appid);

        String receiver;
        if ("TNA_APP".equals(appid)) {
            receiver = "user";
        } else if ("HET_APP".equals(appid)) {
            receiver = "neimeng";
        } else {
            receiver = "user";
        }

        try {
            log.info("##PatientInquiryController.closeByPatientv2## inquiryOrderId: " + id + " patientId:" + patientId + " type: " + type);
            if (type == null || type == 1) {
                manager = inquiryStatusManagerMap.get(InquiryOrderStatusEnum.EVALUATING.getManager());
            } else if (type == 2) {
                manager = inquiryStatusManagerMap.get(InquiryOrderStatusEnum.Cancelled.getManager());
            } else if (type == 3) {
                manager = inquiryStatusManagerMap.get(InquiryOrderStatusEnum.EVALUATING.getManager());
            } else {
                manager = inquiryStatusManagerMap.get(InquiryOrderStatusEnum.RefuseCLOSE.getManager());
            }
            if (manager != null) {
                InquiryOrderRecord request = new InquiryOrderRecord();
                request.setId(id);
                request.setPatientId(patientId);
                return manager.execute(request, userInfo, receiver, token);
            }
        } catch (Exception e) {
            log.error("##PatientInquiryController.closeByPatientv2## ERROR");
            e.printStackTrace();
        }

        RestItemResult response = new RestItemResult();
        response.setCode(ResponseCodeEnum.Service_Error.getCode());
        response.setMessage(ResponseCodeEnum.Service_Error.getMsg());
        response.setResult(ResultType.failure);
        return response;
    }

    @ApiOperation(value = "获取统计信息")
    @PostMapping("/v1/getStatisticList")
    @AuthCheck
    public RestItemResult<Map<String, HashMap<String, String>>> getStatisticList(@RequestBody List<String> docIds) {
        RestItemResult<Map<String, HashMap<String, String>>> response = new RestItemResult();
        if (docIds == null || docIds.isEmpty()) {
            return response;
        }
        try {
            log.info("##PatientInquiryController.getStatisticList##docIds:" + docIds.toString());
            List<DoctorSatisticInfo> doctorSatisticInfoList = doctorSatisticServer.getListByDoctorIds(docIds);

            Map<String, HashMap<String, String>> resultMutiMap = new HashMap<>();
            for (DoctorSatisticInfo info : doctorSatisticInfoList) {
                HashMap<String, String> tmp = new HashMap<>();
                tmp.put("acceptNum", info.getAcceptedInquiryNum() == null ? null : info.getAcceptedInquiryNum().toString());
                tmp.put("praiseRate", info.getPraiseRate() == null ? null : info.getPraiseRate().toString());
                resultMutiMap.put(info.getDoctorId(), tmp);
            }
            response.setCode(ResponseCodeEnum.SUCESS.getCode());
            response.setResult(ResultType.success);
            response.setMessage(ResponseCodeEnum.SUCESS.getMsg());
            response.setItem(resultMutiMap);
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCodeEnum.Service_Error.getCode());
            response.setResult(ResultType.failure);
            response.setMessage(ResponseCodeEnum.Service_Error.getMsg());
        }
        return response;
    }

    @ApiOperation(value = "查询问询列表", httpMethod = "POST")
    @PostMapping("/v1/getInquiryList")
    @AuthCheck
    public RestItemResult<List<InquiryOrderRecord>> getInquiryList(
            @RequestBody InquiryOrderQueryDto inquiryOrderQuery, UserInfo userInfo) {
        inquiryOrderQuery.setPatientId(userInfo.getUserId());
        RestItemResult<List<InquiryOrderRecord>> response = new RestItemResult();
        try {
            if (inquiryOrderQuery == null || !inquiryOrderQuery.passCheck()) {
                response.setCode(ResponseCodeEnum.Parameter_Error.getCode());
                response.setMessage(ResponseCodeEnum.Parameter_Error.getMsg());
                response.setResult(ResultType.failure);
                return response;
            }
            log.info("#PatientInquiryController.getInquiryList#" + inquiryOrderQuery.toString());

            List<InquiryOrderRecord> resultList = inquiryFlowServer.getInquiryList(inquiryOrderQuery);
            response.setCode(ResponseCodeEnum.SUCESS.getCode());
            response.setMessage(ResponseCodeEnum.SUCESS.getMsg());
            response.setResult(ResultType.success);
            response.setItem(resultList);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCodeEnum.Service_Error.getCode());
            response.setMessage(ResponseCodeEnum.Service_Error.getMsg());
            response.setResult(ResultType.failure);
            return response;
        }
    }

    @ApiOperation(value = "获取统计信息")
    @GetMapping("/v1/getStatisticInfo")
    @AuthCheck
    public RestItemResult<HashMap<String, Object>> getStatisticInfo(@RequestParam(value = "doctorId") String doctorId) {
        RestItemResult<HashMap<String, Object>> response = new RestItemResult();
        if (doctorId == null || doctorId.isEmpty()) {
            log.warn("##PatientInquiryController.getStatisticInfo##doctorId is NULLg");
            return response;
        }
        try {
            log.info("##PatientInquiryController.getStatisticInfo##doctorId:" + doctorId.toString());
            DoctorSatisticInfo info = doctorSatisticServer.getDoctorSatisticInfoByDoctorId(doctorId);
            if (info != null) {
                HashMap<String, Object> tmp = new HashMap<>();
                tmp.put("acceptNum", info.getAcceptedInquiryNum());
                tmp.put("praiseRate", info.getPraiseRate());
                response.setItem(tmp);
            }
            response.setCode(ResponseCodeEnum.SUCESS.getCode());
            response.setResult(ResultType.success);
            response.setMessage(ResponseCodeEnum.SUCESS.getMsg());

        } catch (Exception e) {
            e.printStackTrace();
            response.setResult(ResultType.failure);
            response.setCode(ResponseCodeEnum.Service_Error.getCode());
            response.setMessage(ResponseCodeEnum.Service_Error.getMsg());
        }
        return response;
    }


    @ApiOperation(value = "获取诊断信息")
    @GetMapping("/v1/getDiagnosis")
    @AuthCheck
    public RestItemResult<InquiryDiagnosisRecord> getDiagnosis(@RequestParam(value = "inquiryOrderId") Long inquiryOrderId) {
        RestItemResult<InquiryDiagnosisRecord> response = new RestItemResult();
        try {
            log.info("##PatientInquiryController.getDiagnosis##inquiryOrderId:" + inquiryOrderId);
            InquiryDiagnosisRecord record = inquiryFlowServer.getDiagnosisByInquiryId(inquiryOrderId);
            response.setCode(ResponseCodeEnum.SUCESS.getCode());
            response.setResult(ResultType.success);
            response.setMessage(ResponseCodeEnum.SUCESS.getMsg());
            response.setItem(record);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult(ResultType.failure);
            response.setCode(ResponseCodeEnum.Service_Error.getCode());
            response.setMessage(ResponseCodeEnum.Service_Error.getMsg());
        }
        return response;
    }
}
