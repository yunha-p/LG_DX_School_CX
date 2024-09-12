package com.LgCxProject.controller.supplements;
import com.LgCxProject.domain.supplements.Supplements;
import com.LgCxProject.service.supplements.SupplementService;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class SupplementViewController {

    @Autowired
    private SupplementService supplementService;

    // 컨테이너 값을 세션에 저장
    @PostMapping("/supplements/scan")
    public String scan(@RequestParam("container") int container, HttpSession session) {
        session.setAttribute("container", container); // 세션에 container 값 저장
        return "/supplements/scan"; // 다음 페이지로 이동
    }

    // 영양제명을 입력받아 DB에서 조회 후 add 페이지로 이동
    @PostMapping("/supplements/add")
    public String getSupplementInfo(@RequestParam("supplementName") String supplementName, Model model) {
        // 입력받은 영양제명으로 DB에서 영양제 데이터를 조회
        Optional<Supplements> supplements = supplementService.FindSupplement(supplementName);

        if (supplements.isPresent()) {
            // 조회된 데이터를 모델에 추가
            Supplements supp = supplements.get();
            model.addAttribute("supplement_Name", supp.getSupplementName());
            model.addAttribute("main_ingredients", supp.getMainIngredients());
            model.addAttribute("intake_amount", supp.getIntakeAmount());
            model.addAttribute("intake_frequency", supp.getIntakeFrequency());
            model.addAttribute("intake_method", supp.getIntakeMethod());
            model.addAttribute("medication_precautions", supp.getMedicationPrecautions());
        } else {
            // 영양제 정보가 없는 경우
            model.addAttribute("error", "해당 영양제 정보를 찾을 수 없습니다.");
        }

        // add.html 페이지로 이동
        return "/supplements/add";
    }
}
