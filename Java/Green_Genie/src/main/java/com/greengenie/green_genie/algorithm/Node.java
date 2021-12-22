package com.greengenie.green_genie.algorithm;
import java.util.ArrayList;
import java.util.List;

public class Node {
		
	 private List<Node> children = new ArrayList<Node>();
	 private Node parent;	
	 private String data;
	 private NodeType type;
	 
	 public Node() {		
		
	 }
	 
	 public Node(String data) {		
		 setData(data);
	 }
	 
	 public Node(String data, NodeType type) {
		 setData(data);
		 this.type = type;	
	 }
	 
	 public Node(Node parent) {		
		 this.parent = parent;
	 }
	 
	 public String getData() {
		 return data;
	 }
	 
	 public void setData(String data) {
		 this.data = data;
	 }
	 
	 public NodeType getType() {
		 return type;
	 }
	 
	 public void setType(NodeType type) {
		 this.type = type;
	 } 

		 
	 public List<Node> getChildren() {
	  return children;
	 }
	 
	 public Node getParent() {
	  return parent;
	 }
	 
	 public void addChild(Node node) {		  
		 node.parent = this;		 
		 this.children.add(node);		  
	 }	 
	
	 
	 public void removeChildren() {
	        children = new ArrayList<Node>();
	    }
	 
	 //based on post from stackoverflow by VasyaNovikov
	 public void print(String prefix, boolean isTail) {	
		 System.out.println(prefix + (isTail ? "└── " : "├── ") + data);
	        for (int i = 0; i < children.size() - 1; i++) {
	            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
	        }
	        if (children.size() > 0) {
	            children.get(children.size() - 1)
	                    .print(prefix + (isTail ?"    " : "│   "), true);
	        }
	    }	 		 		
}

