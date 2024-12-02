package hello.itemservie.web.basic;


import hello.itemservie.domain.item.Item;
import hello.itemservie.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicProductController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String products(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable(name = "itemId") Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    // 상품 등록 폼
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // @PostMapping("/add")
    // 변수명은 addForm의 itemName과 같게 해야 됨.
    public String addItemV1(@RequestParam(name = "itemName") String itemName,
                       @RequestParam(name = "price") int price,
                       @RequestParam(name = "quantity") Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        model.addAttribute("item", item);
        return "basic/item";
    }

    // @PostMapping("/add")
    // 변수명은 addForm의 itemName과 같게 해야 됨.
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
        // 생략가능 model.addAttribute("item", item); //여기서 "item"이 @ModelAttribute("item") 임.
        return "basic/item";
    }

    //@PostMapping("/add")
    //@ModelAttribute의 name("")속성을 제거하면 Item클래스의 I가 i로 바뀌고 item으로 알아서 저장함.
    public String addItemV3(@ModelAttribute Item item, Model model){
        itemRepository.save(item);
        return "basic/item";
    }

    // 상품 저장
    //@PostMapping("/add")
    public String addItemV4(Item item, Model model){
        itemRepository.save(item);
        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV5(@ModelAttribute(name = "item") Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    // 상품 수정 폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable(name = "itemId") Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    // 상품 수정 처리
    // 리다이렉트
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable(name = "itemId") Long itemId, @ModelAttribute("item") Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /*
    테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
