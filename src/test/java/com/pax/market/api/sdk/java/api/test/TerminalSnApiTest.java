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


import com.pax.market.api.sdk.java.api.base.dto.Result;
import com.pax.market.api.sdk.java.api.terminal.TerminalSnApi;
import com.pax.market.api.sdk.java.api.terminal.dto.*;
import com.pax.market.api.sdk.java.api.terminalGroup.dto.TerminalSnGroupRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author tanjie
 * @date 2018-07-02
 */
public class TerminalSnApiTest {
	
	private static final Logger logger = LoggerFactory.getLogger(TerminalSnApiTest.class.getSimpleName());
	
	TerminalSnApi terminalApi;
	
	@Before
    public void init(){
    	terminalApi = new  TerminalSnApi(TestConstants.API_BASE_URL, TestConstants.API_KEY, TestConstants.API_SECRET);
    }

    @Test
    public void testGetTerminalIncludeAccessoryInfo() {
    	String serialNo = "SN6132522";
    	Result<TerminalDTO> getResult = terminalApi.getTerminal(serialNo,true);
    	logger.debug("Result of get terminal: {}",getResult.toString());
    	Assert.assertTrue(getResult.getBusinessCode() == 0);
    }

    @Test
    public void testGetTerminalIncludeInstalledApks() {
		String serialNo = "SN6132522";
    	Result<TerminalDTO> getResult = terminalApi.getTerminal(serialNo,false, true);
    	logger.debug("Result of get terminal: {}",getResult.toString());
    	Assert.assertTrue(getResult.getBusinessCode() == 0);
    }
    
	@Test
	public void testBatchAddTerminalToGroup(){
		TerminalSnGroupRequest groupRequest = new TerminalSnGroupRequest();
		Set<String> serialNoList = new HashSet<>();
		serialNoList.add("SN6132522");
		serialNoList.add("SN6685133");
		Set<Long> groupIds = new HashSet<>();
		groupIds.add(16529L);
		groupIds.add(16527L);
 		groupRequest.setSerialNos(serialNoList);
		groupRequest.setGroupIds(groupIds);
	    Result<String> result = terminalApi.batchAddTerminalToGroup(groupRequest);
		logger.debug("Result of search terminal: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);

	}

	@Test
	public void testUpdateTerminalConfig(){
		String serialNo = "SN6132522";
		TerminalConfigUpdateRequest terminalConfigUpdateRequest = new TerminalConfigUpdateRequest();
		terminalConfigUpdateRequest.setAllowReplacement(true);
	    Result<String> result = terminalApi.updateTerminalConfig(serialNo,terminalConfigUpdateRequest);
		logger.debug("Result of update Terminal Config: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);

	}

	@Test
	public void testGetTerminalConfig(){
		String serialNo = "SN6132522";
	    Result<TerminalConfigDTO> result = terminalApi.getTerminalConfig(serialNo);
		logger.debug("Result of get Terminal Config: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);

	}


    @Test
	public void testGetTerminalPed(){
		String serialNo = "SN6132522";
	    Result<TerminalPedDTO> result = terminalApi.getTerminalPed(serialNo);
		logger.debug("Result of get Terminal ped: {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);
	}

	@Test
	public void testMoveTerminal() {
		String serialNo = "SN6132522";
		Result<String> result = terminalApi.moveTerminal(serialNo, "PAX", "6666");
		logger.debug("Result of move Terminal {}",result.toString());
		Assert.assertTrue(result.getBusinessCode() == 0);
	}

	@Test
	public void testPushTerminalAction() {
		String serialNo = "SN6132522";
		Result<String> result = terminalApi.pushCmdToTerminal(serialNo, TerminalSnApi.TerminalPushCmd.Unlock);
		logger.debug("Result of push terminal action {}",result.toString());
		Assert.assertEquals(0, result.getBusinessCode());
	}
}
