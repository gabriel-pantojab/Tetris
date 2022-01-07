package Colect;


/**
 * Write a description of class Colector here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.nio.file.*;
import java.io.*;
import java.util.*;

public class Colector {
    static File file;
    static File[] files;
    static String rutaO = "./", rutaD = "./build";
    static ArrayList<File> delete;
    
    public static void main(String args[]) {
        init(null, null);
        colectar();
    }
    
    public static void init(String rutaOrigen, String rutaDestino) {
        if (rutaO == null && rutaD == null) {
            rutaO = rutaOrigen;
            rutaD = rutaDestino;
        }
        delete = new ArrayList<File>();
    }
    
    public static void colectar() {
        try{
            file = new File(rutaO);
            files = file.listFiles();
            moveFiles(files);
            deleteFiles();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch (Exception ioe){
            ioe.printStackTrace();
        }
    }
    
    private static void moveFiles(File[] f) throws IOException{
        for(File fi : f) {
            if (fi.isDirectory() && !fi.getName().equals("Colect") && !fi.getName().equals(".git") && !fi.getName().equals("build") && !fi.getName().equals("storage")) {
                File newFile = new File("./build/"+fi.getName());
                newFile.mkdir();
                moveFiles(fi.listFiles());
            }else {
                if(fi.getName().contains(".class") || fi.getName().contains(".txt")) {
                    File aux = new File(fi.getParent());
                    Path fileMove = Files.move(Paths.get(fi.getAbsolutePath()), Paths.get(rutaD + "/"+aux.getName()+"/"+fi.getName()), StandardCopyOption.REPLACE_EXISTING);
                }else {
                    if(fi.getName().contains(".ctxt")) {
                        delete.add(fi);
                    }
                }
            }
        }
    }
    
    private static void deleteFiles() throws IOException {
        for(File f : delete) {
            Files.delete(Paths.get(f.getAbsolutePath()));
        }
    }
}
