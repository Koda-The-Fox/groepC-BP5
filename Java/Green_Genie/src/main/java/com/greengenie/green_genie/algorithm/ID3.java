package com.greengenie.green_genie.algorithm;

import com.greengenie.green_genie.model.Input;

import java.io.*;
import java.util.*;

public class ID3 {

    public static final boolean DEV_MODE = false; //allow verbose printing to the screen
    public List<ArrayList<String>> rawdata = new ArrayList<>();
    public Tree tree = new Tree();

    /**
     * <p>
     * Accepts a file path pointing to a csv file and returns an ArrayList of
     * ArrayList of strings
     *
     * @param filepath the location of the csv file
     * @return ArrayList<ArrayList<String>>
     * @see ArrayList
     */
    public List<ArrayList<String>> loadCSV(String filepath) throws FileNotFoundException {
        List<ArrayList<String>> data = new ArrayList<>();
        Scanner scan = new Scanner(new File(filepath));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            ArrayList<String> lineArrayList = new ArrayList<>(Arrays.asList(line.split(",")));

            data.add(lineArrayList);
        }
        return data;
    }

    /**
     * Write the rawdata back to the CSV file.
     * @param filepath the location of the csv file
     * @throws IOException
     */
    public void writeCSV(String filepath) throws IOException {
        FileWriter writer = new FileWriter(filepath);
        StringBuilder fileData = new StringBuilder();
        try {
            for (ArrayList<String> alS : rawdata) {
                String row = "";
                for (String str : alS) {
                    row += str;
                    if (!str.equals(alS.get(alS.size() - 1))) {
                        row += ",";
                    }
                }
                fileData.append(row + "\n");
            }
            writer.write(fileData.toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
        }

    }

    public void addRecord(Input inpt){
        ArrayList<String> simulatedLine = new ArrayList<>();
        simulatedLine.add(inpt.Grond_Vocht.toString());
        simulatedLine.add(inpt.Grond_Temp.toString());
        simulatedLine.add(inpt.Lucht_Vocht.toString());
        simulatedLine.add(inpt.Lucht_Temp.toString());
        simulatedLine.add(inpt.Acceptabel);

        int removeInt = getRecordPosition(simulatedLine);
        if (removeInt == rawdata.size()+1) {
            System.out.println("Adding new record ("+removeInt+").");
            rawdata.add(simulatedLine);
        }
        else {
            System.out.println("Record already exists ("+removeInt+").");
        }
    }

    public void removeRecord(Input inpt){
        ArrayList<String> simulatedLine = new ArrayList<>();
        simulatedLine.add(inpt.Grond_Vocht.toString());
        simulatedLine.add(inpt.Grond_Temp.toString());
        simulatedLine.add(inpt.Lucht_Vocht.toString());
        simulatedLine.add(inpt.Lucht_Temp.toString());
        simulatedLine.add(inpt.Acceptabel);
        int removeInt = getRecordPosition(simulatedLine);
        if (removeInt != rawdata.size()+1) {
            System.out.println("Removing record ("+removeInt+").");
            rawdata.remove(removeInt);
        }
    }
    public int getRecordPosition(ArrayList<String> record){
        int recordInt = rawdata.size() + 1;
        for (int i = 0; i < rawdata.size(); i++) {
            ArrayList<String> lineArrayList = rawdata.get(i);
            int same = 0;
            /* 0 = not set
             * 1 = same
             * 2 = not same */
            for (int j = 0; j<lineArrayList.size();j++){
                if (lineArrayList.get(j).equals(record.get(j))){
                    if (same != 2)
                        same = 1;
                }else{
                    same = 2;
                }
            }
            if (same == 1){
                System.out.println("Found record (" + recordInt + ").");
                recordInt = i;
            }
        }
        return recordInt;
    }

    /**
     * <p>
     * Takes a list of ArrayLists of strings and prints an ascii table
     *
     * @param data ArrayList<ArrayList<String>>
     * @see ArrayList
     */
    static public void printArrayList(List<ArrayList<String>> data) {

        //record that largest string length in each column for formatting
        int colnum = 0;
        List<Integer> maxcolwidths = new ArrayList<>();
        for (ArrayList<String> row : data) {
            for (String item : row) {
                if (maxcolwidths.size() <= colnum) {
                    maxcolwidths.add(item.length());
                } else if (item.length() > maxcolwidths.get(colnum)) {
                    maxcolwidths.set(colnum, item.length());
                }
                colnum++;
            }
            colnum = 0;
        }

        for (ArrayList<String> row : data) {
            for (String item : row) {
                String format = "| %-" + maxcolwidths.get(colnum) + "s";
                System.out.printf(format, item);
                colnum++;
            }
            colnum = 0;
            System.out.println();
        }
    }

    /**
     * <p>
     * Returns the column values in a table of ArrayList<ArrayList<String>
	 * @param colnum the number of the column you wish to extract
     * @return List<String>
     * @see ArrayList
     */
    private List<String> getColumnValues(List<ArrayList<String>> datain, int colnum) {
        List<ArrayList<String>> data = new ArrayList<>(datain);
        //remove column headers
        data.remove(0);
        List<String> values = new ArrayList<>();
        for (ArrayList<String> row : data) {
            values.add(row.get(colnum));
        }
        return values;
    }

    /**
     * <p>
     * Returns the unique column values in a table of
     * ArrayList<ArrayList<String> given the column value
     *
     * @param data ArrayList<ArrayList<String>>
	 * @param colnum the number of the column you wish to extract
     * @return List<String>
     * @see ArrayList
     */
    public List<String> getUniqueAttributes(List<ArrayList<String>> data, int colnum) {

        List<String> values;
        values = getColumnValues(data, colnum);

        Set<String> uniqueitems = new HashSet<>(values);
        values = new ArrayList<>(uniqueitems);
        return values;
    }

    /**
     * <p>
     * Returns the entropy value given the number of items and total number of
     * items
     *
     * @param items double the number of items
     * @param totalitems double the number of total items
     * @return Double
     */
    public Double calculateEntropy(double items, double totalitems) {
        if (items == 0 || totalitems == 0) {
            return 0.0;
        }
        double probability = items / totalitems;
        return (probability * (Math.log(probability) / Math.log(2))) * -1.0;

    }

    /**
     * <p>
     * Calculate the entropy in a given column of ArrayList<ArrayList<String>>
	 * @param data ArrayList<ArrayList<String>>
	 * @param colnum int the column selected
     * @return Double
     */
    public Double entropyInColumn(List<ArrayList<String>> data, int colnum) {
        List<String> uniquevalues = getUniqueAttributes(data, colnum);
        List<String> allvalues = getColumnValues(data, colnum);

        Double entropy = 0.0;

        for (String item : uniquevalues) {
            int occurrences = Collections.frequency(allvalues, item);
            entropy += calculateEntropy(occurrences, allvalues.size());
        }
        return entropy;
    }

    //
    /**
     * <p>
     * Calculate the occurrences of a given value in a given column of
     * ArrayList<ArrayList<String>>
	 * @param allvaluesin all values in a column
     * @param alltargetvaluesin list of all the outcome values
     * @param item we are looking
     * @param targetitem the outcome attribute we wish to compare
     * @return int
     */
    public int getValuesTotalWithTarget(List<String> allvaluesin,
            List<String> alltargetvaluesin,
            String item, String targetitem) {

        List<String> allvalues = new ArrayList<>(allvaluesin);
        List<String> alltargetvalues = new ArrayList<>(alltargetvaluesin);

        //remove any items that is not the item we are checking
        //done in reverse order so that index values does not change	
        for (int i = allvalues.size() - 1; i >= 0; i--) {
            //item is different so remove 			
            if (item.compareTo(allvalues.get(i)) != 0 || targetitem.compareTo(alltargetvalues.get(i)) != 0) {
                allvalues.remove(i);
                alltargetvalues.remove(i);
            }

        }
        //now left with only the attribute that we wish to look at so return its size
        return allvalues.size();
    }

    /**
     * <p>
     * Return the item with the most frequency in a column
     *
     * @param datain the ArrayList<ArrayList<String> containing all of the data
     * @param columnno the number of the column you wish to obtain the highest
     * frequency item
     * @return String
     */
    public String getLargestFrequencyInColumn(List<ArrayList<String>> datain, int columnno) {

        //List<String> allvaluesincolumn = getColumnValues(datain, datain.get(0).size() - 1);
        getColumnValues(datain, datain.get(0).size() - 1);
        List<String> uniquevalues = getUniqueAttributes(datain, columnno);

        int largestfrequency = 0;
        String largestitem = "";
        for (String item : uniquevalues) {
            int occurrences = Collections.frequency(datain, item);
            if (occurrences > largestfrequency) {
                largestfrequency = occurrences;
                largestitem = item;
            }
        }
        return largestitem;
    }

    /**
     * <p>
     * Calculate the gain value in a column given the source entropy
     *
     * @param data in the ArrayList<ArrayList<String> containing all of the data
     * @param colnum the number of the column you wish to obtain the gain for
     * @param sourceEntropy the source entropy
     * @return String
     */
    public Double gainInColumn(List<ArrayList<String>> data, int colnum, double sourceEntropy) {
        List<String> uniquevalues = getUniqueAttributes(data, colnum);
        List<String> allvalues = getColumnValues(data, colnum);

        //get unique targets (normally 2 - yes/no, but can be more)
        List<String> uniquetargetvalues = getUniqueAttributes(data, data.get(0).size() - 1);
        List<String> alltargetvalues = getColumnValues(data, data.get(0).size() - 1);

        Double entropy = 0.0;
        double entropytotal = 0.0;
        int totalvalueswithtarget;
        for (String item : uniquevalues) {
            int occurrences = Collections.frequency(allvalues, item);
            //for each target/classification get the total

            for (String targetitem : uniquetargetvalues) {
                totalvalueswithtarget = getValuesTotalWithTarget(allvalues, alltargetvalues, item, targetitem);
                entropy -= calculateEntropy(totalvalueswithtarget, occurrences);
            }

            entropy = entropy * ((double) occurrences / (double) allvalues.size());
            entropytotal -= entropy;
            entropy = 0.0;

        }
        return sourceEntropy - entropytotal;
    }

    /**
     * <p>
     * check if all outcome attributes are equal<p>
     * Output attributes are always the last column
     *
     * @param newdata the ArrayList<ArrayList<String> containing all of the data
     * @return Boolean
     */
    public Boolean areOutputValuesEqual(List<ArrayList<String>> newdata) {

        List<String> uniquevalues = getUniqueAttributes(newdata, newdata.get(0).size() - 1);
        return uniquevalues.size() == 1;
    }

    /**
     * <p>
     * gets the value of the output value<p>
     * Output attributes are always the last column
     * <p>
     * Should only be run on column where all values are known to be equal
     *
     * @param data the ArrayList<ArrayList<String> containing all of the data
     * @return Boolean
     */
    public String getOutputValue(List<ArrayList<String>> data) {
        List<ArrayList<String>> newdata = new ArrayList<>(data);
        List<String> uniquevalues = getUniqueAttributes(newdata, newdata.get(0).size() - 1);
        return uniquevalues.get(0);
    }

    /**
     * <p>
     * Run ID3 against given data<p>
     * @param data the ArrayList<ArrayList<String> containing all of the data we wish to process
     * @param node current node for storing our decision tree
     * @return Node
     */
    public Node runID3(List<ArrayList<String>> data, Node node) {
        if (DEV_MODE) {
            System.out.println("\n****Running ID3*****");
            printArrayList(data);
        }

        Node root = null;
        if (data.isEmpty()) {
            root = new Node("No Data");
        } else if (areOutputValuesEqual(data)) {
            if (DEV_MODE) {
                System.out.println("Outputs are all equal - creating leaf");
            }
            String outputvalue = getOutputValue(data);
            Node leafnode = new Node(outputvalue, NodeType.LEAFNODE);
            node.addChild(leafnode);
        } else if (data.get(0).size() == 1) {
            //no attributes left, so choose the most frequent value
            if (DEV_MODE) {
                System.out.println("Cannot classify element 100%.  Using largest frequency");
            }
            String largestitem = getLargestFrequencyInColumn(data, 0);
            Node leafnode = new Node(largestitem, NodeType.LEAFNODE);
            node.addChild(leafnode);
        } else {

            if (DEV_MODE) {
                System.out.println("Starting column selection");
            }

            Double entropy = entropyInColumn(data, data.get(0).size() - 1);
            if (DEV_MODE) {
                System.out.println("output value for col: " + data.get(0).get(data.get(0).size() - 1));
                System.out.println("Entropy: " + entropy);
            }

            //calculate column with largest gain			
            Double largestGain = 0.0;
            int largestGainCol = -1;
            //obtain column with largest gain
            for (int i = 0; i < data.get(0).size() - 1; i++) {
                Double gain = gainInColumn(data, i, entropy);
                if (DEV_MODE) {
                    System.out.println("Gain for " + data.get(0).get(i) + ": " + gain);
                }
                if (gain > largestGain) {
                    largestGain = gain;
                    largestGainCol = i;
                }
            }

            if (largestGain == 0) {
                //Inconsistent data
                if (DEV_MODE) {
                    System.out.println("Inconsistent data");
                    printArrayList(data);
                }

                String largestitem = getLargestFrequencyInColumn(data, 0);
                Node leafnode = new Node(largestitem, NodeType.LEAFNODE);
                node.addChild(leafnode);

            } else {

                if (DEV_MODE) {
                    System.out.println("Largest Gain : " + largestGain);
                    System.out.println(data.get(0).get(largestGainCol));
                }

                Node newnode = new Node(data.get(0).get(largestGainCol), NodeType.LEAFNODE);
                if (tree.isEmpty()) {
                    if (DEV_MODE) {
                        System.out.println("Empty Tree");
                    }
                    tree.setRoot(newnode);
                    newnode.setType(NodeType.ROOTNODE);
                } else {
                    if (DEV_MODE) {
                        System.out.println("Adding Node");
                    }
                    node.addChild(newnode);
                }

                //split on this attribute and add node		
                for (String item : getUniqueAttributes(data, largestGainCol)) {
                    if (DEV_MODE) {
                        System.out.println("add branch for: " + item);
                    }
                    Node branchnode = new Node(item, NodeType.BRANCH);
//                    Node branchnode = new Node("Is " + item, NodeType.BRANCH); // Original, the text "is" runes the efficiency, it's useful for visualizing but nor for our purpose
                    newnode.addChild(branchnode);

                    //create table for each item attribute					
                    List<ArrayList<String>> subdata;

                    subdata = copyList(data);
                    subdata = splitTableOnItem(subdata, largestGainCol, item);
                    //printArrayList(subdata);					
                    subdata = removeColumn(subdata, largestGainCol);
                    runID3(subdata, branchnode);

                }
            }

        }
        return root;
    }

    /**
     * <p>
     * Remove a column from our ArrayList<ArrayList<String>
	 * @param data the ArrayList<ArrayList<String> containing all the data
     * @param coltoremove the number of the column to remove
     * @return ArrayList<ArrayList<String>
     */
    public List<ArrayList<String>> removeColumn(List<ArrayList<String>> data, int coltoremove) {
        for (ArrayList<String> row : data) {
            row.remove(coltoremove);
        }
        return data;
    }

    /**
     * <p>
     * Remove the rows from our data that does not much the item string we wish
     * to split on
     *
     * @param subdata the ArrayList<ArrayList<String> containing all of the data
     * @param coltofilter the number of the column to filter on
     * @param itemtosplit the string value of the item to split on
     * @return ArrayList<ArrayList<String>
     */
    public List<ArrayList<String>> splitTableOnItem(List<ArrayList<String>> subdata, int coltofilter, String itemtosplit) {

        for (int i = subdata.size() - 1; i > 0; i--) {
            //item is different so remove 			
            //if not attribute value to keep then remove row
            if (subdata.get(i).get(coltofilter).compareTo(itemtosplit) != 0) {
                subdata.remove(i);
            }
        }

        return subdata;
    }

    /**
     * <p>
     * Create a copy of an ArrayList<ArrayList<String>
	 * @param data the ArrayList<ArrayList<String> containing all the data
     * @return ArrayList<ArrayList<String>
     */
    public static List<ArrayList<String>> copyList(List<ArrayList<String>> data) {
        List<ArrayList<String>> subdata = new ArrayList<>();
        for (ArrayList<String> row : data) {
            ArrayList<String> newrow = new ArrayList<>(row);
            subdata.add(newrow);
        }
        return subdata;
    }

}
