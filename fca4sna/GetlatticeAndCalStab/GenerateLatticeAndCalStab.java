package fca4sna.GetlatticeAndCalStab;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class GenerateLatticeAndCalStab {
	static String fileName;
	static int[][] adjMat;
	static Integer numObj;	
	static String tmpS;
	static ArrayList<String> obj = new ArrayList<String>();
	static ArrayList<String> attr = new ArrayList<String>();
	static BasicCL basicCL = new BasicCL();    
	static BPCliques bpcObj;
	static BPCliques bpcAttr;
	static HashMap<String, ArrayList<String>> dictAll = new HashMap<>();
	static HashSet<ArrayList<String>> objResult = new HashSet<>();
	static BPCliques CL;     
	static HashSet<String> bpcAllCLHashSet = new HashSet<String>();
	static int[][] new1AdjMat;    
	static Integer newNumObj ;   
	static ArrayList<String> newAttr = new ArrayList<String>();
	static BasicCL new1BasicCL = new BasicCL(); 
	static BPCliques new1BpcObj;
	static BPCliques new1BpcAttr;
	static HashMap<String, ArrayList<String>> new1DictAll = new HashMap<>();
	static HashSet<ArrayList<String>> new1ObjResult = new HashSet<>();
	static BPCliques new1CL;	 
	static HashSet<String> new1BpcAllCLHashSet = new HashSet<String>();
	static int[][] tot1AdjMat;
	static HashMap<String, ArrayList<String>> tot1DictAll = new HashMap<>();
	static ArrayList<ArrayList<String>> objI1 = new ArrayList<>();
	static ArrayList<ArrayList<String>> objI2 = new ArrayList<>();
	static HashSet<ArrayList<String>>   objIS = new HashSet<ArrayList<String>>();
	static BPCliques fin1BPCliques = new BPCliques();
	static HashSet<String> tot1BpcAllCLHashSet = new HashSet<String>();
	static int[][] new2AdjMat;
	static ArrayList<String> newObj = new ArrayList<String>();
	static BasicCL newBasicCL = new BasicCL(); 
	static BPCliques newBpcObj;
	static BPCliques newBpcAttr;
	static HashMap<String, ArrayList<String>> newDictAll = new HashMap<>();
	static HashSet<ArrayList<String>> newObjResult = new HashSet<>();
	static BPCliques newCL;	 
	static HashSet<String> new2BpcAllCLHashSet = new HashSet<String>();
	static int[][] totalAdjMat;
	static HashMap<String, ArrayList<String>> totalDictAll = new HashMap<>();
	static ArrayList<ArrayList<String>> attrI1 = new ArrayList<>();
	static ArrayList<ArrayList<String>> attrI2 = new ArrayList<>();
	static HashSet<ArrayList<String>> attrIS = new HashSet<ArrayList<String>>();
	static BPCliques finalBPCliques = new BPCliques();
	static HashSet<String> totalBpcAllCLHashSet = new HashSet<String>();
	static ArrayList<ArrayList<String>> objSet = new ArrayList<>();
	static ArrayList<ArrayList<String>> itemSet = new ArrayList<>();    
	static ArrayList<ArrayList<String>> equiConSetExtend = new ArrayList<>();
	static ArrayList<ArrayList<String>> equiConSetIntend = new ArrayList<>();
	static double stabIndex = 0;	
	
	public static class resultPair
	{
		private double stab;
		private ArrayList<String> MINGEN;
		public double getStab() {
			return stab;
		}
		public void setStab(double stab) {
			this.stab = stab;
		}
		public ArrayList<String> getMINGEN() {
			return MINGEN;
		}
		public void setMINGEN(ArrayList<String> mINGEN) {
			MINGEN = mINGEN;
		}	
	}
	
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		input(keyboard);        	
		generateConceptLattice();	
		System.out.println("初始概念格长度："+bpcAllCLHashSet.size());
		newInputItem(keyboard);
		new1GenerateConceptLattice();
		System.out.println("增量概念格长度："+new1BpcAllCLHashSet.size());
		fin1ConceptLattice();  		
		System.out.println("总概念格长度：" + tot1BpcAllCLHashSet.size());
		System.out.println("概念格生成结束！");		
		newInputObj(keyboard);
		new2GenerateConceptLattice();
		System.out.println("增量概念格长度："+new2BpcAllCLHashSet.size());
		fin2ConceptLattice();  		
		System.out.println("总概念格长度：" + totalBpcAllCLHashSet.size());
		System.out.println("概念格生成结束！");
		
		TextUtil tu = new TextUtil();
		String content = "生成的概念格为：\r\n";			
		for( int i = 0; i < objSet.size(); i++ ) {
			content = content + objSet.get(i) + "\t" + itemSet.get(i) + "\t" ;
			resultPair result = calStab(objSet.get(i), itemSet.get(i));
			content = content +"稳定值为："+ result.getStab() +"\t" +"MinGen为："+ result.getMINGEN()+ "\r\n";
		}
		tu.fileChaseFW("J:\\"+"test\\增量社交网络\\结果\\" + fileName + ".txt", content);
	}
	
	public static  void input(Scanner keyboard){
		String fileName1 = "";
		File file = new File(fileName1);
		String result = "";
		int temp = -1;
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
			BufferedReader br = new BufferedReader(reader);
			String s = null;
			s = br.readLine();
			numObj = Integer.parseInt(s);
			adjMat = new int[numObj][numObj];
				for(int r = 0; r < numObj; r++){
					s = br.readLine();
					for(int t = 0 ; t < numObj; t++){
						String ss = s;
						ss = ss.substring(t, t+1);
						temp = Integer.parseInt(ss);
						adjMat[r][t] = temp;
					}
				}
				for (int i = 0; i < numObj; i++) {
					tmpS = i + 1 + "";
					obj.add(tmpS);
				}
				for (int i = 0; i < numObj; i++) {
					tmpS = "a" + (i+1) + "";
					attr.add(tmpS);
				}
				System.out.println("Enter the filename:");
				fileName = keyboard.next();
				System.out.println("数据输入完毕......................................................");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void newInputItem(Scanner keyboard){
		String fileNa = "";
		File file = new File(fileNa);
		int temp = -1;
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
			BufferedReader br = new BufferedReader(reader);
			String s = null;
			s = br.readLine();
			newNumObj = Integer.parseInt(s);			
			new1AdjMat = new int[numObj][newNumObj];  
				for(int r = 0; r < numObj; r++){
					s = br.readLine();
					for(int t = numObj ; t < numObj+newNumObj; t++){
						String ss = s;
						ss = ss.substring(t, t+1);
						temp = Integer.parseInt(ss);
						new1AdjMat[r][t-numObj] = temp;   
					}
				}
				for (int i = numObj; i < numObj+newNumObj; i++) {
					tmpS = "a" + (i+1) + "";
					newAttr.add(tmpS);
				}
				System.out.println("数据输入完毕......................................................");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void newInputObj(Scanner keyboard){
		String fileNa = "";
		File file = new File(fileNa);
		int temp = -1;
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
			BufferedReader br = new BufferedReader(reader);
			String s = null;
			new2AdjMat = new int[newNumObj][numObj+newNumObj];   
			br.readLine();   
			for(int i = 0 ; i < numObj; i++) {
				br.readLine();
			}
			for(int r = 0; r < newNumObj; r++){		
				s = br.readLine();
				for(int t = 0 ; t < numObj+newNumObj; t++){
					String ss = s;
					ss = ss.substring(t, t+1);
					temp = Integer.parseInt(ss);
					new2AdjMat[r][t] = temp;
				}
			}
			for (int i = numObj; i < numObj + newNumObj; i++) {
				tmpS = i + 1 + "";
				newObj.add(tmpS);
			}
			System.out.println("数据输入完毕......................................................");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public static void generateConceptLattice(){
		bpcObj  = basicCL.getBPCliqueObj(adjMat, obj, attr, numObj, numObj); 
		bpcAttr = basicCL.getBPCliqueAttr(adjMat, obj, attr, numObj, numObj);
		dictAll = basicCL.buildDict(adjMat, obj, attr, numObj, numObj); 
		objResult = basicCL.objResult(attr, obj, bpcAttr, dictAll);
		CL = basicCL.FinalBpcAll(objResult, dictAll);   
		for (int i = 0; i < CL.size(); i++) {
			String content = basicCL.AtoS(CL.get(i).getL()) + "#"
					+ "" + basicCL.AtoS(CL.get(i).getR());
			bpcAllCLHashSet.add(content);
		}
		String newSpc2 = "#";
		for(String s : attr){
			newSpc2 = newSpc2 + s +" ";
		}
		CommonUtil.judgeSpecialConcept(newSpc2,bpcAllCLHashSet);
	}
	
	public static void new1GenerateConceptLattice(){
		new1BpcObj = new1BasicCL.getBPCliqueObj(new1AdjMat, obj, newAttr, numObj, newNumObj);
		new1BpcAttr = new1BasicCL.getBPCliqueAttr(new1AdjMat, obj, newAttr, numObj, newNumObj);
		new1DictAll = new1BasicCL.buildDict(new1AdjMat, obj, newAttr, numObj, newNumObj);
		new1ObjResult = new1BasicCL.objResult(newAttr, obj, new1BpcAttr, new1DictAll);
		new1CL = new1BasicCL.FinalBpcAll(new1ObjResult, new1DictAll);
		for (int i = 0; i < new1CL.size(); i++) {
			String content = new1BasicCL.AtoS(new1CL.get(i).getL()) + "#" + new1BasicCL.AtoS(new1CL.get(i).getR());
			new1BpcAllCLHashSet.add(content);
		}
		String newSpc2 = "#";
		for(String s : newAttr){
			newSpc2 = newSpc2 + s +" ";
		}
		CommonUtil.judgeSpecialConcept(newSpc2,new1BpcAllCLHashSet);
	}
	
	public static void new2GenerateConceptLattice(){
		newBpcObj = newBasicCL.getBPCliqueObj(new2AdjMat, newObj, attr, newNumObj, numObj+newNumObj );  
		newBpcAttr = newBasicCL.getBPCliqueAttr(new2AdjMat, newObj, attr, newNumObj, numObj+newNumObj );
		newDictAll = newBasicCL.buildDict(new2AdjMat, newObj, attr, newNumObj, numObj+newNumObj );
		newObjResult = newBasicCL.objResult(attr, newObj, newBpcAttr, newDictAll);
		newCL = newBasicCL.FinalBpcAll(newObjResult, newDictAll);
		for (int i = 0; i < newCL.size(); i++) {
			String content = newBasicCL.AtoS(newCL.get(i).getL()) + "#" + newBasicCL.AtoS(newCL.get(i).getR());
			new2BpcAllCLHashSet.add(content);
		}
			String newSpc1 = "";
			String newSpc2 = "#";
			for(String s : newObj){
				newSpc1 = newSpc1 + s +" "; 
			}
			newSpc1 = newSpc1 + "#";
			for(String s : attr){
				newSpc2 = newSpc2 + s +" ";
			}
			new2BpcAllCLHashSet.add(newSpc1);
			new2BpcAllCLHashSet.add(newSpc2);
	}
	
	public static void fin1ConceptLattice(){
		tot1AdjMat = new int[numObj][numObj+newNumObj];   
		for (int i = 0; i < numObj; i++) {
			for (int j = 0; j < numObj + newNumObj; j++) {
				if(j<numObj){   
					tot1AdjMat[i][j] = adjMat[i][j];
				}else{			
					tot1AdjMat[i][j] = new1AdjMat[i][j-numObj];
				}
			}
		}
		attr.addAll(newAttr);   
		tot1DictAll = basicCL.buildDict(tot1AdjMat, obj, attr, numObj, numObj + newNumObj);
		objI1 = basicCL.SubContent(bpcAllCLHashSet);
		objI2 = newBasicCL.SubContent(new1BpcAllCLHashSet);
		objIS = basicCL.ContentIS(objI1, objI2);
		fin1BPCliques = basicCL.FinalBpcAll(objIS, tot1DictAll);
		for (int i = 0; i < fin1BPCliques.size(); i++) {
			String newContent = basicCL.AtoS(fin1BPCliques.get(i).getL()) + "#" + basicCL.AtoS(fin1BPCliques.get(i).getR());
			tot1BpcAllCLHashSet.add(newContent);
		}
		String nullRs = " #";
		tot1BpcAllCLHashSet.remove(nullRs);
		for(String s : attr){
			nullRs = nullRs + s +" ";
		}
		tot1BpcAllCLHashSet.add(nullRs);
	}
	
	public static void fin2ConceptLattice(){
		totalAdjMat = new int[numObj+newNumObj][numObj+newNumObj];    
		for (int i = 0; i < numObj + newNumObj; i++){
			for(int j = 0; j< numObj+newNumObj; j++){
				if(i < numObj){   
					totalAdjMat[i][j] = tot1AdjMat[i][j];
				}else{			
					totalAdjMat[i][j] = new2AdjMat[i - numObj][j];
				}
			}
		}
		obj.addAll(newObj);   
		totalDictAll = basicCL.buildDict(totalAdjMat, obj, attr, numObj + newNumObj, numObj+newNumObj); 
		attrI1 = basicCL.SubIntent(tot1BpcAllCLHashSet);  
		attrI2 = newBasicCL.SubIntent(new2BpcAllCLHashSet);
		attrIS = basicCL.ContentIS(attrI1, attrI2);
		finalBPCliques = basicCL.FinalBpcAll(attrIS, totalDictAll);
		for (int i = 0; i < finalBPCliques.size(); i++) {
			if( !finalBPCliques.get(i).getR().isEmpty()) {
				String newContent = basicCL.AtoS(finalBPCliques.get(i).getR()) + "#" + basicCL.AtoS(finalBPCliques.get(i).getL());
				totalBpcAllCLHashSet.add(newContent);
				objSet.add(finalBPCliques.get(i).getR());
				itemSet.add(finalBPCliques.get(i).getL());
				if( finalBPCliques.get(i).getL().size() == finalBPCliques.get(i).getR().size() ) {
					equiConSetExtend.add(finalBPCliques.get(i).getR());
					equiConSetIntend.add(finalBPCliques.get(i).getL());
				}
			}
		}
	
	}

	public static resultPair calStab( ArrayList<String> A, ArrayList<String> B ) {
		ArrayList<String> MINGEN = new ArrayList<String>();
		ArrayList<String> Gen = new ArrayList<String>();
		double stab = 0;
		double aSize = A.size();
		System.out.println("-------------");
		System.out.println("当前处理的概念是：");
		for(int i = 0 ;i <A.size(); i++) {
			A.set(i, A.get(i)+"/");
		}
		System.out.println("外沿："+A);
		System.out.println("内涵："+B);
		for (String a : A) {	
			System.out.println("a："+a);
			System.out.println(newBasicCL.shangYS(a, totalDictAll));
			if(newBasicCL.Supp(a, totalDictAll) == B.size()) {
    			Gen.add(a);			
			}				
		}
		MINGEN.addAll(Gen);
		System.out.println("---------");
		System.out.println("Gen:"+Gen);  
		System.out.println("A:"+A);   
		if(Gen.isEmpty() == false) {
			stab = 1-1/Math.pow(2, Gen.size());
		}
		System.out.println("------下面构造1-objset的MaxNonGen---------");
		A.removeAll(Gen);   
		System.out.println("A去掉Gen后："+A);
		System.out.println("A去掉Gen后作为BUIlD函数的输入");
		ArrayList<String> MaxNonGen = BUILD_MAX_NONGEN(A, B.size());  
		System.out.println();
		System.out.println("--------下面将集合A根据MaxNonGen分为α和β集合--------");
		A.removeAll(MaxNonGen);
		ArrayList<String> α = A;
		System.out.println("集合(α)："+ α);	
		ArrayList<String> β = MaxNonGen;
		System.out.println("最大非生成器集合(β)："+ β);
		System.out.println("------------");
		ArrayList<String> WUCHA = new ArrayList<String>();
		for (String a : α) { 
			WUCHA.add(a);
			stab = stab + HANDLE_CURRENT_ELEMENT( MINGEN,a, α, β, WUCHA, B.size()) / Math.pow(2, aSize);
		}
		resultPair result = new resultPair();
		result.setStab(stab);
		result.setMINGEN(MINGEN);
		return result;
	}
	
	public static ArrayList<String> BUILD_MAX_NONGEN(ArrayList<String> X , int N ) {
	
		ArrayList<String> MaxNonGen1 = new ArrayList<String>();
		for (String a : X) {
			MaxNonGen1.add(a);
			System.out.println("当前遍历元素是" + MaxNonGen1);
			System.out.println("上运算结果为："+newBasicCL.shangYS( MaxNonGen1, totalDictAll));
			System.out.println("其对应的supp值为："+newBasicCL.Supp( MaxNonGen1, totalDictAll));
			if(newBasicCL.Supp(MaxNonGen1, totalDictAll) != N) {
				System.out.println(a +"并入集合MaxNonGen1");
				System.out.println();
			}else {
				MaxNonGen1.remove(a);
				System.out.println(a+ "不应该并入到集合MaxNonGen");
				System.out.println();
			}
		}
		System.out.println("--------BUILD函数结束------------");
		System.out.println("返回值MaxNonGen为："+MaxNonGen1);	//
		return MaxNonGen1;
	}
	
	public static double HANDLE_CURRENT_ELEMENT(ArrayList<String> MINGEN,String a, ArrayList<String> α, ArrayList<String> β, ArrayList<String> WUCHA,int N) {
		double Nbgen = 0;
		while( !α.isEmpty() ) {
			MINGEN = GET_MINGEN(MINGEN, a, α, β, N, WUCHA);
			System.out.println("------------");
			System.out.println("开始执行Compute_Gen");
		    Nbgen = Nbgen + COMPUTE_GEN( a, α, β, N, WUCHA );
		    System.out.println("------------");
			System.out.println("开始执行Return_NonGenSet");
		    ArrayList<String> NonGenSet = RETURN_NonGenSet( Nbgen, a, α, β, N, WUCHA );
		    System.out.println("Get函数的输入"+NonGenSet);
			α = GET_α(a, NonGenSet, N);
			β = GET_β(a, NonGenSet, N);
			a = GET_a(a, NonGenSet, N);
			System.out.println("修改后a的值："+a);
		}
		System.out.println("Nbgen："+ Nbgen);
		return Nbgen;
	} 
	
	public static double COMPUTE_GEN(String a, ArrayList<String> α, ArrayList<String> β, int N, ArrayList<String> WUCHA) {
		double Nbgen = 0;
		ArrayList<String> NonGenSet = null;
		ArrayList<String> MinGenSet = null;
		System.out.println("----------");
		System.out.println("定位计算COMPUTER函数传入值");
		System.out.println("传入集合(α)："+ α);
		System.out.println("传入集合(β)："+ β);
		System.out.println("----------");
		ArrayList<String> TempSetβ = new ArrayList<String>();
		for (String string : β) {
			TempSetβ.add(string);
		}
		System.out.println("下面完成β∪α a");
		TempSetβ.addAll(α);
		System.out.println("暂存集合TempSetβ并上α："+ TempSetβ);
		String[] tempa = a.split("/");
		for(int i =0; i < tempa.length; i++) {
			tempa[i] = tempa[i] + "/";
			while( TempSetβ.contains(tempa[i]) ) {
				TempSetβ.remove(tempa[i]);
			}
		}
		/*for(int i=0; i<a.length(); i++) {
			TempSetβ.remove(a.substring(i, i+1));
		}*/
		System.out.println("暂存集合TempSetβ去掉元素a："+ TempSetβ);
		System.out.println("误差"+WUCHA);
		TempSetβ.removeAll(WUCHA);
		System.out.println("暂存集合TempSetβ去掉误差："+ TempSetβ);
		System.out.println("----------");
	/*	System.out.println("暂存后，集合α和β的值不变");
		System.out.println("集合(α)："+ α);
		System.out.println("集合(β)："+ β);*/
		System.out.println();
		System.out.println("-------定位--------");
		System.out.println("当前处理的前缀是："+a);
		System.out.println("当前的遍历集合TempSetβ："+ TempSetβ);		
		System.out.println("下面将前缀和该集合分别相交并判断是否是Min-generator");
		MinGenSet = new ArrayList<String>();
		NonGenSet = new ArrayList<String>();
		for (String b : TempSetβ) {
			String ab1 = a + b;
			System.out.println("字符串ab1:"+ab1);
			if (newBasicCL.Supp(ab1, totalDictAll) == N) {
				System.out.println( ab1+ "是"+",并入MinGenSet");
				MinGenSet.add(ab1);
			}else {
				System.out.println(ab1+ "不是"+",并入NonGenSet");
				NonGenSet.add(ab1);
			}
		}
		System.out.println("MinGenSet:"+MinGenSet);
		System.out.println("NonGenSet:"+NonGenSet);	
		System.out.println();
		System.out.println("-------计数------");
		System.out.println("初始Nbgen:"+Nbgen);
		System.out.println();
		for(int i = 1; i <= MinGenSet.size(); i++) {
			System.out.println("第"+i+"次循环Nbgen："+Math.pow(2, NonGenSet.size()+MinGenSet.size()-i));
			Nbgen = Nbgen + Math.pow(2, NonGenSet.size()+MinGenSet.size()-i);		
		}
		System.out.println();
		System.out.println("计数结束，Nbgen："+Nbgen);
		return Nbgen;
	}
	
	public static ArrayList<String> GET_MINGEN(ArrayList<String> MINGEN,String a, ArrayList<String> α, ArrayList<String> β, int N, ArrayList<String> WUCHA) {
		double Nbgen = 0;
		ArrayList<String> TempSetβ = new ArrayList<String>();
		for (String string : β) {
			TempSetβ.add(string);
		}
		TempSetβ.addAll(α);
		String[] tempa = a.split("/");
		for(int i =0; i < tempa.length; i++) {
			tempa[i] = tempa[i] + "/";
			while( TempSetβ.contains(tempa[i]) ) {
				TempSetβ.remove(tempa[i]);
			}
		}
		TempSetβ.removeAll(WUCHA);
		for (String b : TempSetβ) {		
			String ab1 = a + b;
			if (newBasicCL.Supp(ab1, totalDictAll) == N) {
				MINGEN.add(ab1);	
			}		
		}
		return MINGEN;
	}
	
	public static ArrayList<String> RETURN_NonGenSet(double Nbgen, String a, ArrayList<String> α, ArrayList<String> β, int N, ArrayList<String> WUCHA) {
		ArrayList<String> NonGenSet = null;
		ArrayList<String> MinGenSet = null;
		ArrayList<String> TempSetβ = new ArrayList<String>();
		for (String string : β) {
			TempSetβ.add(string);
		}
		TempSetβ.addAll(α);
		String[] tempa = a.split("/");
		for(int i =0; i < tempa.length; i++) {
			tempa[i] = tempa[i] + "/";
			while( TempSetβ.contains(tempa[i]) ) {
				TempSetβ.remove(tempa[i]);
			}
		}
		TempSetβ.removeAll(WUCHA);
		System.out.println("----------");
		System.out.println();
		MinGenSet = new ArrayList<String>();
		NonGenSet = new ArrayList<String>();
		for (String b : TempSetβ) {
			String ab1 = a + b;
			if (newBasicCL.Supp(ab1, totalDictAll) == N) {			
				MinGenSet.add(ab1);
			}else {
				NonGenSet.add(ab1);
			}
		}
		System.out.println("RETURN函数的返回值NonGenSet:" + NonGenSet);	
		return NonGenSet;
	}
	
	public static ArrayList<String> GET_α( String a, ArrayList<String> NonGenSet, int N) {
		ArrayList<String> α = new ArrayList<String>();
		ArrayList<String> β = new ArrayList<String>();
		for (String str : NonGenSet) {
			System.out.println("----------------");
			System.out.println("当前GET_α处理的NonGenSet中的元素："+ str);
			String[] ss = str.split("/");   
			for(int i = 0; i < ss.length; i++) {
				if( !β.contains(ss[i]+"/") ) {
					β.add(ss[i]+"/");					
				}
			}
			System.out.println("已经拆分按位添加str进入β:"+β);
			if(newBasicCL.Supp(β, totalDictAll) == N) {
				for(int i=0; i< ss.length; i++) {
					if( !α.contains(ss[i]+"/") ) {
						α.add( ss[i]+"/" );	
						β.remove(ss[i]+"/");
					}
				}		
				a = str ;   
			}else {
			}
			System.out.println("判断完该元素后的α："+α);
			System.out.println("判断完该元素后的β："+β);
			System.out.println();
		}
		return α;
	}
	
	public static String GET_a( String a, ArrayList<String> NonGenSet, int N) {
		ArrayList<String> α = new ArrayList<String>();
		ArrayList<String> β = new ArrayList<String>();
		for (String str : NonGenSet) {
			String[] ss = str.split("/"); 
			for(int i = 0; i < ss.length; i++) {
				if( !β.contains(ss[i]+"/") ) {
					β.add(ss[i]+"/");					
				}
			}
			if(newBasicCL.Supp(β, totalDictAll) == N) {
				for(int i=0; i< ss.length; i++) {
					if( !α.contains(ss[i]+"/") ) {
						α.add( ss[i]+"/" );	
						β.remove(ss[i]+"/");
					}
				}
				a = str ;   
			}else {
			}
		}
		return a;
	}	
	
	public static ArrayList<String> GET_β(String a ,ArrayList<String> NonGenSet, int N) {
		ArrayList<String> α = new ArrayList<String>();
		ArrayList<String> β = new ArrayList<String>();
		for (String str : NonGenSet) {
			String[] ss = str.split("/"); 
			for(int i = 0; i < ss.length; i++) {
				if( !β.contains(ss[i]+"/") ) {
					β.add(ss[i]+"/");					
				}
			}
			if(newBasicCL.Supp(β, totalDictAll) == N) {
				for(int i=0; i< ss.length; i++) {
					if( !α.contains(ss[i]+"/") ) {
						α.add( ss[i]+"/" );	
						β.remove(ss[i]+"/");
					}
				}
				a = str ;   
			}else {
			}
		}
		return β;
	}
}