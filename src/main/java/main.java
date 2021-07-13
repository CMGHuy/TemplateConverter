import javax.swing.plaf.metal.MetalIconFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        //String path = "C:/Users/min.tran/Pictures/reportResult0202/reportResultCopy";

        String path = args[0];

        //list the number of file

        List<String> results = new ArrayList<String>();

        File[] folderPaths = new File(path).listFiles();

        for (File file : folderPaths) {
            results.add(file.getPath());
        }

        for (int filePathCount = 0; filePathCount < results.size(); filePathCount++){
            //for linux
            //String subreportTextPath = results.get(filePathCount).concat("/reports/subreport_text.txt");
            
            // individual path, example C:\Users\min.tran\Pictures\reportResult\reportResult\report-10.57.51.2\reports\C2C Test Suite Main_11112020_090559189\logs
            //File[] individualtReportPathRoot = new File(results.get(filePathCount).concat("\\reports")).listFiles();
            File[] individualtReportPathRoot = new File(results.get(filePathCount).concat("/reports")).listFiles();
            List<String> individualTestName = new ArrayList<String>();
            List<String> elementsForHtml = new ArrayList<String>();

            String individualReportPath = null; 
            for (File file : individualtReportPathRoot) {
                if(file.getName().contains("C2C") && file.isDirectory()){
                    individualReportPath = file.getPath();
                    //System.out.println(individualReportPath);
                }
            }

            //File[] individualReports = new File(individualReportPath.concat("\\logs")).listFiles();
            File[] individualReports = new File(individualReportPath.concat("/logs")).listFiles();

            String previousFilenameBegin = "";
            //String mainResultName = "";
            String mainResultPath = "";
            for (File file : individualReports) {
                String currentFileNameBegin = file.getName().substring(0,8);

                if(previousFilenameBegin.contains(currentFileNameBegin) && file.getName().startsWith("1-") && !file.getName().contains("Shortcut")){
                    individualTestName.remove(individualTestName.size()-1);
                    individualTestName.add(file.getName());
                }

                else if(file.getName().startsWith("1-") && file.isFile() && !file.getName().contains("Shortcut") ){
                    individualTestName.add(file.getName());
                    previousFilenameBegin = file.getName().substring(0,8);

                }

                if(file.getName().contains("Result")){
                    //mainResultName = file.getName();
                    mainResultPath = file.getPath();
                }
            }

            if(mainResultPath.isEmpty())continue;

            //String subreportTextPath = results.get(filePathCount).concat("\\reports\\subreport_text.txt");
            String subreportTextPath = results.get(filePathCount).concat("/reports/subreport_text.txt");

            ArrayList<String[]> textStringArray = new ArrayList<String[]>();
            try {
                textStringArray = readText(subreportTextPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ArrayList<String[]> htmlInputString = new ArrayList<String[]>();
            for(String[] line : textStringArray){

                for(String testName : individualTestName){
                    if(testName.contains(line[1])){
                        String[] htmlLineString = new String[]{line[0],line[1], line[2],line[3], testName};
                        //System.out.println(line[0] + line[1] + line[2] + line[3] + testName);
                        htmlInputString.add(htmlLineString);
                    }
                }
            }

            htmlGenerator htmlGenerator= new htmlGenerator();
            try {
                //System.out.println("mainResultPath " + mainResultPath);
                htmlGenerator.genertateHtml(htmlInputString,mainResultPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private static ArrayList readText(String textFilePath) throws FileNotFoundException {
        File file = new File(textFilePath);
        ArrayList<String[]> textStringArray = new ArrayList<String[]>();
        Scanner sc = new Scanner(file);
        String line = null;
        while (sc.hasNextLine()) {

            line = sc.nextLine();
            if(line.isEmpty() || line.contains("Testcase_id"))continue;
            String[] currentLine = line.split("\\t");
            textStringArray.add(currentLine);
        }
        return textStringArray;

    }
}
