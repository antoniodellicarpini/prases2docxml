package phrases2docXML;

import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import jxl.write.Number;
public class querySet {
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}

	public static void main(String[] args) throws IOException, RowsExceededException, WriteException {
		// TODO Auto-generated method stub

		//FileWriter outFile = new FileWriter("C:\\Users\\Windows\\Desktop\\docCorpus\\file.txt");
		//PrintWriter out = new PrintWriter(outFile);
		WritableWorkbook workbook = Workbook.createWorkbook(new File("C:\\Users\\Windows\\Desktop\\docCorpus\\Test2.xls"));
    	WritableSheet sheet = workbook.createSheet("First Sheet", 0);
    	int numRow=0;
    	sheet.addCell(new Label(0, numRow, "N Docs")); 
    	sheet.addCell(new Label(1, numRow, "N Clusters"));
    	sheet.addCell(new Label(2, numRow, "N Hit"));
    	sheet.addCell(new Label(3, numRow, "N DocHit"));
    	sheet.addCell(new Label(4, numRow, "Query Type"));
    	sheet.addCell(new Label(5, numRow, "QTime"));
    	sheet.addCell(new Label(6, numRow, "CorpusDocs"));
 	
    	HashSet words=new HashSet(); 
    		
	  for(int j=0;j<7;j++)
	  {  
		  
		String content = readFile("C:\\Users\\Windows\\Desktop\\docCorpus\\TXT\\"+(j+1)+".txt", StandardCharsets.UTF_8);	
		String[] frase=content.split("\\.");
		
		for(int i=0;i<frase.length; i++)
		{
						
			
			String[] f= frase[i].split("\\s+");			
			Random rand= new Random();
			if(f.length>2)
			{
				//**********************************
				 //String first=rb.req.getParams().get("first");
				 SolrServer server = new HttpSolrServer("http://localhost:8983/solr/corpus");
				 SolrQuery query = new SolrQuery();
				 boolean match=false;
				 int contatore=0;
				 String queryType="";
				 while(!match)
				 {	
					 int r=rand.nextInt(f.length-1);
					 queryType=f[r]+" "+f[r+1];
					 if(queryType.matches("[a-zA-Z]+\\.?")) 
					 { queryType=queryType.toLowerCase();
						if(words.add(queryType))
					 	{  numRow++;
							match=true;
							 query.set("q",queryType);
							 System.out.println(queryType);
							 query.set("qt", "/search");
							    /*
							     * Effettuiamo la query sul server e recuperiamo tutti i documenti ottenuti in output.
								*/
							    QueryResponse response = null;
								try {
									response = server.query(query);
								} catch (SolrServerException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								sheet.addCell(new Number(0, numRow, response.getResults().size())); 
						    	sheet.addCell(new Number(1, numRow,((ArrayList) response.getResponse().get("clusters")).size()));
						    	sheet.addCell(new Number(2, numRow, (Integer)response.getResponse().get("numHit")));
						    	sheet.addCell(new Number(3, numRow, (Integer) response.getResponse().get("numDocHit")));
						    	sheet.addCell(new Label(4, numRow, queryType));
						    	sheet.addCell(new Number(5, numRow, (Integer) response.getResponseHeader().get("QTime")));
						    	sheet.addCell(new Number(6, numRow, 1000));
					 	} 
					 } 
					 contatore++;
					 if(contatore==5)
						 {
						  match=true;
						 }
				}		
			}
		}
	  }
	  workbook.write(); 
  	  workbook.close();
	
  	  System.err.println("fine");
		
	}

}
