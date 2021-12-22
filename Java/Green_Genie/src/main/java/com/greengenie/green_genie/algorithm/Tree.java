/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greengenie.green_genie.algorithm;

public class Tree {
	
	private Node root;
	
	public Node getRoot() {
        return this.root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public boolean isEmpty() {
        return (root == null);
    }
   
    public String toString() {
      return "";       
    }
    
    public void print() {
    	root.print("",true);
    }
    
   /* public  void printTree2(Node node, String appender) {
    	System.out.println(appender + node.getBranchName());
    	System.out.println(appender + appender+node.getName());
    	for (Node each : node.getChildren().values()) {
    	   printTree2(each, appender + appender);
    	  }    	
		 }
    */
    
    
 
   /* 
     private void addIdents(int levels,String append) {
    	for(int i=0;i<levels;i++)
    		System.out.print(append);
    }
     public  void printTree(Node node, int level) {
    	if (level > 0) {
    		
	    	addIdents(level," ");
	    	//System.out.println("is "+node.getBranchName()+"?");
    	}
    	
    	addIdents(level," ");
    	level++;
    	if (node.getChildren().size() == 0) { //end node
    		
    		addIdents(1,"-");
    		System.out.println(node.getName().toUpperCase() );
    		
    	}
    	else {
    		
    		
    		System.out.println(node.getName().toUpperCase() );
    		
    	}
    	
    	//addIdents(level);
		  
		  for (Node each : node.getChildren()) {			  
		   printTree(each, level);
		   
		  }
		 }
    */   
}

