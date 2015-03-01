package ador.recipe.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;



public class Comparator {
	public static void uEqual(Object a, Object b){
		Assert.assertEquals(a, b);

	}
	public static void uEqualWithObjectChain(String objectChain, Object a, Object b){
		Assert.assertEquals(objectChain, a, b);

	}
	
	public static void uNull(Object a){
		Assert.assertNull(a);
	}

	public static void compareByObjList(Object actualResult, Object expectedResult, List<String> list){
		if(!list.isEmpty()){
			for(Iterator<String> iter = list.iterator(); iter.hasNext(); ){
				String keyRelationship = iter.next();
			
				CompareEngineByReflection(actualResult, expectedResult, keyRelationship);
				 
			}
		}
	}
	private static void CompareEngineByReflection(Object a, Object b, String relation) {
		try {
			String keys[] = relation.split("\\.");
			// System.out.println("keys.length=" + keys.length);
			Object currentCompareObject1 = null;
			Object currentCompareObject2 = null;
			System.out.println("comparation key = " + keys[keys.length-1]);
			String objectChain = "";
			for(int i = 1; i < keys.length; i++){
				
				String key = keys[i];
				if(key.startsWith("_")){
					key = key.substring(1);
				}
				if(key.endsWith("_")){
					key = key.substring(0, key.length()-1);
				}
				System.out.println("key[" + i + "] = " + key);
				String getterMethodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
				String booleanGetterMethodName = "is" + key.substring(0, 1).toUpperCase() + key.substring(1);
				
				if(currentCompareObject1 == null){
					Method m = null;
					try{
						m = a.getClass().getMethod(getterMethodName);
						System.out.println("method[" + i + "] = " + getterMethodName + "()");
					} catch(Exception e){
						//e.printStackTrace();
						m = a.getClass().getMethod(booleanGetterMethodName);
						System.out.println("method[" + i + "] = " + booleanGetterMethodName + "()");
					}
					currentCompareObject1 = m.invoke(a);
					currentCompareObject2 = m.invoke(b);
				} else {
					Method m1= null;
					try{
						m1 = currentCompareObject1.getClass().getMethod(getterMethodName);
						System.out.println("method[" + i + "] = " + getterMethodName + "()");
					} catch(Exception e){
						m1 = currentCompareObject1.getClass().getMethod(booleanGetterMethodName);
						System.out.println("method[" + i + "] = " + booleanGetterMethodName + "()");
					}
					currentCompareObject1 = m1.invoke(currentCompareObject1);
					Method m2= null;
					try{
						m2 = currentCompareObject2.getClass().getMethod(getterMethodName);
					} catch(Exception e){
						//e.printStackTrace();
						m2 = currentCompareObject2.getClass().getMethod(booleanGetterMethodName);
					}
					currentCompareObject2 = m2.invoke(currentCompareObject2);
				}
				objectChain += "." + key.substring(0, 1).toUpperCase() + key.substring(1);
			}
			System.out.println("objectChain = " + objectChain);
			System.out.println("currentCompareObject1.value = " + currentCompareObject1);
			System.out.println("currentCompareObject2.value = " + currentCompareObject2);
			if(currentCompareObject1 != null && currentCompareObject1 != null){
				System.out.println("compare result is --->  " + currentCompareObject1.equals(currentCompareObject2));
				uEqualWithObjectChain(objectChain, currentCompareObject2,currentCompareObject1);
			} else {
				if(currentCompareObject1 == null && currentCompareObject1 == null){
					System.out.println("compare result is --->  both value is " + null);
				}else{
					System.out.println("compare result is --->  " + false);
				}
			}
			System.out.println("---------------------------------------------");
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static <T> boolean compareByGetterWithExclusion(List<T> objectList, String[] excludeMethods) {
		T t1 = objectList.get(0);
		T t2 = objectList.get(1);
		//Only getters to compare 
		if(t1==null && t2==null){
			return true;
		}else if(t1!=null && t2!=null && t1==t2){
			return true;
		}else if((t1==null && t2!=null) || (t1!=null && t2 ==null)){
			return false;
		}

		Method[] methods = t1.getClass().getMethods();
		Set<Method> methodList = new HashSet<Method>();
		for(Method m:methods){
			if(m.getName().startsWith("get")){
				methodList.add(m);
			}
		}
		
		//Remove those method to exclude
		if(excludeMethods!=null){
			for(String excludeMethodName:excludeMethods){
				Method methodToRemove;
				try {
					methodToRemove = t1.getClass().getMethod(excludeMethodName);
					methodList.remove(methodToRemove);
				} catch (SecurityException e) {
					e.printStackTrace();
					return false;
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		//Compare
		for(Method methodToExcute:methodList){
			try {
				Object o1 = methodToExcute.invoke(t1);
				Object o2 = methodToExcute.invoke(t2);
				if(o1==null && o2==null){
					continue;
				}else if(o1!=null && o2!=null && o1.equals(o2)){
					continue;
				}else{
					System.out.println(methodToExcute.getName() + " result not same");
					return false;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return false;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return false;
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;	
	}
	
	public static boolean compareArray(Object[] a1 , Object[] a2){
		if(a1==null && a2==null){
			return true;
		}
		if(a1!=null && a2!=null & a1.length==a2.length){
			for(int i=0;i<a1.length;i++){
				if(!a1[i].equals(a2[i])){
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
