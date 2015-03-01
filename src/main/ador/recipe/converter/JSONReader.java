package ador.recipe.converter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import ador.recipe.util.RecipeFinderConstant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JSONReader {
	

	public <T> List<T> convertRecipeListJsonToObjectList(String fileName, Type listType){
		//Gson gson = new Gson();
		Gson gson = new GsonBuilder().setDateFormat(RecipeFinderConstant.AU_DATE_FORMAT).create(); 
		String strJSON = getStrFromFile(fileName);
		List<T> returnObj=null;
		try {
			returnObj = gson.fromJson(strJSON, listType);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return returnObj;
	}
	
	private static String getStrFromFile(String strFileName){
		BufferedReader br =null;
		StringBuffer sbf=new StringBuffer();
		//String fileName=null;
		
		//fileName = strFileName;

		//URL readStep1JSON = JSONReader.class.getClassLoader().getResource(fileName);
		
		try {
			FileReader fr=new FileReader(strFileName);
			br = new BufferedReader(fr);
			String line="";
			try {
				while ((line = br.readLine()) != null) {
					sbf.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				br=null;
			}
			
//			System.out.println("Json Step"+strFileName+" data :  "  +sbf.toString());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sbf.toString();
	}

}
