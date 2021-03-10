package com.cisco.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    /**
     * <p>Row 0 needs to have all keys and Row 1 should have all the values for the keys</p>
     * <p>If multiline true, multiple values can be entered for single key</p>
     *
     * @param file
     * @param sheetName
     * @param multiline
     * @return
     */

    public static String[][] readTestData(String file, String sheetName, boolean multiline) {
        Workbook wb;
        Sheet sh;
        String[][] data = null;
        Cell cell;
        try (InputStream fis = Files.newInputStream(Paths.get(file))) {
            wb = WorkbookFactory.create(fis);
            sh = wb.getSheet(sheetName);
            if (sh != null) {
                if (multiline) {
                    int rowCnt = sh.getLastRowNum();
                    int colCnt = sh.getRow(0).getLastCellNum();
                    data = new String[rowCnt][colCnt];

                    for (int i = 0; i < rowCnt; i++)
                        for (int j = 0; j < colCnt; j++) {
                            cell = sh.getRow(i + 1).getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            switch (cell.getCellType()) {
                                case NUMERIC:
                                    data[i][j] = String.valueOf((int) cell.getNumericCellValue());
                                    break;
                                case STRING:
                                    data[i][j] = cell.getStringCellValue();
                                    break;
                                case BOOLEAN:
                                    data[i][j] = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case BLANK:
                                case _NONE:
                                    data[i][j] = "";
                                    break;
                                default:
                                    System.out.println("Verify the input text for the cell value");
                            }
                        }

                } else {
                    int rowCnt = sh.getLastRowNum() + 1;
                    int colCnt = sh.getRow(0).getLastCellNum();

                    data = new String[rowCnt][colCnt];

                    for (int i = 0; i < rowCnt; i++) {
                        for (int j = 0; j < colCnt; j++) {
                            cell = sh.getRow(i).getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            switch (cell.getCellType()) {
                                case NUMERIC:
                                    data[i][j] = String.valueOf((int) cell.getNumericCellValue());
                                    break;
                                case STRING:
                                    data[i][j] = cell.getStringCellValue();
                                    break;
                                case BOOLEAN:
                                    data[i][j] = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case BLANK:
                                case _NONE:
                                    data[i][j] = "";
                                    break;
                                default:
                                    System.out.println("Verify the input text for the cell value");
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }


    public static String[][] readTestDataForModule(String file, String sheetName, boolean multiline) {
        Workbook wb;
        Sheet sh;
        String[][] data = null;
        Cell cell;
        try (InputStream fis = Files.newInputStream(Paths.get(file))) {
            wb = WorkbookFactory.create(fis);
            sh = wb.getSheet(sheetName);
            int rowCnt = 0;
            if (sh != null) {
                if (multiline) {
                    rowCnt = sh.getLastRowNum();
                } else {
                    rowCnt = sh.getLastRowNum() + 1;
                }
                int colCnt = sh.getRow(0).getLastCellNum();
                data = new String[rowCnt][colCnt];
                for (int i = 0; i < rowCnt; i++) {
                    for (int j = 0; j < colCnt; j++) {
                        cell = sh.getRow(i + 1).getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                data[i][j] = String.valueOf((int) cell.getNumericCellValue());
                                break;
                            case STRING:
                                data[i][j] = cell.getStringCellValue();
                                break;
                            case BOOLEAN:
                                data[i][j] = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case BLANK:
                            case _NONE:
                                data[i][j] = "";
                                break;
                            default:
                                System.out.println("Verify the input text for the cell value");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public static List<Map<String, String>> getTestDataAsList(String file, String sheetName, boolean multiline) {
        Workbook wb;
        Sheet sh;
        String[][] data = null;
        Cell cell;
        try (InputStream fis = Files.newInputStream(Paths.get(file))) {
            wb = WorkbookFactory.create(fis);
            sh = wb.getSheet(sheetName);
            if (sh != null) {
                if (multiline) {
                    int rowCnt = sh.getLastRowNum();
                    int colCnt = sh.getRow(0).getLastCellNum();
                    data = new String[rowCnt][colCnt];

                    for (int i = 0; i < rowCnt; i++)
                        for (int j = 0; j < colCnt; j++) {
                            cell = sh.getRow(i + 1).getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            switch (cell.getCellType()) {
                                case NUMERIC:
                                    data[i][j] = String.valueOf((int) cell.getNumericCellValue());
                                    break;
                                case STRING:
                                    data[i][j] = cell.getStringCellValue();
                                    break;
                                case BOOLEAN:
                                    data[i][j] = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case BLANK:
                                case _NONE:
                                    data[i][j] = "";
                                    break;
                                default:
                                    System.out.println("Verify the input text for the cell value");
                            }
                        }

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        List<Map<String, String>> d = new ArrayList<>();
        return d;
    }

}
