package com.springBootExcel.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

	/**
	 * 创建一个文件夹 如果存在 不创建 如果不存在 创建
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean makeDirs(String filePath) {
		File folder = new File(filePath);
		return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
	}

	/**
	 * 删除单个文件 路径 是全路径 c盘啥啥的全路径
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				// System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			// System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * 通过该方法将在指定目录下添加指定文件
	 * 
	 * @param file:文件的二进制
	 * @param filePath:文件的路径
	 * @param fileName:文件名
	 * @throws IOException
	 */
	public static void fileupload(byte[] file, String filePath, String fileName) throws IOException {
		// 创建目标目录
		File targetfile = new File(filePath);
		if (targetfile.exists()) {
			// 创建文件夹
			targetfile.mkdirs();
		}
		// 二进制流写入
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}
}
