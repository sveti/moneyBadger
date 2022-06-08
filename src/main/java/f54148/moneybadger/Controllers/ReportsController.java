package f54148.moneybadger.Controllers;

import f54148.moneybadger.DTOs.ReportDTO;
import f54148.moneybadger.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportsController {

    private final UserService userService;

    @GetMapping(path = "/{userId}")
    public String getReports(Model model,@PathVariable("userId") Long userId) {
        model.addAttribute("userId", userId);
        List<ReportDTO> reports = userService.getReports(userId);
        model.addAttribute("reports",reports);
        Double all = 0.0;
        for(ReportDTO r: reports){
            if(r.getType().equals("income")){
                all+= r.getTotal();
            }
            else{
                all -= r.getTotal();
            }
        }
        model.addAttribute("totalReports",all);
        return "reports";
    }



}
