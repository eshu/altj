package altj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Аннотация @Controller говорит Spring'у о том, что в давнном классе
 * будут описаны контроллеры. Экземпляр класса создастся автоматически
 * при запуске.
 */
@Controller
public class PwgenController {
    /*
     * В данной точке после создания класса PwgenController
     * магией внедрения зависимостей переменной будет присвоено значение.  
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("pwgen")
    public String pwgen(
            @RequestParam(value = "password", required = false, defaultValue = "adminpssd") String password,
            Model model) {
        model.addAttribute("password", password);
        model.addAttribute("encodedPassword", passwordEncoder.encode(password));
        return "pwgen";
    }

}
