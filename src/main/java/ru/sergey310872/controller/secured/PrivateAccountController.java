package ru.sergey310872.controller.secured;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sergey310872.entity.RecordStatus;
import ru.sergey310872.entity.User;
import ru.sergey310872.entity.dto.RecordsContainerDto;
import ru.sergey310872.service.RecordService;
import ru.sergey310872.service.UserService;
import ru.sergey310872.util.DeviceDetector;

@Controller
@RequestMapping("/account")
public class PrivateAccountController {
    private final UserService userService;
    private final RecordService recordService;

    @Autowired
    public PrivateAccountController(RecordService recordService, UserService userService) {
        this.recordService = recordService;
        this.userService = userService;
    }

    @GetMapping  //home
    public String getMainPage(Model model, HttpServletRequest request, @RequestParam(name = "filter", required = false) String filterMode) {
//        String userAgent = request.getHeader("User-Agent");
        DeviceDetector.DeviceInfo info = DeviceDetector.detectDevice(request);

        User user = userService.getCurrentUser();
        RecordsContainerDto container = recordService.findAllRecords(filterMode);
        model.addAttribute("userName", user.getName());
        model.addAttribute("records", container.getRecords());
        model.addAttribute("numberOfDoneRecords", container.getNumberOfDoneRecords());
        model.addAttribute("numberOfActiveRecords", container.getNumberOfActiveRecords());
        model.addAttribute("devise", info.getDeviceType());

//        return info.getDeviceType() + "/main-page";// /WEB-INF/views/main-page.jsp
//        return "/main-page";// /WEB-INF/views/main-page.jsp
        return "private/account-page";//"/main-page";// /WEB-INF/views/main-page.jsp
    }

    @PostMapping(value = "/add-record")
    public String addRecord(@RequestParam String title) {
        recordService.saveRecord(title);
        return "redirect:/account";
    }

    @PostMapping(value = "/make-record-done")
    public String makeRecordDone(@RequestParam int id,
                                 @RequestParam(name = "filter", required = false) String filterMode) {
        recordService.updateRecordStatus(id, RecordStatus.DONE);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }

    @PostMapping(value = "/delete-record")
    public String deleteRecord(@RequestParam int id,
                               @RequestParam(name = "filter", required = false) String filterMode) {
        recordService.deleteRecord(id);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }
}
