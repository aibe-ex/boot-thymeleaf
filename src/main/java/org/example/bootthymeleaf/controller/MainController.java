package org.example.bootthymeleaf.controller;

import lombok.RequiredArgsConstructor;
import org.example.bootthymeleaf.model.entity.Word;
import org.example.bootthymeleaf.model.repository.WordRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final WordRepository wordRepository;

    @GetMapping
    public String index(Model model) {
//        Word word = new Word();
//        word.setText("고양이");
//        wordRepository.save(word);
        model.addAttribute("words",wordRepository.findAll());
        return "index";
    }

    @PostMapping("/reset")
    public String resetWords(RedirectAttributes redirectAttributes) {
        wordRepository.deleteAll();
        // Model -> forward
//        redirectAttributes.addAttribute("message", "초기화 완료");
        // 주소창을 통해서 전달 message -> parameter
        redirectAttributes.addFlashAttribute("message", "끝말잇기 기록 삭제!"); // 바로 model로 넣어줌
        return "redirect:/";
    }
}
