/**
 * 
 */
package de.hszg.computenode.service.util;

import java.util.HashMap;
import java.util.Vector;

/**
 * @author Tobias Mack
 *
 */
public class MacHelper {

	private static MacHelper macHelper = null;
	
	private HashMap<String, String> map = new HashMap<String, String>();
	
	private MacHelper(){
		map.put("0000", "0");
		map.put("0001", "1");
		map.put("0010", "2");
		map.put("0011", "3");
		map.put("0100", "4");
		map.put("0101", "5");
		map.put("0110", "6");
		map.put("0111", "7");
		map.put("1000", "8");
		map.put("1001", "9");
		map.put("1010", "A");
		map.put("1011", "B");
		map.put("1100", "C");
		map.put("1101", "D");
		map.put("1110", "E");
		map.put("1111", "F");
	}
	
	public static MacHelper getInstance(){
		if(macHelper == null){
			macHelper = new MacHelper();
		}
		return macHelper;
	}
	
	public Vector<String> getMacs(String bucket){
		Vector<String> list = new Vector<String>();
		while(bucket.length() > 0){
			int mod = bucket.length()%4;
			if(mod == 0){
				System.out.println(bucket.substring(0, 4));
				list.add(bucket.substring(0, 4));
				bucket = bucket.substring(4);
			}else{
				System.out.println(bucket.substring(0, mod));
				list.add(bucket.substring(0, mod));
				bucket = bucket.substring(mod);
			}
		}
		Vector<String> macFragments = new Vector<String>();
		int listSize = list.size();
		for(int j = 0; j < list.size(); j++){
			for(String bits : map.keySet()){
				if(bits.endsWith(list.get(j))){
					if(list.get(j).length() < 4){
						macFragments.add(map.get(bits));
					}else{
						if(macFragments.size() > 0){
							for(int i = 0; i < macFragments.size(); i++){
								macFragments.set(i, macFragments.get(i).concat(map.get(bits)));
							}
						}else{
							macFragments.add(map.get(bits));
						}
					}
				}
			}
			listSize--;
			if(listSize%2 == 0 && listSize != 0){
				for(int i = 0; i < macFragments.size(); i++){
					macFragments.set(i, macFragments.get(i).concat(":"));
				}
			}	
		}
		return macFragments;
	}
	
}
