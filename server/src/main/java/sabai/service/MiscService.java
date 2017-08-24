package sabai.service;

import java.io.IOException;
import java.util.List;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sabai.helper.GoogleDriveSheet;

@Service
public class MiscService {
	private  final Logger logger = LogManager.getLogger(this.getClass());

	//////////////Google /////////////////////////
	@Async
	public  void connectGoogleSheetAddData(List<Object> value, int option)
	{
		GoogleDriveSheet qs = new GoogleDriveSheet();
		logger.debug("Connecting to Google .....");				
		try {
			//System.err.println("Before calling connectToGooGleSheetAndView");
			logger.debug("Before calling connectToGooGleSheetAndView");
			qs.connectToGooGleSheetAndView(option);
			logger.debug("After calling connectToGooGleSheetAndView");
			//System.err.println("After calling connectToGooGleSheetAndView");

		}
		catch(IOException ioEx)
		{
			logger.error("Exception while connecting to Google .....",ioEx);				
			//System.err.println("Exception calling connectToGooGleSheetAndView");
			//System.err.println(ioEx);
		}

		try {
			//System.err.println("Before calling connectToGooGleSheetAndInsert");
			
			//List<Object> value = new ArrayList<>();
			//Adoptar.copyAnbargalToList(newAnbar,value);
			//System.err.println("newAnbar "+ 	newAnbar.toString());
			logger.debug("Before calling connectToGooGleSheetAndInsert");
			
			qs.connectToGooGleSheetAndInsertAnbar(value,option);
			
			logger.debug("After calling connectToGooGleSheetAndInsert");
		}	
		catch(IOException ioEx)
		{
			logger.error("Exception while connecting to Google .....",ioEx);				
		}		
	}
}
