package phrases2docXML;

import java.nio.charset.StandardCharsets;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;



public class querySet {
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		FileWriter outFile = new FileWriter("C:\\Users\\Windows\\Desktop\\docCorpus\\file.txt");
		PrintWriter out = new PrintWriter(outFile);
		              
	  for(int j=0;j<7;j++)
	  {  
		  
		String content = readFile("C:\\Users\\Windows\\Desktop\\docCorpus\\TXT\\"+(j+1)+".txt", StandardCharsets.UTF_8);	
		String[] frase=content.split("\\.");
		
		for(int i=0;i<frase.length; i++)
		{
			 SolrServer server = new HttpSolrServer("http://localhost:8983/solr/thesaurus");
			String[] f= frase[i].split("\\s+");			
			Random rand= new Random();
			if(f.length>2)
				out.println(f[rand.nextInt(f.length-1)]);

		}
	  
	  }
		
	  out.close();
	  System.out.println("fine");
		
		
	}

}
