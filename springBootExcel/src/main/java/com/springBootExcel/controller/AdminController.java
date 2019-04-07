package com.springBootExcel.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.springBootExcel.entity.Client;
import com.springBootExcel.service.ClientService;
import com.springBootExcel.util.DateUtils;
import com.springBootExcel.util.ExcelUtil;
import com.springBootExcel.util.FileUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ClientService clientService;

	/**
	 * add_update.jsp页面添加操作
	 * 
	 * @Valid:用于校验client接收的数据是否符合要求，如果不符合就会返回实体类属性上面的注解信息
	 * @param client
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/insert")
	@ResponseBody
	public JSONObject add(@Valid Client client, BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result = new JSONObject();
		if (bindingResult.hasErrors()) {
			result.put("success", false);
			// bindingResult.getFieldError.getDefaultMessage()用于获取相应字段上添加的message中的内容
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		} else {
			client.setCreateDateTime(new Date());
			// 数据入库
			int num = clientService.insertClient(client);
			if (num > 0) {
				result.put("success", true);
				result.put("msg", "添加成功");
				return result;
			} else {
				result.put("success", false);
				result.put("msg", "添加操作失败");
				return result;
			}
		}
	}

	/**
	 * 数据分页显示
	 * 
	 * @param page:页码
	 * @param limit：每页显示多少条数据
	 * @param client
	 * @return
	 */
	@RequestMapping("/pageList")
	@ResponseBody
	public Map<String, Object> pageList(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit, Client client) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapsMap = new HashMap<String, Object>();

		// 定义SQL语句起始索引
		int a = (page - 1) * limit;
		// 定义页码
		int b = limit;

		System.out.println("page=" + page);
		System.out.println("limit=" + limit);

		mapsMap.put("a", a);
		mapsMap.put("b", b);
		// 分页查询
		List<Client> clientList = clientService.selectClientPage(mapsMap);
		int count = clientService.selectCount();

		System.out.println("时间格式是=====" + clientList.get(0).getCreateDateTime());

		map.put("clientList", clientList);
		map.put("count", count);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}

	/**
	 * Excel文件的导出
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/downloadExcel")
	public void setUpExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 查询数据库所有数据
		List<Client> list = clientService.findAllClient();
		// 创建Excel文件
		@SuppressWarnings("resource")
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Sheet
		HSSFSheet sheet = wb.createSheet();
		// 在Sheet中添加第0行(表头)
		HSSFRow row = sheet.createRow(0);
		// 创建单元格，设置表头居中
		@SuppressWarnings("unused")
		HSSFCellStyle style = wb.createCellStyle();
		sheet.autoSizeColumn(1, true);

		// 在表头创建第一个单元格
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("ID");
		// 在表头创建第二个单元格
		cell = row.createCell(1);
		cell.setCellValue("编号");
		cell = row.createCell(2);
		cell.setCellValue("姓名");
		cell = row.createCell(3);
		cell.setCellValue("电话");
		cell = row.createCell(4);
		cell.setCellValue("备注");
		cell = row.createCell(5);
		cell.setCellValue("创建时间");

		// 填充数据
		for (int j = 0; j < list.size(); j++) {
			// 创建数据的行数
			row = sheet.createRow(j + 1);
			// 数据库每一行数据就是一个数据
			Client client = list.get(j);
			row.createCell(0).setCellValue(client.getId());
			row.createCell(1).setCellValue(client.getBianhao());
			row.createCell(2).setCellValue(client.getName());
			row.createCell(3).setCellValue(client.getPhone());
			row.createCell(4).setCellValue(client.getRemark());
			row.createCell(5).setCellValue(DateUtils.formatDate(client.getCreateDateTime(), "yyyy-MM-dd HH:mm:ss"));
		}

		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename=" + "Customer.xls");// Excel文件名
		try {
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将workbook中的内容写入输出流中
		wb.write(response.getOutputStream());
	}

	/**
	 * Excel文件的导入
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return @throws IOException @throws
	 * @throws ParseException
	 */
	@PostMapping("/upload_excel")
	@ResponseBody
	public JSONObject upload_excel(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {
		JSONObject result = new JSONObject();

		System.out.println("该文件的原名称是:" + file.getOriginalFilename());

		if (!file.isEmpty()) {
			// 获取项目的根路径
			String webPath = request.getServletContext().getRealPath("") + "upload_Excel/";
			System.out.println("该项目的根路径是：" + webPath);
			// 转换文件名称
			String fileName = DateUtils.formatDate(new Date(), "yyyyMMdd-HHmmssSSS") + "_" + file.getOriginalFilename();

			try {
				// 文件保存至服务器
				FileUtil.fileupload(file.getBytes(), webPath, fileName);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("出现异常了--------------");
			}

			// 获取实体
			List<Client> clientList = excel_to_clientInfo(new File(webPath + fileName));

			for (@SuppressWarnings("unused")
			Client item : clientList) {
				int num = clientService.insertClientList(clientList);
				if (num < 0) {
					result.put("success", false);
					result.put("msg", "导入失败");
				}
			}
		}
		result.put("success", true);
		result.put("msg", "导入成功");
		return result;
	}

	/**
	 * 解析上传的Excel文件
	 * 
	 * @param userUploadFile
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("resource")
	private List<Client> excel_to_clientInfo(File userUploadFile) throws ParseException {

		List<Client> list = new ArrayList<Client>();
		Client client = new Client();

		System.out.println("怎么不报错呀");

		POIFSFileSystem fs = null;
		try {
			fs = new POIFSFileSystem(new FileInputStream(userUploadFile));
			HSSFWorkbook wb = new HSSFWorkbook(fs);

			HSSFSheet sheet = wb.getSheetAt(0);

			if (sheet != null) {
				// 遍历当前sheet中的所有行
				for (int rowNum = sheet.getFirstRowNum() + 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					HSSFRow row = sheet.getRow(rowNum);
					System.out.println("你进了没有");
					if (row == null) {
						System.out.println("你没有执行呀");
						continue;
					}
					// 去掉编码中的 .0 如果全是数字 后面有.0
					String bianma = ExcelUtil.formatCell(row.getCell(1));
					client.setBianhao(bianma);
					// client.setName(ExcelUtil.formatCell(row.getCell(2)).split("\\.")[0]);
					// client.setPhone(ExcelUtil.formatCell(row.getCell(3)));
					// client.setRemark(ExcelUtil.formatCell(row.getCell(4)));
					// client.setCreateDateTime(ExcelUtil.formatDate(row.getCell(5)));

					System.out.println("$$$$$$$$$$$$$$$$$$");

					String name = getCellValue(row.getCell(2));
					String phone = getCellValue(row.getCell(3));
					String remark = getCellValue(row.getCell(4));
					String createDateTime = getCellValue(row.getCell(5));
					System.out.println("[][[=============================");

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = sdf.parse(createDateTime);

					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");

					client.setCreateDateTime(date);
					client.setName(name);
					client.setPhone(phone);
					client.setRemark(remark);

					list.add(client);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("咋又出现异常啦*****************");
		}
		System.out.println(list.get(1).getName() + "/////////////");
		return list;
	}

	/**
	 * 获取cell内容
	 * 
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getCellValue(Cell cell) {
		String value = "";
		if (cell != null) {
			// 以下是判断数据的类型
			switch (cell.getCellType()) {
			// 数字
			case HSSFCell.CELL_TYPE_NUMERIC:
				value = cell.getNumericCellValue() + "";
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null) {
						value = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(date);
						System.out.println("你是猪");
					} else {
						value = "";
						System.out.println("你是猫");
					}
				} else {
					value = new DecimalFormat("0").format(cell.getNumericCellValue());
					System.out.println("你是狗");
				}
				break;
			// 字符串
			case HSSFCell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				System.out.println("Excel的数据格式是字符串");
				break;
			// boolean
			case HSSFCell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue() + "";
				System.out.println("你是boolean");
				break;
			// 公式
			case HSSFCell.CELL_TYPE_FORMULA:
				value = cell.getCellFormula() + "";
				System.out.println("你是公式");
				break;
			// 空值
			case HSSFCell.CELL_TYPE_BLANK:
				value = "";
				System.out.println("你是空值");
				break;
			// 误差
			case HSSFCell.CELL_TYPE_ERROR:
				value = "非法字符";
				System.out.println("你是非法字符");
				break;
			}
		}
		return value.trim();
	}
}