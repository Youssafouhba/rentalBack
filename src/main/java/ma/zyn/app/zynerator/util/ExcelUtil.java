package ma.zyn.app.zynerator.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class ExcelUtil {

    public static boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static String getCellValueAsString(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (cell.getCellType() == CellType.STRING)? cell.getStringCellValue():cell.toString();
    }
    
    public static Boolean getCellValueAsBoolean(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (cell != null) ? cell.getBooleanCellValue() : null;
    }
    
    public static BigDecimal getCellValueAsBigDecimal(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (cell != null) ? BigDecimal.valueOf(cell.getNumericCellValue()) : null;
    }
    
    public static Double getCellValueAsDouble(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (cell != null) ? cell.getNumericCellValue() : null;
    }
    
    public static Integer getCellValueAsInteger(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (cell != null) ? Integer.valueOf(getCellValueAsDouble(row, cellIndex).toString()) : null;
    }
    
    public static Long getCellValueAsLong(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (cell != null) ? Long.valueOf(getCellValueAsDouble(row, cellIndex).toString()) : null;
    }
    
    public static LocalDateTime getCellValueAsLocalDateTime(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return cell == null ? null : cell.getLocalDateTimeCellValue();
    }

}
