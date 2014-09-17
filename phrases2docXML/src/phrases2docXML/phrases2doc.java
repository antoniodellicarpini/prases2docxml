package phrases2docXML;

import java.io.File;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 


import org.w3c.dom.Document;
import org.w3c.dom.Element; 

public class phrases2doc {
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	
	public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {
		// TODO Auto-generated method stub		  
		String[] nomeid={"primo","secondo","terzo","quarto","quinto","sesto","settimo","ottavo","nono","decimo","undicesimo","dodicesimo","tredicesimo","quattordicesimo","quindicesimo","sedicesimo","diciassettesimo","diciottesimo","diciannovesimo"};

		
		
	//for(int j=0;j<19;j++){	
		
		//int nomefile=j+1;
		File f=new File("C:\\Users\\Windows\\Desktop\\corpus\\corpus artigianale\\corpusEconomia.xml");
		String content = readFile("C:\\Users\\Windows\\Desktop\\corpus\\wikicorpus\\wikipedia_economia.txt", StandardCharsets.UTF_8);	
		String[] frase=content.split("\\.");
		String nome=nomeid[7];	
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("add");
			doc.appendChild(rootElement);
			
		
			int count=0;
			
			for (int i=0;i<frase.length;i++){
				
				
				//if(frase[i].length()>150)
				//{// doc elements
					Element documento = doc.createElement("doc");
					rootElement.appendChild(documento);
				
				// id elements
						Element id = doc.createElement("field");
						id.setAttribute("name","id");
						id.appendChild(doc.createTextNode(nome+i));
						documento.appendChild(id);
				//name element
						Element name = doc.createElement("field");
						name.setAttribute("name", "name");
						name.appendChild(doc.createTextNode(frase[i]));
						documento.appendChild(name);
				 count++;
				//}
			}
			
			System.out.println(count);
			
			
			// write the content into xml file
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(f);
			 
					// Output to console for testing
					// StreamResult result = new StreamResult(System.out);
			 
					transformer.transform(source, result);
			 
					System.out.println("File saved!");
		
	    //}
	
	}

}
