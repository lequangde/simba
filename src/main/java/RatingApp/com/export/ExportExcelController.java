package RatingApp.com.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import RatingApp.com.entities.RatingStar;
import RatingApp.com.export.ExportExcel;
import RatingApp.com.repository.ExcelRepository;
import RatingApp.com.repository.RatingAppRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/excel")
public class ExportExcelController {
	
	@Autowired
	RatingAppRepository appRepository;
	
	@Autowired
	ExcelRepository excelRepository;
	
	@GetMapping("/exportPage")
	public String exportPage() {
		return "exportExcel/export";
	}
	
	@GetMapping("/reported")
	public String reportPage(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
    		Model model,HttpSession httpSession) {
		String selectedCountry = (String) httpSession.getAttribute("selectedCountry");
		String startDate = (String) httpSession.getAttribute("startDate");
		String endDate = (String) httpSession.getAttribute("endDate");
		String month = (String) httpSession.getAttribute("month");
		String formattedMonth = String.format("%02d", Integer.parseInt(String.valueOf(month)));		
		System.out.println(startDate + " " + endDate);
		System.out.println(month);
		if(selectedCountry.equals("")) {
			List<RatingStar> dataList1 = excelRepository.findByDepartmentAndDateRateBetweenOrderByRateIdAsc(startDate, endDate,formattedMonth);
			model.addAttribute("listRating", dataList1);
		}else {
			List<RatingStar> dataList = excelRepository.findByDepartmentAndDateRateBetweenOrderByRateIdAsc(selectedCountry, startDate, endDate,formattedMonth);
			model.addAttribute("listRating", dataList);
		}
		
		return "exportExcel/export2";
	}
	
	@PostMapping("/exportExcel")
    public ResponseEntity<byte[]> exportToExcel2(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate,
    		@RequestParam String selectedCountry
    		) {
    	
        try {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
    		String startedDate = startDate.format(formatter);
    		String endedDate = endDate.format(formatter);
    		String department = convertAddress(selectedCountry);
    		LocalDate today = LocalDate.now();
    		int month = today.getMonthValue();
    		String formattedMonth = String.format("%02d", Integer.parseInt(String.valueOf(month)));
    		List<RatingStar> dataList = excelRepository.findByDepartmentAndDateRateBetweenOrderByRateIdAsc(selectedCountry, startedDate, endedDate,formattedMonth); // Assuming you have a method to retrieve data
    		
    		
    		
            // Create Excel file
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ExportExcel excelExporter = new ExportExcel();
            excelExporter.exportToExcel(dataList, outputStream);

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "baoCaoDanhGiaCHB" + department + ".xlsx");
            
            if(selectedCountry.equals("")) {
        		List<RatingStar> dataList1 = excelRepository.findByDepartmentAndDateRateBetweenOrderByRateIdAsc(startedDate, endedDate,formattedMonth); // Assuming you have a method to retrieve data
        		excelExporter.exportToExcel(dataList1, outputStream);
        		headers.setContentDispositionFormData("attachment", "baoCaoDanhGiaCHBTongHop.xlsx");
    		}

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
    
    @PostMapping("/report")
    public String getList(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate,
    		@RequestParam String selectedCountry,@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
    		Model model,HttpSession httpSession
    		) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
    	LocalDate today = LocalDate.now();
		int month = today.getMonthValue();
		String formattedMonth = String.format("%02d", Integer.parseInt(String.valueOf(month)));		
		String startedDate = startDate.format(formatter);
		String endedDate = endDate.format(formatter);
    	httpSession.setAttribute("selectedCountry", selectedCountry);
    	httpSession.setAttribute("startDate", startedDate);
    	httpSession.setAttribute("endDate", endedDate);
    	httpSession.setAttribute("month", formattedMonth);
		return "redirect:/excel/reported";
    }
    
    public static String convertAddress(String address) {
        // Split the input into parts by spaces
        String[] parts = address.split(" ");

        // The first part is the number
        String number = parts[0];

        // Collect the initials of each of the remaining parts
        StringBuilder initials = new StringBuilder();
        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                initials.append(parts[i].charAt(0));
            }
        }

        // Concatenate the number and the initials
        return number + initials.toString();
    }
}
