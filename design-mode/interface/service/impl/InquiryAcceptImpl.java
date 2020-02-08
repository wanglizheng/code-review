package com.inspur.health.inquiry.server.iml;

import com.inspur.health.inquiry.common.InquiryStatusMachine;
import com.inspur.health.inquiry.constant.InquiryOrderStatusEnum;
import com.inspur.health.inquiry.constant.ResponseCodeEnum;
import com.inspur.health.inquiry.dao.InquiryOrderRecordDao;
import com.inspur.health.inquiry.domain.InquiryOrderRecord;
import com.inspur.health.inquiry.domain.author.UserInfo;
import com.inspur.health.inquiry.server.DoctorSatisticServer;
import com.inspur.health.inquiry.server.InquiryStatusManager;
import com.inspur.health.inquiry.server.JPushServer;
import com.inspur.healthcare.common.response.RestItemResult;
import com.inspur.healthcare.common.response.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("accepted")
public class InquiryAccept implements InquiryStatusManager {

    final Logger log = LoggerFactory.getLogger(InquiryAccept.class);

    final InquiryOrderStatusEnum accept = InquiryOrderStatusEnum.ACCEPTED;

    @Resource
    InquiryOrderRecordDao inquiryOrderRecordDao;

    @Autowired
    InquiryStatusMachine inquiryStatusMachine;

    @Autowired
    DoctorSatisticServer doctorSatisticServer;

    @Autowired
    JPushServer jPushServer;

    @Override
    public boolean passCheckParameter(InquiryOrderRecord record, UserInfo userInfo) {
        if(record.getId() == null || record.getDoctorId() == null) {
            return false;
        }
        InquiryOrderRecord inquiryOrder = inquiryOrderRecordDao.getById(record.getId());
        if(inquiryOrder == null || !record.getDoctorId().contentEquals(inquiryOrder.getDoctorId())) {

            return false;
        }

        record.setPatientId(inquiryOrder.getPatientId());
        record.setPatName(inquiryOrder.getPatName());
        record.setDocName(inquiryOrder.getDocName());
        record.setDocTitle(inquiryOrder.getDocTitle());

        return inquiryStatusMachine.passCheck(InquiryOrderStatusEnum.getEnumByValue(inquiryOrder.getStatus()), this.accept);
    }

    @Override
    public boolean haveInquiry(String patientId, String doctorId, int status) {
        return false;
    }

    @Override
    public RestItemResult execute(InquiryOrderRecord record, UserInfo userInfo,String receiver,String token) {
        RestItemResult result = new RestItemResult();
        try {
            if(passCheckParameter(record, userInfo)) {
                inquiryOrderRecordDao.updateStatusById(record.getId(), this.accept.getValue());
                doctorSatisticServer.acceptInquiry(record.getDoctorId());
                jPushServer.pushMsgBaseOnStatus(record, this.accept,receiver,token);
                result.setResult(ResultType.success);
                result.setCode(ResponseCodeEnum.SUCESS.getCode());
                result.setMessage(ResponseCodeEnum.SUCESS.getMsg());
            }else {
                log.warn("InquiryAccept passCheckParameter failed" + record.toString());
                result.setResult(ResultType.failure);
                result.setCode(ResponseCodeEnum.Parameter_Error.getCode());
                result.setMessage(ResponseCodeEnum.Parameter_Error.getMsg());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        result.setResult(ResultType.failure);
        result.setCode(ResponseCodeEnum.Service_Error.getCode());
        result.setMessage(ResponseCodeEnum.Service_Error.getMsg());
        return result;
    }
}
