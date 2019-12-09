package com.cosun.cosunp.tool;

import com.cosun.cosunp.entity.DayJI;
import com.cosun.cosunp.entity.MonthKQInfo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/12/5  上午 9:25
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class MKExcelUtil {

    public static List<String> writeMKdataTOExcel(List<MonthKQInfo> mkList, String finalDirPath, String fileName, String yearMonth, String wd, String fd) {
        List<String> returnArray = new ArrayList<String>();
        MonthKQInfo opw;
        XSSFWorkbook workbook = new XSSFWorkbook();
        FileOutputStream fos = null;
        FileInputStream fis = null;
        String pathname = finalDirPath + fileName;
        returnArray.add(fileName);
        File file = new File(pathname);
        HSSFWorkbook wb = null;
        if (file.exists()) {
            file.delete();
        }
        File targetFile = new File(finalDirPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.createNewFile();
            fis = new FileInputStream(finalDirPath + fileName);
            wb = new HSSFWorkbook();//这里使用的Excel2003，要注意Excel版本


            Font font = wb.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 10);

            Font font2 = wb.createFont();
            font2.setBold(true);
            font2.setFontHeightInPoints((short) 20);

            Font font3 = wb.createFont();
            font3.setBold(false);
            font3.setFontHeightInPoints((short) 10);

            Font font4 = wb.createFont();
            font4.setBold(false);
            font4.setFontHeightInPoints((short) 10);
            font4.setColor(HSSFColor.GREEN.index);

            Font font5 = wb.createFont();
            font5.setBold(false);
            font5.setFontHeightInPoints((short) 10);
            font5.setColor(HSSFColor.RED.index);

            Font font6 = wb.createFont();
            font6.setBold(false);
            font6.setFontHeightInPoints((short) 10);
            font6.setColor(HSSFColor.BLUE.index);

            Font font7 = wb.createFont();
            font7.setBold(false);
            font7.setFontHeightInPoints((short) 10);
            font7.setColor(HSSFColor.VIOLET.index);
            //合并的单元格样式
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setFont(font);
            cellStyle.setWrapText(true);//先设置为自动换行

            //合并的单元格样式
            CellStyle cellStyleA = wb.createCellStyle();
            cellStyleA.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyleA.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyleA.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyleA.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleA.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyleA.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleA.setBorderRight(CellStyle.BORDER_THIN);
            cellStyleA.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleA.setBorderTop(CellStyle.BORDER_THIN);
            cellStyleA.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleA.setFont(font3);
            cellStyleA.setWrapText(true);//先设置为自动换行

            CellStyle cellStyle2 = wb.createCellStyle();
            cellStyle2.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle2.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle2.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle2.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle2.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle2.setFont(font2);
            cellStyle2.setWrapText(true);//先设置为自动换行


            CellStyle cellStyle3 = wb.createCellStyle();
            cellStyle3.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle3.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle3.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle3.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle3.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle3.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3.setFont(font4);
            cellStyle3.setWrapText(true);//先设置为自动换行

            //合并的单元格样式
            CellStyle cellStyleAP = wb.createCellStyle();
            cellStyleAP.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyleAP.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyleAP.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyleAP.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleAP.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyleAP.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleAP.setBorderRight(CellStyle.BORDER_THIN);
            cellStyleAP.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleAP.setBorderTop(CellStyle.BORDER_THIN);
            cellStyleAP.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleAP.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyleAP.setFillForegroundColor(HSSFColor.GOLD.index);//前景填充色
            cellStyleAP.setFont(font3);
            cellStyleAP.setWrapText(true);//先设置为自动换行

            CellStyle cellStyle4 = wb.createCellStyle();
            cellStyle4.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle4.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle4.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle4.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle4.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle4.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4.setFont(font5);
            cellStyle4.setWrapText(true);//先设置为自动换行

            CellStyle cellStyle5 = wb.createCellStyle();
            cellStyle5.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle5.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle5.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle5.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle5.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle5.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle5.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle5.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle5.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle5.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle5.setFont(font6);
            cellStyle5.setWrapText(true);//先设置为自动换行


            CellStyle cellStyle6 = wb.createCellStyle();
            cellStyle6.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle6.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle6.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle6.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle6.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle6.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle6.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6.setFont(font7);
            cellStyle6.setWrapText(true);//先设置为自动换行


            CellStyle cellStyle6P = wb.createCellStyle();
            cellStyle6P.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle6P.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle6P.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle6P.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6P.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle6P.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6P.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle6P.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6P.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle6P.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6P.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyle6P.setFillForegroundColor(HSSFColor.GOLD.index);//前景填充色
            cellStyle6P.setFont(font7);
            cellStyle6P.setWrapText(true);//先设置为自动换行

            CellStyle cellStyleBO = wb.createCellStyle();
            cellStyleBO.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyleBO.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyleBO.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyleBO.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBO.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyleBO.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBO.setBorderRight(CellStyle.BORDER_THIN);
            cellStyleBO.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBO.setBorderTop(CellStyle.BORDER_THIN);
            cellStyleBO.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBO.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyleBO.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);//前景填充色
            cellStyleBO.setFont(font3);
            cellStyleBO.setWrapText(true);//先设置为自动换行

            CellStyle cellStyleBOP = wb.createCellStyle();
            cellStyleBOP.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyleBOP.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyleBOP.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyleBOP.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBOP.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyleBOP.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBOP.setBorderRight(CellStyle.BORDER_THIN);
            cellStyleBOP.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBOP.setBorderTop(CellStyle.BORDER_THIN);
            cellStyleBOP.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBOP.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyleBOP.setFillForegroundColor(HSSFColor.GOLD.index);//前景填充色
            cellStyleBOP.setFont(font3);
            cellStyleBOP.setWrapText(true);//先设置为自动换行


            CellStyle cellStyleBR = wb.createCellStyle();
            cellStyleBR.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyleBR.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyleBR.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyleBR.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBR.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyleBR.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBR.setBorderRight(CellStyle.BORDER_THIN);
            cellStyleBR.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBR.setBorderTop(CellStyle.BORDER_THIN);
            cellStyleBR.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBR.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyleBR.setFillForegroundColor(HSSFColor.RED.index);//前景填充色
            cellStyleBR.setFont(font3);
            cellStyleBR.setWrapText(true);//先设置为自动换行


            CellStyle cellStyleBG = wb.createCellStyle();
            cellStyleBG.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyleBG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyleBG.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyleBG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBG.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyleBG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBG.setBorderRight(CellStyle.BORDER_THIN);
            cellStyleBG.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBG.setBorderTop(CellStyle.BORDER_THIN);
            cellStyleBG.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleBG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyleBG.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);//前景填充色
            cellStyleBG.setFont(font3);
            cellStyleBG.setWrapText(true);//先设置为自动换行

            CellStyle cellStyle4BO = wb.createCellStyle();
            cellStyle4BO.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle4BO.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle4BO.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle4BO.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4BO.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle4BO.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4BO.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle4BO.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4BO.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle4BO.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4BO.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyle4BO.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);//前景填充色
            cellStyle4BO.setFont(font5);
            cellStyle4BO.setWrapText(true);//先设置为自动换行

            CellStyle cellStyle4BOP = wb.createCellStyle();
            cellStyle4BOP.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle4BOP.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle4BOP.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle4BOP.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4BOP.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle4BOP.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4BOP.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle4BOP.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4BOP.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle4BOP.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4BOP.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyle4BOP.setFillForegroundColor(HSSFColor.GOLD.index);//前景填充色
            cellStyle4BOP.setFont(font5);
            cellStyle4BOP.setWrapText(true);//先设置为自动换行


            CellStyle cellStyle6BO = wb.createCellStyle();
            cellStyle6BO.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle6BO.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle6BO.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle6BO.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6BO.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle6BO.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6BO.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle6BO.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6BO.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle6BO.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6BO.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyle6BO.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);//前景填充色
            cellStyle6BO.setFont(font7);
            cellStyle6BO.setWrapText(true);//先设置为自动换行

            CellStyle cellStyle6BOP = wb.createCellStyle();
            cellStyle6BOP.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle6BOP.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle6BOP.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle6BOP.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6BOP.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle6BOP.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6BOP.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle6BOP.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6BOP.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle6BOP.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle6BOP.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyle6BOP.setFillForegroundColor(HSSFColor.GOLD.index);//前景填充色
            cellStyle6BOP.setFont(font7);
            cellStyle6BOP.setWrapText(true);//先设置为自动换行

            CellStyle cellStyle3BO = wb.createCellStyle();
            cellStyle3BO.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle3BO.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle3BO.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle3BO.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3BO.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle3BO.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3BO.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle3BO.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3BO.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle3BO.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3BO.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyle3BO.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);//前景填充色
            cellStyle3BO.setFont(font4);
            cellStyle3BO.setWrapText(true);//先设置为自动换行

            CellStyle cellStyle3BOP = wb.createCellStyle();
            cellStyle3BOP.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle3BOP.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle3BOP.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle3BOP.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3BOP.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle3BOP.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3BOP.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle3BOP.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3BOP.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle3BOP.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3BOP.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyle3BOP.setFillForegroundColor(HSSFColor.GOLD.index);//前景填充色
            cellStyle3BOP.setFont(font4);
            cellStyle3BOP.setWrapText(true);//先设置为自动换行

            CellStyle cellStyle4P = wb.createCellStyle();
            cellStyle4P.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle4P.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle4P.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle4P.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4P.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle4P.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4P.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle4P.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4P.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle4P.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle4P.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyle4P.setFillForegroundColor(HSSFColor.GOLD.index);//前景填充色
            cellStyle4P.setFont(font5);
            cellStyle4P.setWrapText(true);//先设置为自动换行


            CellStyle cellStyle3P = wb.createCellStyle();
            cellStyle3P.setAlignment(CellStyle.ALIGN_CENTER);//左右居中
            cellStyle3P.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中
            cellStyle3P.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle3P.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3P.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle3P.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3P.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle3P.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3P.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle3P.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle3P.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
            cellStyle3P.setFillForegroundColor(HSSFColor.GOLD.index);//前景填充色
            cellStyle3P.setFont(font4);
            cellStyle3P.setWrapText(true);//先设置为自动换行

            HSSFSheet hssfSheet = wb.createSheet("sheet1");
            HSSFRow row = hssfSheet.createRow(0);
            HSSFRow row2 = null;
            HSSFRow row3 = null;
            Cell cell = row.createCell(0);//合并的单元格取第一个cell的位置对象
            cell.setCellValue(yearMonth + "月份考勤表");
            cell.setCellStyle(cellStyle2);

            row = hssfSheet.createRow(1);
            row.setHeight((short) 400);
            cell = row.createCell(0);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("序号");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("姓名");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("部门");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("星期");
            cell.setCellStyle(cellStyle);

            row2 = hssfSheet.createRow(2);
            row2.setHeight((short) 400);
            cell = row2.createCell(3);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("日期");
            cell.setCellStyle(cellStyle);

            hssfSheet.setColumnWidth(0, 6 * 256);
            hssfSheet.setColumnWidth(1, 8 * 256);
            hssfSheet.setColumnWidth(2, 6 * 256);
            hssfSheet.setColumnWidth(3, 6 * 256);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int days = DateUtil.getDaysByYearMonth(sdf.parse(yearMonth + "-01"));
            String day = null;

            boolean isWeekEnd = false;
            boolean isFaDing = false;

            for (int i = 1; i <= days; i++) {
                cell = row.createCell(3 + i);
                if (i < 10) {
                    day = "0" + i;
                } else {
                    day = i + "";
                }

                isWeekEnd = DateUtil.checkIsWeekEnd(wd, i + "");
                isFaDing = DateUtil.checkIsFaDing(fd, i + "");

                hssfSheet.setColumnWidth(3 + i, 4 * 270);
                cell.setCellValue(DateUtil.getWeekStr(yearMonth + "-" + day));
                if (isWeekEnd) {
                    if (isFaDing) {
                        cell.setCellStyle(cellStyleBR);
                    } else {
                        cell.setCellStyle(cellStyleBR);
                    }
                } else {
                    if (isFaDing) {
                        cell.setCellStyle(cellStyleBR);
                    } else {
                        cell.setCellStyle(cellStyle);
                    }
                }
                cell = row2.createCell(3 + i);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(i);
                if (isWeekEnd) {
                    if (isFaDing) {
                        cell.setCellStyle(cellStyleBR);
                    } else {
                        cell.setCellStyle(cellStyleBR);
                    }
                } else {
                    if (isFaDing) {
                        cell.setCellStyle(cellStyleBR);
                    } else {
                        cell.setCellStyle(cellStyle);
                    }
                }
            }

            cell = row.createCell(3 + days + 1);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("正班出勤工时");
            hssfSheet.setColumnWidth(3 + days + 1, 10 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 2);//合并的单元格取第一个cell的位置对象
            hssfSheet.setColumnWidth(3 + days + 2, 8 * 256);
            cell.setCellValue("平时加班(H)");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 3);//合并的单元格取第一个cell的位置对象
            hssfSheet.setColumnWidth(3 + days + 3, 8 * 256);
            cell.setCellValue("周末加班(H)");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 4);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("国家有薪假(H)");
            hssfSheet.setColumnWidth(3 + days + 4, 6 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 5);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("其它有薪假(H)");
            hssfSheet.setColumnWidth(3 + days + 5, 6 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 6);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("事假(H)");
            hssfSheet.setColumnWidth(3 + days + 6, 6 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 7);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("病假(H)");
            hssfSheet.setColumnWidth(3 + days + 7, 6 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 8);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("其它补贴(出差/夜班)");
            hssfSheet.setColumnWidth(3 + days + 8, 6 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 9);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("全勤奖");
            hssfSheet.setColumnWidth(3 + days + 9, 10 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 10);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("伙食费");
            hssfSheet.setColumnWidth(3 + days + 10, 8 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 11);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("房租/水电费");
            hssfSheet.setColumnWidth(3 + days + 11, 8 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 12);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("扣代付养老险");
            hssfSheet.setColumnWidth(3 + days + 12, 8 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 13);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("扣代付医疗险");
            hssfSheet.setColumnWidth(3 + days + 13, 8 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 14);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("扣代付失业险");
            hssfSheet.setColumnWidth(3 + days + 14, 8 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 15);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("扣代付公积金");
            hssfSheet.setColumnWidth(3 + days + 15, 8 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 16);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("代扣家属旅游费");
            hssfSheet.setColumnWidth(3 + days + 16, 8 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 17);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("工作失误");
            hssfSheet.setColumnWidth(3 + days + 17, 8 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 18);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("绩效分");
            hssfSheet.setColumnWidth(3 + days + 18, 8 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 19);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("备注");
            hssfSheet.setColumnWidth(3 + days + 19, 8 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 20);//合并的单元格取第一个cell的位置对象
            cell.setCellValue("请确定无误后签字");
            hssfSheet.setColumnWidth(3 + days + 20, 8 * 256);
            cell.setCellStyle(cellStyle);

            CellRangeAddress region = new CellRangeAddress(1, 2, 0, 0);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 1, 1);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 2, 2);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 1, 3 + days + 1);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 2, 3 + days + 2);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 3, 3 + days + 3);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 4, 3 + days + 4);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 5, 3 + days + 5);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 6, 3 + days + 6);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 7, 3 + days + 7);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 8, 3 + days + 8);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 9, 3 + days + 9);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 10, 3 + days + 10);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 11, 3 + days + 11);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 12, 3 + days + 12);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 13, 3 + days + 13);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 14, 3 + days + 14);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 15, 3 + days + 15);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 16, 3 + days + 16);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 17, 3 + days + 17);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 18, 3 + days + 18);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 19, 3 + days + 19);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 2, 3 + days + 20, 3 + days + 20);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(0, 0, 0, 3 + days);
            hssfSheet.addMergedRegion(region);


            MonthKQInfo oh;
            int beginRow = 3;
            String inComStr;
            String ymdStr;

            List<DayJI> dayJIList = new ArrayList<DayJI>();
            DayJI dayJI = null;
            for (int a = 0; a < mkList.size(); a++) {
                oh = mkList.get(a);
                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay01AM() == null ? 0 : oh.getDay01AM());
                dayJI.setDayJiAMRemark(oh.getDay01AMRemark() == null ? 0.0 : oh.getDay01AMRemark());
                dayJI.setDayJiPM(oh.getDay01PM() == null ? 0 : oh.getDay01PM());
                dayJI.setDayJiPMRemark(oh.getDay01PMRemark() == null ? 0.0 : oh.getDay01PMRemark());
                dayJI.setDayJiExHours(oh.getDay01ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay02AM() == null ? 0 : oh.getDay02AM());
                dayJI.setDayJiAMRemark(oh.getDay02AMRemark() == null ? 0.0 : oh.getDay02AMRemark());
                dayJI.setDayJiPM(oh.getDay02PM() == null ? 0 : oh.getDay02PM());
                dayJI.setDayJiPMRemark(oh.getDay02PMRemark() == null ? 0.0 : oh.getDay02PMRemark());
                dayJI.setDayJiExHours(oh.getDay02ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay03AM() == null ? 0 : oh.getDay03AM());
                dayJI.setDayJiAMRemark(oh.getDay03AMRemark() == null ? 0.0 : oh.getDay03AMRemark());
                dayJI.setDayJiPM(oh.getDay03PM() == null ? 0 : oh.getDay03PM());
                dayJI.setDayJiPMRemark(oh.getDay03PMRemark() == null ? 0.0 : oh.getDay03PMRemark());
                dayJI.setDayJiExHours(oh.getDay03ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay04AM() == null ? 0 : oh.getDay04AM());
                dayJI.setDayJiAMRemark(oh.getDay04AMRemark() == null ? 0.0 : oh.getDay04AMRemark());
                dayJI.setDayJiPM(oh.getDay04PM() == null ? 0 : oh.getDay04PM());
                dayJI.setDayJiPMRemark(oh.getDay04PMRemark() == null ? 0.0 : oh.getDay04PMRemark());
                dayJI.setDayJiExHours(oh.getDay04ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay05AM() == null ? 0 : oh.getDay05AM());
                dayJI.setDayJiAMRemark(oh.getDay05AMRemark() == null ? 0.0 : oh.getDay05AMRemark());
                dayJI.setDayJiPM(oh.getDay05PM() == null ? 0 : oh.getDay05PM());
                dayJI.setDayJiPMRemark(oh.getDay05PMRemark() == null ? 0.0 : oh.getDay05PMRemark());
                dayJI.setDayJiExHours(oh.getDay05ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay06AM() == null ? 0 : oh.getDay06AM());
                dayJI.setDayJiAMRemark(oh.getDay06AMRemark() == null ? 0.0 : oh.getDay06AMRemark());
                dayJI.setDayJiPM(oh.getDay06PM() == null ? 0 : oh.getDay06PM());
                dayJI.setDayJiPMRemark(oh.getDay06PMRemark() == null ? 0.0 : oh.getDay06PMRemark());
                dayJI.setDayJiExHours(oh.getDay06ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay07AM() == null ? 0 : oh.getDay07AM());
                dayJI.setDayJiAMRemark(oh.getDay07AMRemark() == null ? 0.0 : oh.getDay07AMRemark());
                dayJI.setDayJiPM(oh.getDay07PM() == null ? 0 : oh.getDay07PM());
                dayJI.setDayJiPMRemark(oh.getDay07PMRemark() == null ? 0.0 : oh.getDay07PMRemark());
                dayJI.setDayJiExHours(oh.getDay07ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay08AM() == null ? 0 : oh.getDay08AM());
                dayJI.setDayJiAMRemark(oh.getDay08AMRemark() == null ? 0.0 : oh.getDay08AMRemark());
                dayJI.setDayJiPM(oh.getDay08PM() == null ? 0 : oh.getDay08PM());
                dayJI.setDayJiPMRemark(oh.getDay08PMRemark() == null ? 0.0 : oh.getDay08PMRemark());
                dayJI.setDayJiExHours(oh.getDay08ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay09AM() == null ? 0 : oh.getDay09AM());
                dayJI.setDayJiAMRemark(oh.getDay09AMRemark() == null ? 0.0 : oh.getDay09AMRemark());
                dayJI.setDayJiPM(oh.getDay09PM() == null ? 0 : oh.getDay09PM());
                dayJI.setDayJiPMRemark(oh.getDay09PMRemark() == null ? 0.0 : oh.getDay09PMRemark());
                dayJI.setDayJiExHours(oh.getDay09ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay10AM() == null ? 0 : oh.getDay10AM());
                dayJI.setDayJiAMRemark(oh.getDay10AMRemark() == null ? 0.0 : oh.getDay10AMRemark());
                dayJI.setDayJiPM(oh.getDay10PM() == null ? 0 : oh.getDay10PM());
                dayJI.setDayJiPMRemark(oh.getDay10PMRemark() == null ? 0.0 : oh.getDay10PMRemark());
                dayJI.setDayJiExHours(oh.getDay10ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay11AM() == null ? 0 : oh.getDay11AM());
                dayJI.setDayJiAMRemark(oh.getDay11AMRemark() == null ? 0.0 : oh.getDay11AMRemark());
                dayJI.setDayJiPM(oh.getDay11PM() == null ? 0 : oh.getDay11PM());
                dayJI.setDayJiPMRemark(oh.getDay11PMRemark() == null ? 0.0 : oh.getDay11PMRemark());
                dayJI.setDayJiExHours(oh.getDay11ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay12AM() == null ? 0 : oh.getDay12AM());
                dayJI.setDayJiAMRemark(oh.getDay12AMRemark() == null ? 0.0 : oh.getDay12AMRemark());
                dayJI.setDayJiPM(oh.getDay12PM() == null ? 0 : oh.getDay12PM());
                dayJI.setDayJiPMRemark(oh.getDay12PMRemark() == null ? 0.0 : oh.getDay12PMRemark());
                dayJI.setDayJiExHours(oh.getDay12ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay13AM() == null ? 0 : oh.getDay13AM());
                dayJI.setDayJiAMRemark(oh.getDay13AMRemark() == null ? 0.0 : oh.getDay13AMRemark());
                dayJI.setDayJiPM(oh.getDay13PM() == null ? 0 : oh.getDay13PM());
                dayJI.setDayJiPMRemark(oh.getDay13PMRemark() == null ? 0.0 : oh.getDay13PMRemark());
                dayJI.setDayJiExHours(oh.getDay13ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay14AM() == null ? 0 : oh.getDay14AM());
                dayJI.setDayJiAMRemark(oh.getDay14AMRemark() == null ? 0.0 : oh.getDay14AMRemark());
                dayJI.setDayJiPM(oh.getDay14PM() == null ? 0 : oh.getDay14PM());
                dayJI.setDayJiPMRemark(oh.getDay14PMRemark() == null ? 0.0 : oh.getDay14PMRemark());
                dayJI.setDayJiExHours(oh.getDay14ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay15AM() == null ? 0 : oh.getDay15AM());
                dayJI.setDayJiAMRemark(oh.getDay15AMRemark() == null ? 0.0 : oh.getDay15AMRemark());
                dayJI.setDayJiPM(oh.getDay15PM() == null ? 0 : oh.getDay15PM());
                dayJI.setDayJiPMRemark(oh.getDay15PMRemark() == null ? 0.0 : oh.getDay15PMRemark());
                dayJI.setDayJiExHours(oh.getDay15ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay16AM() == null ? 0 : oh.getDay16AM());
                dayJI.setDayJiAMRemark(oh.getDay16AMRemark() == null ? 0.0 : oh.getDay16AMRemark());
                dayJI.setDayJiPM(oh.getDay16PM() == null ? 0 : oh.getDay16PM());
                dayJI.setDayJiPMRemark(oh.getDay16PMRemark() == null ? 0.0 : oh.getDay16PMRemark());
                dayJI.setDayJiExHours(oh.getDay16ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay17AM() == null ? 0 : oh.getDay17AM());
                dayJI.setDayJiAMRemark(oh.getDay17AMRemark() == null ? 0.0 : oh.getDay17AMRemark());
                dayJI.setDayJiPM(oh.getDay17PM() == null ? 0 : oh.getDay17PM());
                dayJI.setDayJiPMRemark(oh.getDay17PMRemark() == null ? 0.0 : oh.getDay17PMRemark());
                dayJI.setDayJiExHours(oh.getDay17ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay18AM() == null ? 0 : oh.getDay18AM());
                dayJI.setDayJiAMRemark(oh.getDay18AMRemark() == null ? 0.0 : oh.getDay18AMRemark());
                dayJI.setDayJiPM(oh.getDay18PM() == null ? 0 : oh.getDay18PM());
                dayJI.setDayJiPMRemark(oh.getDay18PMRemark() == null ? 0.0 : oh.getDay18PMRemark());
                dayJI.setDayJiExHours(oh.getDay18ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay19AM() == null ? 0 : oh.getDay19AM());
                dayJI.setDayJiAMRemark(oh.getDay19AMRemark() == null ? 0.0 : oh.getDay19AMRemark());
                dayJI.setDayJiPM(oh.getDay19PM() == null ? 0 : oh.getDay19PM());
                dayJI.setDayJiPMRemark(oh.getDay19PMRemark() == null ? 0.0 : oh.getDay19PMRemark());
                dayJI.setDayJiExHours(oh.getDay19ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay20AM() == null ? 0 : oh.getDay20AM());
                dayJI.setDayJiAMRemark(oh.getDay20AMRemark() == null ? 0.0 : oh.getDay20AMRemark());
                dayJI.setDayJiPM(oh.getDay20PM() == null ? 0 : oh.getDay20PM());
                dayJI.setDayJiPMRemark(oh.getDay20PMRemark() == null ? 0.0 : oh.getDay20PMRemark());
                dayJI.setDayJiExHours(oh.getDay20ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay21AM() == null ? 0 : oh.getDay21AM());
                dayJI.setDayJiAMRemark(oh.getDay21AMRemark() == null ? 0.0 : oh.getDay21AMRemark());
                dayJI.setDayJiPM(oh.getDay21PM() == null ? 0 : oh.getDay21PM());
                dayJI.setDayJiPMRemark(oh.getDay21PMRemark() == null ? 0.0 : oh.getDay21PMRemark());
                dayJI.setDayJiExHours(oh.getDay21ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay22AM() == null ? 0 : oh.getDay22AM());
                dayJI.setDayJiAMRemark(oh.getDay22AMRemark() == null ? 0.0 : oh.getDay22AMRemark());
                dayJI.setDayJiPM(oh.getDay22PM() == null ? 0 : oh.getDay22PM());
                dayJI.setDayJiPMRemark(oh.getDay22PMRemark() == null ? 0.0 : oh.getDay22PMRemark());
                dayJI.setDayJiExHours(oh.getDay22ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay23AM() == null ? 0 : oh.getDay23AM());
                dayJI.setDayJiAMRemark(oh.getDay23AMRemark() == null ? 0.0 : oh.getDay23AMRemark());
                dayJI.setDayJiPM(oh.getDay23PM() == null ? 0 : oh.getDay23PM());
                dayJI.setDayJiPMRemark(oh.getDay23PMRemark() == null ? 0.0 : oh.getDay23PMRemark());
                dayJI.setDayJiExHours(oh.getDay23ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay24AM() == null ? 0 : oh.getDay24AM());
                dayJI.setDayJiAMRemark(oh.getDay24AMRemark() == null ? 0.0 : oh.getDay24AMRemark());
                dayJI.setDayJiPM(oh.getDay24PM() == null ? 0 : oh.getDay24PM());
                dayJI.setDayJiPMRemark(oh.getDay24PMRemark() == null ? 0.0 : oh.getDay24PMRemark());
                dayJI.setDayJiExHours(oh.getDay24ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay25AM() == null ? 0 : oh.getDay25AM());
                dayJI.setDayJiAMRemark(oh.getDay25AMRemark() == null ? 0.0 : oh.getDay25AMRemark());
                dayJI.setDayJiPM(oh.getDay25PM() == null ? 0 : oh.getDay25PM());
                dayJI.setDayJiPMRemark(oh.getDay25PMRemark() == null ? 0.0 : oh.getDay25PMRemark());
                dayJI.setDayJiExHours(oh.getDay25ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay26AM() == null ? 0 : oh.getDay26AM());
                dayJI.setDayJiAMRemark(oh.getDay26AMRemark() == null ? 0.0 : oh.getDay26AMRemark());
                dayJI.setDayJiPM(oh.getDay26PM() == null ? 0 : oh.getDay26PM());
                dayJI.setDayJiPMRemark(oh.getDay26PMRemark() == null ? 0.0 : oh.getDay26PMRemark());
                dayJI.setDayJiExHours(oh.getDay26ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay27AM() == null ? 0 : oh.getDay27AM());
                dayJI.setDayJiAMRemark(oh.getDay27AMRemark() == null ? 0.0 : oh.getDay27AMRemark());
                dayJI.setDayJiPM(oh.getDay27PM() == null ? 0 : oh.getDay27PM());
                dayJI.setDayJiPMRemark(oh.getDay27PMRemark() == null ? 0.0 : oh.getDay27PMRemark());
                dayJI.setDayJiExHours(oh.getDay27ExHours());
                dayJIList.add(dayJI);

                dayJI = new DayJI();
                dayJI.setDayJiAM(oh.getDay28AM() == null ? 0 : oh.getDay28AM());
                dayJI.setDayJiAMRemark(oh.getDay28AMRemark() == null ? 0.0 : oh.getDay28AMRemark());
                dayJI.setDayJiPM(oh.getDay28PM() == null ? 0 : oh.getDay28PM());
                dayJI.setDayJiPMRemark(oh.getDay28PMRemark() == null ? 0.0 : oh.getDay28PMRemark());
                dayJI.setDayJiExHours(oh.getDay28ExHours());
                dayJIList.add(dayJI);

                row = hssfSheet.createRow(beginRow++);
                row.setHeight((short) 400);
                cell = row.createCell(0);
                cell.setCellValue(a + 1);
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(1);
                cell.setCellValue(oh.getName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(oh.getDeptName());
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(3);
                cell.setCellValue("上午");
                cell.setCellStyle(cellStyleA);

                row2 = hssfSheet.createRow(beginRow++);
                row2.setHeight((short) 400);
                cell = row2.createCell(3);
                cell.setCellValue("下午");
                cell.setCellStyle(cellStyleA);

                row3 = hssfSheet.createRow(beginRow++);
                row3.setHeight((short) 400);
                cell = row3.createCell(3);
                cell.setCellValue("加班");
                cell.setCellStyle(cellStyleA);

                int week;
                for (int n = 0; n < dayJIList.size(); n++) {
                    dayJI = dayJIList.get(n);
                    inComStr = StringUtil.StringToDateStr(oh.getEmpNo());
                    isWeekEnd = DateUtil.checkIsWeekEnd(wd, (n + 1) + "");
                    isFaDing = DateUtil.checkIsFaDing(fd, (n + 1) + "");
                    if (n < 9) {
                        ymdStr = yearMonth + "-0" + (n + 1);
                    } else {
                        ymdStr = yearMonth + "-" + (n + 1);
                    }
                    week = DateUtil.getWeek(ymdStr);
                    if (oh != null && dayJI.getDayJiAM() != null) {
                        if (dayJI.getDayJiAM() == 1) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 2) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 4) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (dayJI.getDayJiAM() == 6) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("△");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (dayJI.getDayJiAM() == 11) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("▲");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (dayJI.getDayJiAM() == 12) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 13) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("夜");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 15) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (dayJI.getDayJiAM() == 18) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 7) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }

                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 8) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("✖");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 17) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3BOP);
                                } else {
                                    cell.setCellStyle(cellStyle3BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3P);
                                } else {
                                    cell.setCellStyle(cellStyle3);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 67) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 77) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 61) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 16) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue(dayJI.getDayJiAMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 19) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue(dayJI.getDayJiAMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 20) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("婚");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 21) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("丧");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 22) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 23) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("陪");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 108) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 107) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOP);
                                } else {
                                    cell.setCellStyle(cellStyle6BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6P);
                                } else {
                                    cell.setCellStyle(cellStyle6);
                                }
                            }

                        } else if (dayJI.getDayJiAM() == 106) {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else {
                            cell = row.createCell(4 + n);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        }
                    }
                    if (oh != null && dayJI.getDayJiPM() != null) {

                        if (dayJI.getDayJiPM() == 1) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 2) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 4) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("假");
                            cell.setCellStyle(cellStyleBR);
                        } else if (dayJI.getDayJiPM() == 6) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("△");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (dayJI.getDayJiPM() == 11) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("▲");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (dayJI.getDayJiPM() == 12) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 13) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("班");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 15) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("假");
                            cell.setCellStyle(cellStyleBR);
                        } else if (dayJI.getDayJiPM() == 18) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 7) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }

                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 8) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("✖");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 17) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3BOP);
                                } else {
                                    cell.setCellStyle(cellStyle3BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3P);
                                } else {
                                    cell.setCellStyle(cellStyle3);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 67) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 77) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 61) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 16) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue(dayJI.getDayJiPMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 19) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue(dayJI.getDayJiPMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 20) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 21) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 22) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 23) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 108) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 107) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOP);
                                } else {
                                    cell.setCellStyle(cellStyle6BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6P);
                                } else {
                                    cell.setCellStyle(cellStyle6);
                                }
                            }

                        } else if (dayJI.getDayJiPM() == 106) {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else {
                            cell = row2.createCell(4 + n);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        }
                    }
                    if (oh != null && dayJI.getDayJiExHours() != null) {
                        cell = row3.createCell(4 + n);
                        cell.setCellValue((dayJI.getDayJiExHours() == 0.0 ? "" : dayJI.getDayJiExHours()).toString());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBOP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleAP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        }

                    } else {
                        cell = row3.createCell(4 + n);
                        cell.setCellValue((dayJI.getDayJiExHours() == null ? "" : dayJI.getDayJiExHours()).toString());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBOP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleAP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        }
                    }
                }


                if (29 <= days) {
                    inComStr = StringUtil.StringToDateStr(oh.getEmpNo());
                    isWeekEnd = DateUtil.checkIsWeekEnd(wd, 29 + "");
                    ymdStr = yearMonth + "-29";
                    week = DateUtil.getWeek(ymdStr);
                    if (oh != null && oh.getDay29AM() != null) {
                        if (oh.getDay29AM() == 1) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 2) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 4) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay29AM() == 6) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("△");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay29AM() == 11) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("▲");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay29AM() == 12) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 13) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("夜");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 15) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay29AM() == 18) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 7) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }

                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay29AM() == 8) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("✖");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay29AM() == 17) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3BOP);
                                } else {
                                    cell.setCellStyle(cellStyle3BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3P);
                                } else {
                                    cell.setCellStyle(cellStyle3);
                                }
                            }
                        } else if (oh.getDay29AM() == 67) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 77) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 61) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 16) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue(oh.getDay29AMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 19) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue(oh.getDay29AMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay29AM() == 20) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("婚");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 21) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("丧");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 22) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 23) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("陪");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 108) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29AM() == 107) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOP);
                                } else {
                                    cell.setCellStyle(cellStyle6BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6P);
                                } else {
                                    cell.setCellStyle(cellStyle6);
                                }
                            }

                        } else if (oh.getDay29AM() == 106) {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else {
                            cell = row.createCell(4 + 28);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        }
                    }
                    if (oh != null && oh.getDay29PM() != null) {
                        if (oh.getDay29PM() == 1) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 2) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 4) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("假");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay29PM() == 6) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("△");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay29PM() == 11) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("▲");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay29PM() == 12) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 13) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("班");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 15) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay29PM() == 18) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 7) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }

                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay29PM() == 8) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("✖");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay29PM() == 17) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3BOP);
                                } else {
                                    cell.setCellStyle(cellStyle3BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3P);
                                } else {
                                    cell.setCellStyle(cellStyle3);
                                }
                            }
                        } else if (oh.getDay29PM() == 67) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 77) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 61) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 16) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue(oh.getDay29PMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 19) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue(oh.getDay29PMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay29PM() == 20) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("婚");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 21) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("丧");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 22) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 23) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 108) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay29PM() == 107) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOP);
                                } else {
                                    cell.setCellStyle(cellStyle6BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6P);
                                } else {
                                    cell.setCellStyle(cellStyle6);
                                }
                            }

                        } else if (oh.getDay29PM() == 106) {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else {
                            cell = row2.createCell(4 + 28);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        }
                    }

                    if (oh != null && oh.getDay29ExHours() != null) {
                        cell = row3.createCell(4 + 28);
                        cell.setCellValue(oh.getDay29ExHours() == 0.0 ? "" : oh.getDay29ExHours().toString());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBOP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleAP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        }

                    } else {
                        cell = row3.createCell(4 + 28);
                        cell.setCellValue(oh.getDay29ExHours() == null ? "" : oh.getDay29ExHours().toString());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBOP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleAP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        }
                    }
                }

                if (30 <= days) {
                    inComStr = StringUtil.StringToDateStr(oh.getEmpNo());
                    isWeekEnd = DateUtil.checkIsWeekEnd(wd, 30 + "");
                    ymdStr = yearMonth + "-30";
                    week = DateUtil.getWeek(ymdStr);
                    if (oh != null && oh.getDay30AM() != null) {
                        if (oh.getDay30AM() == 1) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 2) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 4) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay30AM() == 6) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("△");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay30AM() == 11) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("▲");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay30AM() == 12) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 13) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("夜");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 15) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay30AM() == 18) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 7) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }

                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay30AM() == 8) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("✖");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay30AM() == 17) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3BOP);
                                } else {
                                    cell.setCellStyle(cellStyle3BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3P);
                                } else {
                                    cell.setCellStyle(cellStyle3);
                                }
                            }
                        } else if (oh.getDay30AM() == 67) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 77) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 61) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 16) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue(oh.getDay30AMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 19) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue(oh.getDay30AMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay30AM() == 20) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("婚");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 21) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("丧");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 22) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 23) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("陪");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 108) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 107) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOP);
                                } else {
                                    cell.setCellStyle(cellStyle6BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6P);
                                } else {
                                    cell.setCellStyle(cellStyle6);
                                }
                            }

                        } else if (oh.getDay30AM() == 106) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        }
                    }
                    if (oh != null && oh.getDay30PM() != null) {
                        if (oh.getDay30PM() == 1) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 2) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 4) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay30AM() == 6) {
                            cell = row.createCell(4 + 29);
                            cell.setCellValue("△");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay30PM() == 11) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("▲");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay30PM() == 12) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 13) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("班");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 15) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("假");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay30PM() == 18) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 7) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }

                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay30PM() == 8) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("✖");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay30PM() == 17) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3BOP);
                                } else {
                                    cell.setCellStyle(cellStyle3BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3P);
                                } else {
                                    cell.setCellStyle(cellStyle3);
                                }
                            }
                        } else if (oh.getDay30PM() == 67) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 77) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 61) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 16) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue(oh.getDay30PMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 19) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue(oh.getDay30PMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay30PM() == 20) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 21) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("丧");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 22) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 23) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 108) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 107) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOP);
                                } else {
                                    cell.setCellStyle(cellStyle6BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6P);
                                } else {
                                    cell.setCellStyle(cellStyle6);
                                }
                            }

                        } else if (oh.getDay30PM() == 106) {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else {
                            cell = row2.createCell(4 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        }
                    }
                    if (oh != null && oh.getDay30ExHours() != null) {
                        cell = row3.createCell(4 + 29);
                        cell.setCellValue(oh.getDay30ExHours() == 0.0 ? "" : oh.getDay30ExHours().toString());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBOP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleAP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        }

                    } else {
                        cell = row3.createCell(4 + 29);
                        cell.setCellValue(oh.getDay30ExHours() == null ? "" : oh.getDay30ExHours().toString());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBOP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleAP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        }
                    }
                }

                if (31 <= days) {
                    inComStr = StringUtil.StringToDateStr(oh.getEmpNo());
                    isWeekEnd = DateUtil.checkIsWeekEnd(wd, 31 + "");
                    ymdStr = yearMonth + "-31";
                    week = DateUtil.getWeek(ymdStr);
                    if (oh != null && oh.getDay31AM() != null) {
                        if (oh.getDay31AM() == 1) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 2) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 4) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay31AM() == 6) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("△");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay31AM() == 11) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("▲");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay31AM() == 12) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 13) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("夜");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 15) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay31AM() == 18) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 7) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }

                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay31AM() == 8) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("✖");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay31AM() == 17) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3BOP);
                                } else {
                                    cell.setCellStyle(cellStyle3BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3P);
                                } else {
                                    cell.setCellStyle(cellStyle3);
                                }
                            }
                        } else if (oh.getDay31AM() == 67) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 77) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 61) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 16) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue(oh.getDay31AMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 19) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue(oh.getDay31AMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay31AM() == 20) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("婚");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 21) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("丧");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 22) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 23) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("陪");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 108) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 107) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOP);
                                } else {
                                    cell.setCellStyle(cellStyle6BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6P);
                                } else {
                                    cell.setCellStyle(cellStyle6);
                                }
                            }

                        } else if (oh.getDay31AM() == 106) {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else {
                            cell = row.createCell(4 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        }
                    }
                    if (oh != null && oh.getDay31PM() != null) {
                        if (oh.getDay31PM() == 1) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 2) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 4) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("假");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay31PM() == 6) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("△");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay31PM() == 11) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("▲");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay31PM() == 12) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 13) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("班");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 15) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("假");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay31PM() == 18) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 7) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }

                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay31PM() == 8) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("✖");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay31PM() == 17) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3BOP);
                                } else {
                                    cell.setCellStyle(cellStyle3BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3P);
                                } else {
                                    cell.setCellStyle(cellStyle3);
                                }
                            }
                        } else if (oh.getDay31PM() == 67) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 77) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 61) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 16) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue(oh.getDay31PMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 19) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue(oh.getDay31PMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay31PM() == 20) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 21) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 22) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 23) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 108) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 107) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOP);
                                } else {
                                    cell.setCellStyle(cellStyle6BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6P);
                                } else {
                                    cell.setCellStyle(cellStyle6);
                                }
                            }

                        } else if (oh.getDay31PM() == 106) {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else {
                            cell = row2.createCell(4 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        }
                    }
                    if (oh != null && oh.getDay31ExHours() != null) {
                        cell = row3.createCell(4 + 30);
                        cell.setCellValue(oh.getDay31ExHours() == 0.0 ? "" : oh.getDay31ExHours().toString());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBOP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleAP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        }

                    } else {
                        cell = row3.createCell(4 + 30);
                        cell.setCellValue(oh.getDay31ExHours() == null ? "" : oh.getDay31ExHours().toString());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBOP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleAP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        }
                    }


                }


                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 0, 0);
                hssfSheet.addMergedRegion(region);
                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 1, 1);
                hssfSheet.addMergedRegion(region);
                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 2, 2);
                hssfSheet.addMergedRegion(region);

                dayJIList.clear();


            }


            fos = new FileOutputStream(finalDirPath + fileName);
            wb.write(fos);

        } catch (Exception e) {
            e.printStackTrace();
        }
        returnArray.add(pathname);
        return returnArray;
    }

}
