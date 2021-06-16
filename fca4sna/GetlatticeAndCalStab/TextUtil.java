package fca4sna.GetlatticeAndCalStab;
import java.io.*;
public class TextUtil {
	public boolean createFile(String fileName) throws Exception {
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	private boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("ɾ�������ļ�" + fileName + "�ɹ���");
				return true;
			} else {
				System.out.println("ɾ�������ļ�" + fileName + "ʧ�ܣ�");
				return false;
			}
		} else {
			System.out.println("ɾ�������ļ�ʧ�ܣ�" + fileName + "�����ڣ�");
			return false;
		}
	}
	private boolean deleteDirectory(String dir) {
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("ɾ��Ŀ¼ʧ�ܣ�" + dir + "�����ڣ�");
			return false;
		}
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("ɾ��Ŀ¼ʧ�ܣ�");
			return false;
		}
		if (dirFile.delete()) {
			System.out.println("ɾ��Ŀ¼" + dir + "�ɹ���");
			return true;
		} else {
			return false;
		}
	}
	public boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("ɾ���ļ�ʧ��:" + fileName + "�����ڣ�");
			return false;
		} else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}
	public static String readTxtFile(String fileName) {
		File file = new File(fileName);
		String result = "";
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
			BufferedReader br = new BufferedReader(reader);
			String s = null;
			while ((s = br.readLine()) != null) {
				result = result + s;
				System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean writeTxtFile(String content, File fileName) throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(fileName);
			fileOutputStream.write(content.getBytes("utf-8"));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(null != fileOutputStream){
				fileOutputStream.close();
			}
		}
		return flag;
	}
	public void fileChaseFW(String filePath, String content) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath, true);
			fw.write(content);
		} catch (IOException e) {
			System.out.println("�ļ�д��ʧ�ܣ�" + e);
		}finally {
			if(null != fw){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void fileChaseFWCreateFolder(String filePath, String content) {
		File file = new File(filePath);
		File fileParent = file.getParentFile();
		if(!fileParent.exists()){
			fileParent.mkdirs();
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath, true);
			fw.write(content);
		} catch (IOException e) {
			System.out.println("�ļ�д��ʧ�ܣ�" + e);
		}finally {
			if(null != fw){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static boolean writeTxtFileCreateFolder(String content, String fileName) throws Exception {
		File file = new File(fileName);
		File fileParent = file.getParentFile();
		if(!fileParent.exists()){
			fileParent.mkdirs();
		}
		boolean flag=false;
		FileOutputStream fileOutputStream=null;
		try {
			fileOutputStream = new FileOutputStream(fileName);
			fileOutputStream.write(content.getBytes("gbk"));
			fileOutputStream.close();
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
