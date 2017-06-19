package sabai.helper;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class GoogleDriveSheet {
	/** Application name. */
	private static final String APPLICATION_NAME =
			"Google Sheets API Java Quickstart";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY =
			JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/** Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials
	 * at ~/.credentials/sheets.googleapis.com-java-quickstart
	 */
	private static final List<String> SCOPES =
			Arrays.asList(SheetsScopes.DRIVE);
	//Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public static Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in =
				GoogleDriveSheet.class.getResourceAsStream("/client_secret.json");
		GoogleClientSecrets clientSecrets =
				GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow =
				new GoogleAuthorizationCodeFlow.Builder(
						HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(DATA_STORE_FACTORY)
				.setAccessType("offline")
				.build();
		Credential credential = new AuthorizationCodeInstalledApp(
				flow, new LocalServerReceiver()).authorize("user");
		System.err.println(
				"Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	/**
	 * Build and return an authorized Sheets API client service.
	 * @return an authorized Sheets API client service
	 * @throws IOException
	 */
	public static Sheets getSheetsService() throws IOException {
		Credential credential = authorize();
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME)
				.build();
	}

	public void connectToGooGleSheetAndView(int option) throws IOException {
		// Build a new authorized API client service.
		Sheets service = getSheetsService();

		// our - https://docs.google.com/spreadsheets/d/155_F_rIt-oIDC_agrXRk56JAaErKAnC4LXdb2kk74yM/edit#gid=0
		String spreadsheetId = "155_F_rIt-oIDC_agrXRk56JAaErKAnC4LXdb2kk74yM";

		String sheetName = "";
		if(option == 1)
			sheetName = "Anbar";
		else if(option == 2)
			sheetName = "Activity";
		else if(option == 3)
			sheetName = "TranHeader";
		String range = sheetName + "!A2:F";


		// String range = "Activity!A2:F";
		ValueRange response = service.spreadsheets().values()
				.get(spreadsheetId, range)
				.execute();
		List<List<Object>> values = response.getValues();
		if (values == null || values.size() == 0) {
			System.err.println("No data found.");
		} else {
			System.err.println("Name, Major");
			for (List row : values) {
				// Print columns A and E, which correspond to indices 0 and 4.
				System.err.printf("ROW - %s, %s %s %s %s %s\n", row.get(0), row.get(1),row.get(2), row.get(3),row.get(4), row.get(5));
			}
		}

		//UPDATE

	}

	public void connectToGooGleSheetAndInsertAnbar(List<Object> value1,int option) throws IOException {
		// Build a new authorized API client service.
		Sheets sheetsService = getSheetsService();
		String spreadsheetId = "155_F_rIt-oIDC_agrXRk56JAaErKAnC4LXdb2kk74yM";

		String sheetName = "";
		if(option == 1)
			sheetName = "Anbar";
		else if(option == 2)
			sheetName = "Activity";
		else if(option == 3)
			sheetName = "TranHeader";

		String range = sheetName + "!A2:N2";

		// How the input data should be interpreted.
		String insertDataOption = "INSERT_ROWS"; // TODO: Update placeholder value.
		String valueInputOption = "RAW";
		// TODO: Assign values to desired fields of `requestBody`:
		ValueRange requestBody = new ValueRange();
		System.err.println("Inside value1 = "+value1);
		//List<java.util.List<java.lang.Object>> values = new ArrayList<java.util.List<>>();
		List<List<Object>> values = new ArrayList<>();
		List<Object> value = new ArrayList<>();
		/*value.add("8");value.add("14/5/207");value.add("Donation");value.add("0");;value.add("0");
		value.add("1200");value.add("DO");value.add("8");value.add("XXX");	
		value.add("1200");value.add("DO");value.add("8");value.add("XXX");value.add("XXX");	
		System.err.println("Inside value = "+value);*/
		values.add(value1);
		requestBody.setValues(values);

		//Sheets sheetsService = createSheetsService();
		/*Sheets.Spreadsheets.Values.Update request =
			service.spreadsheets().values().update(spreadsheetId, range, requestBody);
		request.setValueInputOption(valueInputOption);

		UpdateValuesResponse response = request.execute();
		 */

		Sheets.Spreadsheets.Values.Append request =
				sheetsService.spreadsheets().values().append(spreadsheetId, range, requestBody);
		request.setValueInputOption(valueInputOption);
		request.setInsertDataOption(insertDataOption);

		AppendValuesResponse response = request.execute();
		// TODO: Change code below to process the `response` object:
		System.out.println(response);
	}


}