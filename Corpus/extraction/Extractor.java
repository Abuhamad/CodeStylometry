import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Extractor {

	public static void getDownloadURL(int round_numbers_2014, long problem_numbers_2014,
			String name) throws IOException {
		

	            // get URL content
	          String url_open= "http://code.google.com/codejam/contest/scoreboard/do?cmd=GetSourceCode&contest="
				+ round_numbers_2014
				+ "&problem="
				+ problem_numbers_2014
				+ "&io_set_id=0&username=" + name;
	    		
	          URL website = new URL(url_open);
	          ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	          String contestantFolder = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/2014data_unzip/"+name+"/";
	          File aFile = new File(contestantFolder);			
	          if(aFile.exists() == false)
	 	    		aFile.mkdir();
	          String fileName = contestantFolder+round_numbers_2014+"_"+problem_numbers_2014+"_"+name+".zip";
	          FileOutputStream fos = new FileOutputStream(fileName);
	          File bFile = new File(fileName);			
	          if(bFile.exists() == false)
	 	    		bFile.createNewFile();
	          fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);    
	          unzip(fileName,contestantFolder );
	          List all_file_paths = Util.listAllFiles(contestantFolder);	         
	      		for(int j=0; j < all_file_paths.size();j++ )
	    	{
	      			int fileNamelength = all_file_paths.get(j).toString().length();
	      			if(!all_file_paths.get(j).toString().substring(fileNamelength-3, fileNamelength).contains("cpp")
	      					&
	      					!all_file_paths.get(j).toString().substring(fileNamelength-2, fileNamelength).contains("cc"))
	      			{
	      				File allFiles = new File(all_file_paths.get(j).toString());		
	      		      	allFiles.delete();	
	      			}    
	      			
	      			if(all_file_paths.get(j).toString().substring(fileNamelength-3, fileNamelength).contains("cpp"))
	      			{
	      				File cppFiles = new File(all_file_paths.get(j).toString());		
      		      	cppFiles.renameTo(new File(fileName.substring(0, fileName.length()-3)+"cpp"));	
	      			}	
	      			if(all_file_paths.get(j).toString().substring(fileNamelength-2, fileNamelength).contains("cc"))
	      			{
	      				File cppFiles = new File(all_file_paths.get(j).toString());		
      		      	cppFiles.renameTo(new File(fileName.substring(0, fileName.length()-3)+"cpp"));	
	      			}	
	      				
	    	}

	}

	private static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
    }
	
    public static void main(String[] args) throws IOException{

    	
/*
//http://code.google.com/codejam/contest/scoreboard/do?cmd=GetSourceCode&contest=[CONTESTNUMBER]&problem=[PROBLEMNUMBER]&io_set_id=[LARGEORSMALLDATASET]&username=[USERNAME]

//The [LARGEORSMALLDATASET] field is either a 0 or 1 to download either the small or large dataset. Since we always use the small dataset, we can always leave this as 0. So for example, if I wanted the URL for the first problem (small dataset) in the 2014 Qualification round for Gennady.Korotkevich, my URL would be:

//http://code.google.com/codejam/contest/scoreboard/do?cmd=GetSourceCode&contest=2974486&problem=5756407898963968&io_set_id=0&username=Gennady.Korotkevich
*/

    	int[] round_numbers_2014 ={2974486, 2984486, 2994486, 3004486, 3014486, 3024486};
/*    	long[] problem_numbers_2014 = {5756407898963968L,5709773144064000L,5690574640250880L,5644738749267968L,
	5634947029139456L,5766201229705216L,5752104073297920L,//round2
	5751500831719424L,5658282861527040L,5731331665297408L,//round3
	5706278382862336L,5669245564223488L,5658068650033152L,//round4
	5737429512224768L,5721094409420800L,5158144455999488L,5649687893770240L,//round5
	5645447150436352L,5724427840913408L,5690270771314688L,5670781216358400L};//round6
*/    	
    	long[][] problem_numbers_2014 = new long[6][4];
    	problem_numbers_2014[0][0] = 5756407898963968L;
    	problem_numbers_2014[0][1] = 5709773144064000L;
    	problem_numbers_2014[0][2] = 5690574640250880L;
    	problem_numbers_2014[0][3] = 5644738749267968L;
    	problem_numbers_2014[1][0] = 5634947029139456L;
    	problem_numbers_2014[1][1] = 5766201229705216L;
    	problem_numbers_2014[1][2] = 5752104073297920L;
    	problem_numbers_2014[2][0] = 5751500831719424L;
    	problem_numbers_2014[2][1] = 5658282861527040L;
    	problem_numbers_2014[2][2] = 5731331665297408L;
    	problem_numbers_2014[3][0] = 5706278382862336L;
    	problem_numbers_2014[3][1] = 5669245564223488L;
    	problem_numbers_2014[3][2] = 5658068650033152L;
    	problem_numbers_2014[4][0] = 5737429512224768L;
    	problem_numbers_2014[4][1] = 5721094409420800L;
    	problem_numbers_2014[4][2] = 5158144455999488L;
    	problem_numbers_2014[4][3] = 5649687893770240L;
    	problem_numbers_2014[5][0] = 5645447150436352L;
    	problem_numbers_2014[5][1] = 5724427840913408L;
    	problem_numbers_2014[5][2] = 5690270771314688L;
    	problem_numbers_2014[5][3] = 5670781216358400L;

    			

    			
    			
    			
    			
    	BufferedReader in = new BufferedReader(new FileReader("users/2014_fromDB_cpp.txt"));
        String str;

        List<String> list = new ArrayList<String>();
        while((str = in.readLine()) != null){
            list.add(str);
        }

        String[] contestant_username_2014 = list.toArray(new String[0]);    	

        String url_open ="http://code.google.com/codejam/contest/scoreboard/do?cmd="
        		+ "GetSourceCode&contest=2974486&problem=5756407898963968&io_set_id=0&username=14gautam";
    //    java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
        

        
        for (int i=0; i< round_numbers_2014.length; i++){
        	for(int j=0; j<4; j++){
                for(int k=0; k< contestant_username_2014.length; k++){
                	getDownloadURL(round_numbers_2014[i],  problem_numbers_2014[i][j],
                			contestant_username_2014[k].toString());                	
                }
        	}        	
        }
        
        
        }

}