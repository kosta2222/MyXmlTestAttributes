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
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.w3c.dom.NamedNodeMap;

class RegExp{
public boolean test(String testString,String pattern){
Pattern p=Pattern.compile(pattern);
Matcher m=p.matcher(testString);
return m.matches();
}
}
    
    

 
 class DomExample {
     HashMap<String,String> hm=null;
 
   public DomExample(String pathname){
       File f=new File(pathname);
       Calc c=new Calc();
        hm=new HashMap<>();
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
//                    double d=c.calculate(c.opn(s));
//                    System.out.println("Результат вычесления:"+s+"="+d);
                        String str=ParseMathematicExpression(s);
                      System.out.println("exp:"+s);
                      double d=c.calculate(c.opn(str));
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
   public String ParseMathematicExpression(String expression) {
       RegExp re=new RegExp();
       StringBuilder sb=new StringBuilder();
       for(int temp=0;temp<expression.length();temp++){
           String tok=String.valueOf(expression.charAt(temp));
           if(re.test(tok,"[a-z]")){
            String var=hm.get(tok);
            sb.append(var);
           }else
               sb.append(tok);
           
       }
     return sb.toString();
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