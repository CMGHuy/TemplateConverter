import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class htmlGenerator {
    public void genertateHtml(ArrayList<String[]> htmlInputString, String mainResultFilePath) throws IOException {
        String firstLine= "<html><body bgcolor='#DCDCDC'><script src='http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js'></script><script>$(document).ready(function(){var $rows = $('#table tr:not(:first-child)');$('#search').keyup(function() {var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();        $rows.hide().filter(function() {        var text = $(this).text().toLowerCase();        return text.indexOf(val) != -1  && $(this).children().last().text()!='NOT_EXECUTED';    }).show();});});</script><center><h4><font color = '#483D8B'>Search: <input type='text' name='fname' id='search' placeholder='Fail/Pass/ScrumpyID'></font></h4></center><table id='table' border= '1' width='30%' align= 'center' bordercolor='black'><tr><caption><h3><font color = '#483D8B'>C2CINT110820-Result</font></h3></caption></tr><tr bgcolor='#6495ED'><th  align = 'center'><font size='3.5' face='Calibri' color = 'white'>#SI No</font></th><th  align = 'center'><font size='3.5' face='Calibri' color = 'white'>TCID/Reference ID</font></th><th  align = 'center'><font size='3.5' face='Calibri' color = 'white'>External ID</font></th><th  align = 'center'><font size='3.5' face='Calibri' color = 'white'>Description</font></th><th  align = 'center'><font size='3.5' face='Calibri' color = 'white'>  Result</font></th></tr></body></html>";

        //File output = new File("C:\\Users\\min.tran\\Documents\\output.html");
        File output = new File(mainResultFilePath);
        FileWriter writer = new FileWriter(output);

        writer.write(firstLine);

        //count pass fall sum
        int pass = 0;
        int fail = 0;
        for(String[] line : htmlInputString){
            if(line[3].contains("PASS"))pass=++pass;
            else if(line[3].contains("FAIL"))fail=++fail;
        }


        //String htmlLine = "<tr bgcolor='#FFFFFF' style='display: table-row;'><th><font size='2.5' face='Calibri' color = 'black' align = 'center'>1</font></th><th><font size='2.5' face='Calibri' color = 'black' align = 'center'>1-7NO2JD</font></th><th><font size='2.5' face='Calibri' color = 'black' align = 'center'></font></th><th><font size='2.5' face='Calibri' color = 'black' align = 'left'>[1][RT]Sales_UC0410_01_Update contact manually</font></th><th><font size='2.5' face='Calibri' color = 'black' align='center' ><a href=1-7NO2JD_11092020_172523544.html id= \"1\">FAIL</font></th></tr>";
        for(String[] line : htmlInputString){
            writer.write(String.format("<tr bgcolor='#FFFFFF' style='display: table-row;'><th><font size='2.5' face='Calibri' color = 'black' align = 'center'>%s</font></th><th><font size='2.5' face='Calibri' color = 'black' align = 'center'>%s</font></th><th><font size='2.5' face='Calibri' color = 'black' align = 'center'></font></th><th><font size='2.5' face='Calibri' color = 'black' align = 'left'>%s</font></th><th><font size='2.5' face='Calibri' color = 'black' align='center' ><a href=%s id= \"%s\">%s</font></th></tr>\n",line[0],line[1],line[2],line[4],line[0],line[3]));
        }

        //write summary

        writer.write(String.format("<html><body><table border= '1' width='70' align= 'center' bordercolor='black'><tr><caption><h3><font color = '#483D8B'>SUMMARY</font></h3></caption></tr><tr bgcolor='#6495ED' ><th  align = 'center'><font size='3.5' face='Calibri' color = 'white'>Build No</font></th><th  align = 'center'><font size='3.5' face='Calibri' color = 'white'>Total TestCases</font></th><th  align = 'center'><font size='3.5' face='Calibri' color = 'white'>Pass</font></th><th  align = 'center'><font size='3.5' face='Calibri' color = 'white'>Fail</font></th><th align = 'center'><font size='3.5' face='Calibri' color = 'white'>Not Executed</font></th></tr></body></html><br><br><br><br><br><br><br><br><br><tr bgcolor='#FFFFFF' id='summary'><th><font size='2.5' face='Calibri' color = 'black' align = 'center'>1</font></th><th><font size='2.5' face='Calibri' color = 'black' align = 'center'>%d</font></th><th><font size='2.5' face='Calibri' color = 'black' align = 'center'>%d</font></th><th><font size='2.5' face='Calibri' color = 'black' align = 'center'>%d</font></th><th><font size='2.5' face='Calibri' color = 'black' align='center' ><a href=Not_Executed11092020191226.html id='notexec'>0</font></th></tr>\n",pass+fail,pass,fail));

        writer.flush();
        writer.close();
    }
}
