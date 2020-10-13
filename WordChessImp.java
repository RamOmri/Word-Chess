import java.io.FileReader;
import java.util.Arrays;
import java.util.*;
import java.io.*;



public class WordChessImp implements WordChess{

    private char[] alphabet;
    private boolean pathFound = false;
    private LinkedList toExpand;
    private LinkedList foundWords;

        int i = 0;
    public node expandnode(node tree, String[] dictionary, String endWord){
        String tempWord;
        char[] charWord = new char[endWord.length()];


        for(int l = 0; l < endWord.length(); l++){ 
            tempWord = tree.word;
            charWord = tempWord.toCharArray();
        for(int i = 0; i < 26; i++){ 
            charWord[l] = alphabet[i];
            tempWord = new String(charWord); 
          
            if(Arrays.asList(dictionary).contains(tempWord.toUpperCase()) && !foundWords.contains(tempWord)){
                foundWords.add(tempWord);
               node child = new node(tempWord);
               child.parent = tree;
                toExpand.add(child); 
                System.out.println(tempWord);
                if(foundWords.contains(endWord.toLowerCase())){
                pathFound = true;
                return child;
                }
            }
            
            if(pathFound == true) {
                break;
            }
        }
        if(pathFound == true) break;
    }

       return tree;
    }

    public String[] returnWordPath(String endWord, node children, String startWord){
        String[] path = new String[endWord.length()*26];
        int j = 0;
        
           while(children.parent != null){
               path[j] = children.word;
               children = children.parent;
               j++;
               System.out.println("Path: " + path[j-1]);
           }
           j++;
           path[j] = startWord;
           System.out.println(startWord);
        return path;
    }

    public String[] findPath(String[] dictionary, String startWord, String endWord){ 
        node tree = new node(startWord);
        foundWords.add(startWord);
        tree.parent = null;

        tree = expandnode(tree, dictionary, endWord);
        toExpand.add(tree);

        while(!toExpand.isEmpty()){
           
            tree = (node)toExpand.getFirst();
            toExpand.remove();
            tree = expandnode(tree, dictionary, endWord);
          if(foundWords.contains(endWord)){
             return returnWordPath(endWord, (node)toExpand.removeLast(), startWord);
          }
            
        }
        
        return null;
    }

    private int countLines(String wordList){
        int length = 0;
    try{
        File f = new File(wordList);
        FileReader fileReader = new FileReader(f);
        BufferedReader breakLine = new BufferedReader(fileReader);
        while((breakLine.readLine()) != null){
            length++;
            }
        breakLine.close();
      }   
      catch(IOException e){
        e.printStackTrace();
    }

    return length;

}

    private String[] createDictionary(String wordList, int wordsNumber){
        String[] dictionary = new String[wordsNumber];
        try{
        File f = new File(wordList);
        FileReader fileReader = new FileReader(f);
        BufferedReader br = new BufferedReader(fileReader);
        String line;
        int i = 0;
        
        while((line = br.readLine()) != null){
            dictionary[i] = line;
            i++;
          }
          br.close();
     }
     catch(IOException e){
            e.printStackTrace();
      }
      
      return dictionary;
    }

    public void start(String startWord, String endWord, String f){
        findPath(createDictionary(f, countLines(f)), startWord, endWord);
    }

    public WordChessImp(){
      alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
      toExpand = new LinkedList<node>();
      foundWords = new LinkedList<String>();
    }


   public static void main(String[] args){
        WordChessImp implement = new WordChessImp();
        if(args.length != 2){
            System.out.println("Please enter two words");
            return;
        }
        if(args[0].length() != args[1].length()){
            System.out.println("Please make sure that the two words have the same length");
            return;
        }
        implement.start(args[0], args[1], "./words.txt");

    }
    
   
            class node{
                String word;
                node parent;

                public node(String word){
                    this.word = word;
                    parent = null;
                }
            }

          
}