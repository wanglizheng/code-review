package com.inspur.health.inquiry.server;

import com.inspur.health.inquiry.domain.InquiryOrderRecord;
import com.inspur.health.inquiry.domain.author.UserInfo;
import com.inspur.healthcare.common.response.RestItemResult;

public interface InquiryStatusManager {

    public boolean passCheckParameter(InquiryOrderRecord record, UserInfo userInfo);

    public boolean haveInquiry(String patientId, String doctroId, int status);

    public RestItemResult execute(InquiryOrderRecord record, UserInfo userInfo,String receiver,String token);
}
