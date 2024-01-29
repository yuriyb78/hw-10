import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class AlmazholdingSiteTest {

    @BeforeEach
    void setUp () {
        open("https://almazholding.ru/");
        Configuration.browserSize = "1920x1080";
//        Configuration.pageLoadStrategy = "eager";
    }

    @ValueSource(strings = {"Кольца", "Серьги", "Подвески"})
    @ParameterizedTest(name = "Проверка наличия карточек изделий на странице {0}")
    @Tag("Regress")
    void pageShouldHaveProductInfo (String menuElement) {
        $$(".menu__list li").find(text(menuElement)).click();
        $$(".item").shouldBe(sizeGreaterThan(0));
    }

    @CsvFileSource(resources = "/test-data/menu_elements.csv")
    @ParameterizedTest(name = "Проверка наличия в главном меню {0} пункта меню {1}")
    @Tag("Regress")
    void menuShouldHaveMenuElement (String menuElement, String elementName) {
        $$(".menu__list li").find(text(menuElement)).hover();
        $$(".menu__list li").find(text(elementName)).shouldHave(visible);

    }
}


