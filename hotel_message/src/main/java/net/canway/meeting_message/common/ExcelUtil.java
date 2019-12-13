package net.canway.meeting_message.common;

import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {
    public static void modelToExcel(HttpServletResponse response,List<String[]> list,String sheetname,String filename) throws IOException {
        String[] header = list.get(0);


        //声明一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();

        //生成一个表格，设置表格名称为"学生表"
        XSSFSheet sheet = workbook.createSheet(sheetname);

        //设置表格列宽度为10个字节
            sheet.setDefaultColumnWidth(10);

        //创建第一行表头
        XSSFRow headrow = sheet.createRow(0);

        //遍历添加表头(下面模拟遍历学生，也是同样的操作过程)
            for (int i = 0; i < header.length; i++) {
            //创建一个单元格
            XSSFCell cell = headrow.createCell(i);

            //创建一个内容对象
            XSSFRichTextString text = new XSSFRichTextString(header[i]);

            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
    }
    for(int i = 1; i < list.size(); i++) {
        XSSFRow row = sheet.createRow(i);
        for (int j = 0; j < list.get(i).length; j++) {
            XSSFCell cell = row.createCell(j);
            XSSFRichTextString text = new XSSFRichTextString(list.get(i)[j]);
            cell.setCellValue(text);
        }

    }

    //准备将Excel的输出流通过response输出到页面下载
    //八进制输出流
        response.setContentType("application/octet-stream");

    //这后面可以设置导出Excel的名称，此例中名为student.xls
        response.setHeader("Content-disposition", "attachment;filename="+filename+".xlsx");

    //刷新缓冲
        response.flushBuffer();

    //workbook将Excel写入到response的输出流中，供页面下载
        workbook.write(response.getOutputStream());
        //关闭workbook
        workbook.close();
}
}
