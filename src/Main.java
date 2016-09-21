import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//import com.kosta.opn.Calc;
import org.w3c.dom.NamedNodeMap;
 
 class DomExample {
 
   public DomExample(){
//       Calc c=new Calc();
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse("data.xml");
 
            // Получаем корневой элемент
            Node root = document.getDocumentElement();
            
       
            System.out.println();
            // Просматриваем все подэлементы корневого 
            NodeList nodes = root.getChildNodes();
            for (int temp = 0; temp < nodes.getLength(); temp++) {
                Node node = nodes.item(temp);
//                System.out.println(node.getNodeName());
                if(node.getNodeName().equals("exp")){
                    String s=node.getTextContent();
//                    double d=c.calculate(c.opn(s));
//                    System.out.println("Rezultat vichisleniya:"+s+"="+d);
                }
                else if(node.getNodeName().equals("def")){
                    NamedNodeMap namedNodeMap= node.getAttributes();
                    Node nodeFirst=namedNodeMap.getNamedItem("name");
                    System.out.println("name="+nodeFirst.getNodeValue());
                    System.out.println("def value:"+node.getTextContent());
                    
                    
                }
//                System.out.println(node.getTextContent());
                // Если нода не текст, то это книга - заходим внутрь
                if (node.getNodeType() != Node.TEXT_NODE) {
                    NodeList nodeProps = node.getChildNodes();
                    for(int j = 0; j < nodeProps.getLength(); j++) {
                        Node nodeProp = nodeProps.item(j);
                        // Если нода не текст, то это один из параметров книги - печатаем
                        if (nodeProp.getNodeType() != Node.TEXT_NODE) {
//                            System.out.println(nodeProp.getNodeName() + ":" + nodeProp.getChildNodes().item(0).getTextContent());
                        }
                    }
                    System.out.println("===========>>>>");
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
}
 }
    

class Main{
public static void main(String args[]){
DomExample de=new DomExample();
}
}