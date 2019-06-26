package com.cosun.cosunp.tool;

import com.cosun.cosunp.entity.OutPutWorkData;
import com.cosun.cosunp.entity.SalaryDataOutPut;
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

    public static List<String> writeExcelSalary(List<SalaryDataOutPut> outDatas,String yearMonth,String finalDirPath) {
        List<String> returnArray = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SalaryDataOutPut opw;
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet1 = workbook.createSheet("sheet1");
        Date d = new Date();
        String str = sdf.format(d);
        String pathname = finalDirPath + "/linshi/" + yearMonth+str + "工资计算表" + ".xlsx";
        returnArray.add( yearMonth+str + "工资计算表" + ".xlsx");
        File file = new File(pathname);
        if (file.exists()) {
            //如果文件存在就删除
            file.delete();
        }
        try {
            file.createNewFile();
            SalaryDataOutPut outData = null;
            Row row = null;
            Cell cell = null;
            //将内容写入指定的行号中
            row = sheet1.createRow(0);
            //根据行指定列坐标j,然后在单元格中写入数据
            cell = row.createCell(0);
            cell.setCellValue("序号");
            cell = row.createCell(1);
            cell.setCellValue("部门");
            cell = row.createCell(2);
            cell.setCellValue("职务");
            cell = row.createCell(3);
            cell.setCellValue("职位");
            cell = row.createCell(4);
            cell.setCellValue("姓名");
            cell = row.createCell(5);
            cell.setCellValue("入职时间");
            cell = row.createCell(6);
            cell.setCellValue("基本工时");
            cell = row.createCell(7);
            cell.setCellValue("正常出勤工时");
            cell = row.createCell(8);
            cell.setCellValue("正常出勤工资");
            cell = row.createCell(9);
            cell.setCellValue("法定有薪假工时");
            cell = row.createCell(10);
            cell.setCellValue("法定有薪假工资");
            cell = row.createCell(11);
            cell.setCellValue("其它有薪假工时");
            cell = row.createCell(12);
            cell.setCellValue("其它有薪假工资");
            cell = row.createCell(13);
            cell.setCellValue("基本工资小计");
            cell = row.createCell(14);
            cell.setCellValue("平时加班工时");
            cell = row.createCell(15);
            cell.setCellValue("平时加班费");
            cell = row.createCell(16);
            cell.setCellValue("周末加班工时");
            cell = row.createCell(17);
            cell.setCellValue("周末加班费");
            cell = row.createCell(18);
            cell.setCellValue("法定假日加班工时");
            cell = row.createCell(19);
            cell.setCellValue("法定假日加班费");
            cell = row.createCell(20);
            cell.setCellValue("综合技能");
            cell = row.createCell(21);
            cell.setCellValue("岗位工资");
            cell = row.createCell(22);
            cell.setCellValue("职务工资");
            cell = row.createCell(23);
            cell.setCellValue("绩效奖金");
            cell = row.createCell(24);
            cell.setCellValue("绩效分数");
            cell = row.createCell(25);
            cell.setCellValue("奖金/技能小计");
            cell = row.createCell(26);
            cell.setCellValue("业务等级工资");
            cell = row.createCell(27);
            cell.setCellValue("业务等级实得");
            cell = row.createCell(28);
            cell.setCellValue("房补/话补");
            cell = row.createCell(29);
            cell.setCellValue("高温补贴及其它");
            cell = row.createCell(30);
            cell.setCellValue("全勤奖");
            cell = row.createCell(31);
            cell.setCellValue("工龄工资");
            cell = row.createCell(32);
            cell.setCellValue("业务提成");
            cell = row.createCell(33);
            cell.setCellValue("综合工资");
            cell = row.createCell(34);
            cell.setCellValue("扣代付餐费");
            cell = row.createCell(35);
            cell.setCellValue("扣代付水电");
            cell = row.createCell(36);
            cell.setCellValue("扣代付养老险");
            cell = row.createCell(37);
            cell.setCellValue("扣代付医疗险");
            cell = row.createCell(38);
            cell.setCellValue("扣代付失业险");
            cell = row.createCell(39);
            cell.setCellValue("扣代付公积金");
            cell = row.createCell(40);
            cell.setCellValue("其他扣款");
            cell = row.createCell(41);
            cell.setCellValue("专项附加扣除");
            cell = row.createCell(42);
            cell.setCellValue("个税");
            cell = row.createCell(43);
            cell.setCellValue("实发");
            for (int i = 0; i < outDatas.size(); i++) {
                opw = outDatas.get(i);
                row = sheet1.createRow(i + 1);
                //根据行指定列坐标j,然 后在单元格中写入数据
                cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell = row.createCell(1);
                cell.setCellValue(opw.getDeptName());
                cell = row.createCell(2);
                cell.setCellValue(opw.getPositionName());
                cell = row.createCell(3);
                cell.setCellValue(opw.getPositionAttrName());
                cell = row.createCell(4);
                cell.setCellValue(opw.getName() );
                cell = row.createCell(5);
                cell.setCellValue(opw.getInCompDate());
                cell = row.createCell(6);
                cell.setCellValue(opw.getBasickWorkHours());
                cell = row.createCell(7);
                cell.setCellValue(opw.getNorAttenHours());
                cell = row.createCell(8);
                cell.setCellValue(opw.getNorAttendSalary());
                cell = row.createCell(9);
                cell.setCellValue(opw.getChinaPailLeavHours());
                cell = row.createCell(10);
                cell.setCellValue(opw.getChinaPaidLeavSalary());
                cell = row.createCell(11);
                cell.setCellValue(opw.getOtherPaidLeavHours());
                cell = row.createCell(12);
                cell.setCellValue(opw.getOtherPaidLeavSalary());
                cell = row.createCell(13);
                cell.setCellValue(opw.getBasicSalarySubTotal());
                cell = row.createCell(14);
                cell.setCellValue(opw.getUsualExtraHours());
                cell = row.createCell(15);
                cell.setCellValue(opw.getUsralExtraSalary());
                cell = row.createCell(16);
                cell.setCellValue(opw.getWeekendWorkHours());
                cell = row.createCell(17);
                cell.setCellValue(opw.getWeekendWorkSalary());
                cell = row.createCell(18);
                cell.setCellValue(opw.getChinaHoliWorkHours());
                cell = row.createCell(19);
                cell.setCellValue(opw.getChinaHoliWorkSalary());
                cell = row.createCell(20);
                cell.setCellValue(opw.getCompressSalary());
                cell = row.createCell(21);
                cell.setCellValue(opw.getJobSalary());
                cell = row.createCell(22);
                cell.setCellValue(opw.getPositionSalary());
                cell = row.createCell(23);
                cell.setCellValue(opw.getMeritSalary());
                cell = row.createCell(24);
                cell.setCellValue(opw.getMeritScore());
                cell = row.createCell(25);
                cell.setCellValue(opw.getSubbonusTotal());
                cell = row.createCell(26);
                cell.setCellValue(opw.getSalorLevelSalary());
                cell = row.createCell(27);
                cell.setCellValue(opw.getSalrActuGetSalary());
                cell = row.createCell(28);
                cell.setCellValue(opw.getHouseOrTELSubsidy());
                cell = row.createCell(29);
                cell.setCellValue(opw.getHotTempOrOtherAllow());
                cell = row.createCell(30);
                cell.setCellValue(opw.getFullWorkReword());
                cell = row.createCell(31);
                cell.setCellValue(opw.getWorkYearsSalary());
                cell = row.createCell(32);
                cell.setCellValue(opw.getSellCommi());
                cell = row.createCell(33);
                cell.setCellValue(opw.getCompreSalary());
                cell = row.createCell(34);
                cell.setCellValue(opw.getBuckFoodCost());
                cell = row.createCell(35);
                cell.setCellValue(opw.getBuckWaterEleCost());
                cell = row.createCell(36);
                cell.setCellValue(opw.getBuckOldAgeInsurCost());
                cell = row.createCell(37);
                cell.setCellValue(opw.getBuckMedicInsurCost());
                cell = row.createCell(38);
                cell.setCellValue(opw.getBuckUnEmployCost());
                cell = row.createCell(39);
                cell.setCellValue(opw.getBuckAccumCost());
                cell = row.createCell(40);
                cell.setCellValue(opw.getOtherBuckCost());
                cell = row.createCell(41);
                cell.setCellValue(opw.getSixDeducCost());
                cell = row.createCell(42);
                cell.setCellValue(opw.getPersonIncomTaxCost());
                cell = row.createCell(43);
                cell.setCellValue(opw.getNetPaySalary());
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
