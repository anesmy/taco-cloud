package tacos.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tacos.data.IngredientRepository;
import tacos.models.Ingredient;
import tacos.models.Ingredient.Type;
import tacos.models.Taco;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    private final RestTemplate rest = new RestTemplate();

    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject("http://localhost:8080/ingredients/{id}",
                Ingredient.class, ingredientId);
    }

    public void updateIngredient(Ingredient ingredient) {
        rest.put("http://localhost:8080/ingredients/{id}",
                ingredient, ingredient.getId());
    }

    public void deleteIngredient(Ingredient ingredient) {
        rest.delete("http://localhost:8080/ingredients/{id}",
                ingredient.getId());
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return rest.postForObject("http://localhost:8080/ingredients",
                ingredient, Ingredient.class);
    }

    @Autowired
    public DesignTacoController(
            IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = (List<Ingredient>)ingredientRepo.findAll();

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    @GetMapping
    public String showDesignForm(Model model) {
        model.addAttribute("taco", new Taco());
        return "design";
    }

    @PostMapping
    public String processTaco(RedirectAttributes ra, @Valid @ModelAttribute("taco") Taco taco, Errors errors) {
        if (errors.hasErrors())
            return "design";

        // Save the taco...
        // We'll do this in chapter 3

        log.info("Processing taco: " + taco);
        ra.addFlashAttribute("taco", taco);
        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {

        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
