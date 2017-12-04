/**
 * Created by hgoscenski on 1/16/17.
 */

import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class DataParse {

    public static void main(String[] args) {

        Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("PerspecData");

        // Determining year and OS of computer to set default save and load locations.
        // Uses Localdate to pull the date
        // Uses System.getProperty to get the OS
        LocalDate now = LocalDate.now();
        String nowString = "/" + now.toString().substring(0,4);

        String saveDirectory = System.getProperty("user.home");
        String loadDirectory = System.getProperty("user.home");

        System.out.println(System.getProperty("os.name"));

        if(System.getProperty("os.name").toLowerCase().contains("windows")){
            saveDirectory += "\\Desktop\\websiteData.xls";
            loadDirectory += "\\Desktop\\jsondumps";
        } else {
            saveDirectory += "/Desktop/websiteData.xls";
            loadDirectory += "/Desktop/jsondumps";
        }
        // Done Loading Defaults

        // Asking user to verify defaults
        Scanner stdin = new Scanner(System.in);

        System.out.println("Please enter the full location of the JSON files.");
        System.out.println("It ought to look like this: C:\\Users\\username\\Desktop\\jsonfolder\\");
        System.out.println("The default is " + loadDirectory);
        System.out.println("If that is correct type 'y' otherwise type the location and press enter");
        String jsonFolder = stdin.nextLine().trim();

        ArrayList<File> directoryListing;

        if(jsonFolder.equalsIgnoreCase("y")){
            directoryListing = folder(loadDirectory);
        } else{
            directoryListing = folder(jsonFolder);
        }

        Gson gson = new Gson();

        ArrayList<String> dateAccess = new ArrayList();
        ArrayList<String> ipAddress = new ArrayList();
        ArrayList<String> pageVisited = new ArrayList();
        ArrayList<String> country = new ArrayList();

        Row row0 = sheet1.createRow(0);
        row0.createCell(0).setCellValue("Date");
        row0.createCell(1).setCellValue("IP Address");
        row0.createCell(2).setCellValue("Page Title");

        if (directoryListing != null) {

            for (File child : directoryListing) {

                try {

                    try {

//                        try {
//                            System.out.println(child.getCanonicalPath());
//                        }catch (IOException e){
////                             DO NOTHING!
//                        }

                        JsonObject intake = gson.fromJson(new InputStreamReader(new FileInputStream(child), "UTF-8"), JsonObject.class);

                        JsonArray jsonArray = intake.getAsJsonArray("hits");

//                        This is the original code
//                        When I break it all I should uncomment it
                        ArrayList jsonObjList = gson.fromJson(jsonArray, ArrayList.class);



                        int jsonLength = jsonObjList.size();

                        for (int i = 0; i < jsonLength; i++) {
//                            JsonObject visit = jsonArray.get(i).getAsJsonObject();
                            JsonData visit = gson.fromJson(jsonArray.get(i), JsonData.class);

//                            String visitTime = visit.get("accessOnShort").toString();
                            String visitTime = visit.getAccessOnShort();
                            visitTime = visitTime.substring(0, 5);
//                            System.out.println(visitTime+nowString);
                            dateAccess.add(visitTime + nowString);

//                            String visitIP = visit.get("ipAddress").toString();
                            String visitIP = visit.getIpAddress();
                            ipAddress.add(visitIP.replace("\"",""));

//                            String visitPage = visit.get("targetName").toString();
                            String visitPage = visit.getTargetName();
                            pageVisited.add(visitPage.replace("\"", ""));

//                            String countryName = visit.get("country").toString();
                            String countryName = visit.getCountry();
                            country.add(countryName);
                        }

                    } catch (java.io.UnsupportedEncodingException e){
                        System.out.println("Incorrect encoding.");
                        System.exit(1);
                    }

                } catch (java.io.FileNotFoundException e){
                    System.out.println("There were not any parsable JSON files there!");
                }
            }

        } else {
            System.out.println("That is not a directory that contains JSON files or is not a directory at all.");
            System.out.println("Program exiting. Please retry.");
            System.exit(1);
        }

        int arrayLength = ipAddress.size()-1;
        int rowCount = 1;

//        for(int i = 0; i < ipAddress.size(); i++){
//            System.out.println(ipAddress.get(i) + " || " + country.get(i));
//        }

        for(int i = 0; i <= arrayLength; i++){
            if(!ipAddress.get(i).toString().replace("\"", "").equals("173.10.36.254")){
                if(country.get(i).toString().replace("\"", "").equals("Canada") ||
                        country.get(i).toString().replace("\"", "").equals("United States") ||
                                country.get(i).toString().replace("\"", "").equals("US") ||
                                        country.get(i).toString().replace("\"", "").equals("CA")) {
                    sheet1.createRow(rowCount).createCell(0).setCellValue(dateAccess.get(i).toString());
                    sheet1.getRow(rowCount).createCell(1).setCellValue(ipAddress.get(i).toString());
                    sheet1.getRow(rowCount).createCell(2).setCellValue(pageVisited.get(i).toString());

//                    Debugging
//                    sheet1.getRow(rowCount).createCell(3).setCellValue(country.get(i).toString());
//                    sheet1.getRow(rowCount).createCell(4).setCellValue(i);
//                    End debugging
                    rowCount++;
//                    System.out.println(country.get(i).toString());
                }
            }
        }

        try{
            FileOutputStream fileout = new FileOutputStream(new File(saveDirectory));
            try {
                wb.write(fileout);
                fileout.close();
                System.out.println("XLS file created!");
            } catch (java.io.IOException e){
                System.out.println("Error.");
                System.exit(1);
            }

        } catch (java.io.FileNotFoundException e){
            System.out.println("That file does not or cannot exist.");
            System.out.println("Program exiting. Please retry.");
            System.exit(1);
        }

    }

    private static ArrayList<File> folder(String location){
        File dir = new File(location);
        File[] tempDir = dir.listFiles();
        ArrayList<File> directoryListing = new ArrayList<>();
        for(File file : tempDir){
            try {
            if(file.getCanonicalPath().contains(".json")) {
                directoryListing.add(file);
            }
            } catch (IOException e){
//                    do nothing
            }
        }
        return directoryListing;
    }
}
