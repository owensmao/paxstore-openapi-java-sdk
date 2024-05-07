/*
 * *******************************************************************************
 * COPYRIGHT
 *               PAX TECHNOLOGY, Inc. PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or
 *   nondisclosure agreement with PAX  Technology, Inc. and may not be copied
 *   or disclosed except in accordance with the terms in that agreement.
 *
 *      Copyright (C) 2017 PAX Technology, Inc. All rights reserved.
 * *******************************************************************************
 */
package com.pax.market.api.sdk.java.api.terminal;


import com.google.gson.Gson;
import com.pax.market.api.sdk.java.api.BaseThirdPartySysApi;
import com.pax.market.api.sdk.java.api.base.dto.EmptyResponse;
import com.pax.market.api.sdk.java.api.base.dto.Result;
import com.pax.market.api.sdk.java.api.base.request.SdkRequest;
import com.pax.market.api.sdk.java.api.base.request.SdkRequest.RequestMethod;
import com.pax.market.api.sdk.java.api.client.ThirdPartySysApiClient;
import com.pax.market.api.sdk.java.api.constant.Constants;
import com.pax.market.api.sdk.java.api.terminal.dto.*;
import com.pax.market.api.sdk.java.api.terminal.validator.TerminalCopyRequestValidator;
import com.pax.market.api.sdk.java.api.terminal.validator.TerminalRequestValidator;
import com.pax.market.api.sdk.java.api.terminalGroup.dto.TerminalSnGroupRequest;
import com.pax.market.api.sdk.java.api.util.EnhancedJsonUtils;
import com.pax.market.api.sdk.java.api.validate.Validators;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

/**
 * @author tanjie
 * @date 2018-07-02
 */
public class TerminalSnApi extends BaseThirdPartySysApi {
    private static final Logger logger = LoggerFactory.getLogger(TerminalSnApi.class.getSimpleName());

    protected static final String GET_TERMINAL_URL = "/v1/3rdsys/terminal";

    protected static final String ACTIVE_TERMINAL_URL = "/v1/3rdsys/terminal/active";

    protected static final String DISABLE_TERMINAL_URL = "/v1/3rdsys/terminal/disable";

    protected static final String MOVE_TERMINAL_URL = "/v1/3rdsys/terminal/move";

    protected static final String DELETE_TERMINAL_URL = "/v1/3rdsys/terminal";

    protected static final String UPDATE_TERMINAL_URL = "/v1/3rdsys/terminal";

    protected static final String COPY_TERMINAL_URL = "/v1/3rdsys/terminal/copy";

    protected static final String ADD_TERMINAL_TO_GROUP_URL = "/v1/3rdsys/terminal/group";

    protected static final String UPDATE_TERMINAL_REMOTE_CONFIG_URL = "/v1/3rdsys/terminal/config";

    protected static final String GET_TERMINAL_REMOTE_CONFIG_URL = "/v1/3rdsys/terminal/config";

    protected static final String GET_TERMINAL_PED_STATUS_URL = "/v1/3rdsys/terminal/ped";

    protected static final String PUSH_TERMINAL_ACTION_URL = "/v1/3rdsys/terminal/operation";


    public TerminalSnApi(String baseUrl, String apiKey, String apiSecret) {
        super(baseUrl, apiKey, apiSecret);
    }

    public TerminalSnApi(String baseUrl, String apiKey, String apiSecret, Locale locale) {
        super(baseUrl, apiKey, apiSecret, locale);
    }

    public Result<TerminalDTO> getTerminal(String serialNo) {
       return getTerminal(serialNo,false,false);
    }

    public Result<TerminalDTO> getTerminal(String serialNo, boolean includeDetailInfoList) {
        return getTerminal(serialNo, includeDetailInfoList, false);
    }

    public Result<TerminalDTO> getTerminal(String serialNo, boolean includeDetailInfoList, boolean includeInstalledApks) {
        logger.debug("serialNo= {}", serialNo);
        List<String> validationErrs = Validators.validateStr(serialNo, "parameter.not.empty", "serialNo");
        if (!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(GET_TERMINAL_URL);
        request.addRequestParam("includeDetailInfoList", String.valueOf(includeDetailInfoList));
        request.addRequestParam("includeInstalledApks", String.valueOf(includeInstalledApks));
        request.addRequestParam("serialNo", StringUtils.trim(serialNo));
        TerminalResponseDTO terminalResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalResponseDTO.class);
        return new Result<>(terminalResponse);
    }

    public Result<String> activateTerminal(String serialNo) {
        logger.debug("serialNo= {}", serialNo);
        List<String> validationErrs = Validators.validateStr(serialNo, "parameter.not.empty", "serialNo");
        if (!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(ACTIVE_TERMINAL_URL);
        request.setRequestMethod(RequestMethod.PUT);
        request.addRequestParam("serialNo", StringUtils.trim(serialNo));
        EmptyResponse emptyResponse = EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return new Result<>(emptyResponse);
    }

    public Result<String> disableTerminal(String serialNo) {
        logger.debug("serialNo= {}", serialNo);
        List<String> validationErrs = Validators.validateStr(serialNo, "parameter.not.empty", "serialNo");
        if (!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(DISABLE_TERMINAL_URL);
        request.setRequestMethod(RequestMethod.PUT);
        request.addRequestParam("serialNo", StringUtils.trim(serialNo));
        EmptyResponse emptyResponse = EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return new Result<>(emptyResponse);
    }

    public Result<String> moveTerminal(String serialNo, String resellerName, String merchantName) {
        logger.debug("serialNo= {}", serialNo);
        List<String> validationErrs = Validators.validateStr(serialNo, "parameter.not.empty", "serialNo");
        if (!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(MOVE_TERMINAL_URL);
        request.setRequestMethod(RequestMethod.PUT);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        TerminalMoveRequest terminalMoveRequest = new TerminalMoveRequest();
        terminalMoveRequest.setResellerName(resellerName);
        terminalMoveRequest.setMerchantName(merchantName);
        request.addRequestParam("serialNo", StringUtils.trim(serialNo));
        request.setRequestBody(new Gson().toJson(terminalMoveRequest, TerminalMoveRequest.class));
        EmptyResponse emptyResponse = EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return new Result<>(emptyResponse);
    }

    public Result<String> deleteTerminal(String serialNo) {
        logger.debug("serialNo= {}", serialNo);
        List<String> validationErrs = Validators.validateStr(serialNo, "parameter.not.empty", "serialNo");
        if (!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(DELETE_TERMINAL_URL);
        request.setRequestMethod(RequestMethod.DELETE);
        request.addRequestParam("serialNo", StringUtils.trim(serialNo));
        EmptyResponse emptyResponse = EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return new Result<>(emptyResponse);
    }

    public Result<TerminalDTO> updateTerminal(String serialNo, TerminalUpdateRequest terminalUpdateRequest) {
        logger.debug("serialNo= {}", serialNo);
        List<String> validationErrs = Validators.validateStr(serialNo, "parameter.not.empty", "serialNo");
        validationErrs.addAll(TerminalRequestValidator.validate(terminalUpdateRequest, "terminalUpdateRequest"));
        if (!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(UPDATE_TERMINAL_URL);
        request.setRequestMethod(RequestMethod.PUT);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.addRequestParam("serialNo", StringUtils.trim(serialNo));
        request.setRequestBody(new Gson().toJson(terminalUpdateRequest, TerminalUpdateRequest.class));
        TerminalResponseDTO terminalResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalResponseDTO.class);
        return new Result<>(terminalResponse);
    }


    public Result<TerminalDTO> copyTerminal(TerminalSnCopyRequest terminalCopyRequest) {
        List<String> validationErrs = TerminalCopyRequestValidator.validateSerialNo(terminalCopyRequest);
        if (!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        logger.debug("serialNo= {}", terminalCopyRequest.getSerialNo());
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(COPY_TERMINAL_URL);
        request.setRequestMethod(RequestMethod.POST);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.setRequestBody(new Gson().toJson(terminalCopyRequest, TerminalCopyRequest.class));
        TerminalResponseDTO terminalResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalResponseDTO.class);
        return new Result<>(terminalResponse);
    }

    public Result<String> batchAddTerminalToGroup(TerminalSnGroupRequest groupRequest){
        List<String> validationErrs = Validators.validateObject(groupRequest, "terminalGroupRequest");
        if (!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(ADD_TERMINAL_TO_GROUP_URL);
        request.setRequestMethod(RequestMethod.POST);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.setRequestBody(new Gson().toJson(groupRequest, TerminalSnGroupRequest.class));
        EmptyResponse emptyResponse =  EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return  new Result<>(emptyResponse);

    }

    public Result<String> updateTerminalConfig(String serialNo, TerminalConfigUpdateRequest terminalConfigUpdateRequest){
        logger.debug("serialNo={}", serialNo);
        List<String> validationErrs = Validators.validateStr(serialNo, "parameter.not.empty", "serialNo");
        validationErrs.addAll(Validators.validateObject(terminalConfigUpdateRequest, "terminalRemoteConfigRequest"));
        if (!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(UPDATE_TERMINAL_REMOTE_CONFIG_URL);
        request.setRequestMethod(RequestMethod.PUT);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.addRequestParam("serialNo", StringUtils.trim(serialNo));
        request.setRequestBody(new Gson().toJson(terminalConfigUpdateRequest, TerminalConfigUpdateRequest.class));
        EmptyResponse emptyResponse =  EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return  new Result<>(emptyResponse);
    }

    public Result<TerminalConfigDTO> getTerminalConfig(String serialNo){
        logger.debug("serialNo={}", serialNo);
        List<String> validationErrs = Validators.validateStr(serialNo, "parameter.not.empty", "serialNo");
        if (!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(GET_TERMINAL_REMOTE_CONFIG_URL);
        request.setRequestMethod(RequestMethod.GET);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.addRequestParam("serialNo", StringUtils.trim(serialNo));
        TerminalConfigResponse terminalConfigResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalConfigResponse.class);
        return new Result<>(terminalConfigResponse);
    }

    public Result<TerminalPedDTO> getTerminalPed(String serialNo){
        logger.debug("serialNo={}", serialNo);
        List<String> validationErrs = Validators.validateStr(serialNo, "parameter.not.empty", "serialNo");
        if (!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(GET_TERMINAL_PED_STATUS_URL);
        request.setRequestMethod(RequestMethod.GET);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.addRequestParam("serialNo", StringUtils.trim(serialNo));
        TerminalPedResponse terminalPedResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalPedResponse.class);
        return  new Result<>(terminalPedResponse);
    }

    public Result<String> pushCmdToTerminal(String serialNo, TerminalPushCmd command){
        logger.debug("serialNo={}", serialNo);
        List<String> validationErrs = Validators.validateStr(serialNo, "parameter.not.empty", "serialNo");
        validationErrs.addAll(Validators.validateObject(command,"terminalPushCmdRequest"));
        if (!validationErrs.isEmpty()) {
            return new Result<>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(PUSH_TERMINAL_ACTION_URL);
        request.setRequestMethod(RequestMethod.POST);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.addRequestParam("serialNo", StringUtils.trim(serialNo));
        request.addRequestParam("command", command.val());
        EmptyResponse emptyResponse =  EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return  new Result<>(emptyResponse);
    }

    public enum TerminalStatus {
        Active("A"),
        Inactive("P"),
        Suspend("S");
        private final String val;

        TerminalStatus(String status) {
            this.val = status;
        }

        public String val() {
            return this.val;
        }
    }

    public enum TerminalSearchOrderBy {
        Name("name"),
        Tid("tid"),
        SerialNo("serialNo");

        private final String val;

        TerminalSearchOrderBy(String orderBy) {
            this.val = orderBy;
        }

        public String val() {
            return this.val;
        }

    }

    public enum TerminalPushCmd {
        Restart("Restart"),
        Lock("Lock"),
        Unlock("Unlock");


        private final String val;
        TerminalPushCmd(String type) {
            this.val = type;
        }
        public String val(){
            return this.val;
        }
    }
}
