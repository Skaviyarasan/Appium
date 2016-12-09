package com.DataFileAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



//import exception.ElementNotFoundException;

public class ExcelReader {	


	static Logger log = Logger.getLogger(ExcelReader.class.getName());



	public ExcelReader()
	{

	}

	/* 
	 * Method Name: getWorkSheet()
	 * 
	 * Input Parameters  : File Name 			(String)
	 *  				 : Sheet Name           (String)
	 * 
	 * Output Parameters : sheet                (Sheet)
	 * 
	 * Description		: This method takes XL file name and Sheet name as input and returns the Sheet object.
	 * 
	 */

	public Sheet getWorkSheet(String FileName, String SheetName) throws IOException
	{

		Sheet SheetObject = null;
		Workbook workbook = null;
		try
		{
			FileInputStream fis = new FileInputStream(FileName);			

			//Create Workbook instance for xlsx/xls/xlsm file input stream

			if((FileName.toLowerCase().endsWith("xlsx"))||((FileName.toLowerCase().endsWith("xlsm"))))
			{
				workbook = new XSSFWorkbook(fis);
			}
			else if(FileName.toLowerCase().endsWith("xls"))
			{
				workbook = new HSSFWorkbook(fis);
			}


			SheetObject = workbook.getSheet(SheetName);
			/*fis.close();*/


		/*	if(SheetObject == null)
			{
				       	throw new ElementNotFoundException("The Sheet "+SheetName+ " is not found in the File "+FileName+"\n\n",client);
			}*/
		}
		catch(Exception e)
		{
		//	log.error("Exception Occurred",e);
			e.printStackTrace();
		}

		return SheetObject;
	}

	/**************************************************************************************************************************************************************************/
	/**************************************************************************************************************************************************************************/


	/* 
	 * Method Name: readHeaderIndex()
	 * 
	 * Input Parameters  : File Name 			(String)
	 *  				 : Sheet Name           (String)
	 *  				 : Header               (String)
	 * 
	 * Output Parameters : HeaderIndex          (integer)
	 * 
	 * Description		: This method takes XL file name, Sheet name and header as input and returns index of the Header file.
	 * 
	 */	

	public int readHeaderIndex(String fileName,String sheetName, String Header)
	{
		String Parameters = "FileName : "+fileName+", SheetName : "+sheetName+", Header : "+Header+"\n";

		log.info("Inside Method readHeaderIndex() \n Parameters : "+Parameters);

		int ColumnIndex	=	0;
		boolean found = false;

		try
		{

			Sheet sheet = getWorkSheet(fileName,sheetName);

			if(sheet != null)
			{		
				Row HeaderRow=sheet.getRow(0);

				Iterator<Cell> HeaderCellIterator = HeaderRow.cellIterator();

				while(HeaderCellIterator.hasNext())
				{
					Cell celltoFind = HeaderCellIterator.next();
					if(celltoFind.toString().equals(Header))
					{
						ColumnIndex		= 	celltoFind.getColumnIndex();
						found = true;
					}
				}

				if(found == false)
				{
					//		    		throw new ElementNotFoundException("The Column "+Header+" is not found in the "+sheetName+" sheet of the File "+fileName,client);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			log.error("Exception Occurred!!!!", e);
		}


		return ColumnIndex+1;

	}

	/**************************************************************************************************************************************************************************/
	/**************************************************************************************************************************************************************************/


	/* 
	 * Method Name: readXLatIndex()
	 * 
	 * Input Parameters  : File Name 			(String)
	 *  				 : Sheet Name           (String)
	 *  				 : Row to Read          (integer)
	 *  				 : Column to Read       (integer)
	 * 
	 * Output Parameters : Cell Value           (String)
	 * 
	 * Description		: This method takes XL file name, Sheet name, Row index and Column index and returns the Cell Value.
	 * 
	 */	


	@SuppressWarnings("deprecation")
	public String readXLatIndex(String fileName,String sheetName, String cellContent, int ColumnIndex)
	{
		//String Parameters = "FileName : "+fileName+", SheetName : "+sheetName+", Row Index : "+RowIndex+", Column Index : "+ColumnIndex+"\n";
		//log.info("Inside Method readXLatIndex() \n Parameters : "+Parameters);


		int RowIndex=0;
		//RowIndex-= 1;
		ColumnIndex-=1;

		String ColumnStringValue	= "";


		try
		{

			Sheet sheet = getWorkSheet(fileName,sheetName);
			for (Row row : sheet) {
				for (Cell cell : row) {
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
							RowIndex= row.getRowNum(); 

						}
					}
				}
			} 

			if(sheet!=null)
			{
				Row DataRow=sheet.getRow(RowIndex);

				Cell celltoFind = DataRow.getCell(ColumnIndex);

				if(celltoFind != null)
				{

					switch( celltoFind.getCellType())
					{
					case Cell.CELL_TYPE_BLANK:
						ColumnStringValue = "";

					case Cell.CELL_TYPE_NUMERIC:

						//ColumnStringValue = celltoFind.toString().replaceAll("\\.?0*$", "");   //Removing Trailing Zeros and assigning to the String
						//ColumnStringValue = celltoFind.toString().replaceAll("\\.?0*$", "");
						long i = (long)celltoFind.getNumericCellValue();//Getting Numeric value from the sheet and Type casting to 'long' type to hold more than 12 digits 
						ColumnStringValue = String.valueOf(i);  
						break;

					case Cell.CELL_TYPE_STRING:

						ColumnStringValue = celltoFind.toString();
						break;

					case Cell.CELL_TYPE_BOOLEAN:

						ColumnStringValue = celltoFind.toString();

						break;

					case Cell.CELL_TYPE_ERROR:

						ColumnStringValue = celltoFind.toString();
						break;

					case Cell.CELL_TYPE_FORMULA:

						ColumnStringValue = celltoFind.toString();
						break;

					default:
						System.out.println("Default");
						ColumnStringValue	=	"";

					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Exception Occurred",e);
		}
		return ColumnStringValue;

	}

	/**************************************************************************************************************************************************************************/
	/**************************************************************************************************************************************************************************/


	/* 
	 * Method Name: readXLRow()
	 * 
	 * Input Parameters  : File Name 			(String)
	 *  				 : Sheet Name           (String)
	 *  				 : Row to read          (integer)
	 * 
	 * Output Parameters : XLRow                (String[])
	 * 
	 * Description		: This method returns the XL Row in an array of string.
	 * 
	 */

	public String[] readXLRow(String fileName,String SheetName,int RowtoRead)
	{
		String Parameters = "FileName : "+fileName+", SheetName : "+SheetName+", Row to Read : "+RowtoRead+"\n";
		log.info("Inside Method readXLRow() \n Parameters : "+Parameters);	

		RowtoRead = RowtoRead-1;
		String[] cellsinRow = null;

		try
		{
			Sheet sheet = getWorkSheet(fileName,SheetName);

			if(sheet!=null)
			{
				int i=0;

				int cellCount = readNumberofCellsinXL(fileName, SheetName);


				cellsinRow	=	new String[cellCount];

				Row DataRow=sheet.getRow(RowtoRead);


				Iterator<Cell> HeaderCellIterator1 = DataRow.cellIterator();

				while(HeaderCellIterator1.hasNext())
				{
					Cell CurrentRowCells1 = HeaderCellIterator1.next();


					switch( CurrentRowCells1.getCellType())
					{

					case Cell.CELL_TYPE_NUMERIC:

						cellsinRow[i] = CurrentRowCells1.toString().replaceAll("\\.?0*$", "");   //Removing Trailing Zeros and assigning to the String
						break;

					case Cell.CELL_TYPE_BLANK:

						cellsinRow[i] = "";

					case Cell.CELL_TYPE_STRING:

						cellsinRow[i] = CurrentRowCells1.toString();
						break;

					case Cell.CELL_TYPE_BOOLEAN:

						cellsinRow[i] = CurrentRowCells1.toString();
						break;

					case Cell.CELL_TYPE_ERROR:

						cellsinRow[i] = CurrentRowCells1.toString();
						break;

					case Cell.CELL_TYPE_FORMULA:

						cellsinRow[i] = CurrentRowCells1.toString();
						break;

					default:
						System.out.println("Defaul");
						cellsinRow[i]	=	"";					    	
					}

					i++;
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Exception Occurred",e);
		}

		return cellsinRow;
	}


	/* 
	 * Method Name: readNumberofRowsinXL()
	 * 
	 * Input Parameters  : File Name 			(String)
	 *  				 : Sheet Name           (String)
	 * 
	 * Output Parameters : number of Rows       (integer)
	 * 
	 * Description		: This method returns the number of rows in XL.
	 * 
	 */


	public int readNumberofRowsinXL(String fileName,String SheetName)
	{
		String Parameters = "FileName : "+fileName+", SheetName : "+SheetName+"\n";
		log.info("Inside Method readNumberofRowsinXL() \n Parameters : "+Parameters);	

		int NumberofRows = 0;


		try
		{
			Sheet sheet = getWorkSheet(fileName,SheetName);
			if(sheet!=null)
			{
				NumberofRows = sheet.getLastRowNum()+1;
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Exception Occurred",e);
		}

		return NumberofRows;
	}

	/**************************************************************************************************************************************************************************/
	/**************************************************************************************************************************************************************************/

	/* 
	 * Method Name: readNumberofCellsinXL()
	 * 
	 * Input Parameters  : File Name 			(String)
	 *  				 : Sheet Name           (String)
	 * 
	 * Output Parameters : number of Cells      (integer)
	 * 
	 * Description		: This method returns the number of cells in XL.
	 * 
	 */

	public int readNumberofCellsinXL(String fileName,String SheetName)
	{
		String Parameters = "FileName : "+fileName+", SheetName : "+SheetName+"\n";	
		log.info("Inside Method readNumberofCellsinXL() \n Parameters : "+Parameters);	

		int cellCount = 0;

		try
		{
			Sheet sheet = getWorkSheet(fileName,SheetName);
			if(sheet!=null)
			{
				Row DummyRow=sheet.getRow(0);

				Iterator<Cell> HeaderCellIterator = DummyRow.cellIterator();

				// Iterating to find the cell count

				while(HeaderCellIterator.hasNext())
				{
					Cell CurrentRowCells = HeaderCellIterator.next();
					cellCount++;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Exception Occurred",e);
		}


		return cellCount;
	}

	/**************************************************************************************************************************************************************************/
	/**************************************************************************************************************************************************************************/


	/* 
	 * Method Name: writeToXLCell()
	 * 
	 * Input Parameters  : File Name 			(String)
	 *  				 : Sheet Name           (String)
	 *  				 : row 					(Integer)
	 *  				 : Column				(Integer)
	 *  				 : Value to Write       (String)
	 * 
	 * 
	 * Description		: This method writes a String to the Given Cell.
	 * 
	 */

	public void writeToXLCell(String FileName, String SheetName, int row, int column, String ValuetoWrite)throws Exception
	{
		String Parameters = "FileName : "+FileName+", SheetName : "+SheetName+", Row Number : "+row+", Column Number : "+column+", \n Value to Write : " +ValuetoWrite+"\n";
		log.info("Inside Method writeToXLCell() \n Parameters : "+Parameters);	
		try
		{

			row-=1;
			column-=1;		

			Sheet sheet = getWorkSheet(FileName,SheetName);
			if(sheet!=null)
			{
				Row rowObject=sheet.createRow(row);
				Cell cell=rowObject.createCell(column);

				cell.setCellValue(ValuetoWrite);

				FileOutputStream outFile =new FileOutputStream(new File(FileName));

				Workbook workbook = sheet.getWorkbook();
				workbook.write(outFile);
				//outFile.close();
			}
		}
		catch(Exception e)
		{		
			e.printStackTrace();
			log.error("Exception Occurred",e);
		}

	}

	/**************************************************************************************************************************************************************************/
	/**************************************************************************************************************************************************************************/


	/* 
	 * Method Name: saveCopy()
	 * 
	 * Input Parameters  : File Name 			(String)
	 *  				 : New File Name           (String)
	 * 
	 * 
	 * Description		: This method Saves a copy of the given file with a new name given
	 */

	public void saveCopy(String FileName, String NewFileName)
	{
		String Parameters = "Source FileName : "+FileName+", Destination FileName : "+NewFileName+"\n";	
		log.info("Inside Method saveCopy() \n Parameters : "+Parameters);	

		try
		{
			File source	=	new File(FileName);
			File dest		=	new File(NewFileName); 

			Files.copy(source.toPath(), dest.toPath());

		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Exception Occurred",e);
		}
	}

	public int findRow(XSSFSheet sheet, String cellContent) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
						return row.getRowNum();  
					}
				}
			}
		}               
		return 0;
	}

}





