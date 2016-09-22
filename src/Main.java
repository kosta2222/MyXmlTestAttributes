import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.kosta.opn.Calc;
import java.io.File;
import java.util.HashMap;
import org.w3c.dom.NamedNodeMap;
 
 class DomExample {
 
   public DomExample(String pathname){
       File f=new File(pathname);
       Calc c=new Calc();
       HashMap<String,String> hm=new HashMap<>();
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse(f);
 
            // Получаем корневой элемент
            Node root = document.getDocumentElement();
            
       
            System.out.println();
            // Просматриваем все подэлементы корневого 
            NodeList nodes = root.getChildNodes();
            for (int temp = 0; temp < nodes.getLength(); temp++) {
                Node node = nodes.item(temp);

                if(node.getNodeName().equals("exp")){
                    String s=node.getTextContent();
                    double d=c.calculate(c.opn(s));
                    System.out.println("Результат вычесления:"+s+"="+d);
                }
                else if(node.getNodeName().equals("def")){
                    NamedNodeMap namedNodeMap= node.getAttributes();
                    Node nodeFirst=namedNodeMap.getNamedItem("name");
//                    System.out.println("name="+nodeFirst.getNodeValue());
                    hm.put(nodeFirst.getNodeValue(),node.getTextContent());
//                    System.out.println("def value:"+node.getTextContent());
                    
                    
                }

              
            }
 
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        catch(Exception ex){
            ex.printStackTrace(System.out);
        }
       System.out.println("HashMap:"+hm);      
}
 }
    

class Main{
    
public static void main(String args[]){
 if(args.length==0){
     System.out.println("usage :Main <file>.xml");   
 } 
 else{
     String pathname=args[0];
DomExample de=new DomExample(pathname);
 }
}
}