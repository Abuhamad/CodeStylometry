



import java.io.*;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.IOUtils;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.io.FileUtils;




/**
 * Calculators.
 * These features are currently being extended.
 * 
 * @author Aylin Caliskan-Islam (ac993@drexel.edu)
 */


public class FeatureCalculators {
	

    public FeatureCalculators( ) {
    }
    
    public static void preprocessDataToAPISymbols(String filePath) throws IOException, InterruptedException{
    	//should take filename to test each time
   
    	 Runtime dbTime = Runtime.getRuntime();
    	 Runtime joernTime = Runtime.getRuntime();
    	 Runtime scriptTime = Runtime.getRuntime();

          Process stopDB = dbTime.exec(new String[]{"/bin/sh", "-c",
        		   "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/joern_related/neo4j-community-1.9.7/bin/neo4j stop"        		   
           });
           stopDB.waitFor();
           BufferedReader br = new BufferedReader(new InputStreamReader(stopDB.getInputStream()));
           while(br.ready())
               System.out.println(br.readLine());
           
           Process deleteIndex = dbTime.exec(new String[]{"/bin/sh", "-c","rm -r /Users/Aylin/git/joern/.joernIndex"});
           deleteIndex.waitFor();

           Process joernRun = joernTime.exec(new String[]{"/bin/sh", "-c", 
        		   "cd /Users/Aylin/git/joern"+"\n"+ "java -jar /Users/Aylin/git/joern/bin/joern.jar " + filePath });
           joernRun.waitFor();
           BufferedReader br1 = new BufferedReader(new InputStreamReader(joernRun.getInputStream()));
           while(br1.ready())
               System.out.println(br1.readLine());

           Process startDB = dbTime.exec(new String[]{"/bin/sh","-c",  
        		   "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/joern_related/neo4j-community-1.9.7/bin/neo4j start"        		   
           });
           startDB.waitFor();
           BufferedReader br2 = new BufferedReader(new InputStreamReader(startDB.getInputStream()));
           while(br2.ready())
               System.out.println(br2.readLine());
        
           
   		Process runScript = scriptTime.exec(new String[]{"/bin/sh", "-c", 
        		   "cd /Users/Aylin/git/joern-tools"+"\n"+ "python /Users/Aylin/git/joern-tools/template.py"
           });
           runScript.waitFor();
           
           
           BufferedReader br3 = new BufferedReader(new InputStreamReader(runScript.getInputStream()));
     //  	String output_filename = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/small_jam_data/byName/cgy4ever/cgy4ever_3_0.txt";
           String output_filename = filePath.substring(0, filePath.length()-3).concat("txt");
       	while(br3.ready())
           { //   System.out.println(br3.readLine());
           Util.writeFile(br3.readLine().toString() +"\n",output_filename, true);
   		   }
       	
       	
       	
        stopDB = dbTime.exec(new String[]{"/bin/sh", "-c",
      		   "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/joern_related/neo4j-community-1.9.7/bin/neo4j stop"        		   
         });
         stopDB.waitFor();
         BufferedReader br4 = new BufferedReader(new InputStreamReader(stopDB.getInputStream()));
         while(br4.ready())
             System.out.println(br4.readLine());
      
    	
    }
    
    public static void preprocessDataToASTFeatures(String filePath) throws IOException, InterruptedException, ScriptException{
    	//should take filename to test each time
   
    	 Runtime dbTime = Runtime.getRuntime();
    	 Runtime joernTime = Runtime.getRuntime();
    	 Runtime scriptTime = Runtime.getRuntime();

          Process stopDB = dbTime.exec(new String[]{"/bin/sh", "-c",
        		   "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/joern_related/neo4j-community-1.9.7/bin/neo4j stop"        		   
           });
           stopDB.waitFor();
           BufferedReader br = new BufferedReader(new InputStreamReader(stopDB.getInputStream()));
           while(br.ready())
               System.out.println(br.readLine());
           
           Process deleteIndex = dbTime.exec(new String[]{"/bin/sh", "-c","rm -r /Users/Aylin/git/joern/.joernIndex"});
           deleteIndex.waitFor();

           Process joernRun = joernTime.exec(new String[]{"/bin/sh", "-c", 
        		   "cd /Users/Aylin/git/joern"+"\n"+ "java -jar /Users/Aylin/git/joern/bin/joern.jar " + filePath });
           joernRun.waitFor();
           BufferedReader br1 = new BufferedReader(new InputStreamReader(joernRun.getInputStream()));
           while(br1.ready())
               System.out.println(br1.readLine());

           Process startDB = dbTime.exec(new String[]{"/bin/sh","-c",  
        		   "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/joern_related/neo4j-community-1.9.7/bin/neo4j start"        		   
           });
           startDB.waitFor();
           BufferedReader br2 = new BufferedReader(new InputStreamReader(startDB.getInputStream()));
           while(br2.ready())
               System.out.println(br2.readLine());

           String output_filename = filePath.substring(0, filePath.length()-3).concat("ast");
           String cmd1 = "echo \'queryNodeIndex(\"type:Function\").id\' | python /Users/Aylin/git/joern-tools/lookup.py -g |  python /Users/Aylin/git/joern-tools/getAst.py | python /Users/Aylin/git/joern-tools/ast2Features.py >" + output_filename;
           Process joernscripts = dbTime.exec((new String[]{"/bin/sh","-c", cmd1}));

           joernscripts.waitFor();
              BufferedReader br5 = new BufferedReader(new InputStreamReader(joernscripts.getInputStream()));
              while(br5.ready())
                  System.out.println(br5.readLine());
             
              BufferedReader br6 = new BufferedReader(new InputStreamReader(joernscripts.getErrorStream()));
              while(br6.ready())
                  System.out.println(br6.readLine());
        
        	    
        stopDB = dbTime.exec(new String[]{"/bin/sh", "-c",
     		   "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/joern_related/neo4j-community-1.9.7/bin/neo4j stop"        		   
        });
        stopDB.waitFor();
        BufferedReader br4 = new BufferedReader(new InputStreamReader(stopDB.getInputStream()));
        while(br4.ready())
            System.out.println(br4.readLine());
      
    	
    }
    
    
    
    
	public static void main(String[] args) throws Exception, IOException, InterruptedException {

    	
    String test_cpp_dir = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/small_jam_data/byName/";	
    String test = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/small_jam_data/byName/test/";	

       List test_file_paths = Util.listCPPFiles(test_cpp_dir); //use this for preprocessing
       
 //   List test_file_paths = Util.listTextFiles(test_cpp_dir); // use this to list txt files with API symbols
    for(int i=0; i< test_file_paths.size(); i++){
//		int testIDlength = test_file_paths.get(i).toString().length();    		
		String filePath = test_file_paths.get(i).toString();  
//    System.out.println(filePath);
//	preprocessDataToAPISymbols(filePath);
//	preprocessDataToASTFeatures(filePath);
    
    }


  
   //Get API symbols and their count in each txt file
//   String[] APIsymbols = uniqueAPISymbols(test);
    String[] APIsymbols = uniqueASTTypes(test);

    
	String featureText = Util.readFile("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/small_jam_data/byName/test/Gennady.Korotkevich_1_0.ast");

    for (int i=0; i<APIsymbols.length; i++)
    { System.out.println(APIsymbols[i]);}
//    int[] symCount = APISymbolCount(featureText, APIsymbols );
    int[] symCount = ASTTypeCount(featureText, APIsymbols );

    for (int i=0; i<APIsymbols.length; i++)
    { System.out.println(symCount[i]);}
  
    }
     

    public static String[] uniqueASTTypes (String dirPath) throws IOException{
  	  
 	   
	    List test_file_paths = Util.listASTFiles(dirPath);
		HashSet<String> uniqueWords = new HashSet<String>();

	    for(int i=0; i< test_file_paths.size(); i++){
			String filePath = test_file_paths.get(i).toString();  
	   
	   String inputText =Util.readFile(filePath);
	   Pattern pattern = Pattern.compile("type:(.*?)\n");
	   Matcher matcher = pattern.matcher(inputText);
	   while (matcher.find()) {
	       uniqueWords.add(matcher.group(1));
	   }}
	   String[] words = uniqueWords.toArray(new String[0]);

      return words;
}   
 
     public static String[] uniqueAPISymbols (String dirPath) throws IOException{
	  
	   
	    List test_file_paths = Util.listTextFiles(dirPath);
		HashSet<String> uniqueWords = new HashSet<String>();

	    for(int i=0; i< test_file_paths.size(); i++){
			String filePath = test_file_paths.get(i).toString();  
	   
	   String inputText =Util.readFile(filePath);
	   Pattern pattern = Pattern.compile("u'(.*?)'");
	   Matcher matcher = pattern.matcher(inputText);
	   while (matcher.find()) {
	       uniqueWords.add(matcher.group(1));
	   }}
	   String[] words = uniqueWords.toArray(new String[0]);

       return words;
}
  
     public static int [] APISymbolCount (String featureText, String[] APISymbols )
     {    
     int symbolCount = APISymbols.length;
     int [] counter = new int[symbolCount];
     for (int i =0; i<symbolCount; i++){
//if case insensitive, make lowercase
//   String str = APISymbols[i].toString().toLowerCase();
  	 String str = "u'"+APISymbols[i].toString()+"'";
//if case insensitive, make lowercase
//   strcounter = StringUtils.countMatches(featureText.toLowerCase(), str);
  	 counter[i] = StringUtils.countMatches(featureText, str);  	   

     }
     return counter;
     }  
     
     public static int [] ASTTypeCount (String featureText, String[] ASTTypes )
     {    
     int symbolCount = ASTTypes.length;
     int [] counter = new int[symbolCount];
     for (int i =0; i<symbolCount; i++){
//if case insensitive, make lowercase
//   String str = APISymbols[i].toString().toLowerCase();
  	 String str = "type:"+ASTTypes[i].toString()+"\n";
//if case insensitive, make lowercase
//   strcounter = StringUtils.countMatches(featureText.toLowerCase(), str);
  	 counter[i] = StringUtils.countMatches(featureText, str);  	   

     }
     return counter;
     }   
     
   public static float wordCountIndex(String inputText){
	   //returns the word count separated by spaces
	    if (inputText == null)
	       return 0;
	    return inputText.trim().split("\\s+").length;
	}
  
   public static int functionIDCount (String featureText)
	  {		   int counter = 0;

			   String str = "FunctionId";
			   counter = StringUtils.countMatches(featureText, str);
		   return counter;
		   
		   }   
   
   public static int CFGNodeCount (String ASTText)
	  {		   int counter = 0;

			   String str = "isCFGNode:";
			   counter = StringUtils.countMatches(ASTText, str);
		   return counter;
		   
		   }      
   
   public static int ASTFunctionIDCount (String ASTText)
	  {		   int counter = 0;

			   String str = "functionId:";
			   counter = StringUtils.countMatches(ASTText, str);
		   return counter;
		   
		   }   
   
   
   public static int DictionaryIndex (String inputText)
   {
	   
   String [] dictionaryWords = {"a choice",	"a lie",   "your option"};

   
   
   
   int counter = 0;
   int privacyPhraseCount = dictionaryWords.length;
   for (int i =0; i<privacyPhraseCount; i++){
	   int strcounter=0;
	   String str = dictionaryWords[i].toString().toLowerCase();
	   //if case insensitive, make lowercase
	   strcounter = StringUtils.countMatches(inputText.toLowerCase(), str);
	   counter=counter+strcounter;
   }   
   
   return counter;
   
   }

	  
   public static int countQuotesIndex (String inputText){
   int quote_score =0;
   for (Character c: inputText.toCharArray()) {
       if (c.equals('\"')) {

       	quote_score++;
       }
   }

   return quote_score;

}	  
	  
   

   
   
   
	  public static void wordsCount (String featureText) throws IOException 
	   {
		   
		   String[] splitted = featureText.split(" ");
		      HashMap hm = new HashMap();
		      int x;
		   
		   for (int i = 0; i < splitted.length; i++) {
	            if (!hm.containsKey(splitted[i])) {
	                hm.put(splitted[i], 1);
	            } else {
	                hm.put(splitted[i], (Integer) hm.get(splitted[i]) + 1);
	            }
	        }
		   
		   for (Object word : hm.keySet()){
	            System.out.println(word + " " + (Integer) hm.get(word));
	        }
		   
		   
			for(int i=0; i<50; i++)
			{
				
			}}
}