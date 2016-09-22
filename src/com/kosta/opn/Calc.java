package com.kosta.opn;
import java.util.StringTokenizer;
import java.util.Stack;
import java.util.Deque;
import java.util.ArrayDeque;


public class Calc{
private final String OPERATORS = "+-*/^%u";
private final String[] FUNCTIONS = {"sin","cos","tan","procOtChisla"};
private final String SEPARATOR = ",";
private final String PI="3.14";
//private final double PI_TOCNO=3.1415926535897932384626433832795;
private double angleInDegree;

/* temporary stack that holds operators, functions and brackets */
public Stack<String> sStack = new Stack<>();
public StringBuilder sbOut =null;
/*
x
*/
public double x;

    /**
     * Преобразовать строку в обратную польскую нотацию
     * @param sIn Входная строка
     * @return Выходная строка в обратной польской нотации
     */

        public  String opn(String sIn) throws Exception  {
        //System.out.println(sIn);
	       
         sbOut = new StringBuilder("");
	
	/* splitting input string into tokens */
        StringTokenizer stringTokenizer = new StringTokenizer(sIn,
                 OPERATORS +SEPARATOR+" ()", true);
               
        String  cTmp;

        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken(); 
            if(token.equals("pi")){
                token=Double.toString(Math.PI);
            }
            if(token.charAt(token.length()-1)=='g'){
                angleInDegree=(Math.PI*(Double.parseDouble(token.substring(0,token.length()-1))))/180.0;
                token=Double.toString(angleInDegree);
            }
            
            if (isOp(token)) {
               
		 while (!sStack.empty()) {
                    if (isOp(sStack.lastElement()) && (opPrior(token) <= opPrior(sStack.lastElement()))) {
                        sbOut.append(" ").append(sStack.pop()).append(" ");
                         } else {
                        sbOut.append(" ");
                        break;
                    }
                 }

                
              
                sStack.push(token);
                                
                
            }else if(token.equals("(")) {
                sStack.push(token);
                
            }
            else if (token.equals(")")) {
                cTmp = sStack.lastElement();
                while (!cTmp.equals("(")) {
                    if (sStack.size() < 1) {
                        throw new Exception("Ошибка разбора скобок. Проверьте правильность выражения.");
                    }
                    
                    
                    
                    sbOut.append(" ").append(cTmp).append(" ");
                    sStack.remove(cTmp); 
                   cTmp = sStack.lastElement(); 
                    
                   
                             
                    
                    
            }
                
             
                sStack.remove(cTmp);
                if(sStack.size()>0){
                if(isFunction(sStack.lastElement())){sbOut.append(" ").append(sStack.lastElement()).append(" ");
                sStack.remove(sStack.lastElement());}
                }
            }

		else if(isFunction(token)){
		sStack.push(token);
        	} else {
                 if(!token.equals(SEPARATOR)){ // Если символ не оператор - добавляем в выходную последовательность                            
                  
                sbOut.append(" ").append(token).append(" ");
                 }
            }
         
        
        }
        while(!sStack.empty()){
            sbOut.append(" ").append(sStack.pop());
        }
               
return  sbOut.toString();
}
   /**
     * Функция проверяет, является ли текущий символ оператором
    */   

       private static boolean isOp(String c) {
        switch (c) {
            case "-":
            case "+":
            case "*":
            case "/":
            case "^":
            case  "u":
            
                return true;
        }
        return false;
    }
    /**
     * Возвращает приоритет операции
     * @param op String
     * @return byte
     */   

        private static byte opPrior(String op) {
        switch (op) {
            case "u":
                return 4;
            case "^":
                return 3;
            case "*":
            case "/":
            case "%":
                return 2;
            
        }
        return 1; //// Тут остается + и - 
    }
	/**
     * Check if the token is function (e.g. "sin")
     *
     * @param token Input <code>String</code> token
     * @return <code>boolean</code> output
     * @since 1.0
     */

	private boolean isFunction(String token) {
        for (String item : FUNCTIONS) {
            if (item.equals(token)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Считает выражение, записанное в обратной польской нотации
     * @param sIn
     * @return double result
     */   

      
   public  double calculate(String sIn) throws Exception {
       //System.out.println("iz calculate() "+sIn);
        double dA = 0, dB = 0;
        String sTmp;
        Deque<Double> stack = new ArrayDeque<Double>();
        StringTokenizer st = new StringTokenizer(sIn);
        while(st.hasMoreTokens()) {
            try {
                
                sTmp = st.nextToken().trim();
                                         
                if ((1 == sTmp.length() && isOp(sTmp))||isFunction(sTmp)) {
                    if(isFunction(sTmp) && (sTmp.equals("sin")||sTmp.equals("cos")||sTmp.equals("tan"))){
                    dA=stack.pop();
                    switch(sTmp){
                    case "sin":
                        dA=Math.sin(dA);
                        break;
                    case "cos":
                        dA=Math.cos(dA);
                        break;
                        case "tan":
                        dA=Math.tan(dA);
                        break;
                        default:
                            throw new Exception("Недопустимая операция " + sTmp);
                    }
                    stack.push(dA);
                } 
                    if(isFunction(sTmp) && sTmp.equals("procOtChisla")){
                    dA=stack.pop();
                    dB=stack.pop();
                    switch(sTmp){
                    case "procOtChisla":
                        dA=(dA*dB)/100.0;
                        break;
                    
                        default:
                            throw new Exception("Недопустимая операция " + sTmp);
                    }
                    stack.push(dA);
                }                
                    if ((1 == sTmp.length() && isOp(sTmp))&& !sTmp.equals("u")){
                    dB = stack.pop();
                    dA = stack.pop();
                    switch (sTmp) {
                        case "+":
                            dA += dB;
                            break;
                        case "-":
                            dA -= dB;
                            break;
                        case "/":
                            dA /= dB;
                            break;
                        case "*":
                            dA *= dB;
                            break;
                        case "%":
                            dA %= dB;
                            break;
                        case "^":
                            dA = Math.pow(dA, dB);
                            break;
                        default:
                            throw new Exception("Недопустимая операция " + sTmp);
                    }
                    stack.push(dA);
                    
                }
                    if ((1 == sTmp.length() && isOp(sTmp))&& sTmp.equals("u")){
                      dA=stack.pop();
                      switch(sTmp){
                      case "u":
                      dA=-dA;
                          break;
                      default:
                            throw new Exception("Недопустимая операция " + sTmp);
                    }
                      stack.push(dA);
                    }
                    
                }else {
                    try {
                        if(sTmp.equals("x")){
                        dA =x;
                                          
                        stack.push(dA);
                        
                        }else{
                       
                            dA = Double.parseDouble(sTmp);
                             stack.push(dA);
                        }
                        }

                    
                    catch (Exception ex){
                        ex.printStackTrace();
                        

                        
                    }
                }
            
            } catch (Exception e) {
                throw new Exception("Недопустимый символ в выражении");
            }
            
        }
        

        if (stack.size() > 1) {
            throw new Exception("Количество операторов не соответствует количеству операндов");
        }

        return stack.pop();
    }
}

