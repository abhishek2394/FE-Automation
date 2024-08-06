//package functions;
//
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.ss.usermodel.FillPatternType;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//public class ExcelUtility {
//
//    public FileInputStream fi;
//    public FileOutputStream fo;
//    public XSSFWorkbook workbook;
//    public XSSFSheet sheet;
//    public XSSFRow row;
//    public XSSFCell cell;
//    public CellStyle style;
//    String path;
//
//    ExcelUtility(String path)
//    {
//        this.path=path;
//    }
//
//    public int getRowCount(String sheetName) throws IOException
//    {
//        fi=new FileInputStream(path);
//        workbook=new XSSFWorkbook(fi);
//        sheet=workbook.getSheet(sheetName);
//        int rowcount=sheet.getLastRowNum();
//        fi.close();
//        return rowcount;
//    }
//
//
//    public int getCellCount(String sheetName,int rownum) throws IOException
//    {
//        fi=new FileInputStream(path);
//        workbook=new XSSFWorkbook(fi);
//        sheet=workbook.getSheet(sheetName);
//        row=sheet.getRow(rownum);
//        int cellcount=row.getLastCellNum();
//        fi.close();
//        return cellcount;
//    }
//
//
//    public String getCellData(String sheetName,int rownum,int colnum) throws IOException
//    {
//        fi=new FileInputStream(path);
//        workbook=new XSSFWorkbook(fi);
//        sheet=workbook.getSheet(sheetName);
//        row=sheet.getRow(rownum);
//        cell=row.getCell(colnum);
//
//        DataFormatter formatter = new DataFormatter();
//        String data;
//        try{
//            data = formatter.formatCellValue(cell); //Returns the formatted value of a cell as a String regardless of the cell type.
//        }
//        catch(Exception e)
//        {
//            data="";
//        }
//        fi.close();
//        return data;
//    }
//
//
//    public void fillRedColor(String sheetName,int rownum,int colnum) throws IOException
//    {
//        fi=new FileInputStream(path);
//        workbook=new XSSFWorkbook(fi);
//        sheet=workbook.getSheet(sheetName);
//        row=sheet.getRow(rownum);
//        cell=row.getCell(colnum);
//
//        style=workbook.createCellStyle();
//
//        style.setFillForegroundColor(IndexedColors.RED.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//        cell.setCellStyle(style);
//        workbook.write(fo);
//        workbook.close();
//        fi.close();
//        fo.close();
//    }
//
//    public String[][] getData(String sheetName) throws IOException {
//        fi=new FileInputStream(path);
//        workbook=new XSSFWorkbook(fi);
//        sheet=workbook.getSheet(sheetName);
//        int rowCount=sheet.getLastRowNum();
//        row = sheet.getRow(1);
//        int colCount = row.getLastCellNum();
//
//        String[][] fileData = new String[rowCount][colCount];
//
//        for(int i=1; i<rowCount; i++){
//            for(int j=0; j<colCount; j++){
//                fileData[i][j] = getCellData("Sheet1", i, j);
//            }
//        }
//        return fileData;
//    }
//
//
//}
