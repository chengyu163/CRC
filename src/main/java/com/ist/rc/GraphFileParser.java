package com.ist.rc;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

import edu.uci.ics.jung.graph.Graph;

//nota: provavelmente nao preciso de V e E//
class GraphFileParser<G extends Graph>{
  final String V = "V"; //how format defines a vertex
  final String I = "I"; //how format defines something to ignore/extra
  VertexFactory vFactory;
  EdgeFactory eFactory;
  G graph;

  public GraphFileParser(VertexFactory _vFactory, EdgeFactory _eFactory, G _graph){
    vFactory = _vFactory;
    eFactory = _eFactory;
    graph = _graph;
  }
 
  public ParserInfo splitIntoVertex(String format, String phrase,boolean addIgnores){
    format = "|"+ format + "|";  //the algorithms knows when to stop by looking ahead and behind 
    phrase = "|"+ phrase + "|";  //this is to always have something to look ahead and behind
    format = format.toUpperCase();
    int phraseIndex = 0;  
    int formatIndex = 0;
    int vertexNumber = 0;
    StringBuilder[] vertex = new StringBuilder[2];
    ArrayList<StringBuilder> ignoreList = new ArrayList<StringBuilder>();
    vertex[0] = new StringBuilder();
    vertex[1] = new StringBuilder();
    char toMatch;
    while( phraseIndex < phrase.length()){ 
      //If supposed to look for a vertex in phrase (having a \ before is a escape character)
      if(format.charAt(formatIndex)==V.charAt(0) && format.charAt(formatIndex-1)!='\\'){
        formatIndex++;
        toMatch = format.charAt(formatIndex);
        while(phrase.charAt(phraseIndex) != toMatch){
          vertex[vertexNumber].append(phrase.charAt(phraseIndex++)); 
        }
        vertexNumber++; //proximo vertice
      }
      //If supposed to look for a Ignore in phrase (having a \ before is a escape character)
      if(format.charAt(formatIndex)==I.charAt(0) && format.charAt(formatIndex)!='\\'){
        formatIndex++;
        toMatch = format.charAt(formatIndex);
        StringBuilder newIgnore = new StringBuilder();
        if(addIgnores){
        	ignoreList.add(newIgnore);
        }
        while(phrase.charAt(phraseIndex) != toMatch){ 
          if(addIgnores){
          	newIgnore.append(phrase.charAt(phraseIndex));
          }
          phraseIndex++;
        }
      }
      phraseIndex++;
      formatIndex ++;
    }
    
    if(addIgnores){
    	return new ParserInfo(vertex,addIgnores,ignoreList);
    }
    else{
    	return new ParserInfo(vertex);
    }
  }

	public void createGraph(String format, String fileName){
  	createGraph(format, fileName, false);
  }

  //Needs emptygraph to be passed because G is not defined at compile time.
  public void createGraph(String format, String fileName, Boolean addIgnores){


    format = format.toUpperCase();
    String[] splitRegex = format.split(V);
    String newRegex = Pattern.quote(format);
    //problem: this is not accounting for escape characters
    newRegex = newRegex.replaceAll(V,"\\\\E[0-9a-zA-Z]*\\\\Q");
    newRegex = newRegex.replaceAll(I,"\\\\E.*\\\\Q");
    File fp = new File(fileName);
    try{
    	Scanner scan = new Scanner(fp);
	    String group = scan.findWithinHorizon(newRegex,0);
	    while(group != null){
	      ParserInfo s = splitIntoVertex(format,group,addIgnores); //TODO: name s?
	      Vertex[] ve = vFactory.addVertex(graph,s);
	      eFactory.addEdge(graph,s,ve);
	      group = scan.findWithinHorizon(newRegex,0);
	      System.out.println(s);
	    }
	    scan.close();

		} catch(FileNotFoundException e){
   		System.out.println(e);
   		System.exit(-1);
   	}
  }

  class ParserInfo{
      public StringBuilder[] vertex;
      public boolean hasIgnores;
      public ArrayList<StringBuilder> ignoreList;
      /* Not advisable, but, if needed, you can attach anything to ParserInfo
      to complement any other information you might want*/
      public Object strategy  = null; 
      public ParserInfo(StringBuilder[] _vertex){
        vertex = _vertex;
        hasIgnores = false;
        ignoreList = null;
      }
      public ParserInfo(StringBuilder[] _vertex, boolean _hasIgnores, ArrayList<StringBuilder> _ignores){
        vertex = _vertex;
        hasIgnores = _hasIgnores;
        ignoreList = _ignores;
      }
      //*change to have more than one vertex*/
      public String toString(){
        StringBuilder v = new StringBuilder();
        v.append("(v[0],v[1]) = (");
        v.append(vertex[0]);
        v.append(",");
        v.append(vertex[1]);
        v.append(")\n");
        v.append("IGNORELIST:\n");
        if(hasIgnores){
          for(StringBuilder i: ignoreList){
            v.append(i);
            v.append("\n");
          }
        }
        return v.toString();
      }
  }
}

