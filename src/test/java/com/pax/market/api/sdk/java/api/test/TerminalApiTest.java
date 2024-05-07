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
package com.pax.market.api.sdk.java.api.test;


import com.pax.market.api.sdk.java.api.terminal.dto.*;
import com.pax.market.api.sdk.java.api.terminalGroup.dto.TerminalGroupRequest;
import com.pax.market.api.sdk.java.api.terminalGroup.dto.TerminalSnGroupRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pax.market.api.sdk.java.api.base.dto.Result;
import com.pax.market.api.sdk.java.api.terminal.TerminalApi;
import com.pax.market.api.sdk.java.api.terminal.TerminalApi.TerminalSearchOrderBy;
import com.pax.market.api.sdk.java.api.terminal.TerminalApi.TerminalStatus;
import com.pax.market.api.sdk.java.api.terminal.dto.TerminalCreateRequest;
import com.pax.market.api.sdk.java.api.terminal.dto.TerminalDTO;
import com.pax.market.api.sdk.java.api.terminal.dto.TerminalUpdateRequest;

import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;


/**
 *
 * @author tanjie
 * @date 2018-07-02
 */
public class TerminalApiTest {
	
	private static final Logger logger = LoggerFactory.getLogger(TerminalApiTest.class.getSimpleName());
	
	TerminalApi terminalApi;
	
	@Before
    public void init(){
    	terminalApi = new  TerminalApi(TestConstants.API_BASE_URL, TestConstants.API_KEY, TestConstants.API_SECRET);
    }

    @Test
    public void testSearchTerminalList(){
    	Result<TerminalDTO> result = terminalApi.searchTerminal(1, 10, TerminalSearchOrderBy.Name, TerminalStatus.Inactive, "14494956");
		logger.debug("Result of search terminal: {}",result.toString());
    	Assert.assertTrue(result.getBusinessCode() == 0);
    }

	@Test
	public void testSearchTerminalListInluceGeoFirmAppDetail(){
		Result<TerminalDTO> result = terminalApi.searchTerminal(1, 10, TerminalSearchOrderBy.Name, null, "BTG7KFTY", true, true, true);
		logger.debug("Result of search terminal: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);
	}
    

    @Test
    public void testCreateTerminal() {
    	TerminalCreateRequest createReq = new TerminalCreateRequest();
    	createReq.setName("KFC-TML-03");
    	createReq.setMerchantName("merchant test");
    	createReq.setResellerName("reseller test");
    	createReq.setLocation("USA");
    	createReq.setSerialNo("TJ000002");
    	createReq.setModelName("A920");


    	Result<TerminalDTO> result = terminalApi.createTerminal(createReq);
    	logger.debug("Result of create terminal: {}",result.toString());
    	Assert.assertTrue(result.getBusinessCode() == 0);
    	
    	Long terminalId = result.getData().getId();
    	
    	
    	Result<TerminalDTO> getResult = terminalApi.getTerminal(terminalId);
    	logger.debug("Result of get terminal: {}",getResult.toString());
    	Assert.assertTrue(getResult.getBusinessCode() == 0);
    	
    	
    	//test update
    	TerminalUpdateRequest updateReq = new TerminalUpdateRequest();
    	updateReq.setName("KFC-TML-JS");
    	updateReq.setModelName("A920");
    	updateReq.setLocation("CN");
    	updateReq.setSerialNo("TJ000002");
    	updateReq.setResellerName("reseller test");
    	updateReq.setMerchantName("merchant test");
    	
    	Result<TerminalDTO> updateResult = terminalApi.updateTerminal(terminalId, updateReq);
    	logger.debug("Result of update terminal: {}",updateResult.toString());
    	Assert.assertTrue(updateResult.getBusinessCode() == 0);
    	
    	//Test activate
    	Result<String> activateResult = terminalApi.activateTerminal(terminalId);
    	logger.debug("Result of activate terminal: {}",activateResult.toString());
    	Assert.assertTrue(activateResult.getBusinessCode() == 0);


		//TEST copy
		TerminalCopyRequest copyRequest = new TerminalCopyRequest();
		copyRequest.setTerminalId(terminalId);
		copyRequest.setName("COPY_FROM_909822");
		copyRequest.setSerialNo("TJ00001002");
		copyRequest.setStatus(TerminalStatus.Inactive);
		Result<TerminalDTO> copyResult = terminalApi.copyTerminal(copyRequest);
		logger.debug("Result of copy terminal: {}",copyResult.toString());
		Assert.assertTrue(copyResult.getBusinessCode() == 0);

    	//Test disable
    	Result<String> disableResult = terminalApi.disableTerminal(terminalId);
    	logger.debug("Result of disable terminal: {}",disableResult.toString());
    	Assert.assertTrue(disableResult.getBusinessCode() == 0);
    	
    	//Test delete
    	Result<String> deleteResult = terminalApi.deleteTerminal(terminalId);
    	logger.debug("Result of delete terminal: {}",deleteResult.toString());
		Assert.assertTrue(deleteResult.getBusinessCode() == 0);

		Result<String> deleteCopyResult = terminalApi.deleteTerminal(copyResult.getData().getId());
		logger.debug("Result of delete copy terminal: {}",deleteCopyResult.toString());
    	Assert.assertTrue(deleteCopyResult.getBusinessCode() == 0);


    }

    @Test
    public void testGetTerminalIncludeAccessoryInfo() {
    	Long terminalId = 908627L;
    	Result<TerminalDTO> getResult = terminalApi.getTerminal(terminalId,true);
    	logger.debug("Result of get terminal: {}",getResult.toString());
    	Assert.assertTrue(getResult.getBusinessCode() == 0);
    }

    @Test
    public void testGetTerminalIncludeInstalledApks() {
    	Long terminalId = 909822L;
    	Result<TerminalDTO> getResult = terminalApi.getTerminal(terminalId,false, true);
    	logger.debug("Result of get terminal: {}",getResult.toString());
    	Assert.assertTrue(getResult.getBusinessCode() == 0);
    }
    
	@Test
	public void testBatchAddTerminalToGroup(){
		TerminalGroupRequest groupRequest = new TerminalGroupRequest();
		Set<Long> terminalIds = new HashSet<>();
		terminalIds.add(909744L);
		terminalIds.add(909742L);
		Set<Long> groupIds = new HashSet<>();
		groupIds.add(16529L);
		groupIds.add(16527L);
 		groupRequest.setTerminalIds(terminalIds);
		groupRequest.setGroupIds(groupIds);
	    Result<String> result = terminalApi.batchAddTerminalToGroup(groupRequest);
		logger.debug("Result of search terminal: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);

	}

	@Test
	public void testUpdateTerminalReplacementConfig(){
		Long terminalId = 909744L;
		TerminalReplacementUpdateRequest terminalReplacementUpdateRequest = new TerminalReplacementUpdateRequest();
		terminalReplacementUpdateRequest.setAllowReplacement(true);
	    Result<String> result = terminalApi.updateTerminalConfig(terminalId, terminalReplacementUpdateRequest);
		logger.debug("Result of update Terminal Config: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);

	}

	@Test
	public void testUpdateTerminalTimeZoneConfig(){
		Long terminalId = 1547947831459887L;
		TerminalTimeZoneUpdateRequest terminalTimeZoneUpdateRequest = new TerminalTimeZoneUpdateRequest();
		terminalTimeZoneUpdateRequest.setAutomaticTimeZoneEnable(false);
		terminalTimeZoneUpdateRequest.setTimeZone(TimeZone.getDefault().getID());
		Result<String> result = terminalApi.updateTerminalConfig(terminalId, terminalTimeZoneUpdateRequest);
		logger.debug("Result of update Terminal Config: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);

	}

	@Test
	public void testGetTerminalConfig(){
		Long terminalId = 90974L;
	    Result<TerminalConfigDTO> result = terminalApi.getTerminalConfig(terminalId);
		logger.debug("Result of get Terminal Config: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);

	}


    @Test
	public void testGetTerminalPed(){
		Long terminalId = 909755L;
	    Result<TerminalPedDTO> result = terminalApi.getTerminalPed(terminalId);
		logger.debug("Result of get Terminal ped: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);
	}

	@Test
	public void testMoveTerminal() {
		Long terminalId = 1018664801L;
		Result<String> result = terminalApi.moveTerminal(terminalId, "PAX", "6666");
		logger.debug("Result of move Terminal {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);
	}

	@Test
	public void testPushTerminalAction() {
		Long terminalId = 515006L;
		Result<String> result = terminalApi.pushCmdToTerminal(terminalId, TerminalApi.TerminalPushCmd.Unlock);
		logger.debug("Result of push terminal action {}",result.toString());
		Assert.assertEquals(0, result.getBusinessCode());
	}

	@Test
	public void testGetTerminalNetwork(){
		Result<TerminalNetworkDTO> result = terminalApi.getTerminalNetwork("TEST8000999","BTG7KFTY");
		logger.debug("Result of get Terminal network: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);
	}

//	===============20240507新增
@Test
public void testGetTerminalIncludeAccessoryInfoBySn() {
	String serialNo = "SN6132522";
	Result<TerminalDTO> getResult = terminalApi.getTerminalBySn(serialNo,true);
	logger.debug("Result of get terminal: {}",getResult.toString());
	Assert.assertTrue(getResult.getBusinessCode() == 0);
}

	@Test
	public void testGetTerminalIncludeInstalledApksBySn() {
		String serialNo = "SN6132522";
		Result<TerminalDTO> getResult = terminalApi.getTerminalBySn(serialNo,false, true);
		logger.debug("Result of get terminal: {}",getResult.toString());
		Assert.assertTrue(getResult.getBusinessCode() == 0);
	}

	@Test
	public void testBatchAddTerminalToGroupBySn(){
		TerminalSnGroupRequest groupRequest = new TerminalSnGroupRequest();
		Set<String> serialNoList = new HashSet<>();
		serialNoList.add("SN6132522");
		serialNoList.add("SN6685133");
		Set<Long> groupIds = new HashSet<>();
		groupIds.add(16529L);
		groupIds.add(16527L);
		groupRequest.setSerialNos(serialNoList);
		groupRequest.setGroupIds(groupIds);
		Result<String> result = terminalApi.batchAddTerminalToGroupBySn(groupRequest);
		logger.debug("Result of search terminal: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);

	}

	@Test
	public void testUpdateTerminalConfigBySn(){
		String serialNo = "SN6132522";
		TerminalReplacementUpdateRequest terminalConfigUpdateRequest = new TerminalReplacementUpdateRequest();
		terminalConfigUpdateRequest.setAllowReplacement(true);
		Result<String> result = terminalApi.updateTerminalConfigBySn(serialNo,terminalConfigUpdateRequest);
		logger.debug("Result of update Terminal Config: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);

	}

	@Test
	public void testGetTerminalConfigBySn(){
		String serialNo = "SN6132522";
		Result<TerminalConfigDTO> result = terminalApi.getTerminalConfigBySn(serialNo);
		logger.debug("Result of get Terminal Config: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);

	}


	@Test
	public void testGetTerminalPedBySn(){
		String serialNo = "SN6132522";
		Result<TerminalPedDTO> result = terminalApi.getTerminalPedBySn(serialNo);
		logger.debug("Result of get Terminal ped: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);
	}

	@Test
	public void testMoveTerminalBySn() {
		String serialNo = "SN6132522";
		Result<String> result = terminalApi.moveTerminalBySn(serialNo, "PAX", "6666");
		logger.debug("Result of move Terminal {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);
	}

	@Test
	public void testPushTerminalActionBySn() {
		String serialNo = "SN6132522";
		Result<String> result = terminalApi.pushCmdToTerminalBySn(serialNo, TerminalApi.TerminalPushCmd.Unlock);
		logger.debug("Result of push terminal action {}",result.toString());
		Assert.assertEquals(0, result.getBusinessCode());
	}
}
