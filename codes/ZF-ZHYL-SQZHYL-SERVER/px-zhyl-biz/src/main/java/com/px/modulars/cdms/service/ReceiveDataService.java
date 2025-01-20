package com.px.modulars.cdms.service;


import com.px.modulars.cdms.common.bean.SrcDataValue;

/***************************************************************************
 * <PRE>
 * 
 *  Project Name    : cdms-api
 *  
 *  Description     : 血糖设备数据采集
 *  
 *  Author          : huke.zhang
 * 
 * </PRE>
 ***************************************************************************/
public interface ReceiveDataService {

	/**
	 * 处理上报的数据
	 * 
	 * @param srcDatavalue
	 * @return
	 */
	public boolean handleReceivedData(SrcDataValue srcDatavalue);
	
}
