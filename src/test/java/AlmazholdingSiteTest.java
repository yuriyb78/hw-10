
import data.JewelryItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class AlmazholdingSiteTest {

    @BeforeEach
    void setUp() {
        open("https://almazholding.ru/");

    }

    @ValueSource(strings = {"Кольца", "Серьги", "Подвески"})
    @ParameterizedTest(name = "Проверка наличия карточек изделий на странице {0}")
    @Tag("Regress")
    void pageShouldHaveProductInfo(String menuElement) {
        $$(".menu__list li").find(text(menuElement)).click();
        $$(".item").shouldBe(sizeGreaterThan(0));
    }

    @CsvFileSource(resources = "/test-data/menu_elements.csv")
    @ParameterizedTest(name = "Проверка наличия в главном меню {0} пункта меню {1}")
    @Tag("Regress")
    void menuShouldHaveMenuElement(String menuElement, String elementName) {
        $$(".menu__list li").find(text(menuElement)).hover();
        $$(".menu__list li").find(text(elementName)).shouldHave(visible);

    }

    static Stream<Arguments> menuElementsForItems() {
        return Stream.of(
                Arguments.of(
                        new JewelryItem("Кольца"),
                        List.of("Классические","Обручальные")
                )
        );
    }

    @MethodSource("menuElementsForItems")
    @ParameterizedTest(name = "Проверка наличия в меню типа изделия {1}")
    void menuShouldHaveMenuElementTypeItems(JewelryItem jewelryItem, List<String> elementMenuType) {
        String oneElementMenuType;
        $$(".menu__list li").find(text(jewelryItem.jewelryItemType)).hover();
        for (int i = 0; i < elementMenuType.size(); i++) {
            oneElementMenuType = elementMenuType.get(i);
            $(".menu__list>li:nth-child(2)>.menu__holder:nth-child(2)")
                    .shouldHave(text(oneElementMenuType));
        }
    }
}

