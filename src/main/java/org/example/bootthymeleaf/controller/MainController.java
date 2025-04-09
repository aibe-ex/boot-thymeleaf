package org.example.bootthymeleaf.controller;

import lombok.RequiredArgsConstructor;
import org.example.bootthymeleaf.model.dto.WordForm;
import org.example.bootthymeleaf.model.entity.Word;
import org.example.bootthymeleaf.model.repository.WordRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
//        model.addAttribute("words",wordRepository.findAll());
        // 정렬
        // 방법 1. sort (비추천. 간단한 가설 검증)
//        model.addAttribute("words",
//                wordRepository.findAll().stream()
//                .sorted((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt()))
                        // 뒤집는 방법 1 : reverse 옵션이 어딘가에.. (권장 안함)
                        // -> for문을 할 때 뒤부터 세는 for문
                        // 뒤집는 방법 2 : 파라미터 순서 바꿔 a, b -> b, a
//                .sorted((b, a) -> a.getCreatedAt().compareTo(b.getCreatedAt()))
                        // 뒤집는 방법 3 : - 를 붙인다 (boolean이면 not을 하고)
//                .sorted((a, b) -> -a.getCreatedAt().compareTo(b.getCreatedAt()))
//                        .toList()); // 오름차순 -> 데이터가 등장하는 방향과 데이터가 커지는 방향이 같을 때

        // 방법 2. 쿼리 같은걸 만들어줘야하는데.. 기준을 createdAt으로 잡아야겠네?
        model.addAttribute("words", wordRepository.findAllByOrderByCreatedAtDesc());

        // 이미 정의된 폼을 쓰려면 model을 통해 전달해야 함
        model.addAttribute("wordForm", new WordForm());
        return "index";
    }

    @PostMapping("/word")
    public String addWord(WordForm wordForm, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "끝말잇기 추가");
        Word word = new Word();
        word.setText(wordForm.getWord());
        wordRepository.save(word);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteWord(@RequestParam("id") String uuid, RedirectAttributes redirectAttributes) {
        wordRepository.deleteById(uuid);
        redirectAttributes.addFlashAttribute("message", "정상적으로 삭제되었습니다. %s".formatted(uuid));
        return "redirect:/";
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
