package sabai.transaction;	

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sabai.MainController;


@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddTransactionTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	/*@Test
	public void testRetriveTransactions() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		System.err.println("Enter testRetriveTransactions................");
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/rest/tjgs/tranhead/all"),
				HttpMethod.GET, entity, String.class);


		String expected = "{id:Course1,name:Spring,description:10 Steps}";
		System.err.println("REST RESULT : "+response.getBody().toString());

		System.err.println("Exit testRetriveTransactions................");
	}*/

	/*	@Test
	public void testPostTransaction() {
		System.err.println("Enter testPostTransaction................");


		String fooResourceUrl
		  = "http://localhost:8080/spring-rest/foos";
		ResponseEntity<String> response
		  = restTemplate.getForEntity(createURLWithPort("/rest/tjgs//book/all"), String.class);
		//assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

		System.err.println(" testPostTransaction................"+response.getStatusCode());
		System.err.println("exit testPostTransaction................"+response.getStatusCode());


	}
	 */


	/*@Test
	public void listPostTransaction() {
		System.err.println("Enter listPostTransaction................");
		 ParameterizedTypeReference<List<BookInfo>> myBean = new ParameterizedTypeReference<List<BookInfo>>() {};
		 ResponseEntity<List<BookInfo>> response = restTemplate.exchange(createURLWithPort("/rest/tjgs//book/all")
				 ,HttpMethod.GET, null, myBean);
		 List<BookInfo> bList = response.getBody();
		 bList.forEach((book)->{
			 System.err.println(book.getDataName()+":"+book.getDescTamil()+":"+book.getImageName());
		 });
		 System.err.println("Exit listPostTransaction................");

	}*/

	//	@Test

	public void postTransaction(Date tdate, String desc,double amt,String ei,String voc,String hc,String oi){
		//System.err.println("Enter listPostTransaction................");


		TranData td = new TranData();
		td.setAmount(amt);
		td.setDescription(desc);
		td.setHeaderCode(hc);
		td.setInExp(ei);
		td.setOtherInfo(oi);
		td.setVoucherNo(voc);
		td.setTransDate(tdate);

		HttpEntity<TranData> request = new HttpEntity<>(td);

		ResponseEntity<TranData> response = restTemplate
				.exchange(createURLWithPort("/rest/tjgs/tran/add"), HttpMethod.POST, request, TranData.class);
		//System.err.println("Exit listPostTransaction................");

	}

	@Test 
	public void postTrans(){		
		SimpleDateFormat sfd = new SimpleDateFormat("dd/mmm/yyyy");
		String	fileName = "2014.xlsx";
		String  sheetName ="2016";
		String filePath = "c:/temp/"+fileName;
		FileInputStream fs;
		XSSFWorkbook wb = null;
		try {
			fs = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFSheet sh = wb.getSheet(sheetName);
		//Row r = sh.getRow(1);
		//sCell c = r.getCell(1);
		long count = 0,zeroCount = 0,notSelecteed = 0;
		try {
			List<String> tempList = new ArrayList<String>();
			Iterator<Row> itr = sh.iterator();
			while(itr.hasNext()){
				Row row = itr.next();	
				Iterator ci = row.cellIterator();
				Cell c0 = row.getCell(0);

				Cell c1 = row.getCell(1);
				Cell c2 = row.getCell(2);
				Cell c3 = row.getCell(3);
				Cell c4 = row.getCell(4);
				Cell c5 = row.getCell(5);
				Cell c6= row.getCell(6);
				//System.out.println("---Starting row : "+ c0.getNumericCellValue());
				//DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
				Date tranDate = null;
				double amt = 0;
				String desc = "";
			
				String hc = "NULL";	
				
				if(c3 != null )
					amt = c3.getNumericCellValue();

				String ei ="i";
				if(amt == 0 && c4 != null){
					amt = c4.getNumericCellValue();
					ei = "e";
				}
				
				if(c1 != null){
					//System.out.println("Type of c1 column"+c1.getCellTypeEnum());
					tranDate = c1.getDateCellValue();
				}

				String tranDateS = "" + tranDate;
				//if(c1 != null)
					//tranDateS = c1.getStringCellValue();
				
				if(c2 != null)
					desc = c2.getStringCellValue();

			    if(c6 != null)
					hc = c6.getStringCellValue();
				
				String voc = "";
				if(c5 != null && c5.getCellTypeEnum() == CellType.NUMERIC)
					voc = ""+c5.getNumericCellValue();
				else if(c5 != null && c5.getCellTypeEnum() == CellType.STRING)
					voc = c5.getStringCellValue();
				
				if(amt > 0 )
				{
					String chS = "donation";
					String chSales = "sale";
					String chSser = "service";
					String chd = "deek";
					String chP = "print";
					String chD = "dtp";

					if(!hc.equals("NULL") && hc.length() > 0 ) count++;
					else if(ei.equals("i")){
						
						if(desc.toLowerCase().contains(chS))
						{	hc = "DO";  count++; }
						else if(desc.toLowerCase().contains("sale") || desc.toLowerCase().contains("service") 
								|| desc.toLowerCase().contains("deek") ){
							
							hc = "BS"; count ++;
						}
						else
						{
							hc ="MI";
							notSelecteed ++;
						}

					}
					else{ 
						if(desc.toLowerCase().contains("print") 
								|| desc.toLowerCase().contains("dtp")
								|| desc.toLowerCase().contains("lamination")
								|| desc.toLowerCase().contains("bill")) {
							hc = "PE";count++;
						}
						else if(desc.toLowerCase().contains("rent") || 
								desc.toLowerCase().contains("manda")){
							hc = "RE";	count++;
						}
						else if(desc.toLowerCase().contains("travel") || 
								desc.toLowerCase().contains("traveling")){ 
							hc="TE";	count++;
						}
						else if(desc.toLowerCase().contains("parcel")){
							hc="PC";	count++;
						}
						else if(desc.toLowerCase().contains("groce") || desc.toLowerCase().contains("water") || 
								desc.toLowerCase().contains("cook") || desc.toLowerCase().contains("annadhanam")) {
							hc = "FE";count++;
						}
						else if(desc.toLowerCase().contains("bank charge")){
							hc="BC";	count++;
						}
					
						else{
							hc="ME";
							notSelecteed ++;
						}
					}
				}
				else{
					hc="EMPTY";
					zeroCount ++;
				}
				//if(hc.equals("EMPTY"))
				System.err.println(c0.getNumericCellValue()+":---->"+tranDateS+"["+desc+"]["+amt+"]"+hc);
				
				this.postTransaction(tranDate,desc,amt,ei,voc,hc,"");
				if(c0 != null && c0.getNumericCellValue() == 323) break;
				

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total records processed count ="+count);
		System.out.println("Total records zero value count ="+zeroCount);
		System.out.println("Total records not assinged header count ="+notSelecteed);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}