package clients;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class TodoClient{

	public static void main(String args[]) throws NumberFormatException, IOException {

		
		 
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a command: [for help type HELP, to exit type EXIT]");
		while(sc.hasNextLine()){
			String cmd = sc.nextLine();
			if(cmd.equals("EXIT")){
				sc.close();		
				System.exit(0);
			}
			parse(cmd);
			System.out.println("Enter a command: [for help type HELP, to exit type EXIT]");
		}
		sc.close();		
		
	}
	
	public static void parse(String cmd) throws NumberFormatException, IOException{
		String[] cmd_arr = cmd.split(" ");
	
		// IF POST
		if (cmd_arr[0].equals("POST")){
			String message = "";
			for (int i = 2; i < cmd_arr.length; i++){
				message = message + cmd_arr[i] + " "; 
			}
			try{
				int id = Integer.parseInt(cmd_arr[1]);
				post(message, id);
			}
			catch(NumberFormatException e){
				System.out.println("Not a valid POST expression -- see example of valid POST command below");
				System.out.println("POST 99 Go home and eat lunch");
			}
		}
		
		// IF DELETE
		else if (cmd_arr[0].equals("DELETE")){
			try{
				int id = Integer.parseInt(cmd_arr[1]);
				delete(id);
			}
			catch(NumberFormatException e){
				System.out.println("Not a valid DELETE expression -- see example of valid DELETE command below");
				System.out.println("DELETE 99");
			}
		}
		
		// IF REPLICATE
		else if (cmd_arr[0].equals("REPLICATE")){
			//replicate(cmd_arr[1]);
		}
		
		// IF GET 
		else if (cmd_arr[0].equals("GET")){
			if(cmd_arr.length >= 2){
				try{
					int id = Integer.parseInt(cmd_arr[1]);
					get(id);
				}
				catch(NumberFormatException e){
					System.out.println("Not a valid GET expression -- see example of valid GET command below");
					System.out.println("GET 99");
				}
			}
			else{
				getAll();
				}	
			}
		
		// IF COMMAND NOT RECOGNIZED
		else{
			System.out.println("Acceptable commands are listed below");
			System.out.println("POST [id] [todo message]");
			System.out.println("GET [id]");
			System.out.println("GET");
			System.out.println("DELETE [id]");
			System.out.println("REPLICATE [URI]");
		}
		
		
	}
	

	public static String post(String message, int id) throws IOException{
		//try {
			System.out.println("Making POST call");
			String id_string = String.valueOf(id);
			String urlParameters  = "id="+id_string+"&message=\""+message+"\"";
			byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
			int    postDataLength = postData.length;
			String request        = "http://localhost:8080/LAB2/Todo";
			URL    url            = new URL( request );
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();   
			conn.setDoOutput( true );
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod( "POST" );
			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty( "charset", "utf-8");
			conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
			conn.setUseCaches( false );
			try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
			   wr.write( postData );
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String next_record = null;
			while ((next_record = reader.readLine()) != null) {
				System.out.println(next_record);
			}
		//} //catch (IOException e) {
			//throw new RuntimeException("Please try again. \n" + e);
		//}
		return "Success";
	}
	

	public static void delete(int id) throws IOException{
		String id_string = String.valueOf(id);
		String urlParameters  = "id="+id_string;
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		String request        = "http://localhost:8080/LAB2/Todo";
		URL    url            = new URL( request );
		HttpURLConnection conn= (HttpURLConnection) url.openConnection();   
		conn.setDoOutput( true );
		conn.setInstanceFollowRedirects( false );
		conn.setRequestMethod( "DELETE" );
		conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
		conn.setRequestProperty( "charset", "utf-8");
		conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
		conn.setUseCaches( false );
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String next_record;
		String resp = "";
		while ((next_record = reader.readLine()) != null) {
			resp = resp + next_record;
		}
		conn.disconnect();
		reader.close();
	}
	
	
	
	
	
	
	public static void get(int id) throws IOException{
		String id_string = String.valueOf(id);
		String urlParameters  = "id="+id_string;
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		String request        = "http://localhost:8080/LAB2/Todo";
		URL    url            = new URL( request );
		HttpURLConnection conn= (HttpURLConnection) url.openConnection();   
		conn.setDoOutput( false );
		conn.setInstanceFollowRedirects( false );
		conn.setRequestMethod( "GET" );
		conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
		conn.setRequestProperty( "charset", "utf-8");
		conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
		conn.setUseCaches( false );
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String next_record;
		String resp = "";
		while ((next_record = reader.readLine()) != null) {
			resp = resp + next_record;
		}
		conn.disconnect();
		reader.close();
	}
	
	
	
	
	
	public static void getAll() throws IOException{
	
		String request        = "http://localhost:8080/LAB2/Todo";
		URL    url            = new URL( request );
		HttpURLConnection conn= (HttpURLConnection) url.openConnection();   
		conn.setDoOutput( false );
		conn.setInstanceFollowRedirects( false );
		conn.setRequestMethod( "GET" );
		conn.setRequestProperty( "charset", "utf-8");
		conn.setUseCaches( false );
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String next_record;
		String resp = "";
		while ((next_record = reader.readLine()) != null) {
			resp = resp + next_record;
		}
		conn.disconnect();
		reader.close();
	}
	
		
	
	

	

	
	

	
}
