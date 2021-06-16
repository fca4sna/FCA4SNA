package fca4sna.GetlatticeAndCalStab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class BasicCL {
	public HashSet<ArrayList<String>> objResult = new HashSet<>();
	public HashSet<ArrayList<String>> attrResult = new HashSet<>();
	public HashSet<String> bpcAllCL = new HashSet<String>();
	public HashSet<ArrayList<String>> objResult(ArrayList<String> attr, ArrayList<String> obj, BPCliques bpcAttr, HashMap<String, ArrayList<String>> dictAll){
		ArrayList<String> specialObj = new ArrayList<String>();
		if(objResult == null || objResult.size() == 0){
			for(String objTmp : obj){
				specialObj.add(objTmp);
			}
			objResult.add(specialObj);
		}
		for(int i = 0; i<attr.size(); i++){
			ArrayList<ArrayList<String>> objTemp = new ArrayList();
			objTemp.addAll(objResult);	
			ArrayList<String> oneObj = new ArrayList<String>();
			oneObj = bpcAttr.get(i).getR();
			for(int j = 0; j<objTemp.size(); j++){ 
				ArrayList<String> temp = new ArrayList<>();
				temp.addAll(objTemp.get(j));
				temp.retainAll(oneObj);
				objResult.add(temp);
			}
		}
		ArrayList<String> nullContent = new ArrayList();
		objResult.remove(nullContent);
		return objResult;
	}
	public ArrayList<ArrayList<String>> SubContent(HashSet<String> bpcAllHashSet){
		ArrayList<ArrayList<String>> subContent =  new ArrayList<ArrayList<String>>();
		for(String s : bpcAllHashSet){
			ArrayList<String> objOrAttr = new ArrayList<String>();
			String[] temp = s.split("#");	
			String[] ss = temp[0].split(" ");
			for(int i = 0; i<ss.length; i++){
				objOrAttr.add(ss[i]);
			}		
				subContent.add(objOrAttr);
			}
		return subContent;
	}
	public ArrayList<ArrayList<String>> SubIntent(HashSet<String> bpcAllHashSet){
		ArrayList<ArrayList<String>> subIntent =  new ArrayList<ArrayList<String>>();
		for(String s : bpcAllHashSet){
			ArrayList<String> objOrAttr = new ArrayList<String>();
			String[] temp = s.split("#");
			if(temp.length != 1) {
				String[] ss = temp[1].split(" ");
				for(int i = 0; i<ss.length; i++){
					if( !ss[i].isEmpty() )
						objOrAttr.add(ss[i]);
				}	
			} 
			else {
				objOrAttr.add("");
			}
				subIntent.add(objOrAttr);
			}
		return subIntent;
	}
	public BPCliques FinalBpcAll(HashSet<ArrayList<String>> objResult, HashMap<String, ArrayList<String>> dictAll){
		BPCliques finalBpCliques = new BPCliques();
		for(ArrayList<String> obj : objResult){
			Pair pair = intersectForObject(obj,dictAll);
			finalBpCliques.add(pair);
		}
		return finalBpCliques;
	}
	private Pair intersectForObject(ArrayList<String> obj,HashMap<String, ArrayList<String>> dictAll){
		Pair<ArrayList<String>, ArrayList<String>> tmpPair = null;
		ArrayList<String> listTem = null;   
		for (int i = 0; i < obj.size(); i++) {
			String obt = obj.get(i);
			if(listTem==null){
				listTem = new ArrayList<String>();   
				ArrayList<String> attr = dictAll.get(obt);
				if(attr != null){
					for (int j = 0; j < attr.size(); j++) {
						listTem.add(attr.get(j));
					}
				}
			}else{
				listTem.retainAll(dictAll.get(obt));
			}
		}
		if(null != listTem){
			attrResult.add(listTem);
			tmpPair = new Pair<ArrayList<String>, ArrayList<String>>(obj, listTem);
		}
		return tmpPair;
	}
	public BPCliques getBPCliqueObj(int[][] adjMat,ArrayList<String> obj,ArrayList<String> attr,Integer numObj,Integer numAttr) {
		BPCliques tmpBpc = new BPCliques();
		Pair<ArrayList<String>, ArrayList<String>> tmpPair;
		ArrayList<String> tmpObj;
		ArrayList<String> tmpList;
		for (int i = 0; i < numObj; i++) {
			tmpList = new ArrayList<String>();
			tmpObj = new ArrayList<String>();
			tmpObj.add(obj.get(i));
			for (int j = 0; j < numAttr; j++) {
				if (adjMat[i][j] == 1) {
					tmpList.add(attr.get(j));
				}
			}
			tmpPair = new Pair<ArrayList<String>, ArrayList<String>>(tmpObj, tmpList);	
			tmpBpc.add(tmpPair);
		}
		return tmpBpc;
	}
	public BPCliques getBPCliqueAttr(int[][] adjMat,ArrayList<String> obj,ArrayList<String> attr,Integer numObj,Integer numAttr) {
		BPCliques tmpBpc = new BPCliques();
		Pair<ArrayList<String>, ArrayList<String>> tmpPair;
		ArrayList<String> tmpObj;
		ArrayList<String> tmpAttr;
		ArrayList<String> tmpList;
		for (int i = 0; i < numAttr; i++) {
			tmpList = new ArrayList<String>();
			tmpAttr = new ArrayList<String>();
			tmpAttr.add(attr.get(i));
			for (int j = 0; j < numObj; j++) {
				if (adjMat[j][i] == 1) {
					tmpList.add(obj.get(j));
				}
			}
			tmpPair = new Pair<ArrayList<String>, ArrayList<String>>(tmpAttr , tmpList);
			tmpBpc.add(tmpPair);
		}
		return tmpBpc;
	}
	public HashMap<String, ArrayList<String>> buildDict(int[][] adjMat,ArrayList<String> obj,ArrayList<String> attr,Integer numObj,Integer numAttr) {
		HashMap<String, ArrayList<String>> dictTem = new HashMap<>();
		ArrayList<String> tmpList;
		for (int i = 0; i < numObj; i++) {
			tmpList = new ArrayList<String>();
			for (int j = 0; j < numAttr; j++) {
				if (adjMat[i][j] == 1) {
					tmpList.add(attr.get(j));
				}
			}
			dictTem.put(obj.get(i), tmpList);
		}
		for (int i = 0; i < numAttr; i++) {
			tmpList = new ArrayList<String>();
			for (int j = 0; j < numObj; j++) {
				if (adjMat[j][i] == 1) {
					tmpList.add(obj.get(j));
				}
			}
			dictTem.put(attr.get(i), tmpList);
		}
		return dictTem;
	}
	public String AtoS(ArrayList<String> input) {
		String listString = "";
		for (String s : input) {
			listString += s + " ";
		}
		return listString;
	}
	public String AtoS(List<String> input) {
		String listString = "";
		for (String s : input) {
			listString += s + " ";
		}
		return listString;
	}
	public HashSet<ArrayList<String>> ContentIS(ArrayList<ArrayList<String>> subContent, ArrayList<ArrayList<String>> newSubContent){
		HashSet<ArrayList<String>> contentIS = new HashSet<ArrayList<String>>();
		for(ArrayList<String> temp : subContent){
			for(ArrayList<String> newTemp : newSubContent){
				ArrayList<String> temp1 = new ArrayList<String>();
				ArrayList<String> newTemp1 = new ArrayList<String>();
				temp1.addAll(temp);
				newTemp1.addAll(newTemp);
				temp1.retainAll(newTemp1);
				if(temp1 != null && temp.size()!=0 && !temp.equals("") && !temp.equals(" ")){
					contentIS.add(temp1);
				}
			}
		}
		ArrayList<String> nullContent = new ArrayList();
		contentIS.remove(nullContent);
		return contentIS;
	}
	public static List<String> shangYS(List<String> pow ,HashMap<String, ArrayList<String>> totalDictAll){
		List<String> AttList = null;  
		if(pow == null) {
			AttList.addAll(AttList);
		}
		for (int i = 0; i < pow.size(); i++) {
			String obt = pow.get(i);    
			obt = obt.substring(0, obt.length()-1);  
			if(AttList==null){			
				AttList = new ArrayList<String>();   
				ArrayList<String> attr = totalDictAll.get(obt);  
				if(attr != null){
					for (int j = 0; j < attr.size(); j++) {
						AttList.add(attr.get(j));
					}
				} 
			}else{
				if(totalDictAll.get(obt)!=null)
					AttList.retainAll(totalDictAll.get(obt));
			}
		}
		return AttList;
	}
	public static ArrayList<String> shangYS2(List<String> pow ,HashMap<String, ArrayList<String>> totalDictAll){
		ArrayList<String> AttList = null;  
		ArrayList<String> pow2 = new ArrayList<String>();
		for (String str : pow) {
			pow2.add("a"+str+" ");
		}
		if(pow == null) {
			AttList.addAll(AttList);
		}
		for (int i = 0; i < pow.size(); i++) {
			String obt = pow.get(i);    
			if(AttList==null){			
				AttList = new ArrayList<String>();   
				ArrayList<String> attr = totalDictAll.get(obt);  
				if(attr != null){
					for (int j = 0; j < attr.size(); j++) {
						AttList.add(attr.get(j));
					}
				} 
			}else{
				AttList.retainAll(totalDictAll.get(obt));
			}
		}
		AttList.removeAll(pow2);
		ArrayList<String> xiuAttList = new ArrayList<String>();
		for( int i = 0; i < AttList.size(); i++) {
			xiuAttList.add(AttList.get(i).substring(1, AttList.get(i).length()-1));
		}
		return xiuAttList;
	}
	public static int Supp(List<String> pow ,HashMap<String, ArrayList<String>> totalDictAll) {
		List<String> AttList = null;  
		if(pow == null) {
			AttList.addAll(AttList);
		}
		for (int i = 0; i < pow.size(); i++) {
			String obt = pow.get(i);    
			obt = obt.substring(0, obt.length()-1);  
			if(AttList==null){			
				AttList = new ArrayList<String>();   
				ArrayList<String> attr = totalDictAll.get(obt);  
				if(attr != null){
					for (int j = 0; j < attr.size(); j++) {
						AttList.add(attr.get(j));
					}
				} 
			}else{
				AttList.retainAll(totalDictAll.get(obt));
			}
		}
		return AttList.size();
	}
	public static List<String> shangYS(String pow ,HashMap<String, ArrayList<String>> totalDictAll){
		List<String> AttList = null;  
		if(pow == null) {
			AttList.addAll(AttList);
		}
		String[] ss =  pow.split("/");    
		for (int i = 0; i < ss.length; i++) {
			String obt = ss[i];      
			if(AttList==null){			
				AttList = new ArrayList<String>();   
				ArrayList<String> attr = totalDictAll.get(obt);  
				if(attr != null){
					for (int j = 0; j < attr.size(); j++) {
						AttList.add(attr.get(j));
					}
				} 
			}else{
				AttList.retainAll(totalDictAll.get(obt));
			}
		}
		return AttList;
	}
	public static int Supp(String pow ,HashMap<String, ArrayList<String>> totalDictAll){
		List<String> AttList = null;  
		if(pow == null) {
			AttList.addAll(AttList);
		}
		String[] ss = pow.split("/");
		for (int i = 0; i < ss.length; i++) {
			String obt = ss[i];      
			if(AttList==null){			
				AttList = new ArrayList<String>();   
				ArrayList<String> attr = totalDictAll.get(obt);  
				if(attr != null){
					for (int j = 0; j < attr.size(); j++) {
						AttList.add(attr.get(j));
					}
				} 
			}else{
				AttList.retainAll(totalDictAll.get(obt));
			}
		}
		return AttList.size();
	}	
	public static HashSet<List<String>> powerSet(List<String> set) {
        int size = 2 << set.size();
        List<List<String>> powerSet = new ArrayList<>(size);
        powerSet.add(Collections.emptyList());
        for (String element : set) {
            int preSize = powerSet.size();
            for (int i = 0; i < preSize; i++) {
                List<String> combineSubset = new ArrayList<>(powerSet.get(i));
                combineSubset.add(element);
                //
                powerSet.add(combineSubset);
            }
        }
        HashSet<List<String>> ret = new HashSet<>();
        ret.addAll(powerSet);
        return ret;
    }
	public static double calStab(List<String> obj, HashMap<String, ArrayList<String>> totalDictAll) {
		double stab = 0;
		int size = 0;
		int sum = 0; 
		size = obj.size();
		List<String> Intent = shangYS(obj, totalDictAll);
		HashSet<List<String>> powSet = powerSet(obj);
		for (List<String> pow : powSet) {
			List<String> subSetShang = shangYS(pow, totalDictAll);
			if (subSetShang == null) {
				continue;
			}
			if (subSetShang.equals(Intent)) {
				sum = sum + 1;
			}
		}
		stab = sum / Math.pow(2, size);
		return stab;
	}
}
