package com.px.modulars.cdms.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

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
public class HdwDataCheckUtils {
	
	static char[] crc8_table = { '\000', '\007', '\016', '\t', '\034', '\033',
			'\022', '\025', '8', '?', '6', '1', '$', '#', '*', '-', 'p', 'w',
			'~', 'y', 'l', 'k', 'b', 'e', 'H', 'O', 'F', 'A', 'T', 'S', 'Z',
			']', 'à', 'ç', 'î', 'é', 'ü', 'û', 'ò', 'õ', 'Ø', 'ß', 'Ö', 'Ñ',
			'Ä', 'Ã', 'Ê', 'Í', '', '', '', '', '', '', '', '', '¨',
			'¯', '¦', '¡', '´', '³', 'º', '½', 'Ç', 'À', 'É', 'Î', 'Û', 'Ü',
			'Õ', 'Ò', 'ÿ', 'ø', 'ñ', 'ö', 'ã', 'ä', 'í', 'ê', '·', '°', '¹',
			'¾', '«', '¬', '¥', '¢', '', '', '', '', '', '', '', '',
			'\'', ' ', ')', '.', ';', '<', '5', '2', '\037', '\030', '\021',
			'\026', '\003', '\004', '\r', '\n', 'W', 'P', 'Y', '^', 'K', 'L',
			'E', 'B', 'o', 'h', 'a', 'f', 's', 't', '}', 'z', '', '', '',
			'', '', '', '', '', '±', '¶', '¿', '¸', '­', 'ª', '£', '¤',
			'ù', 'þ', '÷', 'ð', 'å', 'â', 'ë', 'ì', 'Á', 'Æ', 'Ï', 'È', 'Ý',
			'Ú', 'Ó', 'Ô', 'i', 'n', 'g', '`', 'u', 'r', '{', '|', 'Q', 'V',
			'_', 'X', 'M', 'J', 'C', 'D', '\031', '\036', '\027', '\020',
			'\005', '\002', '\013', '\f', '!', '&', '/', '(', '=', ':', '3',
			'4', 'N', 'I', '@', 'G', 'R', 'U', '\\', '[', 'v', 'q', 'x', '',
			'j', 'm', 'd', 'c', '>', '9', '0', '7', '"', '%', ',', '+', '\006',
			'\001', '\b', '\017', '\032', '\035', '\024', '\023', '®', '©',
			' ', '§', '²', 'µ', '¼', '»', '', '', '', '', '', '', '',
			'', 'Þ', 'Ù', 'Ð', '×', 'Â', 'Å', 'Ì', 'Ë', 'æ', 'á', 'è', 'ï',
			'ú', 'ý', 'ô', 'ó' };

	public static boolean isSumCheckCode(String datastr) {
		
		if ((StringUtils.isEmpty(datastr)) || (datastr.length() < 43)) {
			return false;
		}
		
		String data = datastr.substring(0, 39);
		String checkCode = datastr.substring(39, 43);
		Integer sum = Integer.valueOf(0);
		
		for (int i = 0; i < data.length(); i++) {
			char ch = data.charAt(i);
			sum = Integer.valueOf(sum.intValue()
					+ Integer.parseInt(Integer.toHexString(ch), 16));
		}
		
		return StringUtils.leftPad(Integer.toHexString(sum.intValue()), 4, "0")
				.equalsIgnoreCase(checkCode);
	}

	public static boolean isCrc8Code(String data) {
		
		if ((data == null) || (data.length() != 87)) {
			return false;
		}
		
		String dataval = data.substring(0, 85);
		
		char crc8 = '\000';
		
		for (int i = 0; i < dataval.length(); i++) {
			crc8 = crc8_table[(0xFF & (crc8 ^ dataval.charAt(i)))];
		}
		
		return StringUtils.leftPad(Integer.toHexString(crc8), 2, "0")
				.equalsIgnoreCase(data.substring(85, 87));
	}
	
	// 血糖仪是以mg/dL为单位传送，如果要显示为mmol/dL的单位，则要除以18，并四舍五入才能与血糖仪端显示的数据一致
	public static BigDecimal bsDataToResult(BigDecimal val){
		BigDecimal b = new BigDecimal(val.doubleValue()/18D);
		double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return new BigDecimal(f1);
	} 
}
