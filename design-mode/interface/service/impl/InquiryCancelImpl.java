package com.inspur.health.inquiry.server.iml;

import com.inspur.health.inquiry.common.InquiryStatusMachine;
import com.inspur.health.inquiry.constant.InquiryOrderStatusEnum;
import com.inspur.health.inquiry.constant.ResponseCodeEnum;
import com.inspur.health.inquiry.dao.InquiryDiagnosisRecordDao;
import com.inspur.health.inquiry.dao.InquiryOrderRecordDao;
import com.inspur.health.inquiry.domain.InquiryDiagnosisRecord;
import com.inspur.health.inquiry.domain.InquiryOrderRecord;
import com.inspur.health.inquiry.domain.author.UserInfo;
import com.inspur.health.inquiry.server.InquiryStatusManager;
import com.inspur.health.inquiry.server.JPushServer;
import com.inspur.healthcare.common.response.RestItemResult;
import com.inspur.healthcare.common.response.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 医生请求结束本次问诊，问诊状态转为待关闭
 */
@Component("cancelled")
public class InquiryCancel implements InquiryStatusManager {

    static final Logger logger = LoggerFactory.getLogger(InquiryCancel.class);

    static final InquiryOrderStatusEnum CANCEL = InquiryOrderStatusEnum.Cancelled;

    @Resource
    InquiryOrderRecordDao inquiryOrderRecordDao;

    @Resource
    InquiryDiagnosisRecordDao inquiryDiagnosisRecordDao;

    @Autowired
    InquiryStatusMachine inquiryStatusMachine;

    @Autowired
    JPushServer jPushServer;

    @Override
    public boolean passCheckParameter(InquiryOrderRecord record, UserInfo userInfo) {
        if( record == null || record.getId() == null ) {
            return false;
        }
        if( record.getDoctorId() == null && record.getPatientId() == null) {
            return false;
        }
        InquiryOrderRecord inquiryOrder = inquiryOrderRecordDao.getById(record.getId());
        if( record.getDoctorId() != null) {
            if(inquiryOrder == null || !record.getDoctorId().contentEquals(inquiryOrder.getDoctorId())) {
                return false;
            }
        }
        //jpush的内容
        record.setDocName(inquiryOrder.getDocName());
        record.setDocTitle(inquiryOrder.getDocTitle());
        record.setPatientId(inquiryOrder.getPatientId());
        record.setPatName(inquiryOrder.getPatName());

        return inquiryStatusMachine.passCheck(InquiryOrderStatusEnum.getEnumByValue(inquiryOrder.getStatus()), this.CANCEL);
    }

    @Override
    public boolean haveInquiry(String patientId, String doctorId, int status) {
        return false;
    }

    @Override
    public RestItemResult execute(InquiryOrderRecord record, UserInfo userInfo,String receiver,String token) {
        RestItemResult result = new RestItemResult();
        try {
            if(this.passCheckParameter(record, userInfo)) {
                inquiryOrderRecordDao.updateStatusById(record.getId(), InquiryOrderStatusEnum.Cancelled.getValue());
                jPushServer.pushMsgBaseOnStatus(record, InquiryOrderStatusEnum.Cancelled,receiver,token);
                result.setResult(ResultType.success);
                result.setCode(ResponseCodeEnum.SUCESS.getCode());
                result.setMessage(ResponseCodeEnum.SUCESS.getMsg());
            }else {
                logger.warn("InquiryClose passCheckParameter failed" + record.toString());
                result.setResult(ResultType.failure);
                result.setCode(ResponseCodeEnum.Parameter_Error.getCode());
                result.setMessage(ResponseCodeEnum.Parameter_Error.getMsg());
            }
            return result;
        }catch (Exception e) {
            logger.error("##InquiryClose## error");
            e.printStackTrace();
        }
        result.setResult(ResultType.failure);
        result.setCode(ResponseCodeEnum.Service_Error.getCode());
        result.setMessage(ResponseCodeEnum.Service_Error.getMsg());
        return result;
    }

    public RestItemResult execute(InquiryDiagnosisRecord diagnosisRecord, UserInfo userInfo, String receiver,String token) {
        InquiryOrderRecord record = new InquiryOrderRecord();
        record.setId(diagnosisRecord.getInquiryOrderId());
        record.setDoctorId(diagnosisRecord.getDoctorId());
        RestItemResult result = this.execute(record, userInfo,receiver,token);
        try {
            if(result.getCode() != ResponseCodeEnum.SUCESS.getCode()) {
                return result;
            }
            if(diagnosisRecord.getDiag() != null && !diagnosisRecord.getDiag().isEmpty()) {
                InquiryDiagnosisRecord diagNow =
                        inquiryDiagnosisRecordDao.getDiagRecordByInquiryId(diagnosisRecord.getInquiryOrderId());
                if(diagNow == null) {
                    inquiryDiagnosisRecordDao.insertInquiryDiagnosisRecord(diagnosisRecord);
                }else {
                    inquiryDiagnosisRecordDao.updateDiagRecord(diagnosisRecord);
                }
            }
            return result;
        }catch (Exception e) {
            logger.error("##InquiryClose## error");
            e.printStackTrace();
        }
        result.setResult(ResultType.failure);
        result.setCode(ResponseCodeEnum.Service_Error.getCode());
        result.setMessage(ResponseCodeEnum.Service_Error.getMsg());
        return result;
    }


}
