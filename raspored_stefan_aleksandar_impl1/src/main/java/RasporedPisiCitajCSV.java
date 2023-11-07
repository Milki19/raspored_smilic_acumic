import com.opencsv.*;
import java.io.*;
import java.util.*;

public class RasporedPisiCitajCSV extends Raspored {

    List<String[]> allData = new ArrayList<>();
    Map<Integer, String> headers = new TreeMap<>();

    @Override
    public boolean ucitajPodatke(String path, String configPath) throws IOException {
        readHeadersFromConfig(configPath);
        /// readDataLineByLine(s);
        readAllDataAtOnce(path);
        /// readDataFromCustomSeparator(s);
        return false;
    }

    @Override
    public boolean exportujPodatke(String path) throws IOException {
        writeDataLineByLine(path);
        /// writeDataAtOnce(path);
        /// writeDataForCustomSeparatorCSV(path);
        return false;
    }

    private void readHeadersFromConfig(String configPath) throws FileNotFoundException {
        File file = new File(configPath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splitLine = line.split(" ", 3);

            if(Integer.valueOf(splitLine[0]) == -1) continue;
            headers.put(Integer.valueOf(splitLine[0]), splitLine[1]);
        }

        scanner.close();

    }

    private void readDataLineByLine(String file) throws IOException
    {

        // Create an object of filereader class
        // with CSV file as a parameter.
        FileReader filereader = new FileReader(file);

        // create csvReader object passing
        // filereader as parameter
        ///CSVReader csvReader = new CSVReader(filereader);
        CSVReader csvReader = new CSVReaderBuilder(filereader)
                .withSkipLines(1)
                .build();
        String[] nextRecord;

        // we are going to read data line by line
        while ((nextRecord = csvReader.readNext()) != null) {
            for (String cell : nextRecord) {
                ///System.out.print(cell + ";");
            }
            allData.add(nextRecord);
        }

    }

    private void readAllDataAtOnce(String file) throws IOException
    {
        // Create an object of filereader class
        // with CSV file as a parameter.
        FileReader filereader = new FileReader(file);

        // create csvReader object
        // and skip first Line
        CSVReader csvReader = new CSVReaderBuilder(filereader)
                .withSkipLines(1)
                .build();

        allData = csvReader.readAll();

        // print Data
        for (String[] row : allData) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }


    }

    private void readDataFromCustomSeparator(String file) throws IOException
    {

        // Create object of filereader
        // class with csv file as parameter.
        FileReader filereader = new FileReader(file);

        // create csvParser object with
        // custom separator semi-colon
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

        // create csvReader object with
        // parameter filereader and parser
        CSVReader csvReader = new CSVReaderBuilder(filereader)
                .withCSVParser(parser)
                .build();

        // Read all data at once
        allData = csvReader.readAll();

        // print Data
        for (String[] row : allData) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }


    }


    private void writeDataLineByLine(String filePath) throws IOException
    {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);

        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);

        // create CSVWriter object filewriter object as parameter
        /// CSVWriter writer = new CSVWriter(outputfile);
        CSVWriter writer = new CSVWriter(outputfile, ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        // adding header to csv
        List<String> values = new ArrayList<>();

        for(Integer key : headers.keySet()){
            values.add(headers.get(key));
        }

        String[] header = values.toArray(new String[0]);

        writer.writeNext(header);

        // add data to csv
        for(String[] s : allData)
            writer.writeNext(s);

        // closing writer connection
        writer.close();

    }

    private void writeDataAtOnce(String filePath) throws IOException
    {

        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);


        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);

        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile, ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        writer.writeAll(allData);

        // closing writer connection
        writer.close();

    }

    private void writeDataForCustomSeparatorCSV(String filePath) throws IOException
    {

        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);


        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);

        // create CSVWriter with '|' as separator
        CSVWriter writer = new CSVWriter(outputfile, '|',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        // create a List which contains String array
        writer.writeAll(allData);

        // closing writer connection
        writer.close();
    }


}
