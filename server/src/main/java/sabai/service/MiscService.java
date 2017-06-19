package sabai.service;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import sabai.helper.GoogleDriveSheet;

@Service
public class MiscService {
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());

	//////////////Google /////////////////////////
	@Async
	public  void connectGoogleSheetAddData(List<Object> value, int option)
	{
		GoogleDriveSheet qs = new GoogleDriveSheet();
		logger.error("Connecting to Google .....");				
		try {
			System.err.println("Before calling connectToGooGleSheetAndView");
			qs.connectToGooGleSheetAndView(option);
			System.err.println("After calling connectToGooGleSheetAndView");

		}
		catch(IOException ioEx)
		{
			logger.error("Exception while connecting to Google .....",ioEx);				
			System.err.println("Exception calling connectToGooGleSheetAndView");
			System.err.println(ioEx);
		}

		try {
			System.err.println("Before calling connectToGooGleSheetAndInsert");
			//List<Object> value = new ArrayList<>();
			//Adoptar.copyAnbargalToList(newAnbar,value);
			//System.err.println("newAnbar "+ 	newAnbar.toString());
			qs.connectToGooGleSheetAndInsertAnbar(value,option);
			System.err.println("after calling connectToGooGleSheetAndInsert");
		}	
		catch(IOException ioEx)
		{
			logger.error("Exception while connecting to Google .....",ioEx);				
			System.err.println("Exception calling connectToGooGleSheetAndInsert");
			System.err.println(ioEx);
		}		
	}
}
