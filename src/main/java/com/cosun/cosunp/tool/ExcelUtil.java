package com.cosun.cosunp.tool;

import com.cosun.cosunp.entity.OutPutWorkData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/17 0017 上午 11:15
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ExcelUtil {



    public static List<String> writeExcel(List<OutPutWorkData> outDatas,String finalDirPath) {
        List<String> returnArray = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        OutPutWorkData opw;
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet1 = workbook.createSheet("sheet1");
        Date d = new Date();
        String str = sdf.format(d);
        String pathname = finalDirPath + "/" + str + "考勤结果表" + ".xlsx";
        returnArray.add( str + "考勤结果表"+".xlsx");
        File file = new File(pathname);
        if (file.exists()) {
            //如果文件存在就删除
            file.delete();
        }
        try {
            file.createNewFile();
            OutPutWorkData outData = null;
            Row row = null;
            Cell cell = null;
            //将内容写入指定的行号中
            row = sheet1.createRow(0);
            //根据行指定列坐标j,然后在单元格中写入数据
            cell = row.createCell(0);
            cell.setCellValue("序号");
            cell = row.createCell(1);
            cell.setCellValue("姓名");
            cell = row.createCell(2);
            cell.setCellValue("编号");
            cell = row.createCell(3);
            cell.setCellValue("部门");
            cell = row.createCell(4);
            cell.setCellValue("职位");
            cell = row.createCell(5);
            cell.setCellValue("职位属性");
            cell = row.createCell(6);
            cell.setCellValue("工作日");
            cell = row.createCell(7);
            cell.setCellValue("原考勤整个时间");
            cell = row.createCell(8);
            cell.setCellValue("几日(hao)");
            cell = row.createCell(9);
            cell.setCellValue("规定上午上班");
            cell = row.createCell(10);
            cell.setCellValue("上午上班打卡时间段始");
            cell = row.createCell(11);
            cell.setCellValue("上午上班打卡时间段止");
            cell = row.createCell(12);
            cell.setCellValue("上午上班打卡时间");
            cell = row.createCell(13);
            cell.setCellValue("备注");
            cell = row.createCell(14);
            cell.setCellValue("规定上午下班");
            cell = row.createCell(15);
            cell.setCellValue("上午下班打卡时间始");
            cell = row.createCell(16);
            cell.setCellValue("上午下班打止时间止");
            cell = row.createCell(17);
            cell.setCellValue("上午下班打卡时间");
            cell = row.createCell(18);
            cell.setCellValue("备注");
            cell = row.createCell(19);
            cell.setCellValue("规定下午上班");
            cell = row.createCell(20);
            cell.setCellValue("下午上班打卡时间始");
            cell = row.createCell(21);
            cell.setCellValue("下午上班打卡时间止");
            cell = row.createCell(22);
            cell.setCellValue("下午上班打卡时间");
            cell = row.createCell(23);
            cell.setCellValue("备注");
            cell = row.createCell(24);
            cell.setCellValue("规定下午下班");
            cell = row.createCell(25);
            cell.setCellValue("下午下班打卡时间始");
            cell = row.createCell(26);
            cell.setCellValue("下午下班打卡时间止");
            cell = row.createCell(27);
            cell.setCellValue("下午下班打卡时间");
            cell = row.createCell(28);
            cell.setCellValue("备注");
            cell = row.createCell(29);
            cell.setCellValue("加班时间始");
            cell = row.createCell(30);
            cell.setCellValue("加班时间止");
            cell = row.createCell(31);
            cell.setCellValue("加班时长");
            cell = row.createCell(32);
            cell.setCellValue("请假时间始");
            cell = row.createCell(33);
            cell.setCellValue("请假时间止");
            cell = row.createCell(34);
            cell.setCellValue("备注");
            for (int i = 0; i < outDatas.size(); i++) {
                opw = outDatas.get(i);
                row = sheet1.createRow(i + 1);
                //根据行指定列坐标j,然 后在单元格中写入数据
                cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell = row.createCell(1);
                cell.setCellValue(opw.getEmployeeName());
                cell = row.createCell(2);
                cell.setCellValue(opw.getEmpNo());
                cell = row.createCell(3);
                cell.setCellValue(opw.getDeptName());
                cell = row.createCell(4);
                cell.setCellValue(opw.getPositionName() );
                cell = row.createCell(5);
                cell.setCellValue(opw.getPositionLevel());
                cell = row.createCell(6);
                cell.setCellValue(opw.getWorkDate());
                cell = row.createCell(7);
                cell.setCellValue(opw.getOrginClockInStr());
                cell = row.createCell(8);
                cell.setCellValue(opw.getWorkInDate());
                cell = row.createCell(9);
                cell.setCellValue(opw.getWorkSetAOn());
                cell = row.createCell(10);
                cell.setCellValue(opw.getWorkSetAonFroml());
                cell = row.createCell(11);
                cell.setCellValue(opw.getWorkSetAOnEnd());
                cell = row.createCell(12);
                cell.setCellValue(opw.getClockAOn());
                cell = row.createCell(13);
                cell.setCellValue(opw.getIsAonOk());
                cell = row.createCell(14);
                cell.setCellValue(opw.getWorkSetAOff());
                cell = row.createCell(15);
                cell.setCellValue(opw.getWorkSetAOffFrom());
                cell = row.createCell(16);
                cell.setCellValue(opw.getWorkSetAOffEnd());
                cell = row.createCell(17);
                cell.setCellValue(opw.getClockAOff());
                cell = row.createCell(18);
                cell.setCellValue(opw.getIsAoffOk());
                cell = row.createCell(19);
                cell.setCellValue(opw.getWorkSetPOn());
                cell = row.createCell(20);
                cell.setCellValue(opw.getWorkSetPOnFrom());
                cell = row.createCell(21);
                cell.setCellValue(opw.getWorkSetPOnEnd());
                cell = row.createCell(22);
                cell.setCellValue(opw.getClockPOn());
                cell = row.createCell(23);
                cell.setCellValue(opw.getIsPOnOk());
                cell = row.createCell(24);
                cell.setCellValue(opw.getWorkSetPOff());
                cell = row.createCell(25);
                cell.setCellValue(opw.getWorkSetPOffForm());
                cell = row.createCell(26);
                cell.setCellValue(opw.getWorkSetPOffEnd());
                cell = row.createCell(27);
                cell.setCellValue(opw.getClockPOff());
                cell = row.createCell(28);
                cell.setCellValue(opw.getIsPOffOk());
                cell = row.createCell(29);
                cell.setCellValue(opw.getWorkSetExtOn());
                cell = row.createCell(30);
                cell.setCellValue(opw.getWorkSetExtOff());
                cell = row.createCell(31);
                cell.setCellValue(opw.getExtHours()==null ? 0.0 :opw.getExtHours());
                cell = row.createCell(32);
                cell.setCellValue(opw.getLeaveDateStart());
                cell = row.createCell(33);
                cell.setCellValue(opw.getLeaveDateEnd());
                cell = row.createCell(34);
                cell.setCellValue(opw.getRemark());
            }
            OutputStream stream = new FileOutputStream(file);
            workbook.write(stream);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnArray.add(pathname);
        return returnArray;

    }
}
