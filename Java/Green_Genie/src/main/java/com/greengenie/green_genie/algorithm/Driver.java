
package com.greengenie.green_genie.algorithm;

import com.greengenie.green_genie.model.Input;
import com.greengenie.green_genie.model.MinMaxWaardes;

import java.io.FileNotFoundException;
import java.util.Objects;

public class Driver {


	static final String filepath = System.getProperty("user.dir")+"/src/main/resources/com/greengenie/dataset/PlantGroeiDataSet.csv";

	public static void main(String[] args) throws FileNotFoundException {
		
		ID3 id3 = new ID3();
		id3.rawdata = id3.loadCSV(filepath);
//		id3.printArrayList(id3.rawdata);
		
		id3.runID3(id3.rawdata,null);
		Node rootnode = id3.tree.getRoot();

//		System.out.println("\nDisplaying Tree\n");
		rootnode.print("",true);
		//WalkTrough(rootnode);
	}

	public static String getAdvice(Input inpt) throws FileNotFoundException {
		ID3 id3 = new ID3();
		id3.rawdata = id3.loadCSV(filepath);

		id3.runID3(id3.rawdata,null);
		Node rootnode = id3.tree.getRoot();

		return WalkTrough(rootnode, inpt);
	}


	private static String WalkTrough(Node rootnode, Input inpt){
		String result =null;
		if (rootnode.getChildren().size() == 0){
			result = rootnode.getData();
		}
		else
			switch (rootnode.getData()){
				case "grond vocht":
					for (Node node: rootnode.getChildren())
						if (Objects.equals(inpt.Grond_Vocht.toString(), node.getData())){
							return WalkTrough(node.getChildren().get(0), inpt);
						}
					break;
				case "grond temp":
					for (Node node: rootnode.getChildren())
						if (Objects.equals(inpt.Grond_Temp.toString(), node.getData())){
							return WalkTrough(node.getChildren().get(0), inpt);
						}
					break;
				case "lucht vocht":
					for (Node node: rootnode.getChildren())
						if (Objects.equals(inpt.Lucht_Vocht.toString(), node.getData())){
							return WalkTrough(node.getChildren().get(0), inpt);
						}
					break;
				case "lucht temp":
					for (Node node: rootnode.getChildren())
						if (Objects.equals(inpt.Lucht_Temp.toString(), node.getData())){
							return WalkTrough(node.getChildren().get(0), inpt);
						}
					break;
			}
		return result;
	}
}

