package com.px.modulars.cdms.common.consts;

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
public class ApiConsts {

	/** 机种码常量 */
	public enum DataTypeEnum {

		BLPRESSURE("01", "血压数据"), BLGLUCOSE("02", "血糖数据"), BLOXYGEN("03",
				"血氧数据"), TPERTURE("04", "体温数据"), ECG("05", "心电数据"), URICACID(
				"06", "尿酸数据"), CHOLESTEROL("07", "胆固醇数据");

		private String code;
		private String desc;

		private DataTypeEnum(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return this.code;
		}

		public String getDesc() {
			return this.desc;
		}

		public String toString() {
			return getCode();
		}		
	}
}
