package com.springBootExcel.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtil {

	/**
	 * 格式化单元格返回其内容 格式化成string返回。
	 * 
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String formatCell(HSSFCell cell) {
		if (cell == null) {
			return "";
		} else {
			if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				return String.valueOf(cell.getNumericCellValue());
			} else {
				return String.valueOf(cell.getStringCellValue());
			}
		}
	}

	/**
	 * 返回 日期 date 2018/3/28 14:18:00 这种类型的可以
	 * 
	 * @param cell
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public static Date formatDate(HSSFCell cell) throws ParseException {

		String str = cell.toString();

		Date date = null;
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			date = cell.getDateCellValue();
		}

		return date;
	}

	/**
	 * 返回 BigDecimal 数据
	 * 
	 * @param cell
	 * @return
	 * @throws ParseException
	 */
	public static BigDecimal formatBigDecimal(HSSFCell cell) throws ParseException {
		String str = ExcelUtil.formatCell(cell);
		BigDecimal num = new BigDecimal(str.trim());
		return num;
	}
}